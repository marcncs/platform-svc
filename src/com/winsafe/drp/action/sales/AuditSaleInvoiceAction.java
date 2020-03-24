package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleInvoice;
import com.winsafe.drp.dao.SaleInvoice;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class AuditSaleInvoiceAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {
			Integer siid = RequestTool.getInt(request,"SIID");
			AppSaleInvoice aso = new AppSaleInvoice();
			SaleInvoice so = aso.getSaleInvoiceByID(siid);

			if (so.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			aso.updIsAudit(siid, userid, 1);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 6,"销售发票>>复核销售发票,编号:"+siid);

			return mapping.findForward("audit");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
