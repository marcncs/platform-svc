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
import com.winsafe.drp.dao.PurchaseInvoice;
import com.winsafe.drp.dao.PurchaseInvoiceForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListPurchaseInvoiceAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {

			String[] tablename = { "PurchaseInvoice" };
			String whereSql = getWhereSql2(tablename); 

			String timeCondition = getTimeCondition("InvoiceDate");
			whereSql = whereSql + timeCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppProvider apv = new AppProvider();
			AppPurchaseInvoice api = new AppPurchaseInvoice();
			List apls = api.searchPurchaseInvoice(request, pagesize, whereSql);
			ArrayList alpl = new ArrayList();
			for (int i = 0; i < apls.size(); i++) {
				PurchaseInvoiceForm pif = new PurchaseInvoiceForm();
				PurchaseInvoice o = (PurchaseInvoice) apls.get(i);
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

			request.setAttribute("alpl", alpl);

			DBUserLog.addUserLog(userid, 2, "采购管理>>列表采购发票");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
