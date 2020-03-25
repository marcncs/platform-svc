package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPhoneBookSort;
import com.winsafe.drp.dao.PhoneBookSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class ListPhoneBookSortDetailAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String strid = request.getParameter("ID");
    Integer id = Integer.valueOf(strid);
    try{
      PhoneBookSort pbs = new PhoneBookSort();
      AppPhoneBookSort apbs = new AppPhoneBookSort();
      pbs = apbs.getPhoneBookSortByID(id);

      request.setAttribute("pbs",pbs);
      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      DBUserLog.addUserLog(userid,0,"我的办公桌>>列表电话本结构详情");
      return mapping.findForward("listphonedetail");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
