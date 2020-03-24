package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRepositoryType;

public class ToAddRepositoryAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
   String psid = request.getParameter("PSID");
   try{
     AppRepositoryType appRepositoryType = new AppRepositoryType();
     List uls = appRepositoryType.getRepositoryTypeCanUse();

  
 
     request.setAttribute("psid",psid);
     request.setAttribute("uls", uls);

     return mapping.findForward("toadd");
   }catch(Exception e){
     e.printStackTrace();
   }
   return new ActionForward(mapping.getInput());
  }
}
