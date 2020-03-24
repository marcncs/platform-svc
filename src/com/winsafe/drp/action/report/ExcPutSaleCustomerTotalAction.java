package com.winsafe.drp.action.report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutSaleCustomerTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " isendcase =1 and isblankout = 0" + visitorgan
					+ "  ";

			String[] tablename = { "SaleOrder" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String bulr = getKeyWordCondition("CMobile");
			whereSql = whereSql +bulr+ timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSaleOrder aso = new AppSaleOrder();
			List list = aso.getSaleCustomerTotal(whereSql);
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=SaleCustomerTotal.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出零售按会员汇总");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List list, OutputStream os,
			HttpServletRequest request) throws IOException,
			RowsExceededException, WriteException {
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
			sheets[j].mergeCells(0, start, 7, start);
			sheets[j].addCell(new Label(0, start, "零售按会员汇总",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "配送机构:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("oname2")));
			sheets[j].addCell(new Label(4, start+1, "日期:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+2, "会员名称:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("CName")));
			sheets[j].addCell(new Label(2, start+2, "会员手机:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "会员编号", wcfFC));
			sheets[j].addCell(new Label(1, start+4, "会员名称", wcfFC));
			sheets[j].addCell(new Label(2, start+4, "手机", wcfFC));
			sheets[j].addCell(new Label(3, start+4, "金额", wcfFC));
			int row=0;
			Double totalsum=0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("id").toString()));
				sheets[j].addCell(new Label(1, row, p.get("name").toString()));
				sheets[j].addCell(new Label(2, row, p.get("mobile").toString()));
				sheets[j].addCell(new Number(3, row, Double.valueOf(p.get("totalsum").toString()),wcfN));
				totalsum+=Double.valueOf(p.get("totalsum").toString());
				
			}
			sheets[j].addCell(new Label(0, row+1, "合计："));
			sheets[j].addCell(new Label(1,  row+1, ""));
			sheets[j].addCell(new Label(2,  row+1, ""));
			sheets[j].addCell(new Number(3,  row+1,totalsum,wcfN));
			
		}
		workbook.write();
		workbook.close();
		os.close();
	}

	public DateTime setDateTime(int cell, int row, Date datetime) {
		jxl.write.DateFormat df = new jxl.write.DateFormat(
				"yyyy-MM-dd hh:mm:ss");
		jxl.write.WritableCellFormat wcfDF = new jxl.write.WritableCellFormat(
				df);
		return new DateTime(cell, row, datetime, wcfDF);
	}

}
