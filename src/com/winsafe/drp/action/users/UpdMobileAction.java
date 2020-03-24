package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.IdentityCode;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;

public class UpdMobileAction extends BaseAction{
	private static Logger logger = Logger.getLogger(UpdMobileAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		initdata(request);
		try{
			AppUsers appUsers = new AppUsers();
			String idCode=request.getParameter("idCode");
			//检查验证码
			IdentityCode ic = (IdentityCode)request.getSession().getAttribute("identifyCode");
			if(ic == null) {
				ResponseUtil.writeJsonMsg(response, "-1", "验证码不正确");
				return null;
			}
			if(ic.isExpired(Dateutil.getCurrentDate())) {
				ResponseUtil.writeJsonMsg(response, "-1", "验证码已过期");
				return null;
			}
			if(!ic.getIdentifyCode().equals(idCode)) {
				ResponseUtil.writeJsonMsg(response, "-1", "验证码不正确");
				return null;
			}
			
			String mobile = request.getParameter("mobile");
			if(!ic.getMobile().equals(mobile)) {
				ResponseUtil.writeJsonMsg(response, "-1", "验证码不正确");
				return null;
			}
			
			if(appUsers.isMobileAlreadyExists(mobile, null)) {
				ResponseUtil.writeJsonMsg(response, "-5", "系统中已存在该手机号");
				return null;
			}
			
			Users user = appUsers.getUsersByid(users.getUserid());
			user.setMobile(mobile);
			appUsers.updUsers(user);

		    DBUserLog.addUserLog(request, "修改手机号");
		    ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, "修改成功");
		}catch(Exception e){
			logger.error("", e);
		}
		return null;
	}
}
