package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.action.newreport.SalesLnventoryReportForm;
import com.winsafe.drp.action.newreport.SalesLnventoryReportService;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.util.Constants; 
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;

public class ExportSalesConsumptionInventoryYearMonthlyReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExportSalesConsumptionInventoryYearMonthlyReportAction.class);
	private AppRegion appRegion = new AppRegion();
	private SalesLnventoryReportService scService = new SalesLnventoryReportService();
	DecimalFormat decimalFormat = new DecimalFormat("#,##0.000");
	

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//初始化
		super.initdata(request);
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		
		SalesLnventoryReportForm scrForm = new SalesLnventoryReportForm();
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
		
		List<SalesLnventoryReportForm> list = scService.querySalesDetailReport(request, 0, scrForm,users);
		
		if (list.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=SalesConsumptionInventoryYTDAndMTDReport"+Dateutil.getCurrentDateTimeString()+".xls");
		response.setContentType("application/msexcel");
		writeXls(list, os, request);
		os.flush();
		os.close();
		DBUserLog.addUserLog(request, "列表");
		return null;
	}
	
	private void writeXls(List<SalesLnventoryReportForm> list, OutputStream os,
			HttpServletRequest request) throws Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		int colIndex = 0;
		int rowIndex = 0;
//		int rangeRows = 0;
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

			sheets[j].mergeCells(0, start, 26, start);
			sheets[j].addCell(new Label(0, start, "销售消耗库存年月报表", wchT));
			sheets[j].addCell(new Label(colIndex++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 1, appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(colIndex++, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("organName"))));
			sheets[j].addCell(new Label(colIndex++, start + 1, "仓库:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 1, request.getParameter("wname")));

			colIndex = 0;
			sheets[j].addCell(new Label(colIndex++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(colIndex++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(colIndex++, start + 2, request.getParameter("packSizeName")));
//			sheets[j].addCell(new Label(colIndex++, start + 2, "日期:", seachT));
//			sheets[j].addCell(new Label(colIndex++, start + 2, request.getParameter("beginDate") + " - " + request.getParameter("endDate")));
			
			// 表头
			colIndex = 0;
			rowIndex = start + 4;
//			rangeRows = 2;
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "区\r\nRegion", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "省份\r\nProvince", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "机构代码\r\nSAP Code", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "机构名称\r\nPD Name", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "仓库\r\nWarehouse", wcfFC));
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
			
//			int curColIndex = colIndex;
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex + 7, rowIndex);
			
//			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\nVolume(KG/L)\r\n", wcfFC));
//			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\nValue(RMB)\r\n", wcfFC));
//			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\nVolume(KG/L)", wcfFC));
//			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\nValue(RMB)", wcfFC));
			 
//			rowIndex++;
//			colIndex = curColIndex;
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("year")+ "年/YTD\r\n销售数量\nSales Volume(KG/L)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("year")+ "年/YTD\r\n消耗数量\nConsumption Volume(KG/L)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("year")+ "年/YTD\r\n期初库存数量\nBegin Inventory Volume(KG/L)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("year")+ "年/YTD\r\n期末库存数量\nEnd Inventory Volume(KG/L)", wcfFC));
			
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("year")+ "年/YTD\r\n销售金额\nSales Value(RMB)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("year")+ "年/YTD\r\n消耗金额 \nConsumption Value(RMB)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("year")+ "年/YTD\r\n期初库存金额 \nBegin Inventory Value(RMB) ", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("year")+ "年/YTD\r\n期末库存金额 \nEnd Inventory Value(RMB)", wcfFC));
			
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\n销售数量 \nSales Volume(KG/L)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\n消耗数量  \nConsumption Volume(KG/L)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\n期初库存数量 \nBegin Inventory Volume(KG/L)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\n期末库存数量 \nEnd Inventory Volume(KG/L)", wcfFC));
			
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\n销售金额 \nSales Value(RMB)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\n消耗金额  \nConsumption Value(RMB)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\n期初库存金额 \nBegin Inventory Value(RMB)", wcfFC));
			sheets[j].addCell(new Label(colIndex++, rowIndex, request.getAttribute("yearMonth")+ "年/MTD\r\n期末库存金额 \nEnd Inventory Value(RMB)", wcfFC));
			
			rowIndex++;
			
			for (int i = start; i < currentnum; i++) {
				
				SalesLnventoryReportForm rs = list.get(i);
				colIndex = 0;
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getRegionName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProvince())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getOrganId())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(ESAPIUtil.decodeForHTML(rs.getOrganName()))));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getWarehouseName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getmCode())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getMatericalChDes())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getMatericalEnDes())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProductName())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getProductNameen())));
				sheets[j].addCell(new Label(colIndex++, rowIndex, StringUtil.removeNull(rs.getPackSizeNameEn())));
				
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getSalesVolume())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getConsumptionVolume())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getYearBeginInventory())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getYearEndInventory())));
				
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getSalesValue())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getConsumptionValue())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getYearBeginInventoryValue())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getYearEndInventoryValue())));
				
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getMonthSalesVolume())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getMonthConsumptionVolume())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getMonthBeginInventory())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getMonthEndInventory())));
				
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getMonthSalesValue())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getMonthConsumptionValue())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getMonthBeginInventoryValue())));
				sheets[j].addCell(new Number(colIndex++, rowIndex, getDouble(rs.getMonthEndInventoryValue())));
				
				rowIndex++;
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	private Double getDouble(Double value) {
		if(value == null) {
			return 0d;
		}
		return value;
	}
	
}
