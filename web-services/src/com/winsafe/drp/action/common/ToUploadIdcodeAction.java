package com.winsafe.drp.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToUploadIdcodeAction
    extends BaseAction {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
	  request.setAttribute("billsort", request.getParameter("billsort"));
   return mapping.findForward("success");
 }

}
