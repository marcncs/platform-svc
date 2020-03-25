package com.winsafe.drp.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class MsgForwardAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
              HttpServletRequest request,
              HttpServletResponse response) throws IOException,ServletException
          {
      		  String msg = request.getParameter("result");
      		  request.setAttribute("result", msg);
                  return mapping.findForward("success");
          }

}
