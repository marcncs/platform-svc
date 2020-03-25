/**
 * 
 */
package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.RoleItem;
import com.winsafe.drp.dao.Users;
import com.winsafe.hbm.util.StringUtil;

/**
 * @author alex
 * 
 */
public class ListUserRoleAction extends BaseAction {
	private AppRole aba = new AppRole();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		super.initdata(request);
		
		try {
			String userid = request.getParameter("uid");
//			List als = aba.getRoleByParent(Integer.parseInt(userid),super.userid);
			List als = aba.getRoleByUserId(Integer.parseInt(userid));
			List rls = new ArrayList();
			
			RoleItem ri = null;
			for (int i = 0; i < als.size(); i++) {
				ri = new RoleItem();
				Object[] o = (Object[]) als.get(i);
				ri.setId(Integer.valueOf(o[0].toString()));
				ri.setRolename(o[1].toString());
				ri.setIspopedom(Integer.valueOf(o[2].toString()));
				ri.setUserroleid(Integer.valueOf(o[3].toString()));
				ri.setDescribes(StringUtil.removeNull(String.valueOf(o[4])));
				rls.add(ri);
			}
			Users user = aba.getUserById(Integer.valueOf(userid));
			request.setAttribute("currentUser", user);
			request.setAttribute("roleList", rls);

			// Integer luserid = user.getUserid();
			// DBUserLog.addUserLog(luserid,"列表用户角色");
			return mapping.findForward("listRole");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
