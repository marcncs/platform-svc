package com.winsafe.drp.action.users;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.server.UserApplyServices;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;


public class AjaxCheckUsersAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ln =request.getParameter("ln");
		String checkmobile =request.getParameter("type");
		try {
			if("1".equals(checkmobile)) {
				String userId = request.getParameter("userId");
				String mobile = request.getParameter("mobile");
				UserApplyServices uas = new UserApplyServices();
				if(uas.isMobileAlreadyExists(mobile, userId)) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "该手机号已注册过!");
				} else { 
					ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, "");
				}
			} else {
				/*UsersService applm = new UsersService();
				Users users = null;
				if (!StringUtil.isEmpty(checkmobile)&&"checkmobile".equals(checkmobile)) {
					// 检查手机号
					AppUsers appo = new AppUsers();
					users = appo.getUsersByMobile(ln);
				} else {
					users = applm.getUsers(ln);
				}*/
				Users users = null;
				UserApplyServices uas = new UserApplyServices();
				if(uas.isMobileAlreadyExists(ln, null)) {
					users = new Users();
				} 
				
				JSONObject json = new JSONObject();			
				json.put("users", users);				
				response.setContentType("text/html; charset=UTF-8");
			    response.setHeader("Cache-Control", "no-cache");
			    PrintWriter out = response.getWriter();
			    out.print(json.toString());
			    out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
