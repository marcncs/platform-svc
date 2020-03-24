package com.winsafe.drp.action.phone;

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
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;
/**
 * 通过APPSCAN修改IS对应用户的密码
 */
public class UpdateMemberPasswordAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(UpdateMemberPasswordAction.class);
	
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
			if(null != users){
				newPassoword = Encrypt.getSecret(createDecryptor(newPassoword), 1);
				users.setPassword(newPassoword);
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
	
	public String createDecryptor(String password) throws Exception{
		 password = password.substring(5, password.length());
		 password = password.substring(0, password.length()-5);
    	 return password;
	}
}
