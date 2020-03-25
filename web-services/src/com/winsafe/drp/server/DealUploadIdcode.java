package com.winsafe.drp.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.drp.dao.AppCodeUnit;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppIdcodeUploadLog;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.ErrorBean;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.IdcodeUpload;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.UploadIdcode;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.IdcodeUploadListener;

/**
 * @author jerry
 * @version 2009-8-17 下午04:36:33 www.winsafe.cn
 */
public abstract class DealUploadIdcode {

	private Logger logger = Logger.getLogger(DealUploadIdcode.class);

	protected CodeRuleService crs = new CodeRuleService();

	protected AppIdcodeUpload appiu = new AppIdcodeUpload();

	protected AppCodeUnit appcu = new AppCodeUnit();

	protected AppICode appicode = new AppICode();

	protected AppIdcode appidcode = new AppIdcode();

	protected AppProduct appPro = new AppProduct();

	protected AppTakeTicket apptt = new AppTakeTicket();

	protected AppTakeTicketDetail appttd = new AppTakeTicketDetail();

	protected AppTakeTicketIdcode apptti = new AppTakeTicketIdcode();
	
	protected AppIdcodeUploadLog appIdcodeUploadLog = new AppIdcodeUploadLog();

	private String filepath;

	private String fileaddress;

	private int iuid;

	protected int valinum;

	protected int failnum;

	private String failAddress;
	
	private UploadIdcode uploadIdcode; 

	// 保存产品信息的Map
	protected Map<String, Product> lcodeProductMap = new HashMap<String, Product>();
	// 保存条码信息
	protected Map<String, Idcode> idcodeMap = new HashMap<String, Idcode>();
	// 保存tt单信息
	protected Map<String, TakeTicket> takeTicketMap = new HashMap<String, TakeTicket>();
	// 保存ttd详细信息
	protected Map<String, List<TakeTicketDetail>> takeTickeDetailtMap = new HashMap<String, List<TakeTicketDetail>>();
	// 保存tti详细信息
	protected Set<String> takeTicketIdcodeSet = new HashSet<String>();
	// 保存产品信息
	protected Map<String, Product> productMap = new HashMap<String, Product>();
	// 存放错误信息
	protected List<ErrorBean> errorList = new ArrayList<ErrorBean>();

	protected Date fileLastModifiedDate;

	public DealUploadIdcode() {
	}

	public DealUploadIdcode(String filepath, String fileaddress, int iuid) {
		this.filepath = filepath;
		this.fileaddress = fileaddress;
		this.iuid = iuid;
		failAddress = fileaddress.substring(0, fileaddress.lastIndexOf("."))
				+ "_fail.txt";
	}

	public ArrayList<String> run() {
		ArrayList<String> t_list = null;
		try {
			valinum = 0;
			failnum = 0;
			// 读条码
			t_list = readTxt();
			t_list.add("成功：" + valinum + " 条；失败：" + failnum + " 条");
		} catch (Exception e) {
			logger.error("", e);
		}
		return t_list;
	}

