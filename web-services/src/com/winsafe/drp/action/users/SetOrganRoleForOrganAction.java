package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganRole;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.OrganRole;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class SetOrganRoleForOrganAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		String organid = (String) request.getSession().getAttribute("organid");
	try {
		String[] pid = request.getParameterValues("pid");
		OrganService os = new OrganService();
		AppOrganRole aor = new AppOrganRole();
		AppRole ar = new AppRole();
//		AppMoveCanuseOrgan aop = new AppMoveCanuseOrgan();
		
		for (int i = 0; i < pid.length; i++) {
			OrganRole mco = new OrganRole();
			mco.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"organ_role", 0, "")));
			mco.setRoleid(Integer.valueOf(pid[i]));
			mco.setRname(ar.getRoleById(mco.getRoleid()).getRolename());
			mco.setOrganid(organid);
			mco.setOname(os.getOrganByID(mco.getOrganid()).getOrganname());
			
			aor.addOrganRole(mco);

		}
		
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		DBUserLog.addUserLog(userid,11,"机构管理>>设置机构拥有角色,机构编号:"+organid);
		request.setAttribute("result", "databases.add.success");
		request.setAttribute("gourl", "../users/listOrganRoleByOrganForSelectAction.do?OrganID="+organid);
		return mapping.findForward("set");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
