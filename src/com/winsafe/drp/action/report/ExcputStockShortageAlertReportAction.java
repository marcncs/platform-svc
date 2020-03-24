package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
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
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.ReportForm;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;

import common.Logger;

public class ExcputStockShortageAlertReportAction extends BaseAction {
	private Logger logger = Logger.getLogger(StockShortageAlertReportAction.class);
	private StockShortageAlertReportService ssars = new StockShortageAlertReportService();
	private AppRegion appRegion = new AppRegion();
	private WarehouseService ws = new WarehouseService();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		List<ReportForm> list = new ArrayList<ReportForm>();
		if (request.getParameter("dataPage") != null) {
			list = ssars.queryReport(null, 0, map, users);
		}
		//获取当前日期
		String nowDate = Dateutil.getCurrentDateTimeString();
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=StockShortageAlertReport"+nowDate+".xls");
		response.setContentType("application/msexcel");
		writeXls(list, os, request);
		os.flush();
		os.close();
		DBUserLog.addUserLog(request, "列表");
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
	public void writeXls(List<ReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "库存短缺报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(m++, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("oname")));
			sheets[j].addCell(new Label(m++, start + 1, "仓库:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("wname")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("packSizeName")));
			sheets[j].addCell(new Label(m++, start + 2, "预警数量:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("stockpile")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "单位:", seachT));
			if (request.getParameter("countByUnit").equals("true")) {
				sheets[j].addCell(new Label(m++, start + 3, "升,千克"));
			} else {
				sheets[j].addCell(new Label(m++, start + 3, "件"));
			}
			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "大区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "省份", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构内部编码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "仓库名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品类别", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "报告日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "库存数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				ReportForm reportForm = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, reportForm.getArea() == null ? "" : String.valueOf(reportForm.getArea())));
				sheets[j].addCell(new Label(m++, row, reportForm.getProvince() == null ? "" : String.valueOf(reportForm.getProvince())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(reportForm.getOrganid())));
				sheets[j].addCell(new Label(m++, row, reportForm.getOecode()));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(reportForm.getOrganname())));
				sheets[j].addCell(new Label(m++, row, ws.getWarehouseName(reportForm.getWarehouseid())));
				sheets[j].addCell(new Label(m++, row, reportForm.getProductsort()));
				sheets[j].addCell(new Label(m++, row, reportForm.getMcode()));
				sheets[j].addCell(new Label(m++, row, reportForm.getProductname()));
				sheets[j].addCell(new Label(m++, row, reportForm.getProductNameen()));
				sheets[j].addCell(new Label(m++, row, reportForm.getSpecmode()));
				sheets[j].addCell(new Label(m++, row, reportForm.getPackSizeNameEn()));
				sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(reportForm.getReportdate(), "yyyy-MM-dd")));
				sheets[j].addCell(new Number(m++, row,Double.valueOf(reportForm.getStockpile().replace(",", ""))));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getResourceName(request, "CountUnit", reportForm.getCountunit())));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
