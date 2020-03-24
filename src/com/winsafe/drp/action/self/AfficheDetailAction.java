package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.Affiche;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class AfficheDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer id = Integer.valueOf(request.getParameter("id"));
		AppAffiche aff = new AppAffiche();
		try {
			Affiche af = aff.getAfficheByID(id);
			request.setAttribute("content", af.getAffichecontent().replace("\n", "<br/>"));
			request.setAttribute("af", af);
//			aff.updAfficheBrowse(id, UserManager.getUser(request).getUserid());
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(request, "公告详情");
			return mapping.findForward("afffichedetail");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("afffichedetail");
	}

}
