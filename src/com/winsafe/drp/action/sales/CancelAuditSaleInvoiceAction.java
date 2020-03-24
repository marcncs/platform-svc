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

public class CancelAuditSaleInvoiceAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			Integer siid = RequestTool.getInt(request,"SIID");
			AppSaleInvoice aso = new AppSaleInvoice();
			SaleInvoice so = aso.getSaleInvoiceByID(siid);

			if (so.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			if (!String.valueOf(so.getAuditid()).contains(userid.toString())) {
//				String result = "databases.record.cancelaudit";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}

			aso.updIsAudit(siid, userid, 0);
			
			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 6,"销售发票>>取消初审销售发票,编号："+siid);

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
