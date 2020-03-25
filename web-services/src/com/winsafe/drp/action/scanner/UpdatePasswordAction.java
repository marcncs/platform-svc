package com.winsafe.drp.action.scanner;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.PasswordUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;
/**
 * 通过APPSCAN修改IS对应用户的密码
 */
public class UpdatePasswordAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(UpdatePasswordAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		String newPassoword = request.getParameter("Newpassword");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		try
		{	
			AppUsers appUsers = new AppUsers();
			Users users = appUsers.getUsers(username);
			String msg = PasswordUtil.checkPwdForUpdate(newPassoword, users.getLoginname(), 0);
			if(!StringUtil.isEmpty(msg)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, msg);
				return null;
			} 
			if(null != users){
				newPassoword = Encrypt.getSecret(newPassoword, 1);
				users.setPassword(newPassoword);
				//更新有效期
				Calendar today = Calendar.getInstance();
				today.add(Calendar.DAY_OF_YEAR, Constants.PWD_VAL_DATE);
				users.setValidate(today.getTime());
				//修改新密码
				appUsers.updUsers(users);
			}
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, ""
					,users.getUserid(),"采集器","IMEI:[" + scannerNo + "],修改密码",true);
		}catch (Exception e) {
			logger.error("", e);
		}
		finally{
		}
		return null;
	}
}
