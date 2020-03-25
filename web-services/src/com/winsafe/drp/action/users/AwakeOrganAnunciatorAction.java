package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganAnnunciator;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class AwakeOrganAnunciatorAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();

		try{
			
			AppOrganAnnunciator awa = new AppOrganAnnunciator();
			awa.updOrganAnnumciator(users.getMakeorganid(), userid);

		      request.setAttribute("result", "databases.upd.success");

			return mapping.findForward("awake");
		}catch(Exception e){
			e.printStackTrace();
		}finally {

		    }
		
		return null;
	}

}
