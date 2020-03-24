package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.dao.Suit;
import com.winsafe.drp.util.DBUserLog;

public class DelSuitAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String id = request.getParameter("ID");
			AppSuit ap = new AppSuit();
			Suit suit = ap.getSuitByID(id);
			
			if (suit.getIsdeal() == 1) {
				String result = "databases.record.isapprovenooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			ap.delSuit(id);

			request.setAttribute("result", "databases.del.success");
			
			initdata(request);
			DBUserLog.addUserLog(userid, 5,"会员/积分管理>>删除抱怨投诉,编号:" + id,suit);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
