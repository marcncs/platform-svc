package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUserVisit;

public class SetDeptVisitAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		try{
		Integer vu = (Integer) request.getSession().getAttribute("visituser");
		String strspeed = request.getParameter("speedstr");
		strspeed = strspeed.substring(0,strspeed.length()-1);
	    //int count = Integer.parseInt(request.getParameter("uscount"));

	      AppUserVisit auv = new AppUserVisit();
	      auv.UpdDeptVisitByUserID(vu,strspeed);

	      
	      request.setAttribute("result", "databases.add.success");
	      
	      return mapping.findForward("set");
	    }catch(Exception e){
	      e.printStackTrace();
	    }finally {

	    }

	    return new ActionForward(mapping.getInput());	
	}
}
