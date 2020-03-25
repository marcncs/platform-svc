package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.winsafe.hbm.util.MakeCode;

public class AddNewCalendarResultAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		String awakeDate = request.getParameter("AwakeDate");
		String awakeTime = request.getParameter("AwakeTime");
		String awakeContent = request.getParameter("AwakeContent");

		AppCalendarAwake appCalendarAwake = new AppCalendarAwake();

		String awakeDateTime = awakeDate + " " + awakeTime;
		try {
			CalendarAwake ca = new CalendarAwake();
			ca.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"calendar_awake", 1, "")));
			ca.setAwakecontent(awakeContent);
			ca.setAwakedatetime(DateUtil.StringToDatetime(awakeDateTime));
			ca.setAwakemodel(0);
			ca.setIsawake(0);
			ca.setIsdel(0);
			ca.setUserid(userid);

			appCalendarAwake.addNewAwake(ca);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>新增日程,编号：" + ca.getId());
			return mapping.findForward("addresult");
		} catch (Throwable e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}

}
