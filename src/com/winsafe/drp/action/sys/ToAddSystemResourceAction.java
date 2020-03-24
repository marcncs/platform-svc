package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppSystemResource;

public class ToAddSystemResourceAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    try{
    	String tagName = request.getParameter("tagName");
    	
    	request.setAttribute("tagName", tagName);
            return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}