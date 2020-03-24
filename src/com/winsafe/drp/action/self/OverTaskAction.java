package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppTask;
import com.winsafe.drp.dao.AppTaskExecute;
import com.winsafe.drp.dao.TaskExecute;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class OverTaskAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			AppTask at = new AppTask();
			AppTaskExecute ate = new AppTaskExecute();

			TaskExecute so = ate.getTaskExecute(id, userid);

			so.setIsover(1);
			so.setOverdate(DateUtil.getCurrentDate());

			ate.updTaskExecute(so);

			
			int isover = ate.getIsOverUser(id);
			if (isover <= 0) {
				at.updIsOverStatus(id, 3, DateUtil.getCurrentDateString());
			} else {
				at.updIsOverStatus(id, 2, DateUtil.getCurrentDateString());
			}

			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid,0, "完成任务,编号：" + id);

			return mapping.findForward("over");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
