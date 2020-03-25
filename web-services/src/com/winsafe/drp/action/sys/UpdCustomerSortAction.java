package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.dao.CustomerSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class UpdCustomerSortAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		String sortname = request.getParameter("sortname");


		try {
			AppCustomerSort appcs = new AppCustomerSort();
			CustomerSort cs = appcs.getCustomerSortById(id);
			
			cs.setSortname(sortname);

			appcs.updCustomerSort(cs);

			
			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//DBUserLog.addUserLog(userid, "修改客户分类");
			
			return mapping.findForward("updresult");
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			//
			
		}

		return new ActionForward(mapping.getInput());
	}
}
