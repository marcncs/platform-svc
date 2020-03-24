/**
 * 
 */
package com.winsafe.drp.action.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class ListRoleAction extends BaseAction{
	
	private AppRole aba=new AppRole();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		int pagesize = 15; 

		try {
			initdata(request);
			String whereSql = "";
//			Object obj[] = (DbUtil.setPager(request, "Role", whereSql,
//					pagesize));
//			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
//			whereSql = (String) obj[1];
			
//			List als =aa.getAddressForPage(pagesize, whereSql, tmpPgInfo); 
//			List als=aba.getRoleByAdminid(pagesize,whereSql,tmpPgInfo);
			List als=aba.getRole(request, pagesize,whereSql);
//			List rls=new ArrayList();
//			RoleForm rf=null;
//			for(int i=0;i<als.size();i++){
//				rf=new RoleForm();
//				Object [] o=(Object[])als.get(i);
//				rf.setId(Integer.valueOf(o[0].toString()));
//				rf.setRolename(o[1].toString());
//				rf.setDescribes((String)o[2]);
//				rls.add(rf);
//			}

			request.setAttribute("roleList", als);
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, "系统管理", "角色管理>>列表角色");
			return mapping.findForward("listRole");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
 

}
