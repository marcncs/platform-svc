/**
 * 
 */
package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;


public class CopyRoleAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        try {
         Integer srcRoleid = Integer.valueOf(request.getParameter("srcroleid"));
         String rolename = request.getParameter("rolename");
         String describes = request.getParameter("describes");
         if ( srcRoleid == 0 ){
		    	request.setAttribute("result", "databases.add.fail");
		    	return new ActionForward("/sys/lockrecord.jsp");
		 }
         if ( rolename.equals("") ){
		    	request.setAttribute("result", "databases.add.fail");
		    	return new ActionForward("/sys/lockrecord.jsp");
		 }
       	 AppRole ar=new AppRole();
       	 Role r=new Role();
       	 Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("role",0,""));
       	 r.setId(id);
       	 r.setRolename(rolename);
       	 r.setDescribes(describes);
       	 ar.addRole(r);
       	 

       	 ar.copyUserRole(srcRoleid, id);
       	 ar.copyRoleMenu(srcRoleid, id);
       	 ar.copyRoleLeftMenu(srcRoleid, id);
     	
          request.setAttribute("result", "databases.add.success");
          UsersBean users = UserManager.getUser(request);
          Integer userid = users.getUserid();
          DBUserLog.addUserLog(userid,11,"角色管理>>克隆角色");
          
          request.setAttribute("gourl", "listRoleAction.do");
       	 return mapping.findForward("addresult");
        } catch (Exception e) {
       	 e.printStackTrace();

    	 
        }
        request.setAttribute("result", "databases.add.success");
        return mapping.findForward("roleResult");
	}
}
