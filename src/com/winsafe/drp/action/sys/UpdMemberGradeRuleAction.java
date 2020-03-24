package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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

public class UpdMemberGradeRuleAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberGradeRuleForm mgrf = (MemberGradeRuleForm)form;
		try {
			
			AppMemberGradeRule appmgr = new AppMemberGradeRule();
			MemberGradeRule mgr = appmgr.getMemberGradeRuleByID(mgrf.getId());
			MemberGradeRule oldmgr = (MemberGradeRule)BeanUtils.cloneBean(mgr);
			mgr.setStartprice(mgrf.getStartprice());
			mgr.setEndprice(mgrf.getEndprice());
			mgr.setStartintegral(mgrf.getStartintegral());
			mgr.setEndintegral(mgrf.getEndintegral());
			mgr.setMgid(mgrf.getMgid());
			
			
			appmgr.updMemberGradeRule(mgr);
			
			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11,"基础设置>>修改会员级别晋级规则,编号："+mgr.getId(), oldmgr, mgr);// 日志

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("updResult");
	}
}
