package com.winsafe.drp.action.report;

import java.io.IOException;
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
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutSaleTradesBillTotalAction extends BaseAction {
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
			String Condition = " ( isendcase =1 and isblankout = 0 " + visitorgan
					+ " )  ";

			String[] tablename = { "SaleTrades" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppWithdraw aso = new AppWithdraw();
			List list = aso.getWithdrawBillTotal(whereSql);
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=SaleTradesBillTotal.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
		
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出零售换货按单据汇总");
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
			sheets[j].addCell(new Label(0, start, "单据编号"));
			sheets[j].addCell(new Label(1, start, "制单时间"));
			sheets[j].addCell(new Label(2, start, "会员编号"));
			sheets[j].addCell(new Label(3, start, "会员名称"));
			sheets[j].addCell(new Label(4, start, "会员手机"));
			sheets[j].addCell(new Label(5, start, "制单机构"));
			sheets[j].addCell(new Label(6, start, "金额"));
			           
			int row=0;
			Double totalsum=0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 1;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("id").toString()));
				sheets[j].addCell(new Label(1, row, p.get("makedate").toString()));
				sheets[j].addCell(new Label(2, row, p.get("cid").toString()));
				sheets[j].addCell(new Label(3, row, p.get("cname").toString()));
				sheets[j].addCell(new Label(4, row, p.get("cmobile").toString()));
				sheets[j].addCell(new Label(5, row, p.get("makeorganid").toString()));
				sheets[j].addCell(new Number(6, row,Double.valueOf(p.get("totalsum").toString()),wcfN));
				totalsum+=Double.valueOf(p.get("totalsum").toString());
				
			}
			sheets[j].addCell(new Label(0, row+1, "合计："));
			sheets[j].addCell(new Label(1,  row+1, ""));
			sheets[j].addCell(new Label(2,  row+1, ""));
			sheets[j].addCell(new Label(3,  row+1, ""));
			sheets[j].addCell(new Label(4,  row+1, ""));
			sheets[j].addCell(new Label(5,  row+1, ""));
			sheets[j].addCell(new Number(6,  row+1,totalsum,wcfN));
			
		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
