package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganGrade;
import com.winsafe.drp.dao.OrganGrade;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdOrganGradeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			AppOrganGrade aw = new AppOrganGrade();
			OrganGrade w = aw.getOrganGradeByID(RequestTool.getInt(request, "id"));		
			OrganGrade oldor = (OrganGrade)BeanUtils.cloneBean(w);
			w.setGradename(request.getParameter("gradename"));
			w.setPolicyid(RequestTool.getInt(request, "policyid"));
			w.setIntegralrate(RequestTool.getDouble(request, "integralrate"));
			aw.updOrganGrade(w);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,11, "基础设置>>修改经销商级别,编号:"+w.getId(), oldor, w);

			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("updResult");
	}
}
