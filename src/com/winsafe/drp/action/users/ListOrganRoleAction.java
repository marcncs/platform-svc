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
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganRoleAction extends BaseAction {
	private AppOrganRole aor = new AppOrganRole(); 
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	int pagesize = 20; 
	super.initdata(request);
	String roleid = request.getParameter("RoleID");
	if(roleid ==null){
		roleid=(String) request.getSession().getAttribute("roleid");	
	}
	request.getSession().setAttribute("roleid", roleid);
	try {

		Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"OrganRole"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

        String blur = DbUtil.getOrBlur(map, tmpMap, "OName"); 
        whereSql = whereSql + blur;
        whereSql = DbUtil.getWhereSql(whereSql); 

//		AppMoveCanuseOrgan aop=new AppMoveCanuseOrgan();
		List menuls=aor.searchOrganRole(request, pagesize,whereSql);

		request.setAttribute("opls",menuls);
		request.setAttribute("RoleID", roleid);
		
		DBUserLog.addUserLog(userid, 11, "角色管理>>列表角色拥有机构");
		return mapping.findForward("list");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
