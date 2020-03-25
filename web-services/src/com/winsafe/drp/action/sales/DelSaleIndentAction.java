package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleIndent;
import com.winsafe.drp.dao.AppSaleIndentDetail;
import com.winsafe.drp.dao.SaleIndent;
import com.winsafe.drp.util.DBUserLog;

public class DelSaleIndentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String soid = request.getParameter("SOID");
			AppSaleIndent aso = new AppSaleIndent();
			SaleIndent so = aso.getSaleIndentByID(soid);
			if (so.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.lock");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppSaleIndentDetail asld = new AppSaleIndentDetail();
			asld.delSaleIndentDetail(soid);

			aso.delSaleIndent(soid);			
			
			request.setAttribute("result", "databases.del.success");
			
			initdata(request);
			DBUserLog.addUserLog(userid, 6,"零售管理>>删除销售订单,编号："+soid,so);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
