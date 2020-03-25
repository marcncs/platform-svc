/**
 * 
 */
package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.RoleItem;

/**
 * @author jelli
 *
 */
public class ListRoleUserAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		String roleid=request.getParameter("roleid");
		try {

			AppRole aba=new AppRole();
			List als=aba.getUserRoleByRoleid(Integer.parseInt(roleid));
			List rls=new ArrayList();
			RoleItem ri=null;
			for(int i=0;i<als.size();i++){
				ri=new RoleItem();
				Object [] o=(Object[])als.get(i);
				ri.setUserid(o[0].toString());
				ri.setUsername((String)o[1]);
				ri.setIspopedom(Integer.valueOf(o[2].toString()));
				ri.setUserroleid(Integer.valueOf(o[3].toString()));
				rls.add(ri);
			}
			Role role=aba.getRoleById(Integer.valueOf(roleid));
			request.setAttribute("role",role);
			request.setAttribute("roleList", rls);

			return mapping.findForward("listRole");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
 

}
