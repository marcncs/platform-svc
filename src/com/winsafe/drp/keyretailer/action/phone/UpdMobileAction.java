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
import com.winsafe.drp.keyretailer.util.SmsValidateCodesUtil;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil; 
/**
 * 对应用户的手机号
 */
public class UpdMobileAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(UpdMobileAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String username = request.getParameter("Username");
		String oldMobile = request.getParameter("Mobile");
		String newMobile = request.getParameter("newMobile");
		String code = request.getParameter("code");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		try
		{	
			AppUsers appUsers = new AppUsers();
			Users users = appUsers.getUsers(username);
			if(null != users){
				
//				Users uu = appUsers.getUsersByMobile(Encrypt.getSecret(newMobile, 3));
				Users uu = appUsers.getUsersByMobile(newMobile);
				if (uu!=null) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "修改失败,此手机号已经存在");
					return null;
				}
				
				String vCode = SmsValidateCodesUtil.getValidateCode(newMobile);
				if(StringUtil.isEmpty(vCode) && !SmsValidateCodesUtil.isInRange(newMobile)) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "修改失败,验证码已过期");
					return null;
				} else {
					if(!vCode.equals(code)) { 
						ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "修改失败,验证码不正确");
						return null;
					}
				}
				
				if(!oldMobile.equals(users.getMobile())) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "修改失败,旧手机号不正确");
					return null;
				}
				
				users.setMobile(newMobile);
				users.setValkey("0");
				//修改新密码
				appUsers.updUsers(users);
			}
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, ""
					,users.getUserid(),"采集器","IMEI:[" + scannerNo + "],修改手机号",true);
		}catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
