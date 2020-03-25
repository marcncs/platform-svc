package com.winsafe.drp.action.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.Affiche;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.AppOrganAwake;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.LeftMenu;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.service.UserService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Dateutil; 

public class InitMainPageAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/").toString();
		OrganService organService = new OrganService();
		UsersBean usersBean = (UsersBean)request.getSession().getAttribute("users");
		AppRole approle = new AppRole();
		List<LeftMenu> mls = approle.getLeftMenuByUserid(usersBean.getUserid());
		request.setAttribute("mls", mls);
		request.setAttribute("realname", usersBean.getRealname());
		request.setAttribute("makeorganidname", organService
				.getOrganName(usersBean.getMakeorganid()));
		AppOrganAwake appoa = new AppOrganAwake();
		request.setAttribute("isOrganAwake", appoa
				.getOrganAwakeByOidUserid(usersBean.getMakeorganid(),
						usersBean.getUserid()));
		UsersBean users = UserManager.getUser(request);
		//设置密码过期提醒
		if(users.isPsdExpireWarn()) {
			UserService us = new UserService();
			int day = us.differentDaysByMillisecond(Dateutil.getCurrentDate(), usersBean.getVad())+1;
			request.setAttribute("msg", "您的密码将在"+day+"天内过期,是否现在就修改密码?");
			users.setPsdExpireWarn(false);
		}
		int userid = users.getUserid();
	//	DBUserLog.addUserLog(userid, 11, "用户成功登录.登录IP="+ request.getRemoteAddr());
//		String tiptimes = cuv.remainderMonth();
//		request.setAttribute("timetips", tiptimes);
		/*if (usersBean.getIscall() == 1) { 
			request.setAttribute("callcenterip", Internation
					.getStringByKeyPositionDB("CallCenter", 0));
			return mapping.findForward("callcent");
		} else {*/
			AppAffiche appAffiche = new AppAffiche();
			List<Affiche> affiches = appAffiche.getPublishedSysAffiche();
			List<String> affichesMsg = new ArrayList<String>();
			if(affiches.size() > 0) {
				for(Affiche affiche : affiches) {
					int msgLength = 28;
					int index = 0;
					for (; (affiche.getAffichecontent().length() - index) / msgLength > 0; index = index + msgLength) {
						affichesMsg.add(affiche.getAffichecontent().substring(index, index + msgLength));
					}
					affichesMsg.add(affiche.getAffichecontent().substring(index));
				}
			}
			request.setAttribute("affiches", affichesMsg);
			return mapping.findForward("loginsuccess");
//		} 
	}

	
}
