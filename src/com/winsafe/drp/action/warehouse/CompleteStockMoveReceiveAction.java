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
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;

public class CompleteStockMoveReceiveAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(CompleteStockMoveReceiveAction.class);
	
	private AppProductStockpile apsp = new AppProductStockpile();
	private AppFUnit appFunit = new AppFUnit();
	private AppProduct appProduct = new AppProduct();
	private AppTakeTicketIdcode apidcode = new AppTakeTicketIdcode();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        //初始化
		initdata(request);
		AppStockMove asm = new AppStockMove();
		AppTakeTicket  att =new AppTakeTicket();
		AppTakeTicketDetail apid = new AppTakeTicketDetail();
		AppIdcode appIdcode = new AppIdcode();
		

		try{
			String smid = request.getParameter("SMID");
			StockMove sm = asm.getStockMoveByID(smid);
			if (sm.getIsshipment() == 0) {
				request.setAttribute("result", "databases.record.noshipment");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if (sm.getIsblankout() == 1) {
				request.setAttribute("result", "datebases.record.isblankout");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (sm.getIscomplete() == 1) { 
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			TakeTicket tt = att.getTakeTicket(smid);
			List<TakeTicketDetail> ttdList = apid.getTakeTicketDetailByTtid(tt.getId());
			// 更新单据已完成
			asm.updStockMoveIsComplete(smid, 1, userid);
			//更新条码状态
			appIdcode.updIdcodeByttid(tt.getId(), 1, 0);
			appIdcode.updIdcodeByttid(tt.getId(), tt.getInwarehouseid());
			List<TakeTicketIdcode> ttiList = apidcode.getTakeTicketIdcodeByttid(tt.getId());
			// 增加库存
			inProductStockpileTTD(smid,ttdList, ttiList,tt.getInwarehouseid(), Constants.TT_WASTE_BOOK_MEMO[tt.getBsort()]);
			request.setAttribute("result", "databases.add.receivesuccess");
			DBUserLog.addUserLog(request, "编号：" + smid);
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
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
	private void inProductStockpileTTD(String billNo,List<TakeTicketDetail> ttdList,List<TakeTicketIdcode> ttiList,String inWarehouseid,String memo) throws Exception {
		Map<String, Double> idcodeBatchMap = collectIdcode(ttiList);
		inProductStockpileTTD(billNo,ttdList, idcodeBatchMap, inWarehouseid, memo);
	}
	private void inProductStockpileTTD(String billNo,List<TakeTicketDetail> ttdList,Map<String, Double> idcodeBatchMap,String inWarehouseid,String memo) throws Exception {
		// 根据批次增加出库仓库库存
		for (TakeTicketDetail ttd : ttdList) {
			//总数量
			Double quantity = appFunit.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getRealQuantity());
			Integer unitid = appProduct.getProductByID(ttd.getProductid()).getSunit();
			// 先增加所有批次的库存
			for(String key : idcodeBatchMap.keySet()){
				String[] objs = key.split("_");
				String pid = objs[0];
				String batch = objs[1];
				if(pid.equals(ttd.getProductid())){
					// 批次的数量
					Double batchQuantity = idcodeBatchMap.get(key);
					// 增加对应批次库存
					apsp.inProductStockpile(pid, batch, batchQuantity, inWarehouseid, "000", billNo,memo,unitid,true);
					quantity = quantity - batchQuantity;
				}
			}
			// 如果quantity不为0,则对应增加特殊批次的库存
			if(quantity != 0){
				apsp.inProductStockpile(ttd.getProductid(), Constants.NO_BATCH, quantity, inWarehouseid, "000", billNo,memo,unitid,true);
			}
		}
	}
	/**
	 * 根据条码汇总批次数量
	 * Create Time 2014-11-18 下午03:35:11 
	 * @param ttiList
	 * @return
	 */
	private Map<String, Double> collectIdcode(List<TakeTicketIdcode> ttiList){
		Map<String, Double> idcodeBatchMap = new HashMap<String, Double>();
		for(TakeTicketIdcode tti : ttiList){
			if(tti == null) {
				continue;
			}
			String pid = tti.getProductid();
			String batch = tti.getBatch();
			String key = pid + "_" + batch;
			if(StringUtil.isEmpty(batch)){
				continue;
			}
			Double quantity = idcodeBatchMap.get(key);
			if(quantity == null){
				idcodeBatchMap.put(key, tti.getPackquantity());
			}else {
				idcodeBatchMap.put(key, quantity + tti.getPackquantity());
			}
		}
		return idcodeBatchMap;
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
}
