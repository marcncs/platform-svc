package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillTotal;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutPurchaseBillTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " ( pb.isratify=1  " + visitorgan + " )   ";

			String[] tablename = { "PurchaseBill" };
			String whereSql = getWhereSql(tablename);
			String blur = getKeyWordCondition("PID", "PLinkman",
					"Tel");
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList list = new ArrayList();

			AppPurchaseBill aso = new AppPurchaseBill();
			List pils = aso.getPurchaseBill(whereSql);
			double totalsum = 0.00;
			for (Iterator it = pils.iterator(); it.hasNext();) {
				PurchaseBillTotal pbt = new PurchaseBillTotal();
				PurchaseBill pb = (PurchaseBill) it.next();
				pbt.setPid(pb.getId());
				pbt.setTotalsum(pb.getTotalsum());
				pbt.setMakedate(DateUtil.formatDateTime(pb.getMakedate()));
				totalsum += pbt.getTotalsum();
				list.add(pbt);
			}
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=PurchaseBillTotal.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出采购订单按单据汇总");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void writeXls(List list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
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
			sheets[j].mergeCells(0, start, 5, start);
			sheets[j].addCell(new Label(0, start, "采购订单按单据汇总",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "采购部门:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("deptname")));
			sheets[j].addCell(new Label(4, start+1, "制单日期:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+2, "单据编号:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("ID")));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "单据号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "制单时间",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "金额",wcfFC));
			int row = 0;
			Double totalsum = 0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				PurchaseBillTotal p = (PurchaseBillTotal) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getPid()));
				sheets[j].addCell(new Label(1, row, p.getMakedate().toString()));
				sheets[j].addCell(new Number(2, row, p.getTotalsum(),wcfN));
				totalsum += p.getTotalsum();
			}
			sheets[j].addCell(new Label(0, row + 1, "合计："));
			sheets[j].addCell(new Label(1, row + 1, ""));
			sheets[j].addCell(new Number(2, row + 1, totalsum,wcfN));

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
