package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLargess;
import com.winsafe.drp.dao.Largess;
import com.winsafe.drp.util.DBUserLog;

public class DelLargessAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			AppLargess ah = new AppLargess();
			Largess largess = ah.getLargessByID(id);
			ah.delLargess(id);
			request.setAttribute("result", "databases.del.success");
			
			initdata(request);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>删除赠品,编号:" + id, largess);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
