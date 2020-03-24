package com.winsafe.sap.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.ErrorBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.pojo.UploadSAPLog;

/**
 * 无单一级经销商出库
* @Title: DealNoBillStockAlterMove.java
* @version:
 */
public class DealSapCodeUpload {
	
	
	private static Logger logger = Logger.getLogger(DealSapCodeUpload.class);
	
	private File file;
	private UploadSAPLog sapUploadLog;
	private String organId; 
	private boolean flag =true;
//	private StringBuffer codes;
	private Map<String, Integer> codelineMap ;
//	private Set<String> codeSet ;

	private int iuid;
	private String filepath;
	private String fileaddress;
	private String failAddress;
	
	// dao层
	private AppSapUploadLog appiu = new AppSapUploadLog();
	
	private int totalNum = 0;
	private int failNum = 0;

	

	
	/**
	 * 存放错误信息
	 */
	private Map<Integer,ErrorBean> errorMap = new TreeMap<Integer, ErrorBean>();
	

	
	public DealSapCodeUpload(){}

	public DealSapCodeUpload(File file,UploadSAPLog sapUploadLog)
	{
		this.file = file;
		this.filepath = file.getPath();
		this.fileaddress = file.getName();
		this.failAddress = fileaddress.substring(0, fileaddress.lastIndexOf(".")) + "_fail.txt";
		this.sapUploadLog = sapUploadLog;
		
		this.organId = fileaddress.split("_")[0];
		this.iuid = sapUploadLog.getId();
	}

	public StringBuffer deal() throws Exception {
		try {
			//处理上传的文件
			dealTxtFileAndAddToTempTable();
			AppPrepareCode appp= new AppPrepareCode();
			if(!flag){
				return generateErrorInfo(errorMap);
			}else{
				//判断条码是否已存在
//				String wheresql  = " where code in ("+codes.substring(1)+")" ;
				List<Map<String,String>> repeatCodes= appp.getDuplicatedPrepareCode();
				for(Map<String,String> prepareCode : repeatCodes){
					flag = false;
					addErrorInfo(codelineMap.get(prepareCode.get("code")),prepareCode.get("code")+"]["+UploadErrorMsg.getError(UploadErrorMsg.E09003, prepareCode.get("code")));

				}
				if(flag){
					//执行插入
					appp.addPrepareCode(sapUploadLog.getId(), 0, organId, DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()), sapUploadLog.getMakeId());
				}
				return generateErrorInfo(errorMap);
			}
		} catch (Exception e) {
			appiu.updNum(iuid, 2, 0, totalNum, failAddress,5);
			HibernateUtil.commitTransaction();
			throw e;
		}
	}
	/**
	 * 处理上传文件
	* @return
	 * @throws IOException 
	 */
	private void dealTxtFileAndAddToTempTable() throws Exception {
		List<String> codeList = new ArrayList<>();
		Properties p = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		String forecode  = p.getProperty("forecode."+organId);
		// 条码文件
		BufferedReader br = null;
		Set<String> codeSet = new HashSet<String>();
		codelineMap = new HashMap<String,Integer>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String uploadidcode;
			int lineNo = 1;
			// 通过循环得到该TXT文档的所有内容
			while ((uploadidcode = br.readLine()) != null) {
				//首先去掉采集器上传的条码的bom
				//uploadidcode = dealuploadIdcode(uploadidcode);
				// 获取无单上传对像
				 if(uploadidcode.length()!=20){
					flag = false;
					addErrorInfo(lineNo,uploadidcode+"]["+UploadErrorMsg.getError(UploadErrorMsg.E09001, uploadidcode));
				}else if(StringUtil.isEmpty(forecode)){
					flag = false;
					addErrorInfo(lineNo,uploadidcode+"]["+UploadErrorMsg.getError(UploadErrorMsg.E09004, uploadidcode));
				}else if(!checkincode(uploadidcode,forecode)){
					flag = false;
					addErrorInfo(lineNo,uploadidcode+"]["+UploadErrorMsg.getError(UploadErrorMsg.E09005, uploadidcode));
				}
//				else if(!uploadidcode.startsWith(forecode)){
//					flag = false;
//					addErrorInfo(lineNo,uploadidcode+"]["+UploadErrorMsg.getError(UploadErrorMsg.E09005, uploadidcode));
//				}
				else if(codeSet.add(uploadidcode)){
					codelineMap.put(uploadidcode, lineNo);
					codeList.add(getAddToTempSql(uploadidcode));
				}else{
					flag = false;
					addErrorInfo(lineNo,uploadidcode+"]["+UploadErrorMsg.getError(UploadErrorMsg.E00106, uploadidcode));
				}			
				lineNo++;
				totalNum++;
				if(codeList.size() == Constants.DB_BULK_SIZE){
					EntityManager.executeBatch(codeList);
					codeList.clear();
				}
			}
			if(codeList.size() > 0) {
				EntityManager.executeBatch(codeList);
			}
		}catch (Exception e) {
			logger.error("",e);
			throw e;
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					logger.error("",e);
				}
			}
		}
	}

	private String getAddToTempSql(String uploadidcode) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into transaction_temp_preapare_code values ('").append(uploadidcode).append("')");
		return sb.toString();
	}

	private boolean checkincode(String uploadidcode, String forecode) {
		String[] fs= forecode.split(",");
		if(fs.length>0){
			for(int i=0;i<fs.length;i++){
				if(uploadidcode.startsWith(fs[i])){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 封装错误信息
	* @param bean
	* @param idcode
	* @param msg
	 */
	private void addErrorInfo(Integer lineNo,String msg) {
		ErrorBean eb= new ErrorBean();
		eb.setInfo(msg);
		errorMap.put(lineNo, eb);
	}
	

	/**
	 * 封装错误信息
	* @param errorMap
	 */
	private StringBuffer generateErrorInfo(Map<Integer, ErrorBean> errorMap) {
		StringBuffer errorList = new StringBuffer();
		try {
			if(!errorMap.isEmpty()){
				for(Map.Entry<Integer, ErrorBean> entry : errorMap.entrySet()){
					failNum++;
					
					String str = "第" + entry.getKey() +"行";
					ErrorBean bean = entry.getValue();
					if(bean.getIdcode()!=null){
						str += "  "+ bean.getIdcode()+"  ";
					}
					str+="[" + bean.getInfo()+"]";
					//写入文件
//					out.write(str);
//					out.write("\r\n");
					str +="\r\n";
					errorList.append(entry.getKey()).append(str);

					//errorList.add(str);
				}
			}else{
				errorList = errorList.append("成功导入").append(totalNum).append("条数据。");
			}

		} catch (Exception e) {
			logger.error("", e);
		} 
		//errorList.add("成功 "+(totalNum-failNum)+" 条,失败"+failNum+"条");
		return errorList;
	}
	
	/**
	 * 更新条码上传日志 成功失败数量
	* @author    
	* @CreateTime Jan 11, 2013 9:58:50 AM
	 */
	private void updLogNum() throws Exception{
	   appiu.updNum(iuid, 2, totalNum-failNum, failNum, failAddress,3);
	}

	/**
	 * 判断并去掉采集器上传的条码的bom
	 */
	private String dealuploadIdcode(String uploadIdcode) {
		byte[] bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		byte[] buffBytes;
		try {
			buffBytes = uploadIdcode.getBytes("utf-8");
			if (buffBytes[0] == bom[0] && buffBytes[1] == bom[1] && buffBytes[2] == bom[2]) {
				uploadIdcode = new String(buffBytes, 3, buffBytes.length - 3, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return uploadIdcode;
	}
	

}
