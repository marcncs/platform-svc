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

public class ToUpdAfficheAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try
		{
			Integer id=Integer.valueOf(request.getParameter("id"));
			AppAffiche aff=new AppAffiche();
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();

			Affiche af=aff.getAfficheByID(id);
			if(!af.getMakeid().equals(userid)){
				request.setAttribute("result", "databases.del.nosuccess");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			request.setAttribute("af",af);
			
			return mapping.findForward("toupd");
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		
		return mapping.findForward("toupd");
	}
}
