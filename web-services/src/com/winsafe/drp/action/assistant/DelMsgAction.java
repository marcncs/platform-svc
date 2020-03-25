package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMsg;
import com.winsafe.drp.dao.Msg;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelMsgAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			int id = Integer.valueOf(request.getParameter("ID"));
			AppMsg appmsg = new AppMsg();
			 Msg sb = appmsg.getMsgById(id);
					
			 if(sb.getIsaudit()==1){
				 request.setAttribute("result", "databases.record.nodel");
				 return new ActionForward("/sys/lockrecordclose.jsp");
			 }

			appmsg.delMsg(id);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), 12, "删除短信记录,编号："+id,sb);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
