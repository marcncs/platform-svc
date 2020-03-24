package com.winsafe.drp.action.report; 

import java.io.OutputStream;
import java.util.HashMap;
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
import com.winsafe.drp.dao.DuplicateDeliveryIdcodeReportForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.erp.services.CheckDuplicateDeliveryIdcodeService;
import com.winsafe.hbm.util.StringUtil;

import common.Logger;

public class ExcPutDupDeliveryIdcodeReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExcPutDupDeliveryIdcodeReportAction.class);

	private CheckDuplicateDeliveryIdcodeService scService = new CheckDuplicateDeliveryIdcodeService();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 获取查询条件
		try (OutputStream os = response.getOutputStream()) {
			// 获取查询条件
			Map map = new HashMap(request.getParameterMap());
			DuplicateDeliveryIdcodeReportForm scrForm = new DuplicateDeliveryIdcodeReportForm();
			MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
			// 判断页面是否初始化显示
			List<DuplicateDeliveryIdcodeReportForm> list = scService.queryReport(null, 0, scrForm, users);
			
			//获取当前日期
			String nowDate = Dateutil.getCurrentDateTimeString();
			// 写入excel中
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=SalesConsumeReport"+nowDate+".xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			DBUserLog.addUserLog(request, "导出");
		} catch (Exception e) {
			logger.error("", e);
		} 
		
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
	public void writeXls(List<DuplicateDeliveryIdcodeReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		try {
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
				sheets[j].addCell(new Label(0, start, "发货单重码统计报表", wchT));
				sheets[j].addCell(new Label(m++, start + 1, "箱码:", seachT));
				sheets[j].addCell(new Label(m++, start + 1, request.getParameter("idcode")));
				sheets[j].addCell(new Label(m++, start + 1, "发货机构:", seachT));
				sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("organName"))));
				sheets[j].addCell(new Label(m++, start + 1, "发货仓库:", seachT));
				sheets[j].addCell(new Label(m++, start + 1, request.getParameter("wname")));
				m = 0;
				sheets[j].addCell(new Label(m++, start + 2, "发货日期:", seachT));
				sheets[j].addCell(new Label(m++, start + 2, request.getParameter("beginDate") + "  -  " + request.getParameter("endDate")));
				m = 0;

				// 表头
				m = 0;
				sheets[j].addCell(new Label(m++, start + 4, "箱码", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "物料号", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "产品名称", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "批号", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "发货单号", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "SAP单号", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "发货机构", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "发货仓库", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "发货日期", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "收货机构", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "收货仓库", wcfFC));
				sheets[j].addCell(new Label(m++, start + 4, "收货日期", wcfFC));

				// 内容
				for (int i = start; i < currentnum; i++) {
					int row = i - start + 5;
					DuplicateDeliveryIdcodeReportForm salesConsumeReportForm = list.get(i);
					m = 0;
					sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getIdcode()));
					sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getmCode()));
					sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(salesConsumeReportForm.getProductName())));
					sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getSpecMode()));
					sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getBatch()));
					sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getBillNo()));
					sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getNcCode()));
					sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(salesConsumeReportForm.getOutOName())));
					sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(salesConsumeReportForm.getOutWName())));
					sheets[j].addCell(new Label(m++, row, salesConsumeReportForm.getOutDate()));
					sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(StringUtil.removeNull(salesConsumeReportForm.getInOName()))));
					sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(StringUtil.removeNull(salesConsumeReportForm.getInWName()))));
					sheets[j].addCell(new Label(m++, row, StringUtil.removeNull(salesConsumeReportForm.getReceiveDate())));
				}
			}
			workbook.write();
		} catch (Exception e) {
			throw e;
		} finally {
			workbook.close();
		}
	}
}
