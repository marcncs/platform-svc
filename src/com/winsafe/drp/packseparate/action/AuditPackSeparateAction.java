package com.winsafe.drp.packseparate.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.packseparate.dao.AppPackSeparate;
import com.winsafe.drp.packseparate.dao.AppPackSeparateDetail;
import com.winsafe.drp.packseparate.pojo.PackSeparate;
import com.winsafe.drp.packseparate.pojo.PackSeparateDetail;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

public class AuditPackSeparateAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AuditPackSeparateAction.class);
	private AppPackSeparate aps = new AppPackSeparate();
	private AppPackSeparateDetail apsd = new AppPackSeparateDetail();
	private AppWarehouse aw = new AppWarehouse();
	private AppProduct appProduct = new AppProduct();
	private AppProductStockpileAll appPsp = new AppProductStockpileAll();
	private AppFUnit appFunit = new AppFUnit();
	private AppProductStockpile apsp = new AppProductStockpile();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try
		{
			//TT小票ID
			String psid = request.getParameter("ID");
			//TT对象
			PackSeparate ps = aps.getPackSeparateById(psid);
			//TT已作废的情况
			if (ps.getIsAudit() == 1)
			{
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			//得到TT明细
			List<PackSeparateDetail> psdList = apsd.getPackSeparateDetailsByPsid(psid);
						
			
			//判断库存是否正确
			/*
			Warehouse outWarehouse = aw.getWarehouseByID(ps.getWarehouseId());
			if(outWarehouse.getIsMinusStock() == null || outWarehouse.getIsMinusStock() == 0){
				Set<String> checkStockMsg = new HashSet<String>();
				if(!checkStock(psdList, ps.getWarehouseId(),checkStockMsg)){
					request.setAttribute("result", checkStockMsg);
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}*/
			
			// 更新小票状态为已复核
			ps.setIsAudit(1);
			ps.setAuditId(userid);
			ps.setAuditDate(DateUtil.getCurrentDate());
			// 扣减出库仓库库存
			outProductStockpileTTD(psdList, ps.getWarehouseId(), ps.getId());
			// 添加入库仓库库存
			inProductStockpileTTD(psdList, ps.getWarehouseId(), ps.getId());

			DBUserLog.addUserLog(request, "编号：" + psid);
			
			request.setAttribute("result", "databases.audit.success");
			return mapping.findForward("result");
		}
		catch(Exception e)
		{
			logger.error("复核分包单据发生异常",e);
			throw e;
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
	 * 根据检货详情添加库存
	 * @param ttdList
	 * @param ttiList
	 * @param outWarehouseid
	 * @throws Exception
	 */
	private void inProductStockpileTTD(List<PackSeparateDetail> psdList,String outWarehouseid,String billNo) throws Exception {
		Map<String, Double> batchQuantityMap = collectInBatchQuantity(psdList);
		inProductStockpileTTD(batchQuantityMap,outWarehouseid, billNo);
	}
	
	private void inProductStockpileTTD(Map<String, Double> batchQuantityMap, String outWarehouseid, String billNo) throws Exception {
		for(String key : batchQuantityMap.keySet()) {
			String[] objs = key.split("_");
			String pid = objs[0];
			String batch = "";
			if(objs.length > 1) {
				batch = objs[1];
			}
			Integer unitid = appProduct.loadProductById(pid).getSunit();
			// 批次的数量
			Double batchQuantity = batchQuantityMap.get(key);
			if(StringUtil.isEmpty(batch)) {
				//若批次为空,设置默认批次
				batch = Constants.NO_BATCH;
			}
			// 添加对应批次库存
			apsp.inProductStockpile(pid, batch, batchQuantity, outWarehouseid, "000", billNo,
					"分包管理-入库", unitid,true);
		}
	}
	
	
	/**
	 * 根据检货详情及条码扣减库存
	 * @param ttdList
	 * @param ttiList
	 * @param outWarehouseid
	 * @throws Exception
	 */
	private void outProductStockpileTTD(List<PackSeparateDetail> psdList,String outWarehouseid,String billNo) throws Exception {
		Map<String, Double> batchQuantityMap = collectOutBatchQuantity(psdList);
		outProductStockpileTTD(batchQuantityMap,outWarehouseid, billNo);
	}
	
	private void outProductStockpileTTD(Map<String, Double> batchQuantityMap, String outWarehouseid, String billNo) throws Exception {
		for(String key : batchQuantityMap.keySet()) {
			String[] objs = key.split("_");
			String pid = objs[0];
			String batch = "";
			if(objs.length > 1) {
				batch = objs[1];
			}
			Integer unitid = appProduct.loadProductById(pid).getSunit();
			// 批次的数量
			Double batchQuantity = batchQuantityMap.get(key);
			if(StringUtil.isEmpty(batch)) {
				//若批次为空,设置默认批次
				batch = Constants.NO_BATCH;
			}
			// 扣减对应批次库存
			apsp.outProductStockpile(pid, batch, batchQuantity, outWarehouseid, "000", billNo,
					"分包管理-出库", unitid,true);
		}
		
	}

	
	
	/**
	 * 根据批次汇总数量
	 * Create Time 2014-11-18 下午03:35:11 
	 * @param ttiList
	 * @return
	 */
	private Map<String, Double> collectOutBatchQuantity(List<PackSeparateDetail> psdList){
		Map<String, Double> batchMap = new HashMap<String, Double>();
		for(PackSeparateDetail psd : psdList){
			if(psd == null) {
				continue;
			}
			String pid = psd.getOutProductId();
			String batch = psd.getOutBatch();
			String key = pid + "_";
			if(!StringUtil.isEmpty(batch)) {
				key = key + batch;
			}	
			Double quantity = batchMap.get(key);
			if(quantity == null){
				batchMap.put(key, psd.getOutQuantity());
			}else {
				batchMap.put(key, quantity + psd.getOutQuantity());
			}
		}
		return batchMap;
	}
	
	/**
	 * 根据批次汇总数量
	 * Create Time 2014-11-18 下午03:35:11 
	 * @param ttiList
	 * @return
	 */
	private Map<String, Double> collectInBatchQuantity(List<PackSeparateDetail> psdList){
		Map<String, Double> batchMap = new HashMap<String, Double>();
		for(PackSeparateDetail psd : psdList){
			if(psd == null) {
				continue;
			}
			String pid = psd.getInProductId();
			String batch = psd.getInBatch();
			String key = pid + "_";
			if(!StringUtil.isEmpty(batch)) {
				key = key + batch;
			}			
			Double quantity = batchMap.get(key);
			if(quantity == null){
				batchMap.put(key, psd.getInQuantity());
			}else {
				batchMap.put(key, quantity + psd.getInQuantity());
			}
		}
		return batchMap;
	}
}
