package com.winsafe.drp.keyretailer.action.phone;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.keyretailer.util.BonusAfficheMsg;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.DateUtil;

public class CompleteStockAlterMoveReceiveAction extends BaseAction
{
	private Logger logger = Logger.getLogger(CompleteStockAlterMoveReceiveAction.class);
	
	private AppProductStockpile apsp = new AppProductStockpile();
	private AppFUnit appFunit = new AppFUnit();
	private AppProduct appProduct = new AppProduct();
	private AppUsers appUsers = new AppUsers();
	private DecimalFormat ammountFormat = new DecimalFormat("#,##0.00");
	
	private AppStockAlterMoveIdcode asami = new AppStockAlterMoveIdcode();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//初始化
//		initdata(request);
		AppStockAlterMove asm = new AppStockAlterMove();
		AppIdcode appIdcode = new AppIdcode();
		AppStockAlterMoveDetail appSamd = new AppStockAlterMoveDetail();
		StringBuffer errMsg = new StringBuffer();
		
		try
		{
			String username = request.getParameter("Username"); //登陆名
			String scannerno = request.getParameter("IMEI_number"); 
			// 判断用户是否存在
			Users loginUsers = appUsers.getUsers(username);
			String billNo = request.getParameter("billNo");
			StockAlterMove sm = asm.getStockAlterMoveByID(billNo);
			if (sm.getIsshipment() == 0)
			{
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "操作失败,单据还未出库");
				return null;
			}
			if (sm.getIsblankout() == 1)
			{
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "操作失败,单据已作废");
				return null;
			}
			if (sm.getIscomplete() == 1)
			{
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "操作失败,单据已签收");
				return null;
			}
			//修改签收数量
			List<StockAlterMoveDetail> samdList = appSamd.getUsefulStockAlterMoveDetailBySamID(billNo);
			List<StockAlterMoveIdcode> samiList =  asami.getUsefulStockAlterMoveIdcodeBysamid(billNo);
			//更新签收数量	
			updRealQuantity(samdList, samiList); 
			
			// 更新单据已完成
			sm.setIscomplete(1);
			sm.setReceiveid(userid);
			sm.setReceivedate(DateUtil.getCurrentDate());
			asm.updstockAlterMove(sm);
			//更新条码状态
			appIdcode.updIdcodeBySamId(billNo, 1, 0);
			appIdcode.updIdcodeBySamId(billNo, sm.getInwarehouseid());
			// 增加库存
			if(samiList != null && samiList.size() > 0) {
				inProductStockpileTTD(samdList, samiList, sm.getInwarehouseid(), Constants.TT_WASTE_BOOK_MEMO[1]);
			}
			AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
			//退回拒收的码
			List<TakeTicketIdcode> ttis = atti.getRemovedIdcode(sm.getId());
			if(ttis.size() > 0) {
				//生成系统消息
				AppBaseResource appBR = new AppBaseResource();
				Map<Integer, String> countUnitMap = appBR.getBaseResourceMap("CountUnit");
				Map<String,Double> qtyMap = new HashMap<String, Double>();
				Map<String,List<String>> codeMap = new HashMap<String, List<String>>();
				collectQty(ttis, qtyMap, codeMap);
				errMsg.append("单据"+sm.getId()+"中,以下拒收产品条码将不参与积分:\r\n");
				for(String productId : qtyMap.keySet()) {
					Product product = appProduct.loadProductById(productId);
					errMsg.append("产品:"+product.getProductname()+" 规格:"+product.getSpecmode()+" 数量："+ammountFormat.format(qtyMap.get(productId))+countUnitMap.get(product.getCountunit())+" 条码:\r\n");
					for(String code : codeMap.get(productId)) {
						errMsg.append(code).append("\r\n");
					}
				}
				//退回原仓库
				inProductStockpileTTD(ttis,sm.getOutwarehouseid(),sm.getId(), "拒签条码");
				//在原单据中删除拒收的条码
				atti.deRemovedIdcodeFromTakeTicketIdcode(sm.getId());
			}
			//添加积分消息
			if(errMsg.length() > 0) {
				SBonusService.addBonusAffiche(BonusAfficheMsg.TITLE, BonusAfficheMsg.getError(BonusAfficheMsg.DELIVERY_OUT_REMOVE, sm.getReceiveorganidname(),errMsg.toString()), sm.getOutOrganId());
				SBonusService.addBonusAffiche(BonusAfficheMsg.TITLE, BonusAfficheMsg.getError(BonusAfficheMsg.DELIVERY_IN_REMOVE, sm.getOutOrganName(),errMsg.toString(),errMsg.toString()), sm.getReceiveorganid());
			}
				
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, loginUsers.getUserid(),"APP","确认收货单据:"+billNo,true);
		}
		catch(Exception e)
		{
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "操作失败,系统异常");
		}
		return null;
	}
	
	private void collectQty(List<TakeTicketIdcode> ttis, Map<String, Double> qtyMap, Map<String, List<String>> codeMap) {
		for(TakeTicketIdcode tti : ttis) {
			Product product = appProduct.loadProductById(tti.getProductid());
			if(qtyMap.containsKey(tti.getProductid())) {
				qtyMap.put(tti.getProductid(), ArithDouble.add(qtyMap.get(tti.getProductid()), ArithDouble.mul(tti.getPackquantity(), product.getBoxquantity())));
			} else {
				qtyMap.put(tti.getProductid(), ArithDouble.mul(tti.getPackquantity(), product.getBoxquantity()));
			}
			if(codeMap.containsKey(tti.getProductid())) {
				codeMap.get(tti.getProductid()).add(tti.getIdcode());
			} else {
				List<String> codeList = new ArrayList<String>();
				codeList.add(tti.getIdcode());
				codeMap.put(tti.getProductid(),codeList);
			}
		}
	}

	private void inProductStockpileTTD(List<TakeTicketIdcode> ttis,
			String outwarehouseid, String billNo, String memo) throws Exception { 
		Map<String, Double> idcodeBatchMap = collectIdcodeForRefuse(ttis);
		inProductStockpileTTD(idcodeBatchMap, outwarehouseid, billNo, memo);
	}

	private void inProductStockpileTTD(Map<String, Double> idcodeBatchMap,
			String inWarehouseid, String billNo, String memo) throws Exception {
		// 根据批次增加出库仓库库存
		for(String key : idcodeBatchMap.keySet()){
			String[] objs = key.split("_");
			String pid = objs[0];
			String batch = objs[1];
			//判断产品是否相同
			// 批次的数量
			Double batchQuantity = idcodeBatchMap.get(key);
			// 增加对应批次库存
			Integer unitid = appProduct.loadProductById(pid).getSunit();
			apsp.inProductStockpile(pid, batch, batchQuantity, inWarehouseid, "000", billNo,memo,unitid,true);
		}
		
	}

	private Map<String, Double> collectIdcodeForRefuse(List<TakeTicketIdcode> ttis) { 
		Map<String, Double> idcodeBatchMap = new HashMap<String, Double>();
		for(TakeTicketIdcode smai : ttis){
			if(smai == null) {
				continue;
			}
			String pid = smai.getProductid();
			String batch = smai.getBatch();
			String key = pid + "_" + batch;
			if(StringUtil.isEmpty(batch)){
				continue;
			}
			Double quantity = idcodeBatchMap.get(key);
			if(quantity == null){
				idcodeBatchMap.put(key, smai.getPackquantity());
			}else {
				idcodeBatchMap.put(key, quantity + smai.getPackquantity());
			}
		}
		return idcodeBatchMap;
	}

	private void updRealQuantity(List<StockAlterMoveDetail> samdList,
			List<StockAlterMoveIdcode> samiList) {
		Map<String, Double> idcodeBatchMap = new HashMap<String, Double>();
		for(StockAlterMoveIdcode smai : samiList){
			if(smai == null) {
				continue;
			}
			String pid = smai.getProductid();
			Double quantity = idcodeBatchMap.get(pid);
			if(quantity == null){
				idcodeBatchMap.put(pid, smai.getQuantity());
			}else {
				idcodeBatchMap.put(pid, quantity + smai.getQuantity());
			}
		}
		
		for(StockAlterMoveDetail samd : samdList) {
			Double quantity = idcodeBatchMap.get(samd.getProductid());
			if(quantity == null){
				samd.setReceiveQuantity(0d);
			} else {
				samd.setReceiveQuantity(quantity);
			}
		}
		
	}
	
	
	/**
	 * 根据条码中的批次增加库存
	 * @param ttdList
	 * @param ttiList
	 * @param inWarehouseid
	 * @param memo
	 * @throws Exception
	 */
	private void inProductStockpileTTD(List<StockAlterMoveDetail> samdList,List<StockAlterMoveIdcode> samiIdcodes,String inWarehouseid,String memo) throws Exception {
		Map<String, Double> idcodeBatchMap = collectIdcode(samiIdcodes);
		inProductStockpileTTD(samdList, idcodeBatchMap, inWarehouseid, memo);
	}
	
	private void inProductStockpileTTD(List<StockAlterMoveDetail> samdList,Map<String, Double> idcodeBatchMap,String inWarehouseid,String memo) throws Exception {
		// 根据批次增加出库仓库库存
		for (StockAlterMoveDetail smad : samdList) {
			//总数量
			Double quantity = appFunit.getQuantity(smad.getProductid(), smad.getUnitid(), smad.getReceiveQuantity());
			Integer unitid = appProduct.getProductByID(smad.getProductid()).getSunit();
			// 先增加所有批次的库存
			for(String key : idcodeBatchMap.keySet()){
				String[] objs = key.split("_");
				String pid = objs[0];
				String batch = objs[1];
				//判断产品是否相同
				if(pid.equals(smad.getProductid())){
					// 批次的数量
					Double batchQuantity = idcodeBatchMap.get(key);
					// 增加对应批次库存
					apsp.inProductStockpile(pid, batch, batchQuantity, inWarehouseid, "000", smad.getSamid(),memo,unitid,true);
					quantity = quantity - batchQuantity;
				}
			}
			// 如果quantity不为0,则对应增加特殊批次的库存
			if(quantity != 0){
				apsp.inProductStockpile(smad.getProductid(), Constants.NO_BATCH, quantity, inWarehouseid, "000", smad.getSamid(),memo,unitid,true);
			}
		}
	}
	
	/**
	 * 根据条码汇总批次数量
	 * Create Time 2014-11-18 下午03:35:11 
	 * @param ttiList
	 * @return
	 */
	private Map<String, Double> collectIdcode(List<StockAlterMoveIdcode> samiIdcodes){
		Map<String, Double> idcodeBatchMap = new HashMap<String, Double>();
		for(StockAlterMoveIdcode smai : samiIdcodes){
			if(smai == null) {
				continue;
			}
			String pid = smai.getProductid();
			String batch = smai.getBatch();
			String key = pid + "_" + batch;
			if(StringUtil.isEmpty(batch)){
				continue;
			}
			Double quantity = idcodeBatchMap.get(key);
			if(quantity == null){
				idcodeBatchMap.put(key, smai.getPackquantity());
			}else {
				idcodeBatchMap.put(key, quantity + smai.getPackquantity());
			} 
		}
		return idcodeBatchMap;
	}
}
