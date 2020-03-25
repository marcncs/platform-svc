package com.winsafe.drp.action.self;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPhoneBook;
import com.winsafe.drp.dao.AppPhoneBookSort;
import com.winsafe.drp.dao.PhoneBook;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToUpdPhoneBookAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String strid = request.getParameter("ID");
    Integer id = Integer.valueOf(strid);
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();

    try {
      PhoneBook pb = new PhoneBook();
      AppPhoneBook apb = new AppPhoneBook();
      pb = apb.getPhoneBookByID(id);

      AppPhoneBookSort apbs = new AppPhoneBookSort();
      String sex = Internation.getSelectTagByKeyAll("Sex", request, "sex",
          String.valueOf(pb.getSex()), null);
      List sort = apbs.getPhoneBookSort(userid);

      request.setAttribute("sex", sex);
      request.setAttribute("sort", sort);

      request.setAttribute("pb", pb);

      return mapping.findForward("toupd");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
