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
import com.winsafe.drp.dao.AppMoveCanuseOrgan;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListMoveCanuseOrganForSelectAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	int pagesize = 20; 
	String uid = request.getParameter("UID");
	if ( uid == null ){
		uid=(String) request.getSession().getAttribute("sjuid");
	}
	request.getSession().setAttribute("sjuid", uid);
	super.initdata(request);
	try {
		String	Condition = " o.sysid like '"+users.getOrgansysid()+"%' and not exists (select mco.id from MoveCanuseOrgan mco where o.id=mco.oid and mco.uidi="+uid+") ";

		Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"Organ"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

        String blur = DbUtil.getOrBlur(map, tmpMap, "OrganName"); 
        whereSql = whereSql + blur + Condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 

       AppMoveCanuseOrgan amco = new AppMoveCanuseOrgan();

		List menuls = null;
			menuls =amco.getOrganNotInCanuseOrgan(request, pagesize,whereSql);


		request.setAttribute("opls",menuls);
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		DBUserLog.addUserLog(userid,11,"用户管理>>列表转仓机构");
		return mapping.findForward("list");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
