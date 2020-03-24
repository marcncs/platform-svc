package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleIndent;
import com.winsafe.drp.dao.SaleIndent;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class AuditSaleIndentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);
		try {
			String soid = request.getParameter("SOID");
			AppSaleIndent aso = new AppSaleIndent();
			SaleIndent so = aso.getSaleIndentByID(soid);

			if (so.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			so.setIsaudit(1);
			so.setAuditid(userid);
			so.setAuditdate(DateUtil.getCurrentDate());

			aso.updSaleIndent(so);			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, "复核销售订单");

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
