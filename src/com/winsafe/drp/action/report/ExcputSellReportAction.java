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
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.ReportForm;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;
import common.Logger;

public class ExcputSellReportAction extends BaseAction {
	private Logger logger = Logger.getLogger(ExcputSellReportAction.class);
	private SellReportService srs = new SellReportService();
	private AppCountryArea appCountryArea = new AppCountryArea();
	private WarehouseService ws = new WarehouseService();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		List<ReportForm> list = new ArrayList<ReportForm>();
		list = srs.queryReport(null, 0, map, users);
		// 通过excelservice导入
		// ExcelService excelService = new ExcelService();
		// // 需要显示字段
		// String excputField =
		// "province,organid,organname,productname,warehouseid,productsort,mcode,mcodechinesename,mcodeenglishname,specmode,reportdate,inventorypile,countunit,billno,billdate";
		// // 设置单元格样式
		// excelService.setSeachT(seachT);
		// excelService.setWcfFC(wcfFC);
		// excelService.setWchT(wchT);
		//
		// // 设置标题
		// excelService.setTitle("销售报表");
		// // 设置检索条件名
		// String[] condition = { "省份", "机构", "仓库", "产品", "规格", "日期", "单位" };
		// // 设置表头
		// String[] header = { "省份", "机构代码", "机构名称", "仓库名称", "产品类别", "产品",
		// "物料号", "物料中文", "物料英文", "规格", "报告日期", "销售数量", "单位", "单据编号", "单据日期" };
		// // 设置检索条件
		// Map<String, String> searchCondition = new HashMap<String, String>();
		// searchCondition.put("省份",
		// appCountryArea.getAreaNameById(request.getParameter("province")));
		// searchCondition.put("机构", request.getParameter("oname"));
		// searchCondition.put("仓库", request.getParameter("wname"));
		// searchCondition.put("产品", request.getParameter("ProductName"));
		// searchCondition.put("规格", request.getParameter("packsizename"));
		// searchCondition.put("日期", request.getParameter("beginDate") + "  -  "
		// + request.getParameter("endDate"));
		// searchCondition.put("单位", "升,千克");
		//
		// excelService.setCondition(condition);
		// excelService.setHeader(header);
		// excelService.setSearchCondition(searchCondition);
		//
		// // 导出excel
		// OutputStream os = response.getOutputStream();
		// response.reset();
		// response.setHeader("content-disposition",
		// "attachment; filename=SellReport.xls");
		// response.setContentType("application/msexcel");
		// excelService.writeXls(list, os, request, excputField);

		//获取当前日期
		String nowDate = Dateutil.getCurrentDateTimeString();
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=SellReport"+nowDate+".xls");
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
			sheets[j].addCell(new Label(0, start, "销售报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "省份:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, appCountryArea.getAreaNameById(request.getParameter("province"))));
			sheets[j].addCell(new Label(m++, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("oname")));
			sheets[j].addCell(new Label(m++, start + 1, "仓库:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("wname")));
			sheets[j].addCell(new Label(m++, start + 1, "产品:", seachT));
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

			sheets[j].addCell(new Label(m++, start + 4, "省份", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构代码", wcfFC));
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
			sheets[j].addCell(new Label(m++, start + 4, "报告日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "销售数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单据编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单据日期", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				ReportForm reportForm = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, reportForm.getProvince() == null ? "" : String.valueOf(reportForm.getProvince())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(reportForm.getOrganid())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(ESAPIUtil.decodeForHTML(reportForm.getOrganname()))));
				sheets[j].addCell(new Label(m++, row, ws.getWarehouseName(reportForm.getWarehouseid())));
				sheets[j].addCell(new Label(m++, row, reportForm.getProductsort()));
				sheets[j].addCell(new Label(m++, row, reportForm.getProductname()));
				sheets[j].addCell(new Label(m++, row, reportForm.getProductname()));
				sheets[j].addCell(new Label(m++, row, reportForm.getProductNameen()));
				sheets[j].addCell(new Label(m++, row, reportForm.getMcodechinesename()));
				sheets[j].addCell(new Label(m++, row, reportForm.getMcodeenglishname()));
				sheets[j].addCell(new Label(m++, row, reportForm.getSpecmode()));
				sheets[j].addCell(new Label(m++, row, reportForm.getPackSizeNameEn()));
				sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(reportForm.getReportdate(), "yyyy-MM-dd")));
				sheets[j].addCell(new Number(m++, row, Double.valueOf(reportForm.getInventorypile().replace(",", ""))));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getResourceName(request, "CountUnit", reportForm.getCountunit())));
				sheets[j].addCell(new Label(m++, row, reportForm.getBillno()));
				if (StringUtil.isEmpty(reportForm.getBilldate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(DateUtil.formatStrDate(reportForm.getBilldate()), "yyyy-MM-dd")));
				}
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

}