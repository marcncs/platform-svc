package com.winsafe.drp.keyretailer.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
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
import jxl.write.Number;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.BonusReportForm;
import com.winsafe.drp.keyretailer.service.BonusReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;

import common.Logger;

public class ExportSBonusReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExportSBonusReportAction.class);

	private BonusReportService scService = new BonusReportService();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		BonusReportForm scrForm = new BonusReportForm();
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
		List<BonusReportForm> list = new ArrayList<BonusReportForm>();
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

	public void writeDetailXls(List<BonusReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "积分报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("regionName")));
			sheets[j].addCell(new Label(m++, start + 1, "县:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("uname")));
			sheets[j].addCell(new Label(m++, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("organName"))));
			
			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("spec")));
			sheets[j].addCell(new Label(m++, start + 2, "年:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("year")));

			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "年度", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "所属大区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "所属地区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "所属小区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "所属县", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "目标积分", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "YTD积分", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "积分完成进度%", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "是否达标", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "支持度评价", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "最终积分", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "返利确认时间", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				BonusReportForm bonusReportForm = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getYear()));
				sheets[j].addCell(new Label(m++, row, String.valueOf(ESAPIUtil.decodeForHTML(bonusReportForm.getOrganName()))));
				sheets[j].addCell(new Label(m++, row, String.valueOf(bonusReportForm.getBigRegion())));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getMiddleRegion()));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getSmallRegion()));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getAreas()));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getProductName()));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getSpec()));
				sheets[j].addCell(new Number(m++, row, getDouble(bonusReportForm.getTargetPoint()), QWCF2));
				sheets[j].addCell(new Number(m++, row, getDouble(bonusReportForm.getCurPoint()), QWCF2));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getCompleteRate()));
				if("1".equals(bonusReportForm.getIsReached())) {
					sheets[j].addCell(new Label(m++, row, "是"));
				} else {
					sheets[j].addCell(new Label(m++, row, "否"));
				}
				if("1".equals(bonusReportForm.getIsSupported())) {
					sheets[j].addCell(new Label(m++, row, "支持"));
				} else if("2".equals(bonusReportForm.getIsSupported())) {
					sheets[j].addCell(new Label(m++, row, "不支持"));
				} else {
					sheets[j].addCell(new Label(m++, row, "未支持"));
				}
				sheets[j].addCell(new Number(m++, row, getDouble(bonusReportForm.getFinalPoint()), QWCF2));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getConfirmDate()!=null?Dateutil.formatDateTime(bonusReportForm.getConfirmDate()):""));
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
	public void writeXls(List<BonusReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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

			sheets[j].addCell(new Label(0, start, "积分报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("regionName")));
			sheets[j].addCell(new Label(m++, start + 1, "县:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("uname")));
			sheets[j].addCell(new Label(m++, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("organName"))));
			
			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("spec")));
			sheets[j].addCell(new Label(m++, start + 2, "年:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("year")));

			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "年度", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "所属大区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "所属地区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "所属小区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "所属县", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "目标积分", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "YTD积分", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "积分完成进度%", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "是否达标", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "支持度评价", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "最终积分", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "返利确认时间", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				BonusReportForm bonusReportForm = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getYear()));
				sheets[j].addCell(new Label(m++, row, String.valueOf(ESAPIUtil.decodeForHTML(bonusReportForm.getOrganName()))));
				sheets[j].addCell(new Label(m++, row, String.valueOf(bonusReportForm.getBigRegion())));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getMiddleRegion()));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getSmallRegion()));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getAreas()));
				sheets[j].addCell(new Number(m++, row, getDouble(bonusReportForm.getTargetPoint()), QWCF2));
				sheets[j].addCell(new Number(m++, row, getDouble(bonusReportForm.getCurPoint()), QWCF2));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getCompleteRate()));
				if("1".equals(bonusReportForm.getIsReached())) {
					sheets[j].addCell(new Label(m++, row, "是"));
				} else {
					sheets[j].addCell(new Label(m++, row, "否"));
				}
				if("1".equals(bonusReportForm.getIsSupported())) {
					sheets[j].addCell(new Label(m++, row, "支持"));
				} else if("2".equals(bonusReportForm.getIsSupported())) {
					sheets[j].addCell(new Label(m++, row, "不支持"));
				} else {
					sheets[j].addCell(new Label(m++, row, "未支持"));
				}
				sheets[j].addCell(new Number(m++, row, getDouble(bonusReportForm.getFinalPoint()), QWCF2));
				sheets[j].addCell(new Label(m++, row, bonusReportForm.getConfirmDate()!=null?Dateutil.formatDateTime(bonusReportForm.getConfirmDate()):""));
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
