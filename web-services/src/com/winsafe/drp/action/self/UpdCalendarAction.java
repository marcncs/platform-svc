package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCalendarAwake;
import com.winsafe.drp.dao.CalendarAwake;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
public class UpdCalendarAction  extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    AppCalendarAwake appCalendarAwake = new AppCalendarAwake();
    CalendarAwake ca = new CalendarAwake();

    String strid = request.getParameter("ID");
    Integer id = Integer.valueOf(strid);

    try {
     ca = appCalendarAwake.getCalendar(id,userid);


     String awakeDate = String.valueOf(DateUtil.formatDate(ca.getAwakedatetime()));
     String awakeTime = String.valueOf(DateUtil.formatTime(ca.getAwakedatetime()));
     String awakeContent = ca.getAwakecontent();
     String awakeModel = Internation.getSelectTagByKeyAll("AwakeModel",request,"AwakeModel",String.valueOf(ca.getAwakemodel()),null);

     request.setAttribute("ID",id);
     request.setAttribute("awakeDate",awakeDate);
     request.setAttribute("awakeTime",awakeTime);
     request.setAttribute("awakeContent",awakeContent);
     request.setAttribute("awakeModel",awakeModel);

     return mapping.findForward("edit");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
