package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRepository;
import com.winsafe.drp.dao.AppRepositoryFile;
import com.winsafe.drp.dao.AppRepositoryProduct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelRepositoryAction
    extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {


    try {

      UsersBean users = UserManager.getUser(request);
//      Long userid = users.getUserid();

      String id = request.getParameter("id");
     

      AppRepositoryFile arf = new AppRepositoryFile();
      AppRepositoryProduct aprp = new AppRepositoryProduct();
      
      AppRepository ar = new AppRepository();
      ar.delRepository(id);
      arf.delRepositoryFileByRid(id);
      aprp.delRepositoryProductByRid(id);

      request.setAttribute("result", "databases.del.success");
//      DBUserLog.addUserLog(userid,"删除知识库");//日志 

      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return mapping.getInputForward();
  }

}
