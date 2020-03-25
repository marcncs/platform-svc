package com.winsafe.drp.action.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganAwake;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganAwakeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 50;
		try {
			String oid = request.getParameter("OID");
//			if(oid ==null){
//				oid=(String) request.getSession().getAttribute("sjoid");	
//			}
//			request.getSession().setAttribute("sjoid", oid);

			String Condition = " oa.organid='" + oid+"' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"OrganAwake" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppOrganAwake apl = new AppOrganAwake();
			List apls = apl.getOrganAwakeList(request, pagesize, whereSql);
			

			request.setAttribute("OID", oid);
			request.setAttribute("alpl", apls);

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "机构设置>>列表报警提醒人");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
