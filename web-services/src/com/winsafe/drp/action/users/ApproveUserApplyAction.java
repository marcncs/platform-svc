package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping; 

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.server.UserApplyServices;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class ApproveUserApplyAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		UserApplyServices uas = new UserApplyServices();

		String userId = request.getParameter("userId");
		String isApprove = request.getParameter("isApprove");
		String remark = request.getParameter("remark");
		String organId = request.getParameter("organId");
		String loginName = request.getParameter("loginName");
		try {
			
			if(!StringUtil.isEmpty(loginName)) {
				if(uas.isMobileAlreadyExists(loginName, userId)) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "该登陆名已存在!");
					return null;
				} 
			}
			
			if(!uas.auditUsers(userId, isApprove, organId, remark, response, users, loginName)) {
				return null;
			}
			if("1".equals(isApprove)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, "审核通过", userId+" "+isApprove
						,users.getUserid(),"APP_RI","审核用户",true);
			} else {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, "审核不通过", userId+" "+isApprove
						,users.getUserid(),"APP_RI","审核用户",true);
			}
		} catch (Exception e) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "审核失败,网络异常");
			throw e;
		}
		return null;
	}
}
