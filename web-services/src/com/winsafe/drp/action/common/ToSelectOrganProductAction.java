package com.winsafe.drp.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToSelectOrganProductAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
   super.initdata(request);try{

	   String oid = request.getParameter("oid");
	   //request.getSession().setAttribute("pid",pid);	
	   request.setAttribute("oid", oid);
     return mapping.findForward("success");
   }catch(Exception e){
     e.printStackTrace();
   }
   return new ActionForward(mapping.getInput());
  }
}
