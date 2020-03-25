package com.winsafe.drp.action.finance;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.PaymentModeService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutListPaymentLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String[] tablename = { "PaymentLog" };
			String whereSql = getWhereSql2(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String blur = getKeyWordCondition("KeysContent");
			whereSql = whereSql + blur + timeCondition;
			whereSql = DbUtil.getWhereSql(whereSql);
			AppPaymentLog apl = new AppPaymentLog();
			List<PaymentLog> slls = apl.getPaymentLog(whereSql);
			if (slls.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListPaymentLog.xls");
			response.setContentType("application/msexcel");
			writeXls(slls, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 9, "账务管理>>导出收款!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List<PaymentLog> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organService = new OrganService();
		UsersService us = new UsersService();
		PaymentModeService pms  = new PaymentModeService();
		int snum = 1;
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

			sheets[j].mergeCells(0, start, 6, start);
			sheets[j].addCell(new Label(0, start, "付款", wchT));
			sheets[j].addCell(new Label(4, start + 1, "选择对象:", seachT));
			sheets[j].addCell(new Label(5, start + 1, request
					.getParameter("cname")));
			sheets[j].addCell(new Label(2, start + 1, "对象类型:", seachT));
			sheets[j].addCell(new Label(3, start + 1,
					HtmlSelect.getNameByOrder(request, "ObjectSort",
							getInt("ObjectSort"))));
			sheets[j].addCell(new Label(0, start + 1, "是否复核:", seachT));
			sheets[j].addCell(new Label(1, start + 1, HtmlSelect
					.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));

			sheets[j].addCell(new Label(0, start + 2, "是否收款:", seachT));
			sheets[j].addCell(new Label(1, start + 2, HtmlSelect
					.getNameByOrder(request, "YesOrNo", getInt("IsReceive"))));
			sheets[j].addCell(new Label(2, start + 2, "制单机构:", seachT));
			sheets[j].addCell(new Label(3, start + 2, request
					.getParameter("oname")));
			sheets[j].addCell(new Label(4, start + 2, "制单部门:", seachT));
			sheets[j].addCell(new Label(5, start + 2, request
					.getParameter("deptname")));

			sheets[j].addCell(new Label(0, start + 3, "制单人:", seachT));
			sheets[j].addCell(new Label(1, start + 3, request
					.getParameter("uname")));
			sheets[j].addCell(new Label(2, start + 3, "制单日期:", seachT));
			sheets[j].addCell(new Label(3, start + 3, request
					.getParameter("BeginDate")
					+ "-" + request.getParameter("EndDate")));
			sheets[j].addCell(new Label(4, start + 3, "关键字:", seachT));
			sheets[j].addCell(new Label(5, start + 3, request
					.getParameter("KeyWord")));

			sheets[j].addCell(new Label(0, start + 4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start + 4, request.getAttribute(
					"porganname").toString()));
			sheets[j].addCell(new Label(2, start + 4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start + 4, request.getAttribute(
					"pusername").toString()));
			sheets[j].addCell(new Label(4, start + 4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 4, DateUtil
					.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start + 5, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start + 5, "领款人", wcfFC));
			sheets[j].addCell(new Label(2, start + 5, "付款方式", wcfFC));
			sheets[j].addCell(new Label(3, start + 5, "付款金额", wcfFC));
			sheets[j].addCell(new Label(4, start + 5, "制单机构", wcfFC));
			sheets[j].addCell(new Label(5, start + 5, "制单人", wcfFC));
			sheets[j].addCell(new Label(6, start + 5, "制单日期", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				PaymentLog p = (PaymentLog) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, p.getPayee()));
				sheets[j].addCell(new Label(2, row, pms.getPaymentModeName(p.getPaymode())));
				sheets[j].addCell(new Number(3, row, p.getPaysum(), wcfN));
				sheets[j].addCell(new Label(5, row, organService.getOrganName(p
						.getMakeorganid())));
				sheets[j].addCell(new Label(4, row, us.getUsersName(p
						.getMakeid())));
				sheets[j]
						.addCell(new Label(6, row, p.getMakedate().toString()));

			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
