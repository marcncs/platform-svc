package com.winsafe.drp.keyretailer.salesman.action.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping; 

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.UserApplyServices;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class AuditUserAction extends Action{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers appUsers = new AppUsers();
		UserApplyServices uas = new UserApplyServices();

		String userId = request.getParameter("userId");
		String isApprove = request.getParameter("isApprove");
		String remark = request.getParameter("remark");
		String organId = request.getParameter("organId");
		String userName = request.getParameter("Username");
		try {
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(userName);
			
			if(!uas.auditUsers(userId, isApprove, organId, remark, response, loginUsers, null)) { 
				return null;
			}
			if("1".equals(isApprove)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, "审核通过", userId+" "+isApprove
						,loginUsers.getUserid(),"APP_RI","审核用户",true);
			} else {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, "审核不通过", userId+" "+isApprove
						,loginUsers.getUserid(),"APP_RI","审核用户",true);
			}
		} catch (Exception e) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "审核失败,网络异常");
			throw e;
		}
		
		return null;
	}
}