	/**
	 * 读TXT文件,读条码并分析存入数据库
	 * @throws Exception 
	 */
	public ArrayList<String> readTxt() throws Exception {
		// 条码文件
		File ff = new File(filepath + fileaddress);
		fileLastModifiedDate = new Date(ff.lastModified());
		BufferedReader br = null;
		ArrayList<String> failMsgList = new ArrayList<String>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					ff), "utf-8"));
			// 条码处理前
			dealBefore(br);
			
			String uploadidcode;
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					ff), "utf-8"));
			while ((uploadidcode = br.readLine()) != null) {
				//判断并去掉采集器上传的条码的bom
				uploadidcode = dealuploadIdcode(uploadidcode);
				valinum += 1;
				String failMsg = addIdcode(uploadidcode);
				if (!StringUtil.isEmpty(failMsg)) {
					failMsgList.add(failMsg);
				}
			}
			// 条码处理完后
			dealAfter();
			//将错误信息写入数据库中
			addfailMsgToDB(errorList);
			//将错误信息写入fail文件中
			writeFailMsg(failMsgList);
			// 设置IdcodeUpload处理结果（包括是否处理、正确处理条数、处理失败条数、失败信息文件路径）
			updNum();
			//更行tt单文件处理状态
			for(TakeTicket tt : takeTicketMap.values()) {
				tt.setIsChecked(0);
				updTicketStatus(tt);
			}
		} catch (Exception e) {
			appiu.updNum(iuid, -1, 0, 0, failAddress);
			logger.error("", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		return failMsgList;

	}

	/**
	 * 判断并去掉采集器上传的条码的bom Create Time: Oct 11, 2011 3:49:15 PM
	 * 
	 * @param uploadIdcode
	 * @return
	 * @author dufazuo
	 */
	public String dealuploadIdcode(String uploadIdcode) {
		byte[] bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		byte[] buffBytes;
		try {
			buffBytes = uploadIdcode.getBytes("utf-8");
			if (buffBytes[0] == bom[0] && buffBytes[1] == bom[1]
					&& buffBytes[2] == bom[2]) {
				uploadIdcode = new String(buffBytes, 3, buffBytes.length - 3,
						"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		return uploadIdcode;
	}

	private void updNum() throws Exception {
		appiu.updNum(iuid, 2, valinum, failnum, failAddress);
	}

	/**
	 * 处理每行条码的父接口 Create Time 2014-4-18 上午11:11:04
	 * 
	 * @param uploadidcode
	 * @return
	 * @author lipeng
	 */
	public abstract String addIdcode(String uploadidcode);

	/**
	 * 处理完所有条码的操作 Create Time 2014-4-18 上午11:09:22
	 * 
	 * @return
	 * @author lipeng
	 */
	protected void dealAfter() throws Exception {
	}

	/**
	 * 将错误信息写入txt
	 * 
	 * @param str
	 */
	protected void writeTxt(String str) {
		failnum += 1;
		valinum -= 1;
		BufferedWriter out = null;
		String destFile = filepath + failAddress;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destFile, true)));
			out.write(str);
			out.write("\r\n");
			out.close();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}
	/**
	 * 写错误信息
	 */
	private void writeFailMsg(List<String> failMsgList) {
		BufferedWriter out = null;
		String destFile = filepath + failAddress;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destFile),"utf-8"));
			int i = 1; 
			for(String failMsg : failMsgList){
				failnum += 1;
				valinum -= 1;
				out.write("第" + (i++) + "行  " + failMsg);
				out.write("\r\n");
			}
			out.close();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}
	
	/**
	 * 封装错误信息
	 */
	protected void addErrorInfo(String idcode,String msg) {
		ErrorBean eb= new ErrorBean();
		eb.setInfo(msg);
		eb.setErrCode(msg.split(":")[0]);
		eb.setIdcode(idcode);
		errorList.add(eb);
	}

	private void addfailMsgToDB(List<ErrorBean> errorBeans) throws Exception {
		IdcodeUpload idcodeUpload = IdcodeUploadListener.getIdcodeUpload(iuid);
		if(idcodeUpload != null) {
			appIdcodeUploadLog.addIdcodeUploadLogs(errorBeans ,idcodeUpload, uploadIdcode.getOutWarehouseId(), uploadIdcode.getBillNo());
		}
	}

	/**
	 **初始化设置
	 */
	protected void dealBefore(BufferedReader br) throws Exception {
		
		
	}
	/**
	 * 获取单据
	 * @param ttid
	 * @return
	 * @throws Exception
	 */
	protected TakeTicket getTakeTicket(String ttid) throws Exception{
		TakeTicket tt = takeTicketMap.get(ttid);
		if(tt == null){
			tt =  apptt.getTakeTicketById(ttid);
			takeTicketMap.put(ttid, tt);
			if(tt != null) {
				tt.setIsChecked(1);
				updTicketStatus(tt);
			}
		}
		return tt;
	}
	
	/**
	 * 获取条码
	 * @param idcode
	 * @return
	 * @throws Exception
	 */
	protected Idcode getIdcode(String idcode) throws Exception{
		Idcode ic = idcodeMap.get(idcode);
		if(ic == null){
			ic = appidcode.getIdcodeByCode(idcode);
			idcodeMap.put(idcode, ic);
		}
		return ic;
	}
	/**
	 * 获取产品
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	protected Product getProduct(String pid) throws Exception{
		Product p = productMap.get(pid);
		if(p == null){
//			p = appPro.getByMCode(pid);
			p = appPro.getProductByID(pid);
			productMap.put(pid, p);
		}
		return p;
	}

	public void setUploadIdcode(UploadIdcode uploadIdcode) {
		this.uploadIdcode = uploadIdcode;
	}
	
	private void updTicketStatus(TakeTicket tt) {
		try {
			apptt.updTakeTicket(tt);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("更新TT单文件处理标志时发生异常：", e);
		}
	}
	
}
