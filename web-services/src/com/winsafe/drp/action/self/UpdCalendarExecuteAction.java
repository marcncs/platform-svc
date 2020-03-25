package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCalendarAwake;
import com.winsafe.drp.dao.CalendarAwake;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class UpdCalendarExecuteAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();

			String id = request.getParameter("ID");
			System.out.println(id);
			AppCalendarAwake appCalendarAwake = new AppCalendarAwake();
			
			CalendarAwake ca = appCalendarAwake.getAwakeByID(id);
			CalendarAwake oldca = (CalendarAwake) BeanUtils.cloneBean(ca);
			String awakeDate = request.getParameter("AwakeDate");
			String awakeTime = request.getParameter("AwakeTime");
			String awakeContent = request.getParameter("AwakeContent");

			String awakeDateTime = awakeDate + " " + awakeTime;
			ca.setAwakecontent(awakeContent);
			ca.setAwakedatetime(DateUtil.StringToDatetime(awakeDateTime));
			ca.setAwakemodel(Integer.valueOf(0));
			ca.setIsawake(Integer.valueOf("0"));
			ca.setIsdel(Integer.valueOf("0"));
			ca.setUserid(userid);
			appCalendarAwake.updAwake(ca);

			 request.setAttribute("result","databases.upd.success");
			DBUserLog.addUserLog(userid, 0,"我的办公桌>>修改日程",oldca,ca);

			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
