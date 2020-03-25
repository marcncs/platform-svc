/**
 * 
 */
package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCallCenterEvent;
import com.winsafe.drp.dao.UserCallEvent;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdUserCallEventAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int monitoruserid = RequestTool.getInt(request, "muid");
		int[] arid = RequestTool.getInts(request, "role");

		try {

			AppCallCenterEvent ar = new AppCallCenterEvent();		
			ar.DelUserCallEventByMuid(monitoruserid);
			
			
			if ( arid != null ){
				for (int i = 0; i < arid.length; i++) {
					UserCallEvent uce = new UserCallEvent();
					uce.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("user_call_event",0,"")));
					uce.setMonitoruserid(monitoruserid);
					uce.setUserid(arid[i]);
					ar.addUserCallEvent(uce);
				}
			}

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11,"用户管理>>修改呼叫查询设置");
			request.setAttribute("result", "databases.upd.success");
			request.setAttribute("forward", "../users/listUsersAction.do");
			return mapping.findForward("result");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return mapping.findForward("result");
	}

}
