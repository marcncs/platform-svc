package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToSelectIdcodeAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
   super.initdata(request);try{

	   request.setAttribute("pid", request.getParameter("pid"));
	   request.setAttribute("batch", "");
     return mapping.findForward("toselect");
   }catch(Exception e){
     e.printStackTrace();
   }
   return new ActionForward(mapping.getInput());
  }
}
