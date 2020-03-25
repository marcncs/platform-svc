package com.winsafe.drp.keyretailer.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList; 
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.winsafe.drp.dao.Affiche;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSBonusAccount;
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.dao.AppSBonusDetail;
import com.winsafe.drp.keyretailer.dao.AppSBonusLog;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.metadata.BonusType;
import com.winsafe.drp.keyretailer.pojo.SBonusAccount;
import com.winsafe.drp.keyretailer.pojo.SBonusAppraise;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusDetail;
import com.winsafe.drp.keyretailer.pojo.SBonusLog;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.keyretailer.util.BonusAfficheMsg;
import com.winsafe.drp.metadata.ApplyUserType;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.metadata.PlantType;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

public class SBonusService {

	private AppSBonusSetting appSBonusSetting = new AppSBonusSetting();
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise(); 
	private AppSBonusDetail appSBonusDetail = new AppSBonusDetail(); 
	private AppSBonusAccount appSBonusAccount = new AppSBonusAccount();
	private Map<String,Product> productMap = new HashMap<String, Product>();
	private AppProduct ap = new AppProduct();
	private AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
	private AppIdcode appIdcode = new AppIdcode();
	private AppBaseResource appBR = new AppBaseResource(); 
	private AppStockAlterMoveIdcode appSami = new AppStockAlterMoveIdcode();
	private DecimalFormat decimalFormat = new DecimalFormat("###");
	
	public boolean isBonusAlreadyExists(SBonusSetting sbs) {
		return appSBonusSetting.isBonusAlreadyExists(sbs.getYear(),sbs.getMonth(),sbs.getAccountType(),sbs.getProductName(),sbs.getSpec(), null);
	}
	
	public boolean isBonusAlreadyExists(SBonusSetting sbs, String id,int year,int month) {
		return appSBonusSetting.isBonusAlreadyExists(year,sbs.getMonth(),sbs.getAccountType(),sbs.getProductName(),sbs.getSpec(), id);
	}

	public void addSBonusSetting(SBonusSetting sbs) throws Exception {
		appSBonusSetting.addSBonusSetting(sbs);
	}
	
	public void updIdcodeIntegralStatus(String billNo, Set<String> productIdList) throws Exception {
		for(String productId : productIdList) {
			//更新积分码的状态 
			appIdcode.doIntegral(billNo, productId);
		}
	}
	
