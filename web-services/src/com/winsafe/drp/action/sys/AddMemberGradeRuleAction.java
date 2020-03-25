package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMemberGradeRule;
import com.winsafe.drp.dao.MemberGradeRule;
import com.winsafe.drp.dao.MemberGradeRuleForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddMemberGradeRuleAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberGradeRuleForm mgrf = (MemberGradeRuleForm)form;
		try {
			
			MemberGradeRule mgr = new MemberGradeRule();
			mgr.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"member_grade_rule", 0, "")));
			mgr.setStartprice(mgrf.getStartprice());
			mgr.setEndprice(mgrf.getEndprice());
			mgr.setStartintegral(mgrf.getStartintegral());
			mgr.setEndintegral(mgrf.getEndintegral());
			mgr.setMgid(mgrf.getMgid());
			
			AppMemberGradeRule appmgr = new AppMemberGradeRule();
			appmgr.addMemberGradeRule(mgr);
			
			request.setAttribute("result", "databases.add.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11,"基础设置>>新增会员级别晋级规则,编号:"+mgr.getId());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("addResult");
	}
}
