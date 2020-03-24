package com.winsafe.drp.keyretailer.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Number;
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
import com.winsafe.drp.dao.RecAndDisSumReportForm;
import com.winsafe.drp.keyretailer.service.RecAndDisSumReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;

import common.Logger;

public class ExportRecAndDisSumReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExportRecAndDisSumReportAction.class);

	private RecAndDisSumReportService scService = new RecAndDisSumReportService();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		RecAndDisSumReportForm scrForm = new RecAndDisSumReportForm();
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
		List<RecAndDisSumReportForm> list = new ArrayList<RecAndDisSumReportForm>();
		if("1".equals(scrForm.getType())) {
			list = scService.queryReport(null, 0, scrForm, users);
		} else {
			list = scService.queryDetailReport(null, 0, scrForm, users);
		}
		//获取当前日期
		String nowDate = Dateutil.getCurrentDateTimeString();
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=SalesConsumeReport"+nowDate+".xls");
		response.setContentType("application/msexcel");
		if("1".equals(scrForm.getType())) {
			writeXls(list, os, request);
		} else {
			writeDetailXls(list, os, request);
		}
		
		os.flush();
		os.close();
		DBUserLog.addUserLog(request, "列表");
		return null;
	}

	public void writeDetailXls(List<RecAndDisSumReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "收发货汇总报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "发货机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("outOrganName"))));
			sheets[j].addCell(new Label(m++, start + 1, "收货机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("inOrganName"))));
			sheets[j].addCell(new Label(m++, start + 1, "报表类型:", seachT));
			/*if (request.getParameter("reportType").equals("1")) {
				sheets[j].addCell(new Label(m++, start + 1, "收货"));
			} else {
				sheets[j].addCell(new Label(m++, start + 1, "发货"));
			}*/
			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("packsizename")));
			sheets[j].addCell(new Label(m++, start + 2, "日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("beginDate") + "  -  " + request.getParameter("endDate")));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("regionName")));
			sheets[j].addCell(new Label(m++, start + 3, "县:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("uname")));

			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "上级机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "上级机构所属大区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "上级机构所属地区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "下级机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "下级机构所属地区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "下级机构所属小区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
//			sheets[j].addCell(new Label(m++, start + 4, "交易类型", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "交易日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "积分", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				RecAndDisSumReportForm reportForm = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(reportForm.getOutOrganName())));
				sheets[j].addCell(new Label(m++, row, reportForm.getBigRegion()));
				sheets[j].addCell(new Label(m++, row, reportForm.getOutMiddleRegion()));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(reportForm.getInOrganName())));
				sheets[j].addCell(new Label(m++, row, reportForm.getInMiddleRegion()));
				sheets[j].addCell(new Label(m++, row, reportForm.getSmallRegion()));
				sheets[j].addCell(new Label(m++, row, reportForm.getProductName()));
				sheets[j].addCell(new Label(m++, row, reportForm.getSpec()));
				/*if("1".equals(reportForm.getBsort())) {
					sheets[j].addCell(new Label(m++, row, "发货"));
				} else {
					sheets[j].addCell(new Label(m++, row, "退回"));
				}*/
				
				sheets[j].addCell(new Label(m++, row, Dateutil.formatDate(reportForm.getMakeDate())));
				sheets[j].addCell(new Number(m++, row, getDouble(reportForm.getAmount()),QWCF));
				sheets[j].addCell(new Label(m++, row, reportForm.getUnitName()));
				sheets[j].addCell(new Number(m++, row, getDouble(reportForm.getBonusPoint()),QWCF2));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

	/**
	 * @param list
	 * @param os
	 * @param request
	 * @throws Exception
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void writeXls(List<RecAndDisSumReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "收发货汇总报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "发货机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("outOrganName"))));
			sheets[j].addCell(new Label(m++, start + 1, "收货机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("inOrganName"))));
			sheets[j].addCell(new Label(m++, start + 1, "报表类型:", seachT));
			if (request.getParameter("reportType").equals("1")) {
				sheets[j].addCell(new Label(m++, start + 1, "收货"));
			} else {
				sheets[j].addCell(new Label(m++, start + 1, "发货"));
			}
			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("packsizename")));
			sheets[j].addCell(new Label(m++, start + 2, "日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("beginDate") + "  -  " + request.getParameter("endDate")));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("regionName")));
			sheets[j].addCell(new Label(m++, start + 3, "县:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("uname")));
			

			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "上级机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "上级机构所属大区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "上级机构所属地区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "下级机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "下级机构所属地区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "下级机构所属小区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "汇总数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "积分数量", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				RecAndDisSumReportForm reportForm = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(reportForm.getOutOrganName())));
				sheets[j].addCell(new Label(m++, row, reportForm.getBigRegion()));
				sheets[j].addCell(new Label(m++, row, reportForm.getOutMiddleRegion()));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(reportForm.getInOrganName())));
				sheets[j].addCell(new Label(m++, row, reportForm.getInMiddleRegion()));
				sheets[j].addCell(new Label(m++, row, reportForm.getSmallRegion()));
				sheets[j].addCell(new Label(m++, row, reportForm.getProductName()));
				sheets[j].addCell(new Label(m++, row, reportForm.getSpec()));
				sheets[j].addCell(new Number(m++, row, getDouble(reportForm.getAmount()),QWCF));
				sheets[j].addCell(new Label(m++, row, reportForm.getUnitName()));
				sheets[j].addCell(new Number(m++, row, getDouble(reportForm.getBonusPoint()),QWCF2));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	private double getDouble(String value) {
		if(!StringUtil.isEmpty(value)) {
			return Double.parseDouble(value);
		} 
		return 0d;
	}
}
