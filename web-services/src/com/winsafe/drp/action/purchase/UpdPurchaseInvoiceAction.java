package com.winsafe.drp.action.purchase;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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

public class UpdPurchaseInvoiceAction extends Action {

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
			Integer id = Integer.valueOf(request.getParameter("id"));
			AppPurchaseInvoice api = new AppPurchaseInvoice();
			PurchaseInvoice pi = api.getPurchaseInvoiceByID(id);
			PurchaseInvoice oldpi = (PurchaseInvoice) BeanUtils.cloneBean(pi);
			pi.setId(id);
			pi.setInvoicecode(request.getParameter("invoicecode"));
			pi.setInvoicetype(Integer.valueOf(request
					.getParameter("invoicetype")));
			String makeinvoicedate = request.getParameter("makeinvoicedate");
			String invoicedate = request.getParameter("invoicedate");

			pi.setMakeinvoicedate(DateUtil.StringToDate(makeinvoicedate));

			pi.setInvoicedate(DateUtil.StringToDate(invoicedate));

			pi.setProvideid(pid);
			pi.setMemo(request.getParameter("memo"));
			pi.setMakeid(userid);
			pi.setUpdateid(userid);
			pi.setLastdate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));

			
			String strpoid[] = request.getParameterValues("poid");
			String strsubsum[] = request.getParameterValues("subsum");
			String strmakedate[] = request.getParameterValues("makedate");

			String poid;
			Double subsum;

			AppPurchaseInvoiceDetail apid = new AppPurchaseInvoiceDetail();
			apid.delPurchaseInvoiceDetailByPiID(id);
			if(strpoid != null){
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
				pivd.setPiid(id);
				pivd.setPoid(poid);
				pivd.setSubsum(subsum);
				pivd.setMakedate(new Date(strmakedate[i].replace('-', '/')));

				apid.addPurchaseInvoiceDetail(pivd);

			}
			}
			api.updPurchaseInvoiceByID(pi);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 2, "采购管理>>修改采购发票,编号：" + id, oldpi, pi);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
