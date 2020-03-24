package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToAddApproveFlowDetailAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    try{
    	UsersBean users = UserManager.getUser(request);
    	Integer userid = users.getUserid();
    	String actidselect = Internation.getSelectTagByKeyAllDB("ActID", 
    	          "actid", false); 
    	String afid = request.getParameter("AFID");
    	AppUsers au = new AppUsers();
        List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
        
        
        request.setAttribute("afid", afid);
        request.setAttribute("als", als);
        request.setAttribute("actidselect", actidselect);
            return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
