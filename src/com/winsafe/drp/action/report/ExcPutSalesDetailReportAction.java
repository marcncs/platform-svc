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
import com.winsafe.drp.dao.SalesConsumeDetailReportForm;
import com.winsafe.drp.server.SalesConsumeReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

import common.Logger;

public class ExcPutSalesDetailReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExcPutSalesDetailReportAction.class);

	private SalesConsumeReportService scService = new SalesConsumeReportService();
	private AppRegion appRegion = new AppRegion();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		SalesConsumeDetailReportForm scrForm = new SalesConsumeDetailReportForm();
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
		List<SalesConsumeDetailReportForm> list = new ArrayList<SalesConsumeDetailReportForm>();
		if("2".equals(map.get("type"))) {
			list = scService.querySalesDetailReport(null, 0, scrForm, users);
		} else {
			list = scService.querySalesDetailReport2(null, 0, scrForm, users);
		}
		//获取当前日期
		String nowDate = Dateutil.getCurrentDateTimeString();
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=SalesDetailReport"+nowDate+".xls");
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
	public void writeXls(List<SalesConsumeDetailReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "销售明细报表", wchT));
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
			sheets[j].addCell(new Label(m++, start + 4, "批号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "销售数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单据内部编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单据编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单据日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "生产日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "过期日期", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				SalesConsumeDetailReportForm salesConsumeDetailReportForm = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getRegionName() == null ? "" : String.valueOf(salesConsumeDetailReportForm.getRegionName())));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getProvince() == null ? "" : salesConsumeDetailReportForm.getProvince()));
				sheets[j].addCell(new Label(m++, row, String.valueOf(salesConsumeDetailReportForm.getOrganId())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(salesConsumeDetailReportForm.getOecode())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(ESAPIUtil.decodeForHTML(salesConsumeDetailReportForm.getOrganName()))));
				sheets[j].addCell(new Label(m++, row, String.valueOf(salesConsumeDetailReportForm.getWarehouseName())));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getPsName()));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getProductName()));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getProductNameen()));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getmCode()));
				sheets[j].addCell(new Label(m++, row, String.valueOf(salesConsumeDetailReportForm.getMatericalChDes())));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getMatericalEnDes()));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getPackSizeName()));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getPackSizeNameEn()));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getBatch()));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getUnitName()));
				sheets[j].addCell(new Number(m++, row, salesConsumeDetailReportForm.getSalesQuantity()));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getNccode()));
				sheets[j].addCell(new Label(m++, row, salesConsumeDetailReportForm.getBillNo()));
				sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(salesConsumeDetailReportForm.getMakeDate(), "yyyy-MM-dd")));
				if (StringUtil.isEmpty(salesConsumeDetailReportForm.getProduceDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(DateUtil.formatStrDate(salesConsumeDetailReportForm.getProduceDate()), "yyyy-MM-dd")));
				}
				if (StringUtil.isEmpty(salesConsumeDetailReportForm.getExpiryDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(DateUtil.formatStrDate(salesConsumeDetailReportForm.getExpiryDate()), "yyyy-MM-dd")));
				}
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
