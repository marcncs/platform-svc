package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppPurchaseInvoice;
import com.winsafe.drp.dao.AppPurchaseInvoiceDetail;
import com.winsafe.drp.dao.PurchaseInvoice;
import com.winsafe.drp.dao.PurchaseInvoiceDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddPurchaseInvoiceAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String pid = request.getParameter("pid");
			if (pid == null || pid.equals("null") || pid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			PurchaseInvoice pi = new PurchaseInvoice();
			Integer piid = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"purchase_invoice", 0, ""));
			pi.setId(piid);
			pi.setInvoicecode(request.getParameter("invoicecode"));
			pi.setInvoicetype(Integer.valueOf(request
					.getParameter("invoicetype")));
			pi.setMakeinvoicedate(DateUtil.StringToDate(request.getParameter(
					"makeinvoicedate")));
			pi.setInvoicedate(DateUtil.StringToDate(request.getParameter("invoicedate")));
			pi.setProvideid(pid);
			pi.setMemo(request.getParameter("memo"));
			pi.setMakeorganid(users.getMakeorganid());
			pi.setMakedeptid(users.getMakedeptid());
			pi.setMakeid(userid);
			pi.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			pi.setIsaudit(0);
			pi.setAuditid(Integer.valueOf(0));
			pi.setUpdateid(userid);
			pi.setLastdate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			pi.setInvoicesum(Double.valueOf(request.getParameter("totalsum")));

			
			String strpoid[] = request.getParameterValues("poid");
			String strsubsum[] = request.getParameterValues("subsum");
			String strmakedate[] = request.getParameterValues("makedate");
			String poid;
			Double subsum;

			AppPurchaseBill apb = new AppPurchaseBill();
			AppPurchaseInvoice api = new AppPurchaseInvoice();
			AppPurchaseInvoiceDetail apbd = new AppPurchaseInvoiceDetail();
			if (strpoid != null) {
			PurchaseInvoiceDetail[] pidsum = new PurchaseInvoiceDetail[strpoid.length];
			for (int i = 0; i < strpoid.length; i++) {
				poid = strpoid[i];

				if (DataValidate.IsDouble(strsubsum[i])) {
					subsum = Double.valueOf(strsubsum[i]);
				} else {
					subsum = Double.valueOf(0.00);
				}

				PurchaseInvoiceDetail pivd = new PurchaseInvoiceDetail();
				pivd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"purchase_invoice_detail", 0, "")));
				pivd.setPiid(piid);
				pivd.setPoid(poid);
				pivd.setSubsum(subsum);
				pivd.setMakedate(DateUtil.StringToDate(strmakedate[i]));
				pidsum[i] = pivd;
				

				
				apb.updIsMakeTicket(poid, 1);
			}
			apbd.addPurchaseInvoiceDetail(pidsum);
			}
			
			api.addPurchaseInvoice(pi);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid,2, "采购管理>>新增采购发票,编号：" + piid);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
