package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.AppSaleInvoice;
import com.winsafe.drp.dao.SaleInvoice;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutSaleInvoiceAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String Condition = " (si.makeid="+userid+" "+getOrVisitOrgan("si.makeorganid")+") ";
			String[] tablename = { "SaleInvoice" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			String blur =getKeyWordCondition("CID","CNAME","InvoiceCode");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppSaleInvoice asl = new AppSaleInvoice();
			List<SaleInvoice> pils = asl.getSaleInvoiceAll(whereSql);

			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListObjIntegral.xls");
			response.setContentType("application/msexcel");
			writeXls(pils, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,6,"零售管理>>导出零售发票!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<SaleInvoice> list, OutputStream os,
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
			
			
			sheets[j].mergeCells(0, start, 7, start);
			sheets[j].addCell(new Label(0, start, "零售发票",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "发票类型:",seachT));
			sheets[j].addCell(new Label(1, start+1, HtmlSelect.getNameByOrder(request, "InvoiceType", getInt("InvoiceType"))));
			sheets[j].addCell(new Label(2, start+1, "是否复核:", seachT));
			String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"));
			sheets[j].addCell(new Label(3, start+1, IsStr));
			sheets[j].addCell(new Label(4, start+1, "制单机构:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("oname")));
		
			sheets[j].addCell(new Label(0, start+2, "制单人:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("uname")));
			sheets[j].addCell(new Label(2, start+2, "制单日期:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(4, start+2, "关键字:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("KeWord")));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "发票编号",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "会员名称",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "发票类型",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "制票日期",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "开票日期",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "总金额",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "是否复核",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				SaleInvoice p = (SaleInvoice) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId().toString()));
				sheets[j].addCell(new Label(1, row, p.getInvoicecode()));
				sheets[j].addCell(new Label(2, row, p.getCname()));
				sheets[j].addCell(new Label(3, row,  HtmlSelect.getNameByOrder(request, "InvoiceType", p.getInvoicetype())));
				sheets[j].addCell(new Label(4, row, Dateutil.formatDate(p.getMakeinvoicedate())));
				sheets[j].addCell(new Label(5, row, Dateutil.formatDate(p.getInvoicedate())));
				sheets[j].addCell(new Number(6, row, p.getInvoicesum(),wcfN));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(7, row, IsStr));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
