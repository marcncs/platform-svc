package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
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
import com.winsafe.drp.dao.AppInventoryReport;
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

public class ExcPutStockPileTurnoverRateReportAction extends BaseAction {
	AppPurchaseSalesReport apsr = new AppPurchaseSalesReport();
	AppProductStockpileAll apsa = new AppProductStockpileAll();
	AppInventoryReport aip = new AppInventoryReport();
	WarehouseService aw = new WarehouseService();
	StockPileReportService sprs = new StockPileReportService();
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);

		String isshowBatch = request.getParameter("isShowbatch");

		String orderSql = request.getParameter("orderbySql");
		String orderSqlName = request.getParameter("orderbySqlShowName");
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
			
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=SalesReport.xls");
			response.setContentType("application/msexcel");
			writeXls(alp, os, request,isshowBatch, totalTurnoverRate);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,7,"进销存报表>>导出库存周转率报表");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List list, OutputStream os,
			HttpServletRequest request,String isshowBatch, String totalTurnoverRate) throws NumberFormatException, Exception {
		String BeginDate = request.getParameter("BeginDate");
		if(BeginDate.length()>=7){
			BeginDate = BeginDate.substring(0,7);
		}
		
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
			sheets[j].addCell(new Label(0, start, "库存周转率报表(导出)",wchT));
			sheets[j].addCell(new Label(0, start+1, "机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "仓库:",seachT));
			sheets[j].addCell(new Label(3, start+1, ws.getWarehouseName(request.getParameter("WarehouseID").toString())));
			sheets[j].addCell(new Label(4, start+1, "批次:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+2, "产品:",seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("ProductName")));			
			sheets[j].addCell(new Label(0, start+2, "日期（月份）:",seachT));
			sheets[j].addCell(new Label(1, start+2, BeginDate+"月"));	
			
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
			
			sheets[j].addCell(new Label(6, start+4, "月销售量",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "日均库存",wcfFC));
			sheets[j].addCell(new Label(8, start+4, "库存周转率",wcfFC));
			
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
				sheets[j].addCell(new Number(6, row, psf.getSalesAvg(),QWCF));
				sheets[j].addCell(new Number(7, row, psf.getStockpile(),QWCF));
				sheets[j].addCell(new Label(8, row, psf.getTurnoverRate(),QWCF));
				
			}
			sheets[j].addCell(new Label(7, row+1, "总平均周转率:",QWCF));
			sheets[j].addCell(new Label(8, row+1, totalTurnoverRate,QWCF));
			
			
		}
		workbook.write();
		workbook.close();
		os.close();
	}

	
}
