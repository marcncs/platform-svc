package com.winsafe.drp.action.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMember;
import com.winsafe.drp.dao.Member;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;

public class MemberLoginAction extends Action {
	private Logger logger = Logger.getLogger(MemberLoginAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppMember appMember = new AppMember();
		// 登录名
		String username = request.getParameter("Username");
		// 密码
		String password = request.getParameter("Password");
		// 采集器IMEI号
		String phoneNo = request.getParameter("IMEI_number");
		// 得到加密后的密码
		String securityPassword = Encrypt.getSecret(password, 1);
		// 用户名密码验证
		Member member = new Member();
		if (Constants.PHONE_VALIDATE_USER) {
			// 得到用户名和密码符合的用户
			member = appMember.CheckMemberNamePassword(username, securityPassword);
			if (member == null) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL,
						Constants.CODE_LOGIN_FAIL_MSG);
				return null;
			}
			if (member.getStatus() != 1) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "账户不可用!");
				return null;
			}

			// 设置用户在线
			appMember.updateLastLogin(username);
		}
		String returnDate = "";
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG,
				returnDate, member.getId(), "手机", "IMEI:[" + phoneNo + "],登陆", true);
		return null;
	}
}
