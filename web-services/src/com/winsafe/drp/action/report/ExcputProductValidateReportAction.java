package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.winsafe.drp.dao.ProductVlidateReportForm;
import com.winsafe.drp.server.ProductVlidataReportServer;
import com.winsafe.drp.server.SalesConsumeReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.HtmlSelect;
import common.Logger;

public class ExcputProductValidateReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExcputProductValidateReportAction.class);
	private ProductVlidataReportServer pvServer = new ProductVlidataReportServer();

	private SalesConsumeReportService scService = new SalesConsumeReportService();
	private AppRegion appRegion = new AppRegion();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		ProductVlidateReportForm pvrForm = new ProductVlidateReportForm();
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), pvrForm);

		// 页面图的数据
		String categories = "[]";
		String totleData = "[]";
		String trueData = "[]";
		String cateType = "";

		// 判断页面是否初始化显示
		List<ProductVlidateReportForm> list = new ArrayList<ProductVlidateReportForm>();
		Properties systemPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		String pvQueryCount = systemPro.getProperty("productValiQueryCount");

		// 获取查询条件
		list = pvServer.queryReport(null, 0, pvrForm, pvQueryCount, users);
		// 查询地图报表
		Map<String, String> charDateMap = pvServer.queryChartReport(pvrForm, users);
		categories = charDateMap.get("categories");
		totleData = charDateMap.get("totleData");
		trueData = charDateMap.get("trueData");
		cateType = "(" + charDateMap.get("cateType") + ")";

		//获取当前日期
		String nowDate = Dateutil.getCurrentDateTimeString();
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=ProductValidateReport"+nowDate+".xls");
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
	public void writeXls(List<ProductVlidateReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "产品验证分析报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("region") == null ? "" : appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(m++, start + 1, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 1, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("packSizeName")));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("beginDate") + "  -  " + request.getParameter("endDate")));

			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "查询码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "首次查询时间", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "首次查询电话/IP", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "累计次数", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "防伪码属性", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 4;
				ProductVlidateReportForm productVlidateReportForm = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, String.valueOf(productVlidateReportForm.getIdcode())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(productVlidateReportForm.getQueryDate())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(productVlidateReportForm.getQueryAddr())));
				sheets[j].addCell(new Number(m++, row, productVlidateReportForm.getQueryCount()));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "TrueOrFalse", productVlidateReportForm.getChkTrue())));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
