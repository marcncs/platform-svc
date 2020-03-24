package com.winsafe.drp.action.users;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.metadata.UserCategary;
import com.winsafe.drp.util.DBUserLog;

public class ListUsersDetailAction extends Action {
	private AppUsers appusers = new AppUsers();
	private AppBaseResource appBaseResource = new AppBaseResource();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer userid = Integer.valueOf(request.getParameter("userid"));

		try {
			Users us = appusers.getUsersByid(userid);
			Set<Integer> ucSet = appusers.getUserCategarySet(us.getUserid());
			// 详情显示用户性质 
			Integer userTypeId = us.getUserType();
			String UserName = "";
			if (userTypeId != null) {
				BaseResource bs = appBaseResource.getBaseResourceValue(
						"UserType", userTypeId);
				if (bs != null) {
					UserName = bs.getTagsubvalue();
				}
			}
			if(ucSet.size()>0) {
				StringBuffer sb = new StringBuffer();
				for(Integer ucId : ucSet) {
					UserCategary uc = UserCategary.parseByValue(ucId);
					if(uc!= null) {
						sb.append(","+uc.getName());
					}
				}
				if(sb.length()>0) {
					request.setAttribute("ucategory", sb.substring(1)); 
				}
			}
			
			request.setAttribute("UserName", UserName);
			request.setAttribute("uf", us);
			 DBUserLog.addUserLog(userid, "系统管理", "用户管理>>列表用户详情:" + userid);
			return mapping.findForward("info");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
