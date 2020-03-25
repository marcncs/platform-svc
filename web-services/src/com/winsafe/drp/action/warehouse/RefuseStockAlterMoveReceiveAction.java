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
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSalesConsumHistory;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.report.pojo.SalesConsumHistory;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.FUnitUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppInvoice;
import com.winsafe.sap.pojo.Invoice;

public class RefuseStockAlterMoveReceiveAction extends BaseAction
{
	private static Logger logger = Logger.getLogger(RefuseStockAlterMoveReceiveAction.class);
	private AppProductStockpile apsp = new AppProductStockpile();
	private AppFUnit appFunit = new AppFUnit();
	private AppProduct appProduct = new AppProduct();
	private AppTakeTicketIdcode apidcode = new AppTakeTicketIdcode();
	private AppSalesConsumHistory appSalesConsumHistory = new AppSalesConsumHistory();
	private AppBaseResource appBaseResource = new AppBaseResource();
	private AppFUnit appFUnit = new AppFUnit();
	private AppInvoice appInvoice = new AppInvoice();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//初始化
		initdata(request);
		AppStockAlterMove asm = new AppStockAlterMove();
		AppTakeTicketDetail apid = new AppTakeTicketDetail();
		AppTakeTicket  att =new AppTakeTicket();
		AppIdcode appIdcode = new AppIdcode();

		try
		{
			String omid = request.getParameter("smid");
			String refuseReason = request.getParameter("refuseReason");
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
			//设置拒签(作废)相关信息
			sm.setIsblankout(1);
			sm.setBlankoutid(userid);
			sm.setBlankoutreason(refuseReason);
			sm.setBlankoutdate(Dateutil.getCurrentDate());
			TakeTicket tt = att.getTakeTicket(omid);
			tt.setBlankoutid(userid);
			tt.setIsblankout(1);
			tt.setBlankoutreason(refuseReason);
			tt.setBlankoutdate(Dateutil.getCurrentDate());
			// 将单据的出库仓库的出库数量还原回去
			List<TakeTicketDetail> ttds = apid.getTakeTicketDetailByTtid(tt.getId());
			List<TakeTicketIdcode> ttiList = apidcode.getTakeTicketIdcodeByttid(tt.getId());
			inProductStockpileTTD(ttds, ttiList,tt.getWarehouseid(), "拒签");
			//更新条码状态
			appIdcode.updIdcodeByttid(tt.getId(), 1, 0);
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(request, "编号：" + omid);
			
			return mapping.findForward("success");
		}
		catch(Exception e)
		{
			logger.error("",e);
		}
		finally
		{
		}
		return new ActionForward(mapping.getInput());
	}

	/**
	 * 根据条码中的批次增加库存
	 * @param ttdList
	 * @param ttiList
	 * @param inWarehouseid
	 * @param memo
	 * @throws Exception
	 */
	private void inProductStockpileTTD(List<TakeTicketDetail> ttdList,List<TakeTicketIdcode> ttiList,String inWarehouseid,String memo) throws Exception {
		Map<String, Double> idcodeBatchMap = collectIdcode(ttiList);
		inProductStockpileTTD(ttdList, idcodeBatchMap, inWarehouseid, memo);
	}
	
	private void inProductStockpileTTD(List<TakeTicketDetail> ttdList,Map<String, Double> idcodeBatchMap,String inWarehouseid,String memo) throws Exception {
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
					apsp.inProductStockpile(pid, batch, batchQuantity, inWarehouseid, "000", ttd.getTtid(),memo,unitid,true);
					quantity = quantity - batchQuantity;
				}
			}
			// 如果quantity不为0,则对应增加特殊批次的库存
			if(quantity != 0){
				apsp.inProductStockpile(ttd.getProductid(), Constants.NO_BATCH, quantity, inWarehouseid, "000", ttd.getTtid(),memo,unitid,true);
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
	
	
}
