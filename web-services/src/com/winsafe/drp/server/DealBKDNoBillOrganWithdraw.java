package com.winsafe.drp.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.dao.OrganWithdrawIdcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UploadIdcodeBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.ErrorType; 
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

/**
 * 无单退货
* @Title: DealNoBillOrganWithdraw.java
* @version:
 */
public class DealBKDNoBillOrganWithdraw extends DealKeyRetailerNoBillUploadService{
	
	private static Logger logger = Logger.getLogger(DealBKDNoBillOrganWithdraw.class);
	private List<Idcode> idcodeList = new ArrayList<Idcode>();
	public DealBKDNoBillOrganWithdraw(){}

	public DealBKDNoBillOrganWithdraw(String filepath, String fileaddress, int iuid,String username)
	{
		this.filepath = filepath;
		this.fileaddress = fileaddress;
		this.iuid = iuid;
		this.failAddress = fileaddress.substring(0, fileaddress.lastIndexOf(".")) + "_fail.txt";
		this.username = username;
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<String> deal() throws Exception {
		try {
			//获取用户信息
//			getUserInfo(username);
			
			List<UploadIdcodeBean> idcodeBeanList = new ArrayList<UploadIdcodeBean>();
			//处理上传的文件
			dealTxtFile(idcodeBeanList,Constants.SCAN_TYPE_OW_NOBILL_BKR);
			//缓存相关数据
			cacheData();
			//对上传后的产品进行分类整理
//			Map<String,Map<String,DetailBean>> productDetailMap = getProductDetail(idcodeBeanList);
			//对上传后的所有条码进行分类整理
			Map<String,List<UploadIdcodeBean>> idcodeMap = sortUploadIdcodeBean(idcodeBeanList);
			//处理分类后的map
			dealResultMap(idcodeMap);
			//封装错误信息
			ArrayList<String> errorMsgList = generateErrorInfo(errorList);
			//更新条码上传日志
			updLogNum();
			return errorMsgList;
		} catch (Exception e) {
			generateErrorInfo(errorList);
			appiu.updNum(iuid, -1, 0, totalNum, failAddress);
			HibernateUtil.commitTransaction();
			throw e;
		}
	}
	
	/**
	 * 缓存数据
	 */
	private void cacheData() throws Exception{
		//获取出库机构
		bulkQueryOrgan(orgtanIds);
		bulkQueryByInWarehouse(orgtanIds);
		bulkQueryIdcode(idcodes);
		bulkQueryProduct(productIds);
		users = au.getUsersBeanByUserId(userId);
	}
	/**
	 * 处理分类后的Map 
	* @param resultMap
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 10, 2013 10:18:18 AM
	 */
	@SuppressWarnings("unchecked")
	private void dealResultMap(Map<String,List<UploadIdcodeBean>> idcodeBeanMap) throws Exception {
		
		Set<String> idcodeSet = new HashSet<String>();
		for(String out_in_whid : idcodeBeanMap.keySet()){
			
			//得到出入库仓库id的key
			String[] arr = out_in_whid.split("_");
			//出库仓库
			String fromOrganId = arr[0];
			sfromorganid = arr[0];
			//入库仓库
			String toOrganId = arr[1];
			stoorganid = arr[1];
			// 获取产品数量
//			Map<String,DetailBean> productCountMap = productDetailMap.get(out_in_whid);
			// 存放实际的产品数量
			Map<String, Double> idcodeCountMap = new HashMap<String, Double>();
			
			
			try {
				//验证出库仓库
				boolean isOutWarehouseExist = validateWarehouse(fromOrganId);
				//验证出库机构
				boolean isOutOrganExist = validateOrgan(fromOrganId);
				//验证入库仓库
				boolean isInWarehouseExist = validateWarehouse(toOrganId);
				//验证入库机构
				boolean isInOrganExist = validateOrgan(toOrganId);
				
				if(isOutWarehouseExist && isOutOrganExist && isInWarehouseExist && isInOrganExist){
					//验证条码
					List<UploadIdcodeBean> uibList = idcodeBeanMap.get(out_in_whid);
					
					String codeStr = getCodeStr(uibList);
//					Set<String> integralCodesSet = getAllowReturnedIntegralCodeSet(codeStr, fromOrganId, toOrganId);
					Map<String, String[]> integralCodes = getAllowReturnedIntegralCodeMap(codeStr); 
					Map<String, String[]> returnedCodes = getAlreadyReturnedCodeMap(codeStr); 
					
					Organ bkr = organMap.get(fromOrganId);
					Organ bkd = organMap.get(toOrganId);
					
					
					for(UploadIdcodeBean bean : uibList) {
						
						//检查重复性
						boolean isSuccess = idcodeSet.add(bean.getIdcode());
						if(!isSuccess){
							addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00106,bean.getIdcode()),ErrorType.FAIL,"","");
							continue;
						}
						
						Idcode idcode = idcodeMap.get(bean.getIdcode());
						
						String[] organInfo = integralCodes.get(bean.getIdcode());
						
						if(organInfo != null) {
							if(bkd.getIsKeyRetailer() != null 
									&& bkd.getIsKeyRetailer() == 1) {
								if(!toOrganId.equals(organInfo[0])) {
									Product pro = getProduct(idcode.getProductid());
									addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00109,bean.getIdcode(),organInfo[1],bkd.getOrganname()),ErrorType.FAIL,pro.getProductname(),pro.getSpecmode());
									continue;
								} 
								if(!fromOrganId.equals(organInfo[2])) {
									Product pro = getProduct(idcode.getProductid());
									addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00110,bean.getIdcode(),organInfo[1],organInfo[3],bkr.getOrganname()),ErrorType.FAIL,pro.getProductname(),pro.getSpecmode());
									continue;
								}
							}
						}
						
