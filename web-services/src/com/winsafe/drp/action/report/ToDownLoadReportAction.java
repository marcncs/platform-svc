package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppRegionUsers;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.RegionUsers;
import com.winsafe.drp.dao.UserRole;
import com.winsafe.drp.dao.Users;

public class ToDownLoadReportAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			// 0：管理员 4：办事处经理，5：大区经理
			Integer ismain = null;
			// 大区名称_办事处名称
			String regionname = "";
			AppRole approle = new AppRole();
			AppUsers appusers = new AppUsers();
			AppRegion appregion = new AppRegion();
			// 获取当前用户是否是管理员
			List list = approle.getUserRoleByUserid(userid);
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					UserRole ru = (UserRole) list.get(i);
					if (ru != null) {
						if (1 == ru.getRoleid() || 10005 == ru.getRoleid()) {
							ismain = 0;
							break;
						}
					}
				}
			}
			if (ismain == null || 0 != ismain) {
				Users us = appusers.getUsersByid(userid);
				Region r = appregion.getRegionByUserid(userid.toString());
				// 办事处经理
				if (r != null && 4 == us.getUserType()) {
					ismain = 4;
					regionname = r.getParentname() + "_" + r.getSortname();
				}
				// 大区经理
				if (r != null && 5 == us.getUserType()) {
					ismain = 5;
				}
			}
			request.setAttribute("Regionname", regionname);
			request.setAttribute("DateTime", request.getParameter("DateTime"));
			request.setAttribute("flag", ismain);
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
