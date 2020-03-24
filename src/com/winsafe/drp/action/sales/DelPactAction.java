package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPact;
import com.winsafe.drp.dao.Pact;
import com.winsafe.drp.util.DBUserLog;

public class DelPactAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			Integer pid = Integer.valueOf(request.getParameter("PID"));
			AppPact ap = new AppPact();
			Pact pact = ap.getPactByID(pid);
			ap.delPact(pid);
			
			request.setAttribute("result", "databases.del.success");
			
			initdata(request);
			DBUserLog.addUserLog(userid,5,"会员/积分管理>>删除相关合同,编号："+pid,pact);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
