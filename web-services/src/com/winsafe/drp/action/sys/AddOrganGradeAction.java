package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganGrade;
import com.winsafe.drp.dao.OrganGrade;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddOrganGradeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			
			OrganGrade mgr = new OrganGrade();
			mgr.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"organ_grade", 0, "")));
			mgr.setGradename(request.getParameter("gradename"));
			mgr.setPolicyid(RequestTool.getInt(request, "policyid"));
			mgr.setIntegralrate(RequestTool.getDouble(request, "integralrate"));
			
			AppOrganGrade appmgr = new AppOrganGrade();
			appmgr.addOrganGrade(mgr);
			
			request.setAttribute("result", "databases.add.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "基础设置>>新增经销商级别,编号:"+mgr.getId());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("addResult");
	}
}
