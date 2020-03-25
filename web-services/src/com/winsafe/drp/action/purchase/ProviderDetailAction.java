package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.Provider;

public class ProviderDetailAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String strpid = request.getParameter("PID");
    String pid = strpid;
    try{
      AppProvider ap=new AppProvider();
      Provider p = ap.getProviderByID(pid);

      

      request.setAttribute("pf",p);
      //DBUserLog.addUserLog(userid,"供应商详情");
      return mapping.findForward("info");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
