package com.winsafe.drp.keyretailer.action.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;
import com.winsafe.drp.keyretailer.pojo.SBonusAppraise;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
public class DealBackpRroiftAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(DealBackpRroiftAction.class);
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String username = request.getParameter("Username"); //登陆名
		String period = request.getParameter("year");
		
		try
		{	
			AppUsers appUsers = new AppUsers();
			Users users = appUsers.getUsers(username);
			SBonusAppraise sBonusAppraise = appSBonusAppraise.getSBonusAppraiseByOrganIdAndPeriod(users.getMakeorganid(), period);
			sBonusAppraise.setBackprofit("1");
			appSBonusAppraise.updSBonusAppraise(sBonusAppraise);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, ""
					,users.getUserid(),"用户","Username:[" + username + "],确认返利",true);
		}catch (Exception e) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "确认失败,系统异常");
			logger.error("", e);
		}
		return null;
	}
	
	public String createDecryptor(String password) throws Exception{
		 password = password.substring(5, password.length());
		 password = password.substring(0, password.length()-5);
    	 return password;
	}
}
