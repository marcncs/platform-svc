package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Number;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.SalesConsumptionInventory;
import com.winsafe.drp.dao.SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm;
import com.winsafe.drp.service.report.SalesConsumptionInventoryMonthlyVolumeReportService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil; 

public class ExportSalesConsumptionInventoryMonthlyVolumeReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExportSalesConsumptionInventoryMonthlyVolumeReportAction.class);
	private SalesConsumptionInventoryMonthlyVolumeReportService service = new SalesConsumptionInventoryMonthlyVolumeReportService();
	private AppRegion appRegion = new AppRegion();
	

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//初始化
		super.initdata(request);
		
		List<SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm> displayList = service.getSalesConsumptionInventoryMonthlyVolumeReportPageDisplayFormList(request, -1, users);
		if (displayList.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		
		List<SalesConsumptionInventory> titleList = new ArrayList<SalesConsumptionInventory>();
		List<SalesConsumptionInventory> titleBeginList = new ArrayList<SalesConsumptionInventory>();
		service.getTitleList(request, titleBeginList, titleList);
		
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=SalesConsumptionInventoryMonthlyVolumeReport"+Dateutil.getCurrentDateTimeString()+".xls");
		response.setContentType("application/msexcel");
		writeXls(displayList, titleBeginList, titleList, os, request);
		os.flush();
		os.close();
		displayList = null;
		titleBeginList = null;
		titleList = null;
		DBUserLog.addUserLog(request, "列表");
		displayList = null;
		return null;
	}
	
	/**
	 * @param list
	 * @param os
	 * @param request
	 * @throws Exception
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void writeXls(List<SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm> list, List<SalesConsumptionInventory> titleBeginList, List<SalesConsumptionInventory> titleList, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		int m = 0;
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

			sheets[j].mergeCells(0, start, 10 + titleBeginList.size()*2 + titleList.size()*3*2, start);
			sheets[j].addCell(new Label(0, start, "销售消耗库存月数量报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(m++, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("oname2")));
			sheets[j].addCell(new Label(m++, start + 1, "仓库:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("wname")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("packSizeName")));
			sheets[j].addCell(new Label(m++, start + 2, "日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("beginDate") + " - " + request.getParameter("endDate")));
			
			// 表头
			colIndex = 0;
			rowIndex = start + 4;
			rangeRows = 2;
			rangeCols = 2;
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "区\nRegion", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "省份\nProvince", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "机构代码\nSAP Code", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "机构名称\nPD Name", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "仓库\nWarehouse", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "物料号\nMaterial Code", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "物料中文\nMaterial Description CN", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "物料英文\nMaterial Description EN", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "产品名称中文\nProduct Name CN", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "产品名称英文\nProduct Name EN", wcfFC));
//			sheets[j].mergeCells(colIndex, rowIndex, colIndex, rowIndex+rangeRows);
			sheets[j].addCell(new Label(colIndex++, rowIndex, "规格英文\nPack Size EN", wcfFC));
			for(SalesConsumptionInventory sci: titleBeginList){
				sheets[j].addCell(new Label(colIndex++, rowIndex, sci.getDisplayDate() + "\r\n期末库存数量\nEnd Inventory Volume (KG/L)", wcfFC));
				sheets[j].addCell(new Label(colIndex++, rowIndex, sci.getLastYearDisplayDate()+"\r\n期末库存数量\nEnd Inventory Volume (KG/L)", wcfFC));
			}
			for(SalesConsumptionInventory sci: titleList){
				sheets[j].addCell(new Label(colIndex++, rowIndex, sci.getDisplayDate() + "\r\n销售数量\nSales Volume (KG/L)", wcfFC));
				sheets[j].addCell(new Label(colIndex++, rowIndex, sci.getDisplayDate() + "\r\n消耗数量\nConsumption Volume (KG/L)", wcfFC));
				sheets[j].addCell(new Label(colIndex++, rowIndex, sci.getDisplayDate() + "\r\n期末库存数量\nEnd Inventory Volume (KG/L)", wcfFC));
				
				sheets[j].addCell(new Label(colIndex++, rowIndex, sci.getLastYearDisplayDate() + "\r\n销售数量\nSales Volume (KG/L)", wcfFC));
				sheets[j].addCell(new Label(colIndex++, rowIndex, sci.getLastYearDisplayDate() + "\r\n消耗数量\nConsumption Volume (KG/L)", wcfFC));
				sheets[j].addCell(new Label(colIndex++, rowIndex, sci.getLastYearDisplayDate() + "\r\n期末库存数量\nEnd Inventory Volume (KG/L)", wcfFC));
			}
			
			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm rs = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, rs.getRegionName() == null ? "" : rs.getRegionName()));
				sheets[j].addCell(new Label(m++, row, rs.getoProvinceName() == null ? "" : rs.getoProvinceName()));
				sheets[j].addCell(new Label(m++, row, rs.getOecode() == null ? "" : rs.getOecode()));
				sheets[j].addCell(new Label(m++, row, rs.getOrganName() == null ? "" : ESAPIUtil.decodeForHTML(rs.getOrganName())));
				sheets[j].addCell(new Label(m++, row, rs.getWarehouseName() == null ? "" : rs.getWarehouseName()));
				sheets[j].addCell(new Label(m++, row, rs.getMcode() == null ? "" : rs.getMcode()));
				sheets[j].addCell(new Label(m++, row, rs.getMatericalchdes() == null ? "" : rs.getMatericalchdes()));
				sheets[j].addCell(new Label(m++, row, rs.getMatericalendes() == null ? "" : rs.getMatericalendes()));
				sheets[j].addCell(new Label(m++, row, rs.getProductName() == null ? "" : rs.getProductName()));
				sheets[j].addCell(new Label(m++, row, rs.getProductNameEn() == null ? "" : rs.getProductNameEn()));
				sheets[j].addCell(new Label(m++, row, rs.getPacksizeNameEn() == null ? "" : rs.getPacksizeNameEn()));
				for(SalesConsumptionInventory sci: rs.getSalesConsumptionInventoryBeginList()){
					sheets[j].addCell(new Number(m++, row, getDouble(sci.getMonthEndInventory())));
					sheets[j].addCell(new Number(m++, row, getDouble(sci.getLastYearMonthEndInventory())));
				}
				for(SalesConsumptionInventory sci: rs.getSalesConsumptionInventoryList()){
					sheets[j].addCell(new Number(m++, row, getDouble(sci.getSalesVolume())));
					sheets[j].addCell(new Number(m++, row, getDouble(sci.getComsumptionVolume())));
					sheets[j].addCell(new Number(m++, row, getDouble(sci.getMonthEndInventory())));
					sheets[j].addCell(new Number(m++, row, getDouble(sci.getLastYearSalesVolume())));
					sheets[j].addCell(new Number(m++, row, getDouble(sci.getLastYearComsumptionVolume())));
					sheets[j].addCell(new Number(m++, row, getDouble(sci.getLastYearMonthEndInventory())));
				}
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
