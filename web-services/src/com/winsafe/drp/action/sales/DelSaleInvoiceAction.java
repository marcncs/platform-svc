package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleInvoice;
import com.winsafe.drp.dao.AppSaleInvoiceDetail;
import com.winsafe.drp.dao.SaleInvoice;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class DelSaleInvoiceAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			Integer siid = RequestTool.getInt(request,"SIID");
			AppSaleInvoice asi = new AppSaleInvoice();
			SaleInvoice si = asi.getSaleInvoiceByID(siid);
			if (si.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			asi.delSaleInvoice(siid);
			AppSaleInvoiceDetail asid = new AppSaleInvoiceDetail();
			asid.delSaleInvoiceBySIID(siid);
			
			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 6,"销售发票>>删除销售发票,编号："+siid,si);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
