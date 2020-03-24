package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCalendarAwake;
import com.winsafe.drp.dao.CalendarAwake;

public class GetAwakeAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                  HttpServletRequest request,
                HttpServletResponse response) throws Exception{
  String strid = request.getParameter("ID");
  AppCalendarAwake aca = new AppCalendarAwake();
  try{
    CalendarAwake ca = new CalendarAwake();
    ca = aca.getAwakeByID(strid);
    request.setAttribute("ca",ca);

    return mapping.findForward("getawake");
  }catch(Exception e){
    e.printStackTrace();
  }

  return new ActionForward(mapping.getInput());
}
}
