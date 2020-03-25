package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppContactLog;
import com.winsafe.drp.dao.ContactLog;
import com.winsafe.drp.util.DBUserLog;

public class DelContactLogAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Integer clid = Integer.valueOf(request.getParameter("CLID"));
			AppContactLog acl = new AppContactLog();
			ContactLog clg = acl.getContactLog(clid);
			acl.delContactLog(clid);

			request.setAttribute("result", "databases.del.success");
			
			initdata(request);
			DBUserLog.addUserLog(userid,5,"会员/积分管理>>删除商务联系 ,编号："+clid,clg);
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
