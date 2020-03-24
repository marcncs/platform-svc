package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.notification.dao.AppNotification;

public class CompleteStockAlterMoveReceiveAction extends BaseAction
{
	private Logger logger = Logger.getLogger(CompleteStockAlterMoveReceiveAction.class);
	
	private AppProductStockpile apsp = new AppProductStockpile();
	private AppFUnit appFunit = new AppFUnit();
	private AppProduct appProduct = new AppProduct();
	private AppTakeTicketIdcode apidcode = new AppTakeTicketIdcode();
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//初始化
		initdata(request);
		AppStockAlterMove asm = new AppStockAlterMove();
		AppTakeTicketDetail apid = new AppTakeTicketDetail();
		AppStockAlterMoveIdcode appmi = new AppStockAlterMoveIdcode();
		AppTakeTicket  att =new AppTakeTicket();
		AppIdcode appIdcode = new AppIdcode();
		AppStockAlterMoveDetail appSamd = new AppStockAlterMoveDetail();

		try
		{
			String omid = request.getParameter("omid");
			StockAlterMove sm = asm.getStockAlterMoveByID(omid);
			if (sm.getIsshipment() == 0)
			{
				request.setAttribute("result", "databases.record.noshipment");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (sm.getIsblankout() == 1)
			{
				request.setAttribute("result", "datebases.record.isblankout");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (sm.getIscomplete() == 1)
			{
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			String inWarehouseId = request.getParameter("inwarehouseid");
			String movecause = request.getParameter("movecause");
			if(!inWarehouseId.equals(sm.getInwarehouseid())) {
				sm.setOldInwarehouseId(sm.getInwarehouseid());
				sm.setInwarehouseid(inWarehouseId);
				sm.setChangeReason(movecause);
			}
			//修改签收数量
			String[] pids = request.getParameterValues("pid");
			String[] quantitys = request.getParameterValues("receiveQuantity");
			String[] receiveRemarks = request.getParameterValues("receiveRemark");
			List<StockAlterMoveDetail> samdList = appSamd.getUsefulStockAlterMoveDetailBySamID(omid);
			if(pids != null) {
				updRealQuantity(samdList, pids, quantitys,receiveRemarks); 
			}
			List<StockAlterMoveIdcode> samIdcodes = appmi.getUsefulStockAlterMoveIdcodeBysamid(omid);
			//得到TT明细
			TakeTicket tt = att.getTakeTicket(omid);
			if(!sm.getInwarehouseid().equals(tt.getInwarehouseid())) {
				tt.setInwarehouseid(sm.getInwarehouseid());
			}
			att.updTakeTicket(tt);
			// 更新单据已完成
			sm.setIscomplete(1);
			sm.setReceiveid(userid);
			sm.setReceivedate(DateUtil.getCurrentDate());
			asm.updstockAlterMove(sm);
//			asm.updStockAlterMoveIsComplete(omid, 1, userid);
			//更新条码状态
			appIdcode.updIdcodeByttid(tt.getId(), 1, 0);
			appIdcode.updIdcodeByttid(tt.getId(), tt.getInwarehouseid());
			// 增加库存
			if(pids != null) {
				inProductStockpileTTD(samdList, samIdcodes, tt.getInwarehouseid(), Constants.TT_WASTE_BOOK_MEMO[tt.getBsort()]);
			}
			if(!StringUtil.isEmpty(sm.getNccode())) {
				AppNotification appN = new AppNotification();
				appN.delNotificationLog(sm.getNccode().replaceAll("^(0+)", ""));
			}
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(request, "编号：" + omid);
			return mapping.findForward("success");
		}
		catch(Exception e)
		{
			logger.error("", e);
			throw e;
		}
		finally
		{
		}
	}
	/**
	 * 更新确认数量
	 * @param ttds
	 * @param pids
	 * @param quantitys
	 * @throws Exception
	 */
	private void updRealQuantity(List<StockAlterMoveDetail> samds,String[] pids ,String[] quantitys,String[] receiveRemarks) throws Exception{
		int length = pids.length;
		for(int i=0 ; i<length ; i++ ){
			String pid = pids[i];
			Double receiveQuantity = NumberUtil.getDouble(quantitys[i]);
			String receiveRemark = receiveRemarks[i];
			for(StockAlterMoveDetail sam : samds){
				if(sam.getProductid().equals(pid)){
					sam.setReceiveQuantity(receiveQuantity);
					sam.setReceiveRemark(receiveRemark);
					break;
				}
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
	 * 根据订购单据的检货数量增加库存
	 * Create Time 2014-10-16 上午11:23:26 
	 * @param ttdList
	 * @param outWarehouseid
	 * @throws Exception
	 */
	private void inProductStockpileTTD(List<TakeTicketDetail> ttdList,String inWarehouseid,String memo) throws Exception {
		for (TakeTicketDetail ttd : ttdList) {
			// 减出库仓库库存
			apsp.inProductStockpile(ttd.getProductid(), ttd.getBatch(), ttd
					.getQuantity(), inWarehouseid, "000", ttd.getTtid(),
					memo,ttd.getUnitid(),true);
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
