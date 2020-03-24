/**
 * 
 */
package com.winsafe.drp.action.users;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.RoleForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;

/**
 * @author alex	
 *
 */
public class UpdRoleAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		RoleForm vaf = new RoleForm(); 
		Map map = new HashMap(request.getParameterMap());
		MapUtil.mapToObjectIgnoreCase(map, vaf);
		AppRole ar=new AppRole();

        String result="";
        try{
			Role role=ar.getRoleById(vaf.getId());
			Role oldr = (Role)BeanUtils.cloneBean(role);
			role.setDescribes(vaf.getDescribes());
			role.setRolename(vaf.getRolename());
			ar.updRole(role);
	     	
	          request.setAttribute("result", "databases.upd.success");
	          UsersBean users = UserManager.getUser(request);
	          Integer userid = users.getUserid();
	          DBUserLog.addUserLog(request,"编号："+role.getId(), oldr, role);
			
			return mapping.findForward("updresult");
        }catch(Exception e){
        	e.printStackTrace();
        	result="databases.upd.fail";
        	
        }finally {
       	 	//
        }
        return mapping.findForward("roleResult");
	}

}
