package com.winsafe.drp.action.self;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCalendarAwake;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class ListCurDayCalendarAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppCalendarAwake appCalendarAwake = new AppCalendarAwake();
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {

			String tmpDate = request.getParameter("curDate");
			String curDate = null;
			if (tmpDate == null) {
				curDate = DateUtil.getCurrentDateString();
			} else {
				curDate = tmpDate;
			}

			List curAwakeLs = appCalendarAwake.getCurDayAwake(curDate, userid);

			request.setAttribute("curDayAwake", curAwakeLs);

			DBUserLog.addUserLog(userid, 0,"我的办公桌>>列表当天日程");
			return mapping.findForward("listcurday");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
