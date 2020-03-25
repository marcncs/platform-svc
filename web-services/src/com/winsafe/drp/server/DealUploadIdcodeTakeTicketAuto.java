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
import java.util.List;
import java.util.Map;


import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppCodeUnit;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppScannerWarehouse;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.ICode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UploadProduceReport;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

/**
 * 
 * @Title: DealUploadIdcodeTakeTicketAuto.java
 * @Description: 处理转仓无单上传条码
 * @author: wenping
 * @CreateTime: Apr 16, 2012 4:22:22 PM
 */
@SuppressWarnings("unchecked")
public class DealUploadIdcodeTakeTicketAuto {

	protected CodeRuleService crs = new CodeRuleService();
	protected AppIdcodeUpload appiu = new AppIdcodeUpload();
	protected AppCodeUnit appcu = new AppCodeUnit();
	protected AppICode appicode = new AppICode();
	protected AppIdcode appidcode = new AppIdcode();
	protected AppProduct ap = new AppProduct();
	protected AppIdcode ai = new AppIdcode();
	protected AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
	protected AppTakeTicket att = new AppTakeTicket();
	private AppFUnit appfu=new AppFUnit();
	private AppBaseResource abr = new AppBaseResource();
	private String filepath;
	private String fileaddress;
	private int iuid;

	protected int valinum = 0;
	protected int failnum = 0;
	private String failAddress="";
	
	private double packquantity;
	private double uploadquantity;
	private int unitid;
	private UploadProduceReport upr ;
	private Idcode ic;
	private AppUploadProduceReport aupr =new AppUploadProduceReport();
	
	private String fromwarehouseid="";
	private String organfromid="";
	private String warehousetoid ="";
	private String organtoid="";
	private String lcode;
	private String productid;
	private String idcode;
	private List<TakeTicketIdcode> ttis = new ArrayList();
	private List<String> existidcodes = new ArrayList();
	private String  productDate="";
	private String batch="";
	private String  startNo="";
	private String endNo="";
	private String idcodeInTorr;
	//条形码是否被拆分而来
	private int issplit = 0;
	private BaseResource bar ;

  public DealUploadIdcodeTakeTicketAuto(String filepath, String fileaddress, int iuid) {
		this.filepath = filepath;
		this.fileaddress = fileaddress;
		this.iuid = iuid;
	}

