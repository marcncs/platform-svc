package com.winsafe.drp.keyretailer.action.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class UpdBonusConfirmAction extends BaseAction {
	private Logger logger = Logger.getLogger(UpdBonusConfirmAction.class);
	
	private AppUsers appUsers = new AppUsers(); 
	private AppSBonusTarget asbt = new AppSBonusTarget();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String year = request.getParameter("year"); 
			String organId = request.getParameter("organId");
			String isConfirmed = request.getParameter("isConfirmed");
			
			// 判断用户是否存在 
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			asbt.updBonusConfirm(loginUsers.getMakeorganid(), year, organId, isConfirmed);
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, year+loginUsers.getMakeorganid()+organId+isConfirmed
					,loginUsers.getUserid(),"APP_RI","积分返利确认",true);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "失败,系统异常");
		}
		return null;
	}
	
}
