package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInventorySSCCCodeReport;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.InventorySSCCCodeReportForm;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;

public class ExportInventorySSCCCodeReportAction extends BaseAction {
	//private static Logger logger = Logger.getLogger(ExportInventorySSCCCodeReportAction.class);
	private AppInventorySSCCCodeReport aissr = new AppInventorySSCCCodeReport();
	private AppRegion appRegion = new AppRegion();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//初始化
		super.initdata(request);
		int pageSize = -1;
		
		List resultList = aissr.getInventorySSCCCodeReport(request, pageSize, users);
		if (resultList.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		
		List<InventorySSCCCodeReportForm> reports = new ArrayList<InventorySSCCCodeReportForm>();
		for(int i=0; i<resultList.size(); i++){
			Map map = (Map) resultList.get(i);
			InventorySSCCCodeReportForm report = new InventorySSCCCodeReportForm();
			MapUtil.mapToObject(map, report);
			if(report.getAuditDate() != null){
				report.setAuditDateStr(DateUtil.formatDate(report.getAuditDate()));
			}
			if(report.getProductionDate() != null){
				report.setProductionDateStr(DateUtil.formatDate(report.getProductionDate()));
			}
			if(report.getExpiryDate() != null){
				report.setExpiryDateStr(DateUtil.formatDate(report.getExpiryDate()));
			}
			reports.add(report);
		}
		resultList = null;
		
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=InventorySSCCCodeReport"+Dateutil.getCurrentDateTimeString()+".xls");
		response.setContentType("application/msexcel");
		writeXls(reports, os, request);
		os.flush();
		os.close();
		DBUserLog.addUserLog(request, "列表");
		reports = null;
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
	public void writeXls(List<InventorySSCCCodeReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		int m = 0;
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

			sheets[j].mergeCells(0, start, 23, start);
			sheets[j].addCell(new Label(0, start, "库存箱码报表", wchT));
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
			sheets[j].addCell(new Label(m++, start + 2, "批次:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("batch")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "箱号:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("idcode")));
			
			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "区\nRegion", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "省份\nProvince", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构代码\nSAP Code", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构名称\nPD Name", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "仓库\nWarehouse", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号\nMaterial Code", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料中文\nMaterial Description CN", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料英文\nMaterial Description EN", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称中文\nProduct Name CN", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称英文\nProduct Name EN", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格英文\nPack Size EN", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "批次\nBatch No", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "类型\nType", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "箱码\nSSCC Code", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "生产日期\nProduction Date", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "过期日期\nExpiry Date", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "发货单号\nSAP Delivery N", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				InventorySSCCCodeReportForm rs = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, rs.getInRegionName() == null ? "" : rs.getInRegionName()));
				sheets[j].addCell(new Label(m++, row, rs.getInProvinceName() == null ? "" : rs.getInProvinceName()));
				sheets[j].addCell(new Label(m++, row, rs.getInOrganOecode() == null ? "" : rs.getInOrganOecode()));
				sheets[j].addCell(new Label(m++, row, rs.getInOrganName() == null ? "" : ESAPIUtil.decodeForHTML(rs.getInOrganName())));
				sheets[j].addCell(new Label(m++, row, rs.getInWarehouseName() == null ? "" : rs.getInWarehouseName()));
				sheets[j].addCell(new Label(m++, row, rs.getMcode() == null ? "" : rs.getMcode()));
				sheets[j].addCell(new Label(m++, row, rs.getMatericalchdes() == null ? "" : rs.getMatericalchdes()));
				sheets[j].addCell(new Label(m++, row, rs.getMatericalendes() == null ? "" : rs.getMatericalendes()));
				sheets[j].addCell(new Label(m++, row, rs.getProductName() == null ? "" : rs.getProductName()));
				sheets[j].addCell(new Label(m++, row, rs.getProductNameEn() == null ? "" : rs.getProductNameEn()));
				sheets[j].addCell(new Label(m++, row, rs.getPacksizeNameEn() == null ? "" : rs.getPacksizeNameEn()));
				sheets[j].addCell(new Label(m++, row, rs.getBatch() == null ? "" : rs.getBatch()));
				sheets[j].addCell(new Label(m++, row, (rs.getIsOut() == null || rs.getIsOut() == 0) ? "在仓" : "在途"));
				sheets[j].addCell(new Label(m++, row, rs.getIdcode() == null ? "" : rs.getIdcode()));
				sheets[j].addCell(new Label(m++, row, rs.getProductionDateStr() == null ? "" : rs.getProductionDateStr()));
				sheets[j].addCell(new Label(m++, row, rs.getExpiryDateStr() == null ? "" : rs.getExpiryDateStr()));
				sheets[j].addCell(new Label(m++, row, rs.getBillNccode() == null ? "" : rs.getBillNccode()));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
}
