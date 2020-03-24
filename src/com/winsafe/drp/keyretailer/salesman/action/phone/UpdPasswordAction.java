package com.winsafe.drp.keyretailer.salesman.action.phone;

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
public class UpdPasswordAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(UpdPasswordAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String username = request.getParameter("Username");
		String oldPassoword = request.getParameter("Password");
		String newPassoword = request.getParameter("newPassword");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		try
		{	
			if(!StringUtil.isEmpty(oldPassoword)) {
				oldPassoword = Encrypt.getSecret(oldPassoword, 1);
			}
			AppUsers appUsers = new AppUsers();
			Users users = appUsers.getUsers(username);
			if(null != users){
				if(!StringUtil.isEmpty(oldPassoword) && !oldPassoword.equals(users.getPassword())) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "修改失败,旧密码不正确");
					return null;
				}
				
				String msg = PasswordUtil.checkPwdForUpdate(newPassoword, users.getLoginname(), 0);
				if(!StringUtil.isEmpty(msg)) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, msg);
					return null;
				}
				//设置有效期
				Calendar today = Calendar.getInstance();
				today.add(Calendar.DAY_OF_YEAR, Constants.PWD_VAL_DATE);
				users.setValidate(today.getTime());
				
				newPassoword = Encrypt.getSecret(newPassoword, 1);
				users.setPassword(newPassoword);
				users.setValkey("0");
				//修改新密码
				appUsers.updUsers(users);
			}
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, ""
					,users.getUserid(),"采集器","IMEI:[" + scannerNo + "],修改密码",true);
		}catch (Exception e) {
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
