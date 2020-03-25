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

import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UploadIdcodeBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.ErrorType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

/**
 * 无单一级经销商出库
* @Title: DealNoBillStockAlterMove.java
* @version:
 */
public class DealBKDNoBillStockAlterMove extends DealKeyRetailerNoBillUploadService {
	
	private static Logger logger = Logger.getLogger(DealBKDNoBillStockAlterMove.class);
	
//	private AppTakeTicketIdcode aptti = new AppTakeTicketIdcode();
//	private AppTakeTicket aptt = new AppTakeTicket(); 
	public DealBKDNoBillStockAlterMove(){}

	public DealBKDNoBillStockAlterMove(String filepath, String fileaddress, int iuid,String username)
	{
		this.filepath = filepath;
		this.fileaddress = fileaddress;
		this.iuid = iuid;
		this.failAddress = fileaddress.substring(0, fileaddress.lastIndexOf(".")) + "_fail.txt";
		this.username = username;
	}
	
	public ArrayList<String> deal() throws Exception {
		try {
			//获取用户信息
//			getUserInfo(username);
			
			List<UploadIdcodeBean> idcodeBeanList = new ArrayList<UploadIdcodeBean>();
			//处理上传的文件
			dealTxtFile(idcodeBeanList,Constants.SCAN_TYPE_OM_NOBILL_BKD);
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
					Map<String, String[]> integralCodes = getNotAllowedOutIntegralCodeMap(codeStr); 
					Organ bkd = organMap.get(toOrganId);
					
					for(UploadIdcodeBean bean : uibList) {
						
						//检查重复性
						boolean isSuccess = idcodeSet.add(bean.getIdcode());
						if(!isSuccess){
							addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00106,bean.getIdcode()),ErrorType.FAIL,"","");
							continue;
						}
						
						String[] organInfo = integralCodes.get(bean.getIdcode());
						if(organInfo!=null&&fromOrganId.equals(organInfo[0])) {
//							if(bkd.getIsKeyRetailer() != null 
//									&& bkd.getIsKeyRetailer() == 1) {
								Idcode ic = ai.getIdcodeByCode(bean.getIdcode());
								Product pro = getProduct(ic.getProductid());
								addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00111,bean.getIdcode(),organInfo[3]),ErrorType.FAIL,pro.getProductname(),pro.getSpecmode());
								continue;
//							}
						}
						
						//检查是否重复出库
						
						
						//校验条码
						boolean ispass = validateIdcode(bean);
						if(!ispass){
							continue;
						}
						
						Idcode idcode = idcodeMap.get(bean.getIdcode());
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
						StockAlterMove sam = generateBill(fromOrganId,toOrganId);
						String omid = sam.getId();
						//生成TT单据
						String ttid = generateTTBill(fromOrganId,toOrganId,omid,1);
						
						for(Idcode ic : idcodeList) {
							genTakeTicketIdcode(ic, ttid);
						}
						
						for(String pid : idcodeCountMap.keySet()){
							// 根据接口上传的数量为准
							Double quantity = idcodeCountMap.get(pid); 
							Integer unitid = Constants.DEFAULT_UNIT_ID;
							//生成单据详情
							generateDetail(getProduct(pid),omid,quantity,unitid);
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
							saveStockAlterMoveIdcode(ttiList,omid);
							//更改条码信息至出库状态
							ai.updIdcodeByttid(ttid, 0, 1);
//							boolean autoReceive = isAutoReceive(inwarehouse.getId());
							if (inwarehouse.getIsautoreceive() != null 
									&& inwarehouse.getIsautoreceive() == 1) {
								//自动签收单据
								autoReceiveBill(inwarehouse.getId(),ttiList,idcodeCountMap,omid,MEMO_OW_RECEIVE);
								sam.setIscomplete(1);
								sam.setReceivedate(Dateutil.getCurrentDate());
								EntityManager.update(sam);
								ai.updIdcodeBySamId(omid, inwarehouse.getId());
							}
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
	
	private String getCodeStr(List<UploadIdcodeBean> uibList) {
		StringBuffer codes = new StringBuffer();
		for(UploadIdcodeBean bean : uibList) {
			codes.append(",'"+bean.getIdcode()+"'");
		}
		return codes.substring(1);
	}
	
	private Map<String, String[]> getNotAllowedOutIntegralCodeMap(String codeStr) throws HibernateException, SQLException {
		List<Map<String,String>> codeList =  ai.getNotAllowedOutIntegralCode(codeStr);
		Map<String, String[]> codeMap = new HashMap<String, String[]>();
		for(Map<String,String> code : codeList) {
			codeMap.put(code.get("idcode"), new String[]{code.get("oid"),code.get("oname"),code.get("inoid"),code.get("inoname")});
		}
		return codeMap;
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
 * 生成订购单详情
 */
	private void generateDetail(Product p,String omid,Double quantity,Integer unitid) throws NumberFormatException, Exception {
		StockAlterMoveDetail smd = new StockAlterMoveDetail();
		smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_detail", 0, "")));
		smd.setSamid(omid);
		smd.setProductid(p.getId());
		smd.setProductname(p.getProductname());
		smd.setSpecmode(p.getSpecmode());
		// 产品单位
		smd.setUnitid(unitid);
		//物料号
		smd.setNccode(p.getmCode());
		smd.setUnitprice(0d);
		// 计划数量
		smd.setQuantity(quantity);
		// 出库数量
		smd.setTakequantity(quantity);
		// 接收数量
		smd.setReceiveQuantity(quantity);
		// 箱数
		smd.setBoxnum(0);
		// 散数
		smd.setScatternum(0d);
		smd.setSubsum(0d);
		EntityManager.save(smd);
	}
	
	/**
	 * 生成订购单
	 */
	@SuppressWarnings("unchecked")
	private StockAlterMove generateBill(String fromOrganId,String toOrganId) throws Exception {
		Organ inOrgan = organMap.get(toOrganId);
		Organ outOrgan = organMap.get(fromOrganId);
		Warehouse outWarehouse = warehouseMap.get(fromOrganId);
		Warehouse inWarehouse = warehouseMap.get(toOrganId);
		String receiveorganid = null;
		String receiveorganidname = null;
		if(inOrgan!=null){
			receiveorganid = inOrgan.getId();
			receiveorganidname= inOrgan.getOrganname();
		}
		String outOrganId = null;
		String outOrganName = null;
		if(outOrgan != null){
			outOrganId = outOrgan.getId();
			outOrganName = outOrgan.getOrganname();
		}
		String linkman = null;
		String tel = null;
		
		Olinkman olinkman = getOlinkman(inOrgan.getId());
		if(olinkman != null){
			linkman = olinkman.getName();
			tel = olinkman.getMobile();
		}
		
		StockAlterMove sm = new StockAlterMove();
		String smid = MakeCode.getExcIDByRandomTableName("stock_alter_move", 2, "OM");
		sm.setId(smid);
		sm.setMovedate(Dateutil.addSecond2Date(defatult_delay, Dateutil.getCurrentDate()));
		sm.setOutwarehouseid(outWarehouse.getId());
		sm.setIsmaketicket(0);
		sm.setIsreceiveticket(0);
		sm.setMakeorganid(users.getMakeorganid());
		sm.setMakeorganidname(users.getMakeorganname());
		sm.setMakedeptid(users.getMakedeptid());
		sm.setMakeid(users.getUserid());
		sm.setMakedate(Dateutil.addSecond2Date(defatult_delay,Dateutil.getCurrentDate()));
		sm.setInwarehouseid(inWarehouse.getId());
		sm.setIsaudit(1);
		sm.setAuditid(users.getUserid());
		sm.setAuditdate(Dateutil.addSecond2Date(defatult_delay,Dateutil.getCurrentDate()));
		
		sm.setOlinkman(linkman);
		sm.setOtel(tel);
		
		sm.setIsshipment(1);
		sm.setShipmentid(users.getUserid());
		sm.setShipmentdate(Dateutil.addSecond2Date(defatult_delay,Dateutil.getCurrentDate()));
		sm.setIstally(0);
		sm.setIsblankout(0);
		sm.setTakestatus(1);
		sm.setReceiveorganid(receiveorganid);
		sm.setReceiveorganidname(receiveorganidname);
		sm.setOutOrganId(outOrganId);
		sm.setOutOrganName(outOrganName);
		sm.setIscomplete(0);
//		sm.setReceiveid(users.getUserid());
//		sm.setReceivedate(Dateutil.addSecond2Date(3,Dateutil.getCurrentDate()));
		sm.setBonusStatus(0);
		
		StringBuffer keyscontent = new StringBuffer();
		keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",")
		.append(sm.getOtel()).append(",").append(sm.getMakeorganidname());
		sm.setTakestatus(0);
		sm.setKeyscontent(keyscontent.toString());
		
		EntityManager.save(sm);
		
		return sm;
	}
	/***
	 * 校验条码
	* @param bean
	* @param existIdcodes
	* @author    
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
		
		Warehouse outWarehouse = warehouseMap.get(bean.getFromOrganId());
		
		// 判断条码是否在当前仓库(只记录错误信息,不报错)
		if(!ic.getWarehouseid().equals(outWarehouse.getId())){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00005, idcode),ErrorType.WARNING,pro.getProductname(),pro.getSpecmode());
			warnCode.add(idcode);
		}
		return true;
	}
	/**
	 * 保存StockAlterMoveIdcode
	* @param tti
	* @author    
	* @CreateTime Jan 18, 2013 4:30:50 PM
	 */
	private void saveStockAlterMoveIdcode(List<TakeTicketIdcode> idlist,String omid) throws Exception {
		for ( TakeTicketIdcode tti : idlist ){
			StockAlterMoveIdcode idcode = new StockAlterMoveIdcode();
			BeanUtils.copyProperties(idcode, tti);
//			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_idcode", 0, "")));
			idcode.setSamid(omid);
			idcode.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			EntityManager.save(idcode);
		}
	}
	
	/**
	 * check whether warehouse is auto receive
	 * @param warehouseid
	 * @return
	 * @throws Exception 
	 */
	private boolean isAutoReceive(String warehouseid) throws Exception{
		AppWarehouse app = new AppWarehouse();
		
		Warehouse w = app.getWarehouseByID(warehouseid);
		if(w == null || w.getIsautoreceive().intValue() == 0){
			return false;
		}
		return true;
	}
	
}
