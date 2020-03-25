package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInvoice;
import com.winsafe.drp.dao.AppPurchaseInvoiceDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseInvoice;
import com.winsafe.drp.dao.PurchaseInvoiceDetail;
import com.winsafe.drp.dao.PurchaseInvoiceDetailForm;
import com.winsafe.drp.dao.PurchaseInvoiceForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class PurchaseInvoiceDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id =Integer.valueOf(request.getParameter("ID"));

		try {
			AppPurchaseInvoice asb = new AppPurchaseInvoice();
			PurchaseInvoice pi = new PurchaseInvoice();
			AppUsers au = new AppUsers();
			AppProvider apv = new AppProvider();

			pi = asb.getPurchaseInvoiceByID(id);
			PurchaseInvoiceForm sbf = new PurchaseInvoiceForm();
			sbf.setId(pi.getId());
			sbf.setInvoicecode(pi.getInvoicecode());
			sbf.setInvoicetypename(Internation.getStringByKeyPosition("InvoiceType",
					request, pi.getInvoicetype(), "global.sys.SystemResource"));
			sbf.setMakeinvoicedate(pi.getMakeinvoicedate());
			sbf.setInvoicedate(pi.getInvoicedate());
			sbf.setProvideidname(apv.getProviderByID(pi.getProvideid()).getPname());
			sbf.setMemo(pi.getMemo());
			sbf.setMakeid(pi.getMakeid());
			sbf.setMakedate(pi.getMakedate().toString());
			sbf.setIsaudit(pi.getIsaudit());

		    sbf.setAuditid(pi.getAuditid());
		      sbf.setAuditdate(pi.getAuditdate());
			sbf.setUpdateid(pi.getUpdateid());
			sbf.setLastdate(pi.getLastdate());
			sbf.setInvoicesum(pi.getInvoicesum());

			AppPurchaseInvoiceDetail asbd = new AppPurchaseInvoiceDetail();
			List sals = asbd.getPurchaseInvoiceDetailByPbId(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < sals.size(); i++) {
				PurchaseInvoiceDetailForm sbdf = new PurchaseInvoiceDetailForm();
				PurchaseInvoiceDetail o = (PurchaseInvoiceDetail) sals.get(i);
				sbdf.setId(o.getId());
				sbdf.setPiid(o.getPiid());
				sbdf.setPoid(o.getPoid());
				sbdf.setSubsum(o.getSubsum());
				sbdf.setMakedate(o.getMakedate().toString().substring(0,10));

				als.add(sbdf);
			}
			
			request.setAttribute("als", als);
			request.setAttribute("sbf", sbf);
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//DBUserLog.addUserLog(userid,"采购发票详情");
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
