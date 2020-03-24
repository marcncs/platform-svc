package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPlinkman;
import com.winsafe.drp.dao.Plinkman;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class PlinkmanDetailAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String strid = request.getParameter("ID");
    Integer id = Integer.valueOf(strid);
    try{
      AppPlinkman apl = new AppPlinkman();
      Plinkman pl = apl.getProvideLinkmanByID(id);
      String sex = Internation.getStringByKeyPosition("Sex",
            request,
            pl.getSex(), "global.sys.SystemResource");

        String ismain = Internation.getStringByKeyPosition("YesOrNo",
              request,
              pl.getIsmain(), "global.sys.SystemResource");

      request.setAttribute("sex",sex);
      request.setAttribute("ismain",ismain);
      request.setAttribute("pl",pl);
      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();

      return mapping.findForward("detail");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
