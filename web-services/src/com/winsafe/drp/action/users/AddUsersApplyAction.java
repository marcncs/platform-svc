package com.winsafe.drp.action.users;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.UserApply;
import com.winsafe.drp.server.UserApplyServices;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;

public class AddUsersApplyAction extends Action {
	private static Logger logger = Logger.getLogger(AddUsersApplyAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserApplyServices uas = new UserApplyServices();
		UserApply userApply = new UserApply();
		
		try {
			Map map = new HashMap(request.getParameterMap());
			MapUtil.mapToObjectIgnoreCase(map, userApply); 
			//验证有效性
			if(!uas.validateUserApply(userApply, response)) {
				return null;
			}
			
			
			userApply.setMakeDate(Dateutil.getCurrentDate());
			userApply.setPassword(Encrypt.getSecret(userApply.getPassword(), 1));
			userApply.setIsApproved(0);
			
			uas.addUserApply(userApply);

			ResponseUtil.writeJsonMsg(response, "1", "注册成功");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		} 
		return null;
	}
}
