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
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddAfficheAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		AppAffiche appAffiche = new AppAffiche();
		AppAfficheBrowse appab = new AppAfficheBrowse();

		try {
			Affiche affiche = new Affiche();
			BeanCopy bc = new BeanCopy();
			
			bc.copy(affiche, request);
			
			Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("affiche", 0, ""));

			affiche.setId(id);
			affiche.setMakeid(userid);
			affiche.setMakeorganid(users.getMakeorganid());

			if (affiche.getIsPublish().equals("1")) {
				affiche.setMakedate(DateUtil.getCurrentDate());
			}
			
			appAffiche.addAffiche(affiche);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(request, "编号：" + affiche.getId());
			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
		}
		return null;
	}
}