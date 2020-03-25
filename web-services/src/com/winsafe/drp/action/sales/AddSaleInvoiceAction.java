package com.winsafe.drp.action.sales;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleInvoice;
import com.winsafe.drp.dao.AppSaleInvoiceDetail;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.SaleInvoice;
import com.winsafe.drp.dao.SaleInvoiceDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddSaleInvoiceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String invoicecode = request.getParameter("invoicecode");
			String invoicecontent = request.getParameter("invoicecontent");
			Integer invoicetype = RequestTool.getInt(request,"invoicetype");
			String makeinvoicedate = request.getParameter("makeinvoicedate");
			String invoicedate = request.getParameter("invoicedate");
			String memo = request.getParameter("memo");
			String strtotalsum = request.getParameter("totalsum");
			Double totalsum;

			
			String strsoid[] = request.getParameterValues("soid");
			double subsum[] = RequestTool.getDoubles(request, "subsum");
			String strmakedate[] = request.getParameterValues("makedate");
			
			String soid;

			if (DataValidate.IsDouble(strtotalsum)) {
				totalsum = Double.valueOf(strtotalsum);
			} else {
				totalsum = Double.valueOf(0.00);
			}

			SaleInvoice si = new SaleInvoice();
			Integer siid = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"sale_invoice", 0, ""));
			si.setId(siid);
			si.setInvoicecode(invoicecode);
			si.setInvoicecontent(invoicecontent);
			si.setInvoicetype(invoicetype);
			String tmpmakeinvoicedate = makeinvoicedate.replace('-', '/');
			if (tmpmakeinvoicedate != null
					&& tmpmakeinvoicedate.trim().length() > 0) {
				si.setMakeinvoicedate(new Date(tmpmakeinvoicedate));
			}
			String tmpinvoicedate = invoicedate.replace('-', '/');
			if (tmpinvoicedate != null && tmpinvoicedate.trim().length() > 0) {
				si.setInvoicedate(new Date(tmpinvoicedate));
			}
			si.setInvoicesum(totalsum);
			si.setCid(cid);
			si.setCname(request.getParameter("cname"));
			si.setInvoicetitle(request.getParameter("invoicetitle"));
			si.setSendaddr(request.getParameter("sendaddr"));
			si.setMemo(memo);
			si.setIsaudit(0);
			si.setAuditid(0);
			si.setMakeorganid(users.getMakeorganid());
			si.setMakedeptid(users.getMakedeptid());
			si.setMakeid(userid);
			si.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			si.setUpdateid(userid);
			si.setLastdate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));

			AppSaleInvoice asi = new AppSaleInvoice();
			AppSaleOrder aso = new AppSaleOrder();
			AppSaleInvoiceDetail asld = new AppSaleInvoiceDetail();

			if (strsoid != null) {

			for (int i = 0; i < strsoid.length; i++) {
				soid = strsoid[i];

				SaleInvoiceDetail sid = new SaleInvoiceDetail();
				sid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"sale_invoice_detail", 0, "")));
				sid.setSiid(siid);
				sid.setSoid(soid);
				sid.setSubsum(subsum[i]);
				sid.setMakedate(new Date(strmakedate[i].replace('-', '/')));

				asld.addSaleInvoiceDetail(sid);
				
				//改变销售单中的开票标志
				aso.updIsMakeTicket(soid, 1);
			}
			}
			
			asi.addSaleInvoice(si);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 6,"销售发票>>新增销售发票,编号："+siid);
			return mapping.findForward("result");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
