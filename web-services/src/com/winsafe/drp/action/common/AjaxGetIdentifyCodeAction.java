package com.winsafe.drp.action.common; 

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
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.SmsUtil;

public class AjaxGetIdentifyCodeAction extends BaseAction {
	Logger logger = Logger.getLogger(AjaxGetIdentifyCodeAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		initdata(request);
		String userName = request.getParameter("userName");
		String type = request.getParameter("type");
		try {
			AppUsers appUsers = new AppUsers();
			Users users = appUsers.getUsersByLoginname(userName);
			if(users == null) {
				ResponseUtil.writeJsonMsg(response, "-1", "发送失败,用户名不存在");
				return null;
			}
			String mobile = users.getMobile();
			if("1".equals(type)) {
				mobile = request.getParameter("mobile");
			}
			IdentityCode idCode = (IdentityCode)request.getSession().getAttribute("identifyCode");
			//判断是否需要重发
			if(idCode==null || !idCode.isInRange(Dateutil.getCurrentDate())) {
				String identifyCode = SmsUtil.sendUpdPasswordSms(mobile);
				idCode = new IdentityCode(identifyCode, mobile, Dateutil.getCurrentDate());
				request.getSession().setAttribute("identifyCode", idCode);
			}
			ResponseUtil.writeJsonMsg(response, "1", "验证码已发送到您尾号为"+mobile.substring(mobile.length()-4, mobile.length())+"的手机上,请注意查收");
		} catch (Exception e) {
			logger.error("",e);
			ResponseUtil.writeJsonMsg(response, "-1", "发送失败,网络异常");
			
		}
		return null;
	}

}

