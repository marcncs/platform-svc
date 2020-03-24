package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInventoryReport;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppPurchaseSalesReport;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.StockPileReportService;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.WeekdayCountHelper;

public class ListStockPileTurnoverRateReportAction extends BaseAction {
	AppPurchaseSalesReport apsr = new AppPurchaseSalesReport();
	AppProductStockpileAll apsa = new AppProductStockpileAll();
	AppInventoryReport aip = new AppInventoryReport();
	WarehouseService aw = new WarehouseService();
	StockPileReportService sprs = new StockPileReportService();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);

		String isshowBatch = request.getParameter("isShowbatch");

		String orderSql = request.getParameter("orderbySql");
		String orderSqlName = request.getParameter("orderbySqlShowName");
		//String BeginDate = DateUtil.getBeforeByMonth( DateUtil.getCurrentDateString(), 3);
		String date = request.getParameter("BeginDate");
		String BeginDate = "";
		String EndDate = "";
		if(date==null){
			//如果时间为空，则取上月的数据
			BeginDate = DateUtil.getBeforeMonthFirstDay(DateUtil.getCurrentDateString());
			EndDate = DateUtil.getBeforeMonthLastDay(DateUtil.getCurrentDateString());
		}else{
			BeginDate = DateUtil.getMonthFirstDayOfStringDate(date);
			EndDate = DateUtil.getMonthLastDayOfStringDate(date);
		}
		
		
		try {
			String condition = " s.productid=p.id and ps.structcode=p.psid ";
			condition += " and s.warehouseid in (select wv.wid from  Warehouse_Visit  wv where wv.userid="
					+ userid + ")";
			String[] tablename = { "PurchaseSalesReport" };
			String whereSql = getWhereSql(tablename);
			tmpMap.put("BeginDate", BeginDate);
			tmpMap.put("EndDate", EndDate);
			map.put("BeginDate", BeginDate);
			map.put("EndDate", EndDate);
			String timeCondition = getTimeCondition("recorddate");
			Object beginDate = map.get("BeginDate");
			Object endDate = map.get("EndDate");
			
			int day = WeekdayCountHelper.getWeekDays(beginDate == null ? ""
					: beginDate.toString(), endDate == null ? "" : endDate
					.toString());

			String blur = getKeyWordCondition("s.batch");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String whereSql1 = whereSql + leftblur + timeCondition + blur + condition;
			whereSql1 = DbUtil.getWhereSql(whereSql1);

			List sals = new ArrayList();

			if (orderSql != null) {
				orderSql = orderSql.replaceAll("batch", "productid");
			}
			
			sals = aip.getOutQuantityDaily(request, pagesize, whereSql1, orderSql, day);

			List wls = aw.getEnableWarehouseByVisit(userid);
			
			List sdas = apsr.getSalesCount(whereSql1);
			List alp = new ArrayList();
			for (int i = 0; i < sals.size(); i++) {
				ProductStockpileForm psf = new ProductStockpileForm();
				Map p = (Map) sals.get(i);

				psf.setWarehouseid(p.get("warehouseid").toString());
				psf.setWarehourseidname(aw.getWarehouseName(p
						.get("warehouseid").toString()));
				psf.setProductid(p.get("productid").toString());
				psf.setBarcode(p.get("nccode") == null ? "" : p.get("nccode")
						.toString());
				psf.setPsproductname(p.get("productname").toString());
				psf.setPsspecmode(p.get("specmode").toString());
				psf.setSortName(p.get("sortname").toString());
				Integer sunit = Integer.valueOf(p.get("countunit").toString());
				psf.setCountunit(sunit);
				
				
				
				sprs.setPSFBySdas(sdas, psf, p);
				alp.add(psf);
			}
			String totalTurnoverRate = "";
			List sumSalesAvg = apsr.getSalesSumCount(whereSql1);
			List sumAvgStockPile = aip.getOutQuantityDailySum(whereSql1, day);
			if((sumSalesAvg!=null&&sumSalesAvg.size()>0)&&(sumAvgStockPile!=null&&sumAvgStockPile.size()>0)){
				Double sumSalesAvgDouble = ((Map)sumSalesAvg.get(0)).get("salescount")==null?0:Double.valueOf(((Map)sumSalesAvg.get(0)).get("salescount").toString());
				Double sumAvgStockPileDouble = ((Map)sumAvgStockPile.get(0)).get("avgstockpile")==null?0:Double.valueOf(((Map)sumAvgStockPile.get(0)).get("avgstockpile").toString());
				totalTurnoverRate = NumberUtil.formatDouble(sumSalesAvgDouble, sumAvgStockPileDouble);
				totalTurnoverRate = totalTurnoverRate==""?"":totalTurnoverRate+"%";
			}
			

			Map<String, String> orderColumnMap = new HashMap<String, String>();
			generateMap(orderColumnMap);
			request.setAttribute("ttr", totalTurnoverRate);
			request.setAttribute("orderSql", orderSql);
			request.setAttribute("orderSqlName", orderSqlName);
			request.getSession().setAttribute("orderColumnMap", orderColumnMap);
			request.setAttribute("als", alp);
			request.setAttribute("alw", wls);

			DBUserLog.addUserLog(userid, 7, "库存管理>>库存周转率报表");

			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("isshowBatch", isshowBatch);

			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
			String result = "databases.settlement.noexist";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}

	}

	private String getWarehouseId(List wlist) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < wlist.size(); i++) {
			Warehouse w = (Warehouse) wlist.get(i);
			if (i == 0) {
				sb.append("'").append(w.getId()).append("'");
			} else {
				sb.append(",'").append(w.getId()).append("'");
			}
		}
		return sb.toString();
	}

	private void generateMap(Map<String, String> orderColumnMap) {
		orderColumnMap.put("warehouseid", "仓库编号");
		orderColumnMap.put("batch", "批次");
		//orderColumnMap.put("recorddate", "日期");
		// orderColumnMap.put("nccode", "产品内部编号");
		// orderColumnMap.put("p.psid", "产品类别");
	}

	

}
