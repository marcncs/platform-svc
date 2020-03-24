package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.Number;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.SalesConsumeReportForm;
import com.winsafe.drp.server.SalesConsumeReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.MapUtil;

import common.Logger;

public class ExcPutSalesConsumeReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExcPutSalesConsumeReportAction.class);

	private SalesConsumeReportService scService = new SalesConsumeReportService();
	private AppRegion appRegion = new AppRegion();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		SalesConsumeReportForm scrForm = new SalesConsumeReportForm();
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
		List<SalesConsumeReportForm> list = new ArrayList<SalesConsumeReportForm>();
		if("2".equals(map.get("type"))) {
			list = scService.queryReport(null, 0, scrForm, users);
		} else {
			list = scService.queryReport2(null, 0, scrForm, users);
		}
		//获取当前日期
		String nowDate = Dateutil.getCurrentDateTimeString();
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=SalesConsumeReport"+nowDate+".xls");
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
	public void writeXls(List<SalesConsumeReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "销售和消耗汇总报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(m++, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("organName"))));
			sheets[j].addCell(new Label(m++, start + 1, "仓库:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("wname")));
			sheets[j].addCell(new Label(m++, start + 1, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("ProductName")));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("packsizename")));
			sheets[j].addCell(new Label(m++, start + 2, "日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("beginDate") + "  -  " + request.getParameter("endDate")));
			sheets[j].addCell(new Label(m++, start + 2, "单位:", seachT));
			if (request.getParameter("countByUnit").equals("true")) {
				sheets[j].addCell(new Label(m++, start + 2, "升,千克"));
			} else {
				sheets[j].addCell(new Label(m++, start + 2, "件"));
			}
			m = 0;

			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "大区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "省份", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构内部编码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "仓库名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品类别", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料中文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "销售数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "退回工厂数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "消耗数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "下级经销商退货数量", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				SalesConsumeReportForm salesConsumeReportForm = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getRegionName() == null ? "" : String.valueOf(salesConsumeReportForm.getRegionName())));
				sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getProvince() == null ? "" : salesConsumeReportForm.getProvince()));
				sheets[j].addCell(new Label(m++, row, String.valueOf(salesConsumeReportForm.getOrganId())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(salesConsumeReportForm.getOecode())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(ESAPIUtil.decodeForHTML(salesConsumeReportForm.getOrganName()))));
				sheets[j].addCell(new Label(m++, row, String.valueOf(salesConsumeReportForm.getWarehouseName())));
				sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getPsName()));
				sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getProductName()));
				sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getProductNameen()));
				sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getmCode()));
				sheets[j].addCell(new Label(m++, row, String.valueOf(salesConsumeReportForm.getMatericalChDes())));
				sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getMatericalEnDes()));
				sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getPackSizeName()));
				sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getPackSizeNameEn()));
				sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getUnitName()));
				sheets[j].addCell(new Number(m++, row, salesConsumeReportForm.getSalesQuantity(), QWCF));
				sheets[j].addCell(new Number(m++, row, salesConsumeReportForm.getPwQuantity(), QWCF));
				sheets[j].addCell(new Number(m++, row, salesConsumeReportForm.getConsumeQuantity(), QWCF));
				sheets[j].addCell(new Number(m++, row, salesConsumeReportForm.getOwQuantity(), QWCF));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
