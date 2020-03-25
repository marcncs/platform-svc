package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.Affiche;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;

public class UpdAfficheAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			AppAffiche appAffiche = new AppAffiche();

			Affiche affiche = new Affiche();
			BeanCopy bc = new BeanCopy();
			bc.copy(affiche, request);

			Affiche af = appAffiche.getAfficheByID(affiche.getId());

			Affiche oldaf = (Affiche) BeanUtils.cloneBean(af);
			af.setAffichetitle(affiche.getAffichetitle());
			af.setAffichecontent(affiche.getAffichecontent());
			af.setAfficheorganid(affiche.getAfficheorganid());
			af.setAffichedeptid(affiche.getAffichedeptid());
			af.setAffichetype(affiche.getAffichetype());
			af.setPonderance(affiche.getPonderance());
			af.setEndDate(affiche.getEndDate());

			
			appAffiche.updAffiche(af);

			request.getSession().setAttribute("ID", af.getId());
			request.getSession().setAttribute("ReferType", "Affiche");
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request,"我的办公桌>>修改公告", oldaf, af);
			return mapping.findForward("updresult");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return mapping.getInputForward();
	}
}
