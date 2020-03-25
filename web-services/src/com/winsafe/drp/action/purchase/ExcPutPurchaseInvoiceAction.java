package com.winsafe.drp.action.purchase;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInvoice;
import com.winsafe.drp.dao.PurchaseInvoice;
import com.winsafe.drp.dao.PurchaseInvoiceForm;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutPurchaseInvoiceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String[] tablename = { "PurchaseInvoice" };
			String whereSql = getWhereSql2(tablename); 

			String timeCondition = getTimeCondition("InvoiceDate");
			whereSql = whereSql + timeCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppProvider apv = new AppProvider();
			AppPurchaseInvoice api = new AppPurchaseInvoice();
			List<PurchaseInvoice> apls = api.searchPurchaseInvoice(whereSql);
			List<PurchaseInvoiceForm> alpl = new ArrayList<PurchaseInvoiceForm>();
			PurchaseInvoiceForm pif =null;
			for (PurchaseInvoice o : apls) {
				pif = new PurchaseInvoiceForm();
				pif.setId(o.getId());
				pif.setInvoicecode(o.getInvoicecode());
				pif.setInvoicetype(o.getInvoicetype());
				pif.setMakeinvoicedate(o.getMakeinvoicedate());
				pif.setInvoicedate(o.getInvoicedate());
				pif.setProvideidname(apv.getProviderByID(o.getProvideid()).getPname());
				pif.setMakeid(o.getMakeid());
				pif.setMakeorganid(o.getMakeorganid());
				pif.setIsaudit(o.getIsaudit());

				alpl.add(pif);
			}

			if (alpl.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListPurchaseInvoice.xls");
			response.setContentType("application/msexcel");
			writeXls(alpl, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 2,"采购管理>>导出采购发票");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<PurchaseInvoiceForm> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
		UsersService us = new UsersService();
		
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
			sheets[j].mergeCells(0, start, 8, start);
			sheets[j].addCell(new Label(0, start, "采购发票 ",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "供应商:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("ProvideName")));
			sheets[j].addCell(new Label(2, start+1, "发票编号:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("InvoiceCode")));
			sheets[j].addCell(new Label(4, start+1, "发票类型:", seachT));
			sheets[j].addCell(new Label(5, start+1, HtmlSelect.getNameByOrder(request, "InvoiceType", getInt("InvoiceType"))));
			
			sheets[j].addCell(new Label(0, start+2, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("oname")));	
			sheets[j].addCell(new Label(2, start+2, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("deptname")));		
			sheets[j].addCell(new Label(4, start+2, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("uname")));
			
			sheets[j].addCell(new Label(0, start+3, "是否复核:", seachT));
			sheets[j].addCell(new Label(1, start+3, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			sheets[j].addCell(new Label(2, start+3, "开票日期:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+4, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+4, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+5, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start+5, "发票编号", wcfFC));
			sheets[j].addCell(new Label(2, start+5, "供应商", wcfFC));
			sheets[j].addCell(new Label(3, start+5, "发票类型", wcfFC));
			sheets[j].addCell(new Label(4, start+5, "制票日期", wcfFC));
			sheets[j].addCell(new Label(5, start+5, "开票日期", wcfFC));
			sheets[j].addCell(new Label(6, start+5, "是否复核", wcfFC));
			sheets[j].addCell(new Label(7, start+5, "制单人", wcfFC));
			sheets[j].addCell(new Label(8, start+5, "制单机构", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				PurchaseInvoiceForm p = (PurchaseInvoiceForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId().toString()));
				sheets[j].addCell(new Label(1, row, p.getInvoicecode()));
				sheets[j].addCell(new Label(2, row, p.getProvideidname()));
				sheets[j].addCell(new Label(3, row, HtmlSelect.getNameByOrder(request, "InvoiceType",p.getInvoicetype())));
				sheets[j].addCell(new Label(4, row, DateUtil.formatDate(p.getMakeinvoicedate())));
				sheets[j].addCell(new Label(5, row, DateUtil.formatDate(p.getInvoicedate())));
				sheets[j].addCell(new Label(6, row, HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit())));
				String makeuser = us.getUsersName(p.getMakeid());
				sheets[j].addCell(new Label(7, row, makeuser));
				String makeorgan = organs.getOrganName(p.getMakeorganid());
				sheets[j].addCell(new Label(8, row, makeorgan));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
