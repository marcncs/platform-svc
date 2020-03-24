package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.BaseResourceService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddBaseResourceAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {


    try{
      BaseResource br = new BaseResource();
      Long brid = Long.valueOf(MakeCode.getExcIDByRandomTableName("base_resource",0,""));
      br.setId(brid);
      br.setTagname(request.getParameter("tagname"));
      br.setTagsubkey(Integer.valueOf(request.getParameter("tagsubkey")));
      br.setTagsubvalue(request.getParameter("tagsubvalue"));
      br.setIsuse(RequestTool.getInt(request, "isuse"));

      BaseResourceService abr = new BaseResourceService();
     
      abr.addBaseResource(br);

      request.setAttribute("result", "databases.add.success");
      UsersBean users = UserManager.getUser(request);
      int userid = users.getUserid();
      DBUserLog.addUserLog(userid,"系统管理","资源设置>>新增资源,编号:"+brid);
      return mapping.findForward("addresult");
    }catch(Exception e){
      e.printStackTrace();
    }finally {      
    }

    return new ActionForward(mapping.getInput());
  }
}