						if(returnedCodes.containsKey(bean.getIdcode())) {
							String[] returnInfo = returnedCodes.get(bean.getIdcode());
							Product pro = getProduct(idcode.getProductid());
							addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00113,bean.getIdcode(),returnInfo[1],returnInfo[0]),ErrorType.FAIL,pro.getProductname(),pro.getSpecmode());
							continue;
						}
						
						//校验条码
						boolean ispass = validateIdcode(bean);
						if(!ispass){
							continue;
						}
						
						//统计有效条码数量
						if(idcodeCountMap.containsKey(idcode.getProductid())) {
							idcodeCountMap.put(idcode.getProductid(), idcodeCountMap.get(idcode.getProductid()) + idcode.getQuantity());
						} else {
							idcodeCountMap.put(idcode.getProductid(), 1d);
						}
						Product pro = getProduct(idcode.getProductid());
						if(!warnCode.contains(bean.getIdcode())) {
							addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00112,bean.getIdcode()),ErrorType.SUCCESS,pro.getProductname(),pro.getSpecmode());
						}
					}
					
					if(idcodeCountMap.size() > 0) {
						//生成订购单据
						String owid = generateBill(fromOrganId,toOrganId);
						//生成TT单据
						String ttid = generateTTBill(fromOrganId,toOrganId,owid,7);
						
						for(Idcode ic : idcodeList) {
							genTakeTicketIdcode(ic, ttid);
						}
						
						for(String pid : idcodeCountMap.keySet()){
							// 根据接口上传的数量为准
							Double quantity = idcodeCountMap.get(pid); 
							Integer unitid = Constants.DEFAULT_UNIT_ID;
							//生成单据详情
							generateDetail(getProduct(pid),owid,quantity,unitid);
							//生成TT单据详情
							generateTTDetail(getProduct(pid),ttid,quantity,unitid);
							
						}
						
						// 依据实际正确的码进行库存扣减
						List<TakeTicketIdcode> ttiList = att.getTakeTicketIdcodeByttid(ttid);
						if(ttiList != null && ttiList.size() >0){ 
							Warehouse outwarehouse = warehouseMap.get(fromOrganId);
							Warehouse inwarehouse = warehouseMap.get(toOrganId);
							//自动复核单据
							autoAuditBill(outwarehouse.getId(),ttiList,idcodeCountMap,ttid,MEMO_TT);
							//复制条码
							addOrganWithdrawIdcode(ttiList,owid);
							//自动签收单据
							autoReceiveBill(inwarehouse.getId(),ttiList,idcodeCountMap,owid,MEMO_OW_RECEIVE);
							//更改条码信息
							updIdcode(inwarehouse.getId(),ttiList, toOrganId);
							
							HibernateUtil.commitTransaction();
						}else {
							HibernateUtil.rollbackTransaction();
						}
					}
					
				} else {
					if(!isOutWarehouseExist){
						addErrorInfo(1,UploadErrorMsg.getError(UploadErrorMsg.E00101, fromOrganId),ErrorType.FAIL,"","");
					}
					if(!isOutOrganExist){
						addErrorInfo(1,UploadErrorMsg.getError(UploadErrorMsg.E00102, username,fromOrganId),ErrorType.FAIL,"","");
					}
					if(!isInWarehouseExist){
						addErrorInfo(1,UploadErrorMsg.getError(UploadErrorMsg.E00103,fromOrganId),ErrorType.FAIL,"","");
					}
					if(!isInOrganExist){
						addErrorInfo(1,UploadErrorMsg.getError(UploadErrorMsg.E00104,username,fromOrganId),ErrorType.FAIL,"","");
					}
				}
			} catch (Exception e) {
				logger.error("", e);
				HibernateUtil.rollbackTransaction();
				throw e;
			}
		}
		
		
		
		
	}

	private Map<String, String[]> getAlreadyReturnedCodeMap(String codeStr) throws HibernateException, SQLException {
		List<Map<String,String>> codeList =  ai.getAlreadyReturnedIntegralCode(codeStr);
		Map<String, String[]> codeMap = new HashMap<String, String[]>();
		for(Map<String,String> code : codeList) {
			codeMap.put(code.get("idcode"), new String[]{code.get("oname"),code.get("makedate")});
		}
		return codeMap;
	}

	private Map<String, String[]> getAllowReturnedIntegralCodeMap(String codeStr) throws HibernateException, SQLException {
		List<Map<String,String>> codeList =  ai.getAllowReturnedIntegralCode(codeStr);
		Map<String, String[]> codeMap = new HashMap<String, String[]>();
		for(Map<String,String> code : codeList) {
			codeMap.put(code.get("idcode"), new String[]{code.get("oid"),code.get("oname"),code.get("inoid"),code.get("inoname")});
		}
		return codeMap;
	}

	private Set<String> getAllowReturnedIntegralCodeSet(String idcodes,
			String toOrganId, String fromOrganId) throws HibernateException, SQLException {
//		List<Map<String,String>> codeList =  ai.getAllowReturnedIntegralCodeSet(idcodes, toOrganId, fromOrganId);
		List<Map<String,String>> codeList =  ai.getAllowReturnedIntegralCodeSet(idcodes, toOrganId, fromOrganId);
		Set<String> codeSet = new HashSet<String>();
		for(Map<String,String> map : codeList) {
			codeSet.add(map.get("idcode"));
		}
		return codeSet;
	}

	private String getCodeStr(List<UploadIdcodeBean> uibList) {
		StringBuffer codes = new StringBuffer();
		for(UploadIdcodeBean bean : uibList) {
			codes.append(",'"+bean.getIdcode()+"'");
		}
		return codes.substring(1);
	}

	/**
	 * 验证权限
	 */
	private boolean checkAuthority(UploadIdcodeBean bean,boolean isOutExist,boolean hasRUWAuth,boolean isInExist,boolean hasWVAuth) throws Exception{
		if(!isOutExist){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00101, bean.getOutwarehouseid()),ErrorType.FAIL,"","");
			return false;
		}
		
		if(!hasRUWAuth){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00102, username,bean.getOutwarehouseid()),ErrorType.FAIL,"","");
			return false;
		}
		
		if(!isInExist){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00103,bean.getInwarehouseid()),ErrorType.FAIL,"","");
			return false;
		}
		
		if(!hasWVAuth){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00104,username,bean.getInwarehouseid()),ErrorType.FAIL,"","");
			return false;
		}
		return true;
	}

