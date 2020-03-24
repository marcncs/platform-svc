package com.winsafe.drp.keyretailer.action.phone;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.Affiche;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users; 
import com.winsafe.drp.keyretailer.metadata.AfficheType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class CheckReturnCode extends Action {
	private Logger logger = Logger.getLogger(CheckReturnCode.class);
	private AppIdcode ai = new AppIdcode();
	private AppUsers appUsers = new AppUsers();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
//		String imeinumber = request.getParameter("IMEI_number"); // 手机IMEI号
//		String afficheType = request.getParameter("type");// 公告类型
		String username = request.getParameter("Username"); //登陆名
		String fromOrganId = request.getParameter("fromOrganId");
		String toOrganId = request.getParameter("toOrganId");
		String idcodes = request.getParameter("idcodes");
		try {
			Users loginUsers = appUsers.getUsers(username);
			if(StringUtil.isEmpty(idcodes)) {
				
			}
			ai.getReturnCodeInfo(idcodes);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, Constants.CODE_ERROR_MSG, "更新失败,系统异常");
		}
		return null;
	}
}
