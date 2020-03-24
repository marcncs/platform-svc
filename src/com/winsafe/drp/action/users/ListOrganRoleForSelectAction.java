package com.winsafe.drp.action.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganRole;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganRoleForSelectAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	int pagesize = 20; 
	String roleid = request.getParameter("RoleID");
	if ( roleid == null ){
		roleid=(String) request.getSession().getAttribute("roleid");
	}
	request.getSession().setAttribute("roleid", roleid);
	super.initdata(request);
	try {
		String	Condition = " not exists (select r.id from OrganRole as r where o.id=r.organid and r.roleid="+roleid+") ";

		Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"Organ"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

        String blur = DbUtil.getOrBlur(map, tmpMap, "OrganName"); 
        whereSql = whereSql + blur + Condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 

        AppOrganRole aor = new AppOrganRole();
//       AppMoveCanuseOrgan amco = new AppMoveCanuseOrgan();

		List menuls  =aor.getOrganNotInOrganRole(request, pagesize,whereSql);

		request.setAttribute("opls",menuls);
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		DBUserLog.addUserLog(userid,11,"角色管理>>列表角色机构");
		return mapping.findForward("list");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
