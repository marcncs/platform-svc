package com.winsafe.drp.action.warehouse;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.util.DateUtil;

public class AuditTakeTicketAction extends BaseAction
{
	private static Logger logger = Logger.getLogger(AuditTakeTicketAction.class);
	private AppWarehouse aw = new AppWarehouse();
	private AppTakeTicket apb = new AppTakeTicket();
	private AppTakeTicketDetail apid = new AppTakeTicketDetail();
	private AppTakeTicketIdcode apidcode = new AppTakeTicketIdcode();
	private AppProductStockpile apsp = new AppProductStockpile();
	private AppIdcode appIdcode = new AppIdcode();
	private AppProductStockpileAll appPsp = new AppProductStockpileAll();
	private AppFUnit appFunit = new AppFUnit();
	private AppProduct appProduct = new AppProduct();
	private AppStockAlterMoveDetail appSamd = new AppStockAlterMoveDetail();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//初始化
		initdata(request);
		try
		{
			//检查状态是否在处理中
			if (!isTokenValid(request, true))
			{
				request.setAttribute("result", "databases.record.alreadysubmit");
				saveToken(request);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			//TT小票ID
			String ttid = request.getParameter("ttid");
			//TT对象
			TakeTicket tt = apb.getTakeTicketById(ttid);
			//TT已作废的情况
			if (tt.getIsblankout() == 1)
			{
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			//TT已复核的情况
			if (tt.getIsaudit() == 1)
			{
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			//检查状态是否在处理中
			if (tt.getIsChecked() != null && tt.getIsChecked() == 1)
			{
				request.setAttribute("result", "databases.record.codeinprocess");
				saveToken(request);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			//得到TT明细
			List<TakeTicketDetail> ttds = apid.getTakeTicketDetailByTtid(ttid);
			//更新确认数量
			String[] pids = request.getParameterValues("pid");
			String[] quantitys = request.getParameterValues("realQuantity");
			updRealQuantity(ttds, pids, quantitys);
			
			
			//判断库存是否正确
			Warehouse outWarehouse = aw.getWarehouseByID(tt.getWarehouseid());
			// 单据类型为渠道退货的不查检库存
			if(!tt.getBillno().startsWith("OW")){
				// 如果仓库属性[是否负库存]为0,则检查库存
				if(outWarehouse.getIsMinusStock() == null || outWarehouse.getIsMinusStock() == 0){
					// 渠道退货单据不判断库存
					if(!tt.getBillno().startsWith("OW")){
						Set<String> checkStockMsg = new HashSet<String>();
						if(!checkStock(ttds, tt.getWarehouseid(),checkStockMsg)){
							request.setAttribute("result", checkStockMsg);
							return new ActionForward("/sys/lockrecordclose.jsp");
						}
					}
				}
			}
			// 修改检货数量
			for(TakeTicketDetail ttd : ttds){
				apid.updTakeQuantity(tt.getBsort(), tt.getBillno(), ttd.getProductid(), ttd.getRealQuantity());
			}
			Date currentDate = DateUtil.getCurrentDate();
			// 更新小票状态为已复核
			tt.setIsaudit(1);
			tt.setAuditid(userid);
			tt.setAuditdate(currentDate);
			
			//更新单据已检货
			apb.updTakeStatus(tt.getBsort(), tt.getBillno(), 1);
			//更新单据已出库
			apb.updShipment(tt.getBsort(), tt.getBillno(), 1,userid, DateUtil.formatDateTime(currentDate));
			// 将tti中的码复制到订购单据/退货单据/转仓单据中
			if(tt.getBsort() == 1) {
				apidcode.addIdcodeToStockAlterMoveIdcode(tt.getBsort(), tt.getId(), tt.getBillno());
			} else {
				apidcode.addIdcodeToOrder(tt.getBsort(), tt.getId(), tt.getBillno());
			}
			// 扣减出库仓库库存
			List<TakeTicketIdcode> ttiList = apidcode.getTakeTicketIdcodeByttid(ttid);
			outProductStockpileTTD(ttds, ttiList, tt.getWarehouseid());
			//更新条码为出库状态
			appIdcode.updIdcodeByttid(tt.getId(), 0, 1);
			apb.updTakeTicket(tt);    
			DBUserLog.addUserLog(request, "编号：" + tt.getBillno());
			
			//签收
			Warehouse inWarehouse = aw.getWarehouseByID(tt.getInwarehouseid());
			// 仓库类型为自动签收或单据类型为退货的也自动签收
			if(inWarehouse != null && inWarehouse.getIsautoreceive() == 1 || tt.getBillno().startsWith("OW")){
				//更新单据已完成
				apb.updIsComplete(tt.getBsort(), tt.getBillno(), 1, userid);
				inProductStockpileTTD(tt.getBillno(),ttds, ttiList,tt.getInwarehouseid(), Constants.TT_WASTE_BOOK_MEMO[tt.getBsort()]);
				//更新条码为可用状态
				appIdcode.updIdcodeByttid(tt.getId(), 1, 0);
				appIdcode.updIdcodeByttid(tt.getId(), tt.getInwarehouseid());
				// 如果为发货单时更新签收数量
				if(tt != null && tt.getBsort() == 1){
					updateStockAlterMoveReceive(tt.getBillno(), ttds);
				}
				//记录签收日志
				addUserlog(tt.getBsort(),tt.getBillno());
			}
			request.setAttribute("result", "databases.audit.success");
			return mapping.findForward("audit");
		}
		catch(Exception e)
		{
			logger.error("",e);
			throw e;
		} 
	}
	/**
	 * 更新发货单的签收数量
	 * Create Time 2014-12-25 下午02:30:14 
	 */
	private void updateStockAlterMoveReceive(String billno,List<TakeTicketDetail> ttds) throws Exception{
		for(TakeTicketDetail ttd : ttds){
			appSamd.updReceiveQuantity(billno, ttd.getProductid(), ttd.getRealQuantity());
		}
	}
	
	
	/**
	 * 检查库存
	 * Create Time 2014-11-18 上午11:39:55 
	 * @param ttds
	 * @param outWarehouseid
	 * @throws Exception 
	 */
	private boolean checkStock(List<TakeTicketDetail> ttds,String outWarehouseid,Set<String> errorMsg) throws  Exception{
		boolean result = true;
		for(TakeTicketDetail ttd : ttds){
			Double stockQuantity = appPsp.getProductStockpileAllByProductID(ttd.getProductid(), Long.valueOf(outWarehouseid));
			//单位换算
			Double realQuantity = appFunit.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getRealQuantity());
			if(stockQuantity < realQuantity){
				result = false;
				errorMsg.add("产品[ " + ttd.getProductid() + " " + ttd.getProductname() +" ]库存不足 \r\n");
			}
		}
		return result;
	}
	/**
	 * 更新确认数量
	 * @param ttds
	 * @param pids
	 * @param quantitys
	 * @throws Exception
	 */
	private void updRealQuantity(List<TakeTicketDetail> ttds,String[] pids ,String[] quantitys) throws Exception{
		int length = pids.length;
		for(int i=0 ; i<length ; i++ ){
			String pid = pids[i];
			Double realQuantity = NumberUtil.getDouble(quantitys[i]);
			for(TakeTicketDetail ttd : ttds){
				if(ttd.getProductid().equals(pid)){
					ttd.setRealQuantity(realQuantity);
					break;
				}
			}
		}
	}
	/**
	 * 根据检货详情及条码扣减库存
	 * @param ttdList
	 * @param ttiList
	 * @param outWarehouseid
	 * @throws Exception
	 */
	private void outProductStockpileTTD(List<TakeTicketDetail> ttdList,List<TakeTicketIdcode> ttiList,String outWarehouseid) throws Exception {
		Map<String, Double> idcodeBatchMap = collectIdcode(ttiList);
		outProductStockpileTTD(ttdList, idcodeBatchMap, outWarehouseid);
	}
	
	private void outProductStockpileTTD(List<TakeTicketDetail> ttdList,Map<String, Double> idcodeBatchMap,String outWarehouseid) throws Exception {
		// 根据批次减出库仓库库存
		for (TakeTicketDetail ttd : ttdList) {
			//总数量
			Double quantity = appFunit.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getRealQuantity());
			Integer unitid = appProduct.getProductByID(ttd.getProductid()).getSunit();
			// 先扣减所有批次的库存
			for(String key : idcodeBatchMap.keySet()){
				String[] objs = key.split("_");
				String pid = objs[0];
				String batch = objs[1];
				if(pid.equals(ttd.getProductid())){
					// 批次的数量
					Double batchQuantity = idcodeBatchMap.get(key);
					// 扣减对应批次库存
					apsp.outProductStockpile(pid, batch, batchQuantity, outWarehouseid, "000", ttd.getTtid(),
							"检货小票-出货", unitid,true);
					quantity = quantity - batchQuantity;
				}
			}
			// 如果quantity不为0,则对应扣减特殊批次的库存
			if(quantity != 0){
				apsp.outProductStockpile(ttd.getProductid(), Constants.NO_BATCH, quantity, outWarehouseid, "000", ttd.getTtid(),
						"检货小票-出货", unitid,true);
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
	 * 记录签收入库日志
	 * Create Time 2014-10-16 下午05:22:37 
	 * @param bsort
	 * @param billno
	 * @throws Exception
	 */
	private void addUserlog(Integer bsort,String billno) throws Exception{
		// bsort 0|订购 
		if(bsort == 0){
			DBUserLog.addUserLog(userid, 7, "入库>>订购入库>>签收订购入库,编号：" + billno);
		}else {
			DBUserLog.addUserLog(userid, 7, "入库,编号：" + billno);
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
	 * 根据检货出库详情单增加库存
	 * Create Time 2014-10-16 上午11:23:26 
	 * @param ttdList
	 * @param outWarehouseid
	 * @throws Exception
	 */
	private void inProductStockpileTTD(List<TakeTicketDetail> ttdList,String inWarehouseid,String memo) throws Exception {
		for (TakeTicketDetail ttd : ttdList) {
			// 增加库仓库库存
			apsp.inProductStockpile(ttd.getProductid(), ttd.getBatch(), ttd
					.getRealQuantity(), inWarehouseid, "000", ttd.getTtid(),
					memo,ttd.getUnitid(),true);
		}
	}
	/**
	 * 根据检货出库详情单扣减库存
	 * Create Time 2014-10-16 上午11:23:26 
	 * @param ttdList
	 * @param outWarehouseid
	 * @throws Exception
	 */
	private void outProductStockpileTTD(List<TakeTicketDetail> ttdList,String outWarehouseid) throws Exception {
		for (TakeTicketDetail ttd : ttdList) {
			// 减出库仓库库存
			apsp.outProductStockpile(ttd.getProductid(), ttd.getBatch(), ttd
					.getRealQuantity(), outWarehouseid, "000", ttd.getTtid(),
					"检货小票-出货", ttd.getUnitid(),true);
		}
	}
}
