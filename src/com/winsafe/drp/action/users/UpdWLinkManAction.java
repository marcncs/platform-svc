package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWlinkMan;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Wlinkman;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdWLinkManAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppWlinkMan appLinkMan = new AppWlinkMan();

		try {
			Integer id = Integer.valueOf(request.getParameter("id"));
			UsersBean users = UserManager.getUser(request);
			Wlinkman ol =appLinkMan.getWlinkmanByID(id);
			Wlinkman oldol = (Wlinkman)BeanUtils.cloneBean(ol);
			
			ol.setName(RequestTool.getString(request, "name"));
			ol.setMobile(RequestTool.getString(request, "mobile"));

			appLinkMan.updWlinkman(ol);
			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request, "修改仓库联系人"+ol.getId(), oldol,ol);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
