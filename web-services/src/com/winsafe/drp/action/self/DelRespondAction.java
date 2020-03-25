package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRespond;
import com.winsafe.drp.dao.Respond;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-8 下午03:52:21
 * www.winsafe.cn
 */
public class DelRespondAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			Integer id = RequestTool.getInt(request, "ID");
			AppRespond appRespond = new AppRespond();
			Respond respond = appRespond.findByID(id);
			appRespond.delete(respond);
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>删除常见问题 回复,编号：" + id,respond);
			request.setAttribute("result", "databases.del.success");
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
	

}
