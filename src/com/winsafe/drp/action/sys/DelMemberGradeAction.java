package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppMemberGradeRule;
import com.winsafe.drp.dao.AppUserGrade;
import com.winsafe.drp.dao.MemberGrade;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelMemberGradeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			AppUserGrade appug = new AppUserGrade();
			List uglist = appug.getUserGradeMGID(id);
			if ( !uglist.isEmpty() ){
				request.setAttribute("result", "该级别已被使用，不能删除!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			AppMemberGradeRule appmg = new AppMemberGradeRule();
			List mglist = appmg.getMemberGradeRuleByMgid(id);
			if ( !mglist.isEmpty() ){
				request.setAttribute("result", "该级别已被使用，不能删除!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			AppMemberGrade app = new AppMemberGrade();
			if ( app.isAready(id) ){
				request.setAttribute("result", "该级别已被使用，不能删除!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			MemberGrade mg = app.getMemberGradeByID(id);
			app.delMemberGrade(id);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "基础设置>>删除会员级别,编号：" + id, mg);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
}
