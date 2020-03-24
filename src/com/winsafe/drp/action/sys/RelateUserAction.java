package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;

public class RelateUserAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String acode = request.getParameter("acode");
		String usersid = request.getParameter("userid");
		try {
			String parentuserid = null; // 上级经理id
			String parentusername = null; // 上级经理名称
			String username = null; // 当前用户名称
			AppUsers appusers = new AppUsers();
			AppRegion aa = new AppRegion();
			// 添加父级的用户id和name
			Region as = aa.getRegionById(acode);
			String parentid = as.getParentid();
			if (parentid != null && parentid.length() > 0) {
				Region parent = aa.getRegionById(parentid);
				if (parent != null) {
					parentuserid = parent.getUserid();
					if (parentuserid != null && parentuserid.length() > 0) {
						Users us = appusers.getUsersByid(Integer.valueOf(parentuserid));
						if (us != null) {
							parentusername = us.getRealname();
						}
					}
				}
			}
			// 添加本级的用户名称
			if (usersid != null && usersid.length() > 0) {
				 Region re=	aa.getRegionByUserid(usersid);
				  if(re!=null){
					    request.setAttribute("result", "经理已经关联信息!");
						return new ActionForward("/sys/lockrecordclose2.jsp");
				  }
				Users u = appusers.getUsersByid(Integer.valueOf(usersid));
				if (u != null) {
					username = u.getRealname();
				}
				as.setUserid(usersid);
			}
			as.setUsername(username);
			as.setParentuserid(parentuserid);
			as.setParentusername(parentusername);
			
			
			
			
			EntityManager.update(as);
			UsersBean users = UserManager.getUser(request);
			int userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "区域信息>>关联主管,编号:" + acode);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}
}
