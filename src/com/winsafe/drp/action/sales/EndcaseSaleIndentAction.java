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

public class EndcaseSaleIndentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String soid = request.getParameter("SOID");
			AppSaleIndent aso = new AppSaleIndent();
			SaleIndent so = aso.getSaleIndentByID(soid);

			if (so.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.noauditnoaudittow");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (so.getIsendcase() == 1) {
				request.setAttribute("result", "databases.record.audittwo");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			so.setIsendcase(1);
			so.setEndcaseid(userid);
			so.setEndcasedate(DateUtil.getCurrentDate());
			aso.updSaleIndent(so);
			
			request.setAttribute("result", "databases.endcase.success");
			DBUserLog.addUserLog(userid, "结案销售订单");

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
