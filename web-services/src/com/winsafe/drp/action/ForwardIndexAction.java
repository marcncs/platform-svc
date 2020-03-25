package com.winsafe.drp.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ForwardIndexAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
              HttpServletRequest request,
              HttpServletResponse response) throws IOException,ServletException
          {
                  ActionForward forward = new ActionForward("/sys/lostsession.jsp");
                  forward.setRedirect(true);

                  return forward;
          }

}
