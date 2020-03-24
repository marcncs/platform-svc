/**
 * 
 */
package com.winsafe.drp.action.users;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.RoleForm;
import com.winsafe.drp.dao.RoleLeftMenu;
import com.winsafe.drp.dao.RoleMenu;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UserRole;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.MakeCode;


public class AddRoleAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		RoleForm vaf = new RoleForm();
		Map map = new HashMap(request.getParameterMap());
		MapUtil.mapToObjectIgnoreCase(map, vaf); 

        try {        	
       	 AppRole ar=new AppRole();
       	 Role r=new Role();
       	 Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("role",0,""));
       	 r.setId(id);
       	 r.setRolename(vaf.getRolename());
       	 r.setDescribes(vaf.getDescribes());
       	 List list=ar.getUserList();
      	 
       	 
       	 Iterator it=list.iterator();
       	 UserRole ur=null;
       	 while(it.hasNext()){
       		 ur=new UserRole();
       		 Users u=(Users)it.next();
       		 ur.setUserid(u.getUserid());
       		 ur.setIspopedom(0);
       		 ur.setRoleid(id);
       		 ar.addUserRole(ur);       	 }
       	 
       	 
       	 RoleLeftMenu rlm = new RoleLeftMenu();
       	 rlm.setRoleid(r.getId());
       	 rlm.setLmid(Integer.valueOf(1));
       	 ar.addRoleLeftMenuNoExists(rlm);
       	 
       	 RoleMenu rm = new RoleMenu();
       	 rm.setRoleid(r.getId());
       	 rm.setOperateid(Integer.valueOf(1));
       	 ar.addRoleMenu(rm);

       	 ar.addRole(r);
     	
          request.setAttribute("result", "databases.add.success");
          UsersBean users = UserManager.getUser(request);
          Integer userid = users.getUserid();
          DBUserLog.addUserLog(userid,"系统管理","角色管理>>新增角色,编号:"+r.getId());
       	 return mapping.findForward("addresult");
        } catch (Exception e) {
       	  e.printStackTrace();
        }
        request.setAttribute("result", "databases.add.success");
        return mapping.findForward("roleResult");
	}
}
