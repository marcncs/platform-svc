package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

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
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.PaymentModeService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutPaymentLogTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pl.makeorganid");
			}
			String Condition = " pl.ispay=1 " + visitorgan;
			String[] tablename = { "PaymentLog" };
			String whereSql = getWhereSql(tablename);
			String timeCondition =getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);


			AppPaymentLog ail = new AppPaymentLog();
			List list = ail.getPaymentLogTotal(whereSql);

			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ReceivableTotal.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出付款汇总");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		PaymentModeService pms = new PaymentModeService();
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

			sheets[j].mergeCells(0, start, 7, start);
			sheets[j].addCell(new Label(0, start, "付款汇总",wchT));
			sheets[j].addCell(new Label(0, start+1, "对象类型:",seachT));
			sheets[j].addCell(new Label(1, start+1, HtmlSelect.getNameByOrder(request, "ObjectSort", Integer.valueOf(request.getParameter("objectsort")))));
			sheets[j].addCell(new Label(2, start+1, "付款方:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("payername")));
			sheets[j].addCell(new Label(4, start+1, "所属机构:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("oname")));
			
			sheets[j].addCell(new Label(0, start+2, "制单日期:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(2, start+2, "结算方式:", seachT));
			String paymode="";
			try{
				Integer payid = Integer.valueOf(request.getParameter("PayMode"));
				 paymode = pms.getPaymentModeName(payid);
			}catch (Exception e) {
				 paymode="";
			}
			
			sheets[j].addCell(new Label(3, start+2,paymode ));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			
			sheets[j].addCell(new Label(0, start+4, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "领款方编号",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "领款方",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "结算方式",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "款项来源",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "所属机构",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "制单日期",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "已付款金额",wcfFC));
			int row = 0;
			Double totalrv = 0.00;
			AppCashBank acb = new AppCashBank();
			OrganService ao = new OrganService();
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("id").toString()));
				sheets[j].addCell(new Label(1, row, p.get("poid").toString()));
				sheets[j].addCell(new Label(2, row, p.get("payee").toString()));
				sheets[j].addCell(new Label(3, row, pms.getPaymentModeName(Integer.valueOf(p.get("paymode").toString()))));
				sheets[j].addCell(new Label(4, row, acb.getCashBankById(Integer.valueOf(p.get("fundsrc").toString())).getCbname()));
				sheets[j].addCell(new Label(5, row, ao.getOrganName(p.get("makeorganid").toString())));
				sheets[j].addCell(new Label(6, row, p.get("makedate").toString()));
				sheets[j].addCell(new Number(7, row, Double.valueOf(p.get("paysum").toString()),wcfN));
				totalrv += Double.valueOf(p.get("paysum").toString());
			}
			sheets[j].addCell(new Label(0, row + 1, ""));
			sheets[j].addCell(new Label(1, row + 1, ""));
			sheets[j].addCell(new Label(2, row + 1, ""));
			sheets[j].addCell(new Label(3, row + 1, ""));
			sheets[j].addCell(new Label(4, row + 1, ""));
			sheets[j].addCell(new Label(5, row + 1, ""));
			sheets[j].addCell(new Label(6, row + 1, "合计："));
			sheets[j].addCell(new Number(7, row + 1, totalrv,wcfN));

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
