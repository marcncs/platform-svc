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
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppUserGroup;
import com.winsafe.drp.dao.UserGroup;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListUserGroupAction extends BaseAction{
	
	private AppUserGroup appUserGroup= new AppUserGroup();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		int pagesize = 15; 

		try {
			initdata(request);
			Map map=new HashMap(request.getParameterMap());
            Map tmpMap = EntityManager.scatterMap(map);
			
			String blur = DbUtil.getOrBlur(map, tmpMap, "u.RealName", "u.loginname"); 
			String whereSql = " where " + blur;
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			List<UserGroup> als=appUserGroup.getUserGroup(request, pagesize,whereSql);
			
			AppRole appRole = new AppRole();
			Map<Integer,String> nameMap = appRole.getAllRoleNameMap();
			for(UserGroup ug : als) {
				ug.setRoleName(nameMap.get(ug.getRoleId()));
			}

			request.setAttribute("groupList", als);
			DBUserLog.addUserLog(userid, "系统管理", "用户组>>列表");
			return mapping.findForward("listgroup");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
 

}
