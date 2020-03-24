package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Number;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.action.newreport.MonthlyData;
import com.winsafe.drp.action.newreport.SalesMonthlyReportService;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.SalesConsumMonthReportForm;
import com.winsafe.drp.service.report.SalesConsumptionInventoryMonthlyVolumeReportService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;

public class ExportSalesInventoryMonthSummaryReport extends BaseAction {
	private static Logger logger = Logger.getLogger(ExportSalesInventoryMonthSummaryReport.class);
	private SalesConsumptionInventoryMonthlyVolumeReportService service = new SalesConsumptionInventoryMonthlyVolumeReportService();
	private AppRegion appRegion = new AppRegion();
	private SalesMonthlyReportService scService = new SalesMonthlyReportService();
	DecimalFormat decimalFormat = new DecimalFormat("#,##0.000");
	

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//初始化
		super.initdata(request);
		Map map = new HashMap(request.getParameterMap());
		SalesConsumMonthReportForm scrForm = new SalesConsumMonthReportForm();
		// 将map中对应的值赋值给对应的实体类
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
		
		List<SalesConsumMonthReportForm> displayList = scService.queryReport(request, -1, scrForm, users);
		
		if (displayList.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		//动态获取时间并动态添加至页面
		List<String> titleList = service.getTitleList(request);
		
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=SalesConsumptionInventoryMonthlySummaryReport"+Dateutil.getCurrentDateTimeString()+".xls");
		response.setContentType("application/msexcel");
		writeXls(displayList, titleList, os, request);
		os.flush();
		os.close();
		titleList = null;
		DBUserLog.addUserLog(request, "列表");
		displayList = null;
		return null;
	}
	
	private void writeXls(List<SalesConsumMonthReportForm> list,
			List<String> titleList, OutputStream os, HttpServletRequest request) throws Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		int colIndex = 0;
		int rowIndex = 0;
		int rangeRows = 0;
		int rangeCols = 0;
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

			sheets[j].mergeCells(0, start, 10 + titleList.size()*2, start);
			sheets[j].addCell(new Label(0, start, "销售消耗月汇总表", wchT));
			sheets[j].addCell(new Label(colIndex++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 1, appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(colIndex++, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 1, request.getParameter("oname2")));
			sheets[j].addCell(new Label(colIndex++, start + 1, "仓库:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 1, request.getParameter("wname")));

			colIndex = 0;
			sheets[j].addCell(new Label(colIndex++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(colIndex++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 2, request.getParameter("packSizeName")));
			sheets[j].addCell(new Label(colIndex++, start + 2, "日期:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 2, request.getParameter("beginDate") + " - " + request.getParameter("endDate")));
			
			// 表头
			colIndex = 0;
			rowIndex = start + 4;
			rangeRows = 1;
			rangeCols = 1;
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "区\r\nRegion", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "省份\r\nProvince", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "机构代码\r\nSAP Code", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "机构名称\r\nPD Name", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "仓库\r\nWarehouse Name", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "物料号\r\nMaterial Code", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "物料中文\r\nMaterial Description CN", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "物料英文\r\nMaterial Description EN", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "产品名称中文\r\nProduct Name CN", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "产品名称英文\r\nProduct Name EN", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "规格英文\r\nPack Size EN", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "数据类型\r\nData Type", wcfFC));
			for(String monthYear: titleList){
//				sheets[j].mergeCells(colIndex, rowIndex, colIndex+rangeCols, rowIndex);
//				sheets[j].addCell(new Label(colIndex, rowIndex, monthYear, wcfFC));
				sheets[j].addCell(new Label(colIndex, rowIndex, monthYear + "\r\n数量\r\nVolume (KG/L)", wcfFC));
				colIndex++;
				sheets[j].addCell(new Label(colIndex, rowIndex, monthYear + "\r\n金额\r\nValue (RMB)", wcfFC));
				colIndex++;
			}
			
			// 内容
			rowIndex = 5;
			for (int i = start; i < currentnum; i++) {
				
				SalesConsumMonthReportForm rs = list.get(i);
				List<MonthlyData> monthlyDatas = rs.getMonthlyDataList();
				colIndex = 0;
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getRegionName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProvince())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getOecode())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(ESAPIUtil.decodeForHTML(rs.getOrganName()))));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getWarehouseName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getmCode())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getMatericalChDes())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getMatericalEnDes())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProductName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProductNameen())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getPackSizeNameEn())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, "销售\r\nSales", aec));
				
				for(MonthlyData monthlyData : monthlyDatas) {
					sheets[j].addCell(new Number(colIndex++, rowIndex, monthlyData.getSalesVolume()));
					sheets[j].addCell(new Number(colIndex++, rowIndex, monthlyData.getSalesValue()));
				}
				rowIndex++;
				colIndex = 0;
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getRegionName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProvince())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getOecode())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(ESAPIUtil.decodeForHTML(rs.getOrganName()))));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getWarehouseName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getmCode())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getMatericalChDes())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getMatericalEnDes())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProductName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProductNameen())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getPackSizeNameEn())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, "消耗\r\nConsumption", aec));
				
				for(MonthlyData monthlyData : monthlyDatas) {
					sheets[j].addCell(new Number(colIndex++, rowIndex, monthlyData.getConsumptionVolume()));
					sheets[j].addCell(new Number(colIndex++, rowIndex, monthlyData.getConsumptionValue()));
				}
				
				rowIndex++;
				colIndex = 0;
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getRegionName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProvince())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getOecode())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(ESAPIUtil.decodeForHTML(rs.getOrganName()))));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getWarehouseName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getmCode())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getMatericalChDes())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getMatericalEnDes())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProductName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProductNameen())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getPackSizeNameEn())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, "期末库存\r\nEnd Inventory", aec));
				
				for(MonthlyData monthlyData : monthlyDatas) {
					sheets[j].addCell(new Number(colIndex++, rowIndex, monthlyData.getEndInventoryVolume()));
					sheets[j].addCell(new Number(colIndex++, rowIndex, monthlyData.getEndInventoryValue()));
				}
				rowIndex++;
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
}
