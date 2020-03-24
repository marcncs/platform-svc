package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.MemberGrade;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddMemberGradeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			AppMemberGrade aw = new AppMemberGrade();
			MemberGrade w = new MemberGrade();
			w.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("member_grade",0, "")));
			w.setGradename(request.getParameter("gradename"));
			w.setPolicyid(Integer.valueOf(request.getParameter("policyid")));
			w.setIntegralrate(Double.valueOf(request
					.getParameter("integralrate")));

			

			aw.addMemberGrade(w);

			request.setAttribute("result", "databases.add.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "基础设置>>新增会员级别,编号："+w.getId());

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("success");
	}
}
