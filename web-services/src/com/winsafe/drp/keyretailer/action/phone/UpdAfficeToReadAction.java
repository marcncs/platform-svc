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
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users; 
import com.winsafe.drp.keyretailer.metadata.AfficheType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class UpdAfficeToReadAction extends Action {
	private Logger logger = Logger.getLogger(UpdAfficeToReadAction.class);
	private AppAffiche appAffiche = new AppAffiche();
	private AppUsers appUsers = new AppUsers();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
//		String imeinumber = request.getParameter("IMEI_number"); // 手机IMEI号
//		String afficheType = request.getParameter("type");// 公告类型
		String username = request.getParameter("Username"); //登陆名
		String afficheId = request.getParameter("afficheId");
		try {
			Users loginUsers = appUsers.getUsers(username);
			Affiche affiche = appAffiche.getAfficheByID(Integer.valueOf(afficheId));
			if((affiche.getAffichetype().equals(AfficheType.BONUS.getValue())
					|| affiche.getAffichetype().equals(AfficheType.AUDIT.getValue())
					|| affiche.getAffichetype().equals(AfficheType.BONUS_CONFIRM.getValue()))
					&& !"1".equals(affiche.getIsread())) {
				affiche.setIsread("1");
				appAffiche.updAffiche(affiche);
			} else {
				appAffiche.setAfficheToReadById(loginUsers.getUserid(), afficheId);
			}
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, Constants.CODE_ERROR_MSG, "更新失败,系统异常");
		}
		return null;
	}
}
