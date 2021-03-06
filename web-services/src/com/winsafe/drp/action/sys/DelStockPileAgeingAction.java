package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppStockPileAgeing;
import com.winsafe.drp.dao.StockPileAgeing;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelStockPileAgeingAction extends Action {
	AppStockPileAgeing aspa = new AppStockPileAgeing();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			StockPileAgeing spa = aspa.getStockPileAgeingById(id);
			aspa.delStockPileAgeingById(id);
			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "基础设置>>删除库龄参数,编号：" + id, spa);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
}
