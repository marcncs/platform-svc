package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppAwake;

public class AffrieAwakeAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  
    String strid = request.getParameter("ID");
    Integer id = Integer.valueOf(strid);

    AppAwake appAwake=new AppAwake();
    try {
      appAwake.affrieGetAwake(id);
//      UsersBean users = UserManager.getUser(request);
//      Integer userid = users.getUserid();
//      DBUserLog.addUserLog(userid,"确认提醒");
      return null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}

