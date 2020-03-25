package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.Affiche;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.AppAfficheBrowse;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelAfficheAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			AppAffiche aa = new AppAffiche();
			// AppAfficheBrowse aab = new AppAfficheBrowse();
			Affiche affiche = aa.getAfficheByID(id);
			if (!affiche.getMakeid().equals(userid) && userid.intValue() != 1) {
				request.setAttribute("result", "databases.del.nosuccess");
				return mapping.findForward("del");
			}
			if (affiche.getIsPublish().equals("1")) {
				request.setAttribute("result", "databases.del.ispublish");
				return mapping.findForward("del");
			}

			// aab.delAfficheBrowse(id);
			aa.delAffiche(id);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(request, "编号：" + id, affiche);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
