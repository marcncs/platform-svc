package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ToAddProvideProductAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String pid = request.getParameter("pid");
    try{

      request.setAttribute("pid",pid);

      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
