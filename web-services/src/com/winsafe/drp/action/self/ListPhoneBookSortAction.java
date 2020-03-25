package com.winsafe.drp.action.self;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPhoneBookSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class ListPhoneBookSortAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    try{
    	UsersBean users = UserManager.getUser(request);
        Integer userid = users.getUserid();
      //PhoneBookSort pbs = new PhoneBookSort();
      AppPhoneBookSort apbs = new AppPhoneBookSort();
      List pbsls = apbs.getPhoneBookSort(userid);

      request.setAttribute("pbs",pbsls);

      DBUserLog.addUserLog(userid,0,"我的办公桌>>列表电话本结构");
      return mapping.findForward("listsort");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
