package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWlinkMan;
import com.winsafe.drp.util.DBUserLog;

public class DelWLinkmanAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			Integer id = Integer.valueOf(request.getParameter("id"));
			AppWlinkMan al = new AppWlinkMan();
			al.delWlinkman(id);
			
			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(request, "删除仓库联系人"+id);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
