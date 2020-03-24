package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInvoice;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseInvoice;
import com.winsafe.drp.dao.PurchaseInvoiceForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ListPurchaseInvoiceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 5;
		String strpid = request.getParameter("pid");
		if (strpid == null) {
			strpid = (String) request.getSession().getAttribute("pid");
		}
		String pid = strpid;
		initdata(request);
		try {
			String Condition = " pi.provideid='" + pid + "' ";
			String[] tablename = { "PurchaseInvoice" };
			String whereSql = getWhereSql2(tablename);

			whereSql = whereSql + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProvider apv = new AppProvider();
			AppUsers au = new AppUsers();
			AppPurchaseInvoice api = new AppPurchaseInvoice();
			List apls = api.searchPurchaseInvoice(request, pagesize, whereSql);
			ArrayList alpl = new ArrayList();
			for (int i = 0; i < apls.size(); i++) {
				PurchaseInvoiceForm pif = new PurchaseInvoiceForm();
				PurchaseInvoice o = (PurchaseInvoice) apls.get(i);
				pif.setId(o.getId());
				pif.setInvoicecode(o.getInvoicecode());
				pif.setInvoicetypename(HtmlSelect.getNameByOrder(request,
						"InvoiceType", o.getInvoicetype()));
				pif.setMakeinvoicedate(o.getMakeinvoicedate());
				pif.setInvoicedate(o.getInvoicedate());
				pif.setProvideidname(apv.getProviderByID(o.getProvideid())
						.getPname());
				pif.setMakeidname(au.getUsersByid(o.getMakeid()).getRealname());
				pif.setIsauditname(HtmlSelect.getNameByOrder(request,
						"YesOrNo", o.getIsaudit()));
				alpl.add(pif);
			}

			request.getSession().setAttribute("pid", strpid);
			request.setAttribute("pid", pid);
			request.setAttribute("alpl", alpl);
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 2, "采购管理>>列表采购发票");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
