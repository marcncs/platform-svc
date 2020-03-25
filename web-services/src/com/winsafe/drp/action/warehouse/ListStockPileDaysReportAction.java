package com.winsafe.drp.action.warehouse;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

public class ListStockPileDaysReportAction extends BaseAction {
	AppPurchaseSalesReport apsr = new AppPurchaseSalesReport();
	AppProductStockpileAll apsa = new AppProductStockpileAll();
	StockPileReportService sprs = new StockPileReportService();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);

		String isshowBatch = request.getParameter("isShowbatch");

		String orderSql = request.getParameter("orderbySql");
		String orderSqlName = request.getParameter("orderbySqlShowName");
		String BeginDate = DateUtil.getBeforeByMonth( DateUtil.getCurrentDateString(), 3);
		String EndDate = DateUtil.getFirstDayOfMonthYMD();
		
		try {
			String condition = " s.productid=p.id and ps.structcode=p.psid ";
			condition += " and s.warehouseid in (select wv.wid from  Warehouse_Visit  wv where wv.userid="
					+ userid + ")";

			WarehouseService aw = new WarehouseService();
			String[] tablename = { "PurchaseSalesReport" };
			String whereSql = getWhereSql(tablename);
			tmpMap.put("BeginDate", BeginDate);
			tmpMap.put("EndDate", EndDate);
			String timeCondition = getTimeCondition("recorddate");
			Object beginDate = tmpMap.get("BeginDate");
			Object endDate = tmpMap.get("EndDate");

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
			String PSAwhereSql = whereSql + leftblur + blur + condition;
			PSAwhereSql = DbUtil.getWhereSql(PSAwhereSql);
			sals = apsa.getProductStockpileAllGroup(request, pagesize, PSAwhereSql, orderSql);

			List wls = aw.getEnableWarehouseByVisit(userid);
			
			List sdas = apsr.getSalesDailyAvgReportNoBatch(whereSql1, day);
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
				// if(isshowBatch!=null){
				// psf.setBatch(p.get("batch").toString());
				// }
				Integer sunit = Integer.valueOf(p.get("countunit").toString());
				psf.setCountunit(sunit);
				// 实时库存
				psf.setAllstockpile(NumberUtil.formatDouble(p.get("quantity").toString()));
				// 可用库存数
				psf.setStockpile(NumberUtil.formatDouble(p.get("stockpile").toString()));
				// 预定库存数
				psf.setPrepareout(NumberUtil.formatDouble(p.get("prepareout").toString()));
				// 前三个月的日均销量,库存天数
				sprs.setPsfStockPileDays(sdas, psf, p);
				// psf.setSalesAvg(p.get("sda")==null?0:Double.valueOf(p.get("sda").toString()));
				// psf.setStockPileDays(p.get("days")==null?0:Double.valueOf(p.get("days").toString()));
				alp.add(psf);
			}

			Map<String, String> orderColumnMap = new HashMap<String, String>();
			generateMap(orderColumnMap);

			request.setAttribute("orderSql", orderSql);
			request.setAttribute("orderSqlName", orderSqlName);
			request.getSession().setAttribute("orderColumnMap", orderColumnMap);
			request.setAttribute("als", alp);
			request.setAttribute("alw", wls);

			DBUserLog.addUserLog(userid, 7, "库存管理>>库存天数报表");

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
