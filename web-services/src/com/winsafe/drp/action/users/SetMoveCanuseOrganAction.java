package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMoveCanuseOrgan;
import com.winsafe.drp.dao.MoveCanuseOrgan;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class SetMoveCanuseOrganAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		String uid = (String) request.getSession().getAttribute("sjuid");
	try {
		String[] pid = request.getParameterValues("pid");
		OrganService os = new OrganService();
		AppMoveCanuseOrgan aop = new AppMoveCanuseOrgan();
		
		for (int i = 0; i < pid.length; i++) {
			MoveCanuseOrgan mco = new MoveCanuseOrgan();
			mco.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"move_canuse_organ", 0, "")));
			mco.setUidi(Integer.valueOf(uid));
			mco.setOid(pid[i]);
			mco.setOname(os.getOrganByID(mco.getOid()).getOrganname());
			
			aop.addMoveCanuseOrgan(mco);

		}
		
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		DBUserLog.addUserLog(userid,11,"用户管理>>设置转仓机构,用户编号:"+uid);
		request.setAttribute("result", "databases.add.success");
		request.setAttribute("gourl", "../users/listMoveCanuseOrganForSelectAction.do?UID="+uid);
		return mapping.findForward("set");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
