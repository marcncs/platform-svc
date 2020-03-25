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

public class CancelEndcaseSaleIndentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String soid = request.getParameter("SOID");
			AppSaleIndent aso = new AppSaleIndent();
			SaleIndent so = aso.getSaleIndentByID(soid);

			if (so.getIsendcase() == 0) {
				String result = "databases.record.noaudittwo";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (!String.valueOf(so.getIsendcase()).contains(userid.toString())) {
				String result = "databases.record.cancelaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			so.setEndcasedate(DateUtil.getCurrentDate());
			so.setAuditid(userid);
			so.setIsendcase(0);
			 aso.updSaleIndent(so);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, "取消结案销售订单");

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
