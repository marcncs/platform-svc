package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRepositoryFile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelRepositoryFileAction
    extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {


    try {

      UsersBean users = UserManager.getUser(request);
//      Long userid = users.getUserid();

      String id = request.getParameter("id");     

      AppRepositoryFile arf = new AppRepositoryFile();
      arf.delRepositoryFile(Long.valueOf(id));

      request.setAttribute("result", "databases.del.success");
//      DBUserLog.addUserLog(userid,"删除知识库附件");//日志 

      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return mapping.getInputForward();
  }

}