	public String run() {
		String  MSG = "";
		try {
			// 读取采集器上传的TXT文档
			MSG = readTxt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MSG;
	}

	/**
	 * 读取采集器上传的TXT文档
	 * 
	 */
	public String readTxt() {
		// 条码文件
		File ff = new File(filepath + fileaddress);
		if (!ff.exists()) {
		}
		BufferedReader br = null;
		String fileName = ff.getName();
		String MSG= "";
		try {
			// 判断是否是TXT(txt)类型文件
			if (fileName.toLowerCase().lastIndexOf(".txt") == -1) {
				return null;
			}
			br = new BufferedReader(new InputStreamReader(new FileInputStream(ff), "utf-8"));
			String uploadidcode;
			updIsDeal();
			AppWarehouse aw = new AppWarehouse();
			AppOrgan ao = new AppOrgan();
			Organ ogfrom = null;
			Organ ogto = null;
			
			// 通过循环得到该TXT文档的所有内容,放入ttis中
			while ((uploadidcode = br.readLine()) != null) {
				//首先去掉采集器上传的条码的bom
				uploadidcode = dealuploadIdcode(uploadidcode);
				boolean flag = false;
				// 取得扫描类型
				String scanSign = crs.getScanType(uploadidcode);				
				//判断条码是否被删除
				if(scanSign.trim().equals("D")){
					flag = true;
					continue;
				}
				//判断是否为转仓
				if(!scanSign.trim().equals("9")){
					writeTxt(uploadidcode + "[扫描类型错误]");
					flag = true;
					continue;
				}
				// 取得条形码编号
				  idcode = crs.getIdcode(uploadidcode);
				// 判断TXT中是否有重复条码
				for (String existidcode : existidcodes) {
					if (idcode.equals(existidcode)) {
						writeTxt(uploadidcode + "[该条码重复扫描]");
						flag = true;
						continue;
					} else {
					}
				}
				if (flag) {
				} else {
					// 取得采集器编号
					String scannerid = crs.getScannerNo(uploadidcode);
					
					try {
						// 得到调出仓库编号
						fromwarehouseid = new AppScannerWarehouse().getScannersByScannerid(scannerid).get(0).getWarehouseid();
						// 调出机构对象
						ogfrom = ao.getOrganByID(aw.getWarehouseByID(fromwarehouseid).getMakeorganid());
						// 调出机构编号
						organfromid = ogfrom.getId();
					} catch (Exception e) {
						writeTxt(uploadidcode + "[无对应的调出仓库]");
						continue;
					}
					// 调入仓库编号
					 warehousetoid = crs.getWarehouseId(uploadidcode);
					 organtoid="";
					try {
						// 调入机构对象
						ogto =ao.getOrganByID(aw.getWarehouseByID(warehousetoid).getMakeorganid());
						// 调入机构编号
						organtoid = ogto.getId();
					}catch(Exception e){
						writeTxt(uploadidcode + "[无对应的调入仓库]");
						continue;
					}	
					
					lcode =crs.getProductId(uploadidcode);
					// 获取产品编号
					ICode icode = appicode.getICodeByLcode(lcode);
					if(null != icode){
						productid = icode.getProductid();
					}
					
					uploadquantity = Double.valueOf(crs.getProductCount(uploadidcode));
					
					//条码类型
					String f=idcode.substring(0,1);
					if("1".equals(f) || "3".equals(f)){//国内散装
						
						//设置单位
						FUnit aFUnit=appfu.getFUnitExceptFUnitid(productid, "2,18");//除箱和千克
						unitid = aFUnit.getFunitid();
						
						//查找Idcode表
						ic = ai.getIdcodeById(idcode);
						if(ic==null){
							//查找生产记录表
							upr =aupr.getUploadProduceReportByUnitNo(idcode);
							if(upr==null){
								writeTxt(idcode + "[系统无该条码]");
								continue;
							}else{
								//得到所在箱条码
								String xidcode = upr.getBoxCode();
								
								ic= ai.getIdcodeById(xidcode);
								//所在箱条码不存在时
								if(ic==null){
									if(getBaseResourceFlag(2)==1){
										ic = addToIdcode(xidcode);
										issplit= 1;
									}else{
										writeTxt(idcode + "[所在箱码不存在]");
										continue;
									}
									
								}else{
									//验证条码
									String info = validateIdcodeAvaliable(ic);
									if(info!=null){
										continue;
									}
									//验证箱条码的数量
									if(ic.getPackquantity()-appfu.getXQuantity(productid, unitid)<0){
										writeTxt(idcode + "[所在箱码数量小于零]");
										continue;
									}
									issplit= 1;
									
									//修改Idcode 拆箱
//									ic.setFquantity(ic.getFquantity()-appfu.getXQuantity(productid, unitid));
									ic.setPackquantity(ic.getPackquantity()-appfu.getXQuantity(productid, unitid));
//									ic.setIsuse(0);//设置不可用
									ai.updIdcode(ic);
								}
							}
					}else{
						//验证条码
						String info = validateIdcodeAvaliable(ic);
						if(info!=null){
							continue;
						}
						
						if(getBaseResourceFlag(3)==1){//更新条码状态
							ic.setIsuse(0);
							ai.updIdcode(ic);
						}
					}
						
						productDate = ic.getProducedate();
						packquantity = appfu.getXQuantity(productid, unitid);
						batch = ic.getBatch();
						startNo=idcode;
						endNo = idcode;
						
						saveTakeTicketIdcode();
						
					}else if("C".equals(f)&&uploadquantity==1){//国内箱
						
						ic= ai.getIdcodeById(idcode);
						unitid =2;
						packquantity = appfu.getXQuantity(productid, unitid);
						startNo=idcode;
						endNo = idcode;
						
						if(ic==null){//如果条码在系统中不存在
							
							if(getBaseResourceFlag(2)==1){//如果允许上传不存在系统中的条码(由系统变量值确定)
								saveTakeTicketIdcode();
							}else{
								writeTxt(idcode + "[该条码不存在]");
								continue;
							}
						}
						else{
							//验证条码
							String info = validateIdcodeAvaliable(ic);
							if(info!=null){
								continue;
							}
							if(getBaseResourceFlag(3)==1){//更新条码状态
								ic.setIsuse(0);
								ai.updIdcode(ic);
							}
							productDate= ic.getProducedate();
							batch = ic.getBatch();
							
							saveTakeTicketIdcode();
							
						}
						
					}else if("C".equals(f)&&uploadquantity>1){//国内托///
					
						unitid =17;//单位 ：托
						packquantity = appfu.getXQuantity(productid, 2) * uploadquantity;
						
						//托条码中的箱数量
						int n =Integer.parseInt(crs.getProductCount(uploadidcode));
						//托中最后一条条码的后几位顺序码
						int idocdenum = Integer.parseInt(idcode.substring(4,13));
						//条码前四位
						String idcodeprefix = idcode.substring(0,4);
						boolean iflag = false;
//						List<String> idcodes = new ArrayList();
						List<Idcode> idcodes = new ArrayList<Idcode>();
						//循环托中的箱条码 存入TTIdcode
						for(int k =n; k>0;k--){
							idcodeInTorr =idcodeprefix +String.format("%09d", idocdenum) ;
							idocdenum--;
							
							ic= ai.getIdcodeById(idcodeInTorr);
							if(ic==null){
								writeTxt(idcode + "[该托条码有不存在的箱条码]");
								iflag = true;
								break;
							}else{
								//验证条码
								String info = validateIdcodeAvaliable(ic);
								if(info!=null){
									iflag = true;
									break;
								}
								idcodes.add(ic);
//								else{
//									idcodes.add(idcode);
//								}
							}
						}
						
						if(iflag){
							continue;
						}else{
//							for(String  i: idcodes){
//								idcode=i;
								if(getBaseResourceFlag(3)==1){//更新条码状态
								     for(Idcode i : idcodes){
								    	 i.setIsuse(0);
								    	 ai.updIdcode(i);
								     }
								}
								productDate= ai.getIdcodeById(idcode).getProducedate();	
								batch = ai.getIdcodeById(idcode).getBatch();
								startNo=idcodeInTorr;//托码中的第一条码
								endNo = idcode;
								saveTakeTicketIdcode();
//							}
						}
						
				   	}else if("2".equals(f)){//国外 不允许转仓
				   		writeTxt(idcode + "[该条码属于国外产品，不允许转仓]");
						continue;
					}
				}
			}
			br.close();
			Map<String, List<TakeTicketIdcode>> ttisbyorgan = new HashMap();
			ttisbyorgan = DealIdcodeByOrgan(ttis);

			DoDealTakeTicketIdcodeAuto dodeal = new DoDealTakeTicketIdcodeAuto(this.filepath, this.fileaddress,this.failAddress,ttisbyorgan,
					String.valueOf(iuid), failnum, valinum);
			MSG = dodeal.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return MSG;
	}

	private void saveTakeTicketIdcode() throws Exception {
		TakeTicketIdcode tti = new TakeTicketIdcode();
		// 填充TakeTicketIdcode
		tti.setProductid(productid);
		tti.setIsidcode(1);
		tti.setWarehousebit("000");
		tti.setBatch(batch);
		tti.setUnitid(unitid);
		tti.setQuantity(1.0);
		tti.setPackquantity(packquantity);
		tti.setLcode(lcode);
		tti.setStartno(startNo);
		tti.setEndno(endNo);
		tti.setIdcode(idcode);
		tti.setIssplit(issplit);
		tti.setMakedate(new Date());
		tti.setOrganfromid(organfromid);
		tti.setWarehousefromid(fromwarehouseid);
		tti.setOrgantoid(organtoid);
		tti.setWarehousetoid(warehousetoid);
		tti.setProducedate(productDate);
		
		ttis.add(tti);
		existidcodes.add(idcode);
		valinum++;
	}
	
	/**
	 * 保存条码到Idcode表
	 */
	private Idcode addToIdcode(String idcode ) throws Exception{
		Idcode ic = new Idcode();
		ic.setIdcode(idcode);
		ic.setProductid(productid);
		ic.setProductname(ap.getProductNameByID(ic.getProductid()));
		ic.setBatch("");
		ic.setProducedate("");
		ic.setVad("");
		ic.setLcode(lcode);
		ic.setStartno(startNo);
		ic.setEndno(endNo);
		ic.setUnitid(2);
		ic.setQuantity(1d);
		ic.setFquantity(appfu.getXQuantity(productid, 2)-appfu.getXQuantity(productid, unitid));
		ic.setPackquantity(appfu.getXQuantity(productid, 2)-appfu.getXQuantity(productid, unitid));
		ic.setIsuse(0);
		ic.setIsout(0);
		ic.setBillid("");
		ic.setIdbilltype(0);
		ic.setMakeorganid(organfromid);
		ic.setWarehouseid(fromwarehouseid);
		ic.setWarehousebit("000");
		ic.setProvideid("");
		ic.setProvidename("");
		ic.setMakedate(DateUtil.getCurrentDate());
		appidcode.addIdcode(ic);
		
		return ic;
	}
	
	/**
	 * 验证条码是否在当前仓库 是否可用 是否已出库
	* @param ic
	* @return
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jul 13, 2012 3:58:25 PM
	 */
	private String validateIdcodeAvaliable(Idcode ic ) throws Exception{
		
		if(!ic.getWarehouseid().equals(fromwarehouseid) && getBaseResourceFlag(1)==0){
			writeTxt(idcode + "[该条码不在当前仓库中]");
			return "该条码不在当前仓库中";
		}else if(ic.getPackquantity().doubleValue()!=ic.getFquantity().doubleValue()){
			writeTxt(idcode + "[该条码不可用(因为该箱已被拆散码出库)]");
			return "该条码不可用(因为该箱已被拆散码出库)";
		}else if(ic.getIsuse()==0){
			writeTxt(idcode + "[该条码不可用]");
			return "该条码不可用";
		}else if(ic.getIsout()==1){
			writeTxt(idcode + "[该条码已出库]");
			return "该条码已出库";
		}
		
		return null;
	}

	/**
	 * 验证条码是否有当前仓库的出库记录
	* @param idcode
	* @return
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jul 13, 2012 3:59:15 PM
	 */
	/*
	private String validateIsAreadyExist(String idcode ) throws Exception{
		TakeTicketIdcode existtti = atti.getTakeTicketIdcodeByIdcodeAndWid(idcode, fromwarehouseid);
		
		if(existtti!=null){
			writeTxt(idcode + "[该条码已出过库]");
			return "该条码已出过库";
		}
		
		return null;
	}
	
	*/
	private void updIsDeal() {
		try {
			HibernateUtil.currentSession(true);
			appiu.updIsDeal(iuid, 1);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * 判断该条码是否可以被上传
	 * 
	 * @param tti
	 * @return
	 * @throws Exception
	 */
	/*private boolean checkTTIlegal(TakeTicketIdcode tti) throws Exception {
		Product p = ap.getProductByID(tti.getProductid());
		if (null == p) {
			writeTxt(tti.getIdcode() + "[系统中无该条码对应的产品]");
			return false;
		}
		try {
			// 根据条码得到渠道相对应的条码
			Idcode idcode = appidcode.getIdcodeByIdAndWarehouse(tti.getIdcode(), tti.getWarehousefromid());
			// 如果条码不存在
			if (null == idcode) {
				writeTxt(tti.getIdcode() + "[该条码不在系统中]");
				return false;
			} else {
				TakeTicketIdcode existtti = atti.getTakeTicketIdcodeByIdcodeAndWid(tti.getIdcode(), tti.getWarehousefromid());
				if (null == existtti) {
					return true;
				} else {
					writeTxt(tti.getIdcode() + "[该条码不在当前仓库中]" + existtti.getTtid());
					return false;
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			writeTxt(tti.getIdcode() + "[处理异常]");
			return false;
		}
	}*/

	protected void writeTxt(String str) {
		failAddress = fileaddress.substring(0, fileaddress.lastIndexOf(".")) + "_fail.txt";
		failnum++;
		BufferedWriter out = null;
		String destFile = this.filepath + this.failAddress;
		try {
			File file = new File(destFile);
			if (!file.exists()) {
			}
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile, true)));
			out.write(str);
			out.write("\r\n");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 按照转入的机构将条码分类
	 */
	protected Map<String, List<TakeTicketIdcode>> DealIdcodeByOrgan(List<TakeTicketIdcode> ttis) {
		Map<String, List<TakeTicketIdcode>> result = new HashMap();
		for (TakeTicketIdcode tti : ttis) {
			String organto = tti.getOrgantoid();
			List<TakeTicketIdcode> ttisbyorgan = result.get(organto);
			if (null != ttisbyorgan) {
				ttisbyorgan.add(tti);
				result.remove(organto);
				result.put(organto, ttisbyorgan);
			} else {
				ttisbyorgan = new ArrayList<TakeTicketIdcode>();
				ttisbyorgan.add(tti);
				result.put(organto, ttisbyorgan);
			}
		}
		return result;
	}

	/**
	 * 判断并去掉采集器上传的条码的bom
	 */
	public String dealuploadIdcode(String uploadIdcode) {
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
	
	/**
	* @param key
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jul 23, 2012 4:00:04 PM
	 */
	private Integer getBaseResourceFlag(Integer key) throws Exception{
		
		bar=abr.getBaseResourceValue("UploadIdcodeFlag", key);
		Integer intFlag=bar.getIsuse();
		return intFlag;
	}
	
}

