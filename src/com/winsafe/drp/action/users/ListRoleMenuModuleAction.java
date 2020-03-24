/**
 * 
 */
package com.winsafe.drp.action.users;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppLeftMenu;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.Role;


/**
 * @author alex
 *
 */
public class ListRoleMenuModuleAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		String roleid=request.getParameter("roleid");

		try {
			AppRole aba=new AppRole();
			AppLeftMenu alm = new AppLeftMenu();
			
			List lmlist = alm.getLeftMenuByParentid(Integer.valueOf(0), 1, Integer.valueOf(roleid));

			Role role=aba.getRoleById(Integer.valueOf(roleid));
			request.setAttribute("currentRole",role);
			request.setAttribute("lmlist", lmlist);
			return mapping.findForward("listMenu");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}
 


}
