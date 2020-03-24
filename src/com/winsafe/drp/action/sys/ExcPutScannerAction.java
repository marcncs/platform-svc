package com.winsafe.drp.action.sys;

import java.io.OutputStream;
import java.util.List;

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
import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.dao.Scanner;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DateUtil;

public class ExcPutScannerAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// dao层所需类
		AppScanner as = new AppScanner();
		// 初始化
		initdata(request);
		try {
			String id = request.getParameter("idSearch");
			String model = request.getParameter("modelSearch");
			String osVersion = request.getParameter("osVersionSearch");
			String status = request.getParameter("statusSearch");
			String installDate = request.getParameter("installDateSearch");
			String appVersion = request.getParameter("appVersionSearch");
			String appVerUpDate = request.getParameter("appVerUpDateSearch");
			String wHCode = request.getParameter("wHCodeSearch");
			String scannerName = request.getParameter("scannerNameSearch");
			String lastUpDate = request.getParameter("lastUpDateSearch");
			String scannerImeiN = request.getParameter("scannerImeiNSearch");

			// 查询条件
			String whereSql = " where 1 = 1 ";
			if (null != id && id.length() > 0) {
				whereSql += " and  s.id like '%" + id + "%' ";
			}
			if (model != null && model.length() > 0) {
				whereSql += " and  s.model like '%" + model + "%' ";
			}
			if (null != osVersion && !"".equals(osVersion)) {
				whereSql += " and s.osVersion like '%" + osVersion + "%' ";
			}
			if (null != status && !"".equals(status)) {
				whereSql += " and s.status like '%" + status + "%' ";
			}
			if (null != installDate && !"".equals(installDate)) {
				whereSql += " and TO_CHAR(installDate,'YYYY-MM-DD')= '" + installDate + "' ";
			}
			if (null != appVersion && !"".equals(appVersion)) {
				whereSql += " and s.appVersion like '%" + appVersion + "%' ";
			}
			if (null != appVerUpDate && !"".equals(appVerUpDate)) {
				whereSql += " and TO_CHAR(APPVERUPDATE,'YYYY-MM-DD')= '" + appVerUpDate + "' ";
			}
			if (null != wHCode && !"".equals(wHCode)) {
				whereSql += " and s.wHCode like '%" + wHCode + "%' ";
			}
			if (null != scannerName && !"".equals(scannerName)) {
				whereSql += " and s.scannerName like '%" + scannerName + "%' ";
			}
			if (null != lastUpDate && !"".equals(lastUpDate)) {
				whereSql += " and TO_CHAR(LASTUPDATE,'YYYY-MM-DD')= '" + lastUpDate + "' ";
			}
			if (null != scannerImeiN && scannerImeiN.length() > 0) {
				whereSql += " and  s.scannerImeiN like '%" + scannerImeiN + "%' ";
			}
			// 获取产品列表
			List<Scanner> list = as.findByWhereSql(whereSql);
			DBUserLog.addUserLog(userid, "采集器设置", "采集器 >>导出采集器资料");
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=Scanner.xls");
			response.setContentType("application/msexcel");
			// 写入excel中
			writeXls(list, os, request);
			os.flush();
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成excel
	 * 
	 * @param list
	 *            --scanner数据
	 * @param os
	 * @param request
	 * @throws Exception
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @author jason.huang
	 */
	public void writeXls(List<Scanner> list, OutputStream os, HttpServletRequest request)
			throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "采集器资料", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "编号:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("idSearch")));
			sheets[j].addCell(new Label(m++, start + 1, "型号:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("modelSearch")));
			sheets[j].addCell(new Label(m++, start + 1, "系统版本:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("osVersionSearch")));
			sheets[j].addCell(new Label(m++, start + 1, "采集器编号:", seachT));
			sheets[j]
					.addCell(new Label(m++, start + 1, request.getParameter("scannerImeiNSearch")));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "状态:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("statusSearch")));
			sheets[j].addCell(new Label(m++, start + 2, "安装日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("installDateSearch")));
			sheets[j].addCell(new Label(m++, start + 2, "软件版本:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("appVersionSearch")));
			sheets[j].addCell(new Label(m++, start + 2, "更新日期:", seachT));
			sheets[j]
					.addCell(new Label(m++, start + 2, request.getParameter("appVerUpDateSearch")));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "仓库代码:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("wHCodeSearch")));
			sheets[j].addCell(new Label(m++, start + 3, "名字:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("scannerNameSearch")));
			sheets[j].addCell(new Label(m++, start + 3, "最后更新日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("lastUpDateSearch")));
			sheets[j].addCell(new Label(m++, start + 3, "导出机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(m++, start + 3, "导出人:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getAttribute("pusername")
					.toString()));
			sheets[j].addCell(new Label(m++, start + 3, "导出时间:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, DateUtil.getCurrentDateTime()));

			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "型号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "系统版本", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "采集器编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "状态", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "安装日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "软件版本", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "更新日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "仓库代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "名字", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "最后更新日期", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				Scanner p = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, String.valueOf(p.getId())));
				sheets[j].addCell(new Label(m++, row, p.getModel()));
				sheets[j].addCell(new Label(m++, row, p.getOsVersion()));
				sheets[j].addCell(new Label(m++, row, String.valueOf(p.getScannerImeiN())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(p.getStatus())));
				sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(p.getInstallDate())));
				sheets[j].addCell(new Label(m++, row, p.getAppVersion()));
				sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(p.getAppVerUpDate())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(p.getwHCode())));
				sheets[j].addCell(new Label(m++, row, p.getScannerName()));
				sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(p.getLastUpDate())));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
