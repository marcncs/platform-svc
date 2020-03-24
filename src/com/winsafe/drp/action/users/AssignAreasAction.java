package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserArea;
import com.winsafe.hbm.util.MakeCode;

public class AssignAreasAction extends Action{
	  public ActionForward execute(ActionMapping mapping, ActionForm form,
              HttpServletRequest request,
              HttpServletResponse response) throws Exception {
		  
		  String uid=request.getParameter("uid");
		  String caid[]=request.getParameterValues("checkbox");
		  //Session 
		  ////Connection 
		  
		  try{
			  AppUsers au=new AppUsers();
			 au.deleteUserAreas(Integer.valueOf(uid));

			 
			 for(int i=0;i<caid.length;i++){
				 UserArea ua=new UserArea();
				 ua.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("user_area",0,"")));
				 ua.setUserid(Integer.valueOf(uid));
				 ua.setAreaid(Integer.valueOf(caid[i]));
				 au.InsertUserArea(ua);
			 }
			 
			  
			      request.setAttribute("result", "databases.add.success");

			        
			 
			  return mapping.findForward("result");
		  }catch(Exception e){
			  
			  e.printStackTrace();
		  }finally{
			  //
		  }
		  return mapping.getInputForward();
	  }
}