/**
 * 生成退货单详情
* @param riid
 * @throws Exception 
 * @throws NumberFormatException 
* @CreateTime Jan 10, 2013 5:12:44 PM
 */
	private void generateDetail(Product p,String owid,Double quantity,Integer unitid) throws NumberFormatException, Exception {
		OrganWithdrawDetail owd = new OrganWithdrawDetail();
		Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_withdraw_detail", 0, ""));
		owd.setId(id);
		owd.setOwid(owid);
		owd.setProductid(p.getId());
		owd.setProductname(p.getProductname());
		owd.setSpecmode(p.getSpecmode());
		// 产品单位取product中的小包装单位
		owd.setUnitid(unitid);
		owd.setUnitprice(0d);
		// 共有多少小包装数量
		owd.setQuantity(quantity);
		//物料号
		owd.setNccode(p.getmCode());
		owd.setRatifyquantity(quantity);
		//箱数
		owd.setBoxnum(0);
		//散数
		owd.setScatternum(0d);
		owd.setTakequantity(quantity);
		owd.setSubsum(0d);
		EntityManager.save(owd);
	}

	/**
	 * 生成退货单
	* @param whid
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 10, 2013 4:43:46 PM
	 */
	private String generateBill(String fromOrganId,String toOrganId) throws Exception {
		Organ inOrgan = organMap.get(toOrganId);
		Organ outOrgan = organMap.get(fromOrganId);
		Warehouse outWarehouse = warehouseMap.get(fromOrganId);
		Warehouse inWarehouse = warehouseMap.get(toOrganId);
		String outorganid = null;
		String outorganidname = null;
		if(outOrgan!=null){
			outorganid=outOrgan.getId();
			outorganidname = outOrgan.getOrganname();
		}
		
		String linkman = null;
		String tel = null;
		Olinkman olinkman = getOlinkman(inOrgan.getId());
		if(olinkman != null){
			linkman = olinkman.getName();
			tel = olinkman.getMobile();
		}
		
		OrganWithdraw ow = new OrganWithdraw();
		String owid =  MakeCode.getExcIDByRandomTableName("organ_withdraw", 2, "OW");
		ow.setId(owid);
		ow.setPorganid(outorganid);
		ow.setPorganname(outorganidname);
		ow.setWarehouseid(outWarehouse.getId());
		ow.setReceiveorganid(inOrgan.getId());
		ow.setInwarehouseid(inWarehouse.getId());
		ow.setMakeid(users.getUserid());
		ow.setMakeorganid(users.getMakeorganid());
		ow.setMakedeptid(users.getMakedeptid());
		ow.setMakedate(Dateutil.addSecond2Date(defatult_delay, DateUtil.getCurrentDate()));
		ow.setIsaudit(1);
		ow.setAuditid(users.getUserid());
		ow.setAuditdate(Dateutil.addSecond2Date(defatult_delay,Dateutil.getCurrentDate()));
		ow.setIscomplete(1);
		ow.setReceiveid(users.getUserid());
		ow.setReceivedate(Dateutil.addSecond2Date(defatult_delay,Dateutil.getCurrentDate()));
		ow.setIsblankout(0);
		ow.setBlankoutid(0);
		ow.setPrinttimes(0);
		ow.setTakestatus(1);
    	 // 设置批准
		ow.setIsratify(1);
		ow.setRatifyid(users.getUserid());
		ow.setRatifydate(Dateutil.addSecond2Date(3,DateUtil.getCurrentDate()));
		// 设置确认
		ow.setIsaffirm(1);
		ow.setAffirmid(users.getUserid());
		ow.setAffirmdate(Dateutil.addSecond2Date(3,DateUtil.getCurrentDate()));
		ow.setKeyscontent(ow.getId() + "," + ow.getPorganid() + "," + ow.getPorganname() + "," + ow.getPlinkman() + ","
				+ ow.getTel());
		ow.setPlinkman(linkman);
		ow.setTel(tel);
		ow.setBonusStatus(0);
		
		EntityManager.save3(ow);
		
		return owid;
	}

	
	/***
	 * 校验条码
	* @param bean
	* @param existIdcodes
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 10, 2013 2:22:48 PM
	 */
	private boolean validateIdcode(UploadIdcodeBean bean) throws Exception {

		String idcode = bean.getIdcode();
		if(StringUtil.isEmpty(idcode) || idcode.length()<1){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00107, bean.getOutMpinCode()),ErrorType.FAIL,"","");
			return false;
		}
		Idcode ic = idcodeMap.get(bean.getIdcode());
		//条码不存在
		if(ic == null){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00003, idcode),ErrorType.FAIL,"","");
			return false;
		}
		
		Product pro = getProduct(ic.getProductid());
		
		//条码不可用
		if(ic.getIsuse() == 0 && ic.getWarehouseid().equals(bean.getOutwarehouseid())){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00004, idcode),ErrorType.FAIL,pro.getProductname(),pro.getSpecmode());
			return false;
		}
		// 判断条码与产品是否相同
		if(!StringUtil.isEmpty(ic.getProductid()) && !StringUtil.isEmpty(bean.getProductId()) && !ic.getProductid().equals(bean.getProductId())){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00108, idcode),ErrorType.FAIL,pro.getProductname(),pro.getSpecmode());
			return false;
		}
		idcodeList.add(ic);
		Warehouse outwarehouse = warehouseMap.get(bean.getFromOrganId());
		// 判断条码是否在当前仓库
		if(!ic.getWarehouseid().equals(outwarehouse.getId())){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00017, idcode),ErrorType.WARNING,pro.getProductname(),pro.getSpecmode());
			warnCode.add(idcode);
		}
		return true;
	
	}
	
	/**
	 * 保存OrganWithdrawIdcode
	* @param tti
	* @author wenping   
	* @CreateTime Jan 18, 2013 4:30:50 PM
	 */
	private void addOrganWithdrawIdcode(List<TakeTicketIdcode> idlist,String owid) throws Exception {
		for ( TakeTicketIdcode tti : idlist ){
			OrganWithdrawIdcode idcode = new OrganWithdrawIdcode();
			BeanUtils.copyProperties(idcode, tti);
			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("organ_withdraw_idcode", 0, "")));
			idcode.setOwid(owid);
			idcode.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			EntityManager.save3(idcode);
		}
	}
}
