package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSystemResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.SystemResource;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.BaseResourceService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddSystemResourceAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {


    try{
    SystemResource br = new SystemResource();

      br.setTagname(request.getParameter("tagname").trim());
      br.setTagkey(request.getParameter("tagsubkey").trim());
      br.setTagvalue(request.getParameter("tagsubvalue").trim());
      br.setMemo(request.getParameter("memo").trim());

      AppSystemResource appsr=new AppSystemResource();
      appsr.addSystemResource(br);

      request.setAttribute("result", "databases.add.success");
      UsersBean users = UserManager.getUser(request);
      int userid = users.getUserid();
      DBUserLog.addUserLog(userid,11,"基础设置>>新增资源");
      return mapping.findForward("addresult");
    }catch(Exception e){
      e.printStackTrace();
    }finally {      
    }

    return new ActionForward(mapping.getInput());
  }
}
