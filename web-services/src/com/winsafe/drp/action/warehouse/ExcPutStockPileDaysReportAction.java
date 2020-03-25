package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppPurchaseSalesReport;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.StockPileReportService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.WeekdayCountHelper;

public class ExcPutStockPileDaysReportAction extends BaseAction {
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
				alp.add(psf);
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=SalesReport.xls");
			response.setContentType("application/msexcel");
			writeXls(alp, os, request,isshowBatch);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,7,"进销存报表>>导出库存天数报表");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List list, OutputStream os,
			HttpServletRequest request,String isshowBatch) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WarehouseService ws = new WarehouseService();
		int snum = 1;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;
			sheets[j].mergeCells(0, start, 10, start);
			sheets[j].addCell(new Label(0, start, "库存天数报表(导出)",wchT));
			sheets[j].addCell(new Label(0, start+1, "机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "仓库:",seachT));
			sheets[j].addCell(new Label(3, start+1, ws.getWarehouseName(request.getParameter("WarehouseID").toString())));
			sheets[j].addCell(new Label(4, start+1, "批次:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+2, "产品:",seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("ProductName")));			
			
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "仓库编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "仓库名称",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "内部编码",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "产品名称",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "规格",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "单位",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "可用库存数量",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "预定出库数量",wcfFC));
			sheets[j].addCell(new Label(8, start+4, "实时库存",wcfFC));
			sheets[j].addCell(new Label(9, start+4, "日均销量（前3个月）",wcfFC));
			sheets[j].addCell(new Label(10, start+4, "库存天数",wcfFC));
			
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				ProductStockpileForm psf = (ProductStockpileForm) list.get(i);
				sheets[j].addCell(new Label(0, row, psf.getWarehouseid()));
				sheets[j].addCell(new Label(1, row, ws.getWarehouseName(psf.getWarehouseid())));
				sheets[j].addCell(new Label(2, row, psf.getBarcode()));
				sheets[j].addCell(new Label(3, row, psf.getPsproductname()));
				sheets[j].addCell(new Label(4, row, psf.getPsspecmode()));
				sheets[j].addCell(new Label(5, row, "支"));
				sheets[j].addCell(new Number(6, row, psf.getStockpile(),QWCF));
				sheets[j].addCell(new Number(7, row, psf.getPrepareout(),QWCF));
				sheets[j].addCell(new Number(8, row, psf.getAllstockpile(),QWCF));
				sheets[j].addCell(new Label(9, row, psf.getSalesAvg()==null ? "" : psf.getSalesAvg().toString(),QWCF));
				sheets[j].addCell(new Label(10, row, psf.getStockPileDays()==null ? "" : psf.getStockPileDays().toString(),QWCF));
				
			}
			
			
		}
		workbook.write();
		workbook.close();
		os.close();
	}

	
}