	//增加积分
	public void addSBonus(String organId, String oppOrganId, List<Idcode> idcodeList, DealerType dealerType, BonusType bonusType, String billNo, Set<String> productSet) throws Exception {
		StringBuffer errMsg = new StringBuffer();
		StringBuffer bonusDetail = new StringBuffer();
		Calendar now = Calendar.getInstance(); 
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		//获取积分账号
		SBonusAccount sBonusAccount = getSBonusAccount(organId,billNo,bonusType);
		//获取积分
		SBonusAppraise sBonusAppraise = getSBonusAppraise(sBonusAccount.getAccountId(), organId, year);
		//统计可积分的条码对应的产品数量（件数与最小包装数量）
		Map<String,List<String>> codeListMap = new HashMap<String, List<String>>();
		Map<String,Double[]> productCountMap = collectProductCount(idcodeList, errMsg, codeListMap);
		
		//计算积分
		Double totalBonus = 0d;
		if(productCountMap.size() > 0) {
			Map<Integer, String> countUnitMap = appBR.getBaseResourceMap("CountUnit");
			for(String productId : productCountMap.keySet()) {
				Product product = getProduct(productId);
				//产品数量
				double amount = ArithDouble.mul(ArithDouble.mul(productCountMap.get(productId)[0], productCountMap.get(productId)[1]), product.getBoxquantity());
				//获取积分设置
				SBonusSetting sbs = appSBonusSetting.getSBonusSetting(product.getProductname(),product.getSpecmode(), year, month, dealerType.getValue());
				if(sbs == null) {
					errMsg.append("产品:"+product.getProductname()+" 规格:"+product.getSpecmode()+" 数量："+new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP)+countUnitMap.get(product.getCountunit())+" 原因:未找到相关积分设置,不参与积分");
					errMsg.append(" 条码").append("\r\n");
					for(String code : codeListMap.get(product.getId())) {
						errMsg.append(code).append("\r\n");
					}
					continue;
				}
				//取得每件(箱)能获得积分,为了避免误差太大,精度取两位小数
				double codeBonus = ArithDouble.mul(ArithDouble.mul(productCountMap.get(productId)[1], product.getBoxquantity()), ArithDouble.div2(sbs.getBonusPoint(), sbs.getAmount(), 2));
				//取得该产品能获得的总积分数量,四舍五入取整
				double proBonus = Math.rint(ArithDouble.mul(codeBonus, productCountMap.get(productId)[0])); 
				//新增积分
				sBonusAppraise.setBonusPoint(Math.rint(ArithDouble.add(sBonusAppraise.getBonusPoint(), proBonus)));
				//新增积分明细记录
				addSBonusDetail(sBonusAccount.getAccountId(), proBonus, amount, product,organId, oppOrganId, year, month, bonusType, 0, billNo);
				
				productSet.add(productId);
				
				if(bonusType ==  BonusType.DELIVER) {
					//更新每个码的积分数量
					atti.updTakeTicketIdcodeBonus(billNo, productId, codeBonus);
				} else {
					appSami.updStockAlterMoveIdcodeBonus(billNo, productId, codeBonus);
				}
				
				bonusDetail.append("产品:"+product.getProductname()+" 规格:"+product.getSpecmode()+" 数量："+new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP)+countUnitMap.get(product.getCountunit())+" 积分:" +decimalFormat.format(proBonus));
				bonusDetail.append(" 条码").append("\r\n");
				for(String code : codeListMap.get(product.getId())) {
					bonusDetail.append(code).append("\r\n");
				}
				totalBonus = totalBonus + proBonus;
			}
			sBonusAppraise.setActualPoint(Math.rint(sBonusAppraise.getBonusPoint() * sBonusAppraise.getAppraise()));
			sBonusAppraise.setModifiedDate(DateUtil.getCurrentDate());
			appSBonusAppraise.updSBonusAppraise(sBonusAppraise);
		}
		//查看拒收的条码
		/*AppStockAlterMoveIdcode asami = new AppStockAlterMoveIdcode();
		List<Map<String,String>> removedIdcode = asami.getRemovedIdcodeById(billNo);
		if(removedIdcode.size() > 0) {
			errMsg.append("以下拒收条码,需做退货:\r\n");
			for(Map<String,String> map : removedIdcode) {
				errMsg.append(map.get("idcode")).append("\r\n");
			}
 		}*/
		
		//新增积分消息
		if(totalBonus > 0 || errMsg.length() > 0) {
			AppOrgan appOrgan = new AppOrgan();
			Organ oppOrgan = appOrgan.getOrganByID(oppOrganId);
			if(bonusType == BonusType.DELIVER) {
				addBonusAffiche(BonusAfficheMsg.TITLE, BonusAfficheMsg.getError(BonusAfficheMsg.DELIVERY_OUT, oppOrgan.getOrganname(),decimalFormat.format(totalBonus),bonusDetail.append(errMsg).toString()), organId);
			} else {
				addBonusAffiche(BonusAfficheMsg.TITLE, BonusAfficheMsg.getError(BonusAfficheMsg.DELIVERY_IN, oppOrgan.getOrganname(),decimalFormat.format(totalBonus),bonusDetail.append(errMsg).toString()), organId);
			}
		}
		
		//新增错误日志
		if(errMsg.length() > 0) {
			addSBonusLog(billNo, errMsg.toString(), bonusType, organId);
		}
		
	}
	/*
	private void dealTakeTicketIdcode(List<Idcode> idcodeList, String billNo,Integer year,Integer month,Integer dealerType) throws Exception {
		AppProduct appp = new AppProduct();
		AppTakeTicketIdcode appt = new AppTakeTicketIdcode();
		for (Idcode d: idcodeList) {
			Product product = appp.getProductByID(d.getProductid());
			if (product!=null) {
				SBonusSetting sbs = appSBonusSetting.getSBonusSetting(product.getProductname(),product.getSpecmode(), year, month, dealerType);
				if (sbs!=null) {
					double b =  (d.getPackquantity() * product.getBoxquantity()) * sbs.getBonusPoint() / sbs.getAmount();
					appt.updbonusTakeIdcode(billNo, d.getIdcode(), b);
				}
			}
		}
	}*/

	private Map<String, Double[]> collectProductCountForDeduct(List<Idcode> idcodeList, StringBuffer errMsg, String billNo, BonusType bonusType, Map<String, List<String>> codeListMap) throws Exception { 
		//<产品编号,{无积分记录件数,有积分记录件数,有积分的积分总,包装数量}>
		Map<String,Double[]> productCountMap = new HashMap<String, Double[]>();
		//获取条码出库积分时的积分数量
		Map<String,String> idcodeBonus = new HashMap<String, String>();
		if(bonusType == BonusType.RETURN_RECEIVE) {
			idcodeBonus = atti.getIdcodeBonusMapForReturn(billNo);
		} else {
			idcodeBonus = appSami.getIdcodeBonusMapForReturn(billNo);
		}
		//错误条码
		Map<String,Double[]> proErrCountMap = new HashMap<String, Double[]>();
		Map<String, List<String>> errCodeListMap = new HashMap<String, List<String>>();
		for(Idcode idcode : idcodeList) {
			if(idcode.getIsIntegral() == null || idcode.getIsIntegral() == 0) {
				collectProductCount(idcode, proErrCountMap, errCodeListMap);
//				if(sb == null) {
//					sb = new StringBuffer();
//					sb.append("以下条码未积过分,不做扣减积分处理\r\n");
//				}
//				sb.append(idcode.getIdcode()+"\r\n");
				continue;
			}
			if(productCountMap.containsKey(idcode.getProductid())) {
				Double[] qty = productCountMap.get(idcode.getProductid());
				if(idcodeBonus.containsKey(idcode.getIdcode()) && !StringUtil.isEmpty(idcodeBonus.get(idcode.getIdcode()))) {
					qty[1] = ArithDouble.add(qty[1], 1d);
					qty[2] = ArithDouble.add(qty[2], Double.valueOf(idcodeBonus.get(idcode.getIdcode())));
				} else {
					qty[0] = ArithDouble.add(qty[0], 1d);
				}
			} else {
				if(idcodeBonus.containsKey(idcode.getIdcode()) && !StringUtil.isEmpty(idcodeBonus.get(idcode.getIdcode()))) { 
					productCountMap.put(idcode.getProductid(), new Double[]{0d,1d,Double.valueOf(idcodeBonus.get(idcode.getIdcode())),idcode.getPackquantity()});
				} else {
					productCountMap.put(idcode.getProductid(), new Double[]{1d,0d,0d,idcode.getPackquantity()});
				}
			}
			
			if(codeListMap.containsKey(idcode.getProductid())) {
				codeListMap.get(idcode.getProductid()).add(idcode.getIdcode());
			} else {
				List<String> codeList = new ArrayList<String>();
				codeList.add(idcode.getIdcode());
				codeListMap.put(idcode.getProductid(), codeList);
			}
		}
		createErrorMsg(proErrCountMap, errCodeListMap, errMsg, "未积过分,不做扣减积分处理。");
		/*if(sb != null) {
			errMsg.append(sb);
		}*/
		return productCountMap;
	}

	private Map<String, Double[]> collectProductCount(List<Idcode> idcodeList, StringBuffer errMsg, Map<String, List<String>> codeListMap) throws Exception {
		Map<String,Double[]> productCountMap = new HashMap<String, Double[]>();
		//错误条码
		Map<String,Double[]> proErrCountMap = new HashMap<String, Double[]>();
		Map<String, List<String>> errCodeListMap = new HashMap<String, List<String>>();
		for(Idcode idcode : idcodeList) {
			if(idcode.getIsIntegral() != null && idcode.getIsIntegral() == 1) {
				collectProductCount(idcode, proErrCountMap, errCodeListMap);
				continue;
			}
			collectProductCount(idcode, productCountMap, codeListMap);
			/*if(productCountMap.containsKey(idcode.getProductid())) {
				Double[] qty = productCountMap.get(idcode.getProductid());
				qty[0] =  ArithDouble.add(qty[0], 1d);
			} else {
				productCountMap.put(idcode.getProductid(), new Double[]{1d,idcode.getPackquantity()});
			}
			if(codeListMap.containsKey(idcode.getProductid())) {
				codeListMap.get(idcode.getProductid()).add(idcode.getIdcode());
			} else {
				List<String> codeList = new ArrayList<String>();
				codeList.add(idcode.getIdcode());
				codeListMap.put(idcode.getProductid(), codeList);
			}*/
			createErrorMsg(proErrCountMap, errCodeListMap, errMsg, "已积分过,不参与积分。");
		}
		return productCountMap;
	}
	
	

	private void createErrorMsg(Map<String, Double[]> proErrCountMap,
			Map<String, List<String>> errCodeListMap, StringBuffer errMsg, String errReason) throws Exception {
		Map<Integer, String> countUnitMap = appBR.getBaseResourceMap("CountUnit");
		for(String productId : proErrCountMap.keySet()) {
			Product product = getProduct(productId);
			double amount = ArithDouble.mul(ArithDouble.mul(proErrCountMap.get(productId)[0], proErrCountMap.get(productId)[1]), product.getBoxquantity());
			errMsg.append("产品:"+product.getProductname()+" 规格:"+product.getSpecmode()+" 数量:"+new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP)+countUnitMap.get(product.getCountunit())+errReason);
			errMsg.append(" 条码").append("\r\n");
			for(String code : errCodeListMap.get(product.getId())) {
				errMsg.append(code).append("\r\n");
			}
		}
		
	}

	private void collectProductCount(Idcode idcode, Map<String, Double[]> productCountMap,
			Map<String, List<String>> codeListMap) {
		if(productCountMap.containsKey(idcode.getProductid())) {
			Double[] qty = productCountMap.get(idcode.getProductid());
			qty[0] =  ArithDouble.add(qty[0], 1d);
		} else {
			productCountMap.put(idcode.getProductid(), new Double[]{1d,idcode.getPackquantity()});
		}
		if(codeListMap.containsKey(idcode.getProductid())) {
			codeListMap.get(idcode.getProductid()).add(idcode.getIdcode());
		} else {
			List<String> codeList = new ArrayList<String>();
			codeList.add(idcode.getIdcode());
			codeListMap.put(idcode.getProductid(), codeList);
		}
		
	}

	private SBonusAppraise getSBonusAppraise(Integer accountId, String organId,
			int year) throws Exception {
		SBonusAppraise sBonusAppraise = appSBonusAppraise.getSBonusAppraiseByOrganIdAndPeriod(organId, String.valueOf(year));
		//若无积分记录新增积分记录
		if(sBonusAppraise == null) {
			sBonusAppraise = addSBonusAppraise(accountId, organId, year);
		}
		return sBonusAppraise;
	}

	private SBonusAccount getSBonusAccount(String organId, String billNo,
			BonusType bonusType) throws Exception {
		SBonusAccount sBonusAccount = appSBonusAccount.getSBonusAccountByOrganId(organId);
		if(sBonusAccount == null) {
			String errorMsg = "未找到机构编号"+organId+"对应的积分账号";
			addSBonusLog(billNo, errorMsg, bonusType, organId);
			throw new Exception(errorMsg);
		}
		return sBonusAccount;
	}

	public void deductSBonus(String organId, String oppOrganId, List<Idcode> idcodeList,DealerType dealerType, BonusType bonusType, String billNo) throws Exception {
		StringBuffer errMsg = new StringBuffer();
		StringBuffer bonusDetail = new StringBuffer();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		//获取积分账号
		SBonusAccount sBonusAccount = getSBonusAccount(organId,billNo,bonusType);
		//获取积分
		SBonusAppraise sBonusAppraise = getSBonusAppraise(sBonusAccount.getAccountId(), organId, year);
		//统计可积分的条码对应的产品数量(最小单位)与积分数量
		Map<String,List<String>> codeListMap = new HashMap<String, List<String>>();
		Map<String,Double[]> productCountMap = collectProductCountForDeduct(idcodeList, errMsg, billNo, bonusType, codeListMap);
		
		Double totalBonus = 0d;
		if(productCountMap.size() > 0) {
			Map<Integer, String> countUnitMap = appBR.getBaseResourceMap("CountUnit");
			for(String productId : productCountMap.keySet()) {
				Product product = getProduct(productId);
				//产品数量
				double amount = ArithDouble.mul(ArithDouble.mul(ArithDouble.add(productCountMap.get(productId)[0], productCountMap.get(productId)[1]), productCountMap.get(productId)[3]), product.getBoxquantity());
				SBonusSetting sbs = appSBonusSetting.getSBonusSetting(product.getProductname(),product.getSpecmode() , year, month, dealerType.getValue());
				if(sbs == null) {
					errMsg.append("产品:"+product.getProductname()+" 规格:"+product.getSpecmode()+" 数量："+new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP)+countUnitMap.get(product.getCountunit())+"未找到相关积分设置,不参与积分。");
					errMsg.append(" 条码").append("\r\n");
					for(String code : codeListMap.get(product.getId())) {
						errMsg.append(code).append("\r\n");
					}
					continue;
				}
				//取得每件(箱)能获得积分,为了避免误差太大,精度取两位小数
				double codeBonus = ArithDouble.mul(ArithDouble.mul(productCountMap.get(productId)[3], product.getBoxquantity()), ArithDouble.div2(sbs.getBonusPoint(), sbs.getAmount(), 2));
				//取得该产品能获得的总积分数量,四舍五入取整
				double proBonus = Math.rint(ArithDouble.add(ArithDouble.mul(codeBonus, productCountMap.get(productId)[0]), productCountMap.get(productId)[2])); 
				//扣减积分
				sBonusAppraise.setBonusPoint(Math.rint(ArithDouble.sub(sBonusAppraise.getBonusPoint(), proBonus)));
				//新增积分明细记录
				addSBonusDetail(sBonusAccount.getAccountId(), -proBonus, amount, product,organId, oppOrganId, year,month, bonusType, 0, billNo);
				bonusDetail.append("产品:"+product.getProductname()+" 规格:"+product.getSpecmode()+" 数量:"+new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP)+countUnitMap.get(product.getCountunit())+" 积分:-" +decimalFormat.format(proBonus));
				bonusDetail.append(" 条码").append("\r\n");
				for(String code : codeListMap.get(product.getId())) {
					bonusDetail.append(code).append("\r\n");
				}
				totalBonus = totalBonus + proBonus;
			}
			sBonusAppraise.setModifiedDate(DateUtil.getCurrentDate());
			sBonusAppraise.setActualPoint(sBonusAppraise.getBonusPoint() * sBonusAppraise.getAppraise());
			appSBonusAppraise.updSBonusAppraise(sBonusAppraise);
		}
		
		if(totalBonus > 0 || errMsg.length() > 0) {
			AppOrgan appOrgan = new AppOrgan();
			Organ oppOrgan = appOrgan.getOrganByID(oppOrganId);
			if(bonusType == BonusType.RETURN) {
				addBonusAffiche(BonusAfficheMsg.TITLE, BonusAfficheMsg.getError(BonusAfficheMsg.RETURN_IN, oppOrgan.getOrganname(),decimalFormat.format(totalBonus),bonusDetail.append(errMsg).toString()), organId);
			} else {
				addBonusAffiche(BonusAfficheMsg.TITLE, BonusAfficheMsg.getError(BonusAfficheMsg.RETURN_OUT, oppOrgan.getOrganname(),decimalFormat.format(totalBonus),bonusDetail.append(errMsg).toString()), organId);
			}
		}
		
		if(errMsg.length() > 0) {
			addSBonusLog(billNo, errMsg.toString(), bonusType, organId);
		}
		
	}
	
	/**
	 * 积分明细调整
	 * @param sbd
	 * @throws Exception 
	 */
	public void adjustSBonus(SBonusDetail sbd,String organName,String oname,Integer countUnit) throws Exception{
		
		StringBuffer bonusDetail = new StringBuffer();
		Map<Integer, String> countUnitMap = appBR.getBaseResourceMap("CountUnit");
		bonusDetail.append("产品:"+sbd.getProductName()+" 规格:"+sbd.getSpec()+" 数量:"+decimalFormat.format(sbd.getAmount())+countUnitMap.get(countUnit)+"  积分:" +decimalFormat.format(sbd.getBonusPoint())).append("\r\n");
		addBonusAffiche(BonusAfficheMsg.ADJUST_TITLE, BonusAfficheMsg.getError(BonusAfficheMsg.ADJUST,organName,decimalFormat.format(sbd.getBonusPoint()),organName,oname,bonusDetail.toString()),sbd.getOrganId());
		
	}

	public SBonusAppraise addSBonusAppraise(Integer accountId, String organId, int year) throws Exception { 
		SBonusAppraise sBonusAppraise = new SBonusAppraise();
		sBonusAppraise.setAccountId(accountId);
		sBonusAppraise.setOrganId(organId);
		sBonusAppraise.setBonusTarget(0d);
		sBonusAppraise.setAppraise(0d);
		sBonusAppraise.setActualPoint(0d);
		sBonusAppraise.setBonusPoint(0d);
		sBonusAppraise.setModifiedDate(DateUtil.getCurrentDate());
		sBonusAppraise.setPeriod(year);
		appSBonusAppraise.addSBonusAppraise(sBonusAppraise);
		return sBonusAppraise;
		
	}

	/**
	 * @param accountId 积分账户编号
	 * @param bonus 积分
	 * @param amount 产品数量(计量单位)
	 * @param product 产品
	 * @param organId 当前机构编号
	 * @param oppOrganId 对方机构编号
	 * @param year 年
	 * @param month 月
	 * @param bonusType 积分类型
	 * @param uploadId 上传日志编号
	 * @param remark 备注
	 * @return
	 * @throws Exception
	 */
	public SBonusDetail addSBonusDetail(Integer accountId, Double bonus, Double amount, Product product, String organId, String oppOrganId, int year, int month, BonusType bonusType, int uploadId, String remark) throws Exception {
		SBonusDetail abd = new SBonusDetail();
		abd.setAccountId(accountId);
		abd.setAmount(amount);
//		abd.setAmount(productCountMap.get(productId) * product.getBoxquantity());
		abd.setBonusPoint(bonus);
//		if(sBonusTarget != null) {
//			abd.setBonusTarget(sBonusTarget.getTargetAmount());
//		} else {
//			abd.setBonusTarget(0d);
//		}
		abd.setBonusType(bonusType.getValue());
		abd.setOppOrganId(oppOrganId);
		abd.setOrganId(organId);
		if(product != null) {
			abd.setMcode(product.getmCode());
			abd.setProductName(product.getProductname());
			abd.setSpec(product.getSpecmode());
		}
		abd.setYear(year);
		abd.setMakeDate(DateUtil.getCurrentDate());
		abd.setUploadId(uploadId);
		abd.setRemark(remark);
		abd.setMonth(month);
		appSBonusDetail.addSBonusDetail(abd);
		return abd;
	}
	
	public static void addBonusAffiche(String title, String content, String organId) throws Exception { 
		AppAffiche appAffiche = new AppAffiche();
		Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("affiche", 0, ""));
		Affiche affiche = new Affiche();
		affiche.setId(id);
		affiche.setAffichecontent(content);
		affiche.setAfficheorganid(organId);
		affiche.setAffichetitle(title);
		affiche.setAffichetype(4);
		affiche.setMakedate(DateUtil.getCurrentDate());
		affiche.setIsPublish("1");
		appAffiche.addAffiche(affiche);
	}
	
	public void addSBonusAccount(Organ organ, String mobile, String name) throws Exception {
		SBonusAccount sba = new SBonusAccount();
		sba.setActiveFlag(1);
		sba.setAddress(organ.getOaddr());
		sba.setCompanyName(organ.getOrganname());
		sba.setMobile(mobile);
		sba.setName(name);
		sba.setOrganId(organ.getId());
		appSBonusAccount.addSBonusAccount(sba);
	}
	
	private Product getProduct(String pid) throws Exception{
		Product product = productMap.get(pid);
		if(product == null){
			product = ap.getProductByID(pid);
			productMap.put(pid, product);
		}
		return product;
	}

	public static void addSBonusLog(String billNo, String msg, BonusType bonusType, String organId) throws Exception {
		AppSBonusLog appSBonusLog = new AppSBonusLog();
		SBonusLog sBonusLog = new SBonusLog();
		sBonusLog.setBillNo(billNo);
		sBonusLog.setBonusType(bonusType.getValue());
		sBonusLog.setIsSent(0);
		sBonusLog.setLogMsg(msg);
		sBonusLog.setMakeDate(DateUtil.getCurrentDate());
		sBonusLog.setOrganId(organId);
		appSBonusLog.addSBonusLog(sBonusLog);
	}
	
	public static void main(String[] args) {
		DecimalFormat decimalFormat = new DecimalFormat("###");
		System.out.println(decimalFormat.format(-1.50));
	}
	
	public static String getWhereCondition(UsersBean users) throws Exception { 
		StringBuffer condition = new StringBuffer();
		if((users.getUserType() != null && UserType.getAllValueString().contains(users.getUserType().toString()))
				|| "拜耳作物科学有限公司".equals(users.getMakeorganname())
				|| "拜耳作物科学杭州工厂".equals(users.getMakeorganname())) { //销售人员与工厂人员
			condition.append(" (o.id in ("); 
			condition.append("select organId from USER_CUSTOMER where userid = "+users.getUserid()+" ");
			condition.append("UNION ALL ");
			condition.append("select organId from USER_CUSTOMER uc ");
			condition.append("join S_USER_AREA sua on uc.userid = sua.userid and sua.areaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			condition.append("or (o.organType="+OrganType.Dealer.getValue()+" and o.organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") and o.areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)))) ");
		} else {//渠道人员
			/*condition.append(" (o.id in (");
			condition.append("select VISITORGAN from USER_VISIT where USERID ="+users.getUserid());
			condition.append(") or o.id in (");
			condition.append("select VISITORGAN from ORGAN_VISIT where USERID ="+users.getUserid());
			condition.append(") or o.id in (");
			condition.append("select organizationid from S_TRANSFER_RELATION where  opporganid = '"+users.getMakeorganid()+"')) ");*/
			condition.append(" o.id in (");
			condition.append("select organizationid from S_TRANSFER_RELATION where  opporganid = '"+users.getMakeorganid()+"') ");
		}
		return condition.toString();
	}
	
	public static String getWhereConditionForCustomerInfo(UsersBean users) throws Exception { 
		StringBuffer condition = new StringBuffer();
		if((users.getUserType() != null && UserType.getAllValueString().contains(users.getUserType().toString()))
				|| "拜耳作物科学有限公司".equals(users.getMakeorganname())
				|| "拜耳作物科学杭州工厂".equals(users.getMakeorganname())) { //销售人员与工厂人员
			condition.append(" (o.id in ("); 
			condition.append("select organId from USER_CUSTOMER where userid = "+users.getUserid()+" ");
			condition.append("UNION ALL ");
			condition.append("select organId from USER_CUSTOMER uc ");
			condition.append("join S_USER_AREA sua on uc.userid = sua.userid and sua.areaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			condition.append("or (o.organType="+OrganType.Dealer.getValue()+" and o.organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") and o.areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID))) ");
			
			condition.append("or (o.organType="+OrganType.Dealer.getValue()+" and o.organModel ="+DealerType.BKD.getValue()+" and o.areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			condition.append(" and not EXISTS (select id from USER_CUSTOMER where organid = o.id)))");
		} else {//渠道人员
			condition.append(" o.id in (");
			condition.append("select organizationid from S_TRANSFER_RELATION where  opporganid = '"+users.getMakeorganid()+"') ");
		}
		return condition.toString();
	}
	
	public static String getWhereConditionForAuditCustomer(UsersBean users) throws Exception { 
		StringBuffer condition = new StringBuffer();
		if(UserType.SS.getValue().equals(users.getUserType())
				|| UserType.SR.getValue().equals(users.getUserType())) {
			condition.append("o.organType="+OrganType.Dealer.getValue()+" and o.organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") and o.areas in (");
		} else if(UserType.ASM.getValue().equals(users.getUserType())){
			condition.append("o.organType="+OrganType.Dealer.getValue());
			condition.append(" and ((o.organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") ");
			condition.append("AND o.parentid IN (SELECT organid FROM	USER_CUSTOMER WHERE	userid ="+users.getUserid()+")");
			
			/*condition.append(" AND NOT EXISTS (SELECT ID FROM S_USER_AREA WHERE areaid IN (");
			condition.append("select parentid from S_BONUS_AREA where id in (");
			condition.append("SELECT salesareaid FROM SALES_AREA_COUNTRY WHERE countryareaid = o.areas))) ");
			
			condition.append(" AND NOT EXISTS (SELECT ID FROM S_USER_AREA WHERE areaid IN (");
			condition.append("SELECT salesareaid FROM SALES_AREA_COUNTRY WHERE countryareaid = o.areas)) ");*/
			
			condition.append(") OR (o.organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") ");
			condition.append("AND o.areas IN (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			/*condition.append("AND NOT EXISTS (SELECT ID FROM S_USER_AREA WHERE areaid IN (");
			condition.append("SELECT salesareaid FROM SALES_AREA_COUNTRY WHERE countryareaid = o.areas)) ");*/
			
			condition.append(" ) or ( o.organModel ="+DealerType.BKD.getValue()+" and o.areas in (");
//			condition.append("o.organType="+OrganType.Dealer.getValue()+" and o.organModel ="+DealerType.BKD.getValue()+" and o.areas in (");
		} else {
			return null;
		}
		condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
		condition.append("where salesareaid in (");
		condition.append("select id from S_BONUS_AREA ");
		condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
		condition.append("connect by prior id = PARENTID)) ");
		if(UserType.ASM.getValue().equals(users.getUserType())){
			condition.append("))");
		}
		/*if((users.getUserType() != null && UserType.getAllValueString().contains(users.getUserType().toString()))
				||  OrganType.Plant.getValue().equals(users.getOrganType())) { //销售人员与工厂人员
			condition.append(" (o.id in (");
			condition.append("select organId from USER_CUSTOMER where userid = "+users.getUserid()+" ");
			condition.append("UNION ALL ");
			condition.append("select organId from USER_CUSTOMER uc ");
			condition.append("join S_USER_AREA sua on uc.userid = sua.userid and sua.areaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			condition.append("or (o.organType="+OrganType.Dealer.getValue()+" and o.organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") and o.areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)))) ");
		} else {//渠道人员
			condition.append(" (o.id in (");
			condition.append("select VISITORGAN from USER_VISIT where USERID ="+users.getUserid());
			condition.append(") or o.id in (");
			condition.append("select VISITORGAN from ORGAN_VISIT where USERID ="+users.getUserid());
			condition.append(") or o.id in (");
			condition.append("select organizationid from S_TRANSFER_RELATION where  opporganid = '"+users.getMakeorganid()+"')) ");
		}*/
		return condition.toString();
	}
	
	public static String getWhereConditionForAppraise(UsersBean users) throws Exception { 
		StringBuffer condition = new StringBuffer();
		if((users.getUserType() != null && UserType.getAllValueString().contains(users.getUserType().toString()))
				||  OrganType.Plant.getValue().equals(users.getOrganType())) { //销售人员与工厂人员
			condition.append(" (o.id in (");
			condition.append("select organId from USER_CUSTOMER uc ");
			condition.append("join S_USER_AREA sua on uc.userid = sua.userid and sua.areaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			condition.append("or o.areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID))) ");
		} else {//渠道人员
			condition.append(" o.id="+users.getMakeorganid());
		}
		return condition.toString();
	}
	
	public static SBonusConfig getCurrentSBonusConfigByType(int type) throws Exception { 
		AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		return appSBonusConfig.getPeriodConfig(String.valueOf(year), DateUtil.getCurrentDateString(), type);
	}

	public static String getWhereConditionForAuditUser(UsersBean loginUsers) {
		StringBuffer condition = new StringBuffer();
		condition.append(" o.areas in (");
		condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
		condition.append("where salesareaid in (");
		condition.append("select id from S_BONUS_AREA ");
		condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+loginUsers.getUserid()+") ");
		condition.append("connect by prior id = PARENTID)) ");
		return condition.toString(); 
	}

	public static String getWhereConditionForAuditStockMove(UsersBean loginUsers) {
		StringBuffer condition = new StringBuffer(); 
		condition.append("areas in (");
		condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
		condition.append("where salesareaid in (");
		condition.append("select id from S_BONUS_AREA ");
		condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+loginUsers.getUserid()+") ");
		condition.append("connect by prior id = PARENTID)) ");
		return condition.toString(); 
	}
	
}
