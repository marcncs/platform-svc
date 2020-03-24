package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.Reckoning;
import com.winsafe.drp.util.DBUserLog;

public class DelReckoningAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String rid = request.getParameter("RID");
			AppReckoning apl = new AppReckoning();
			Reckoning l = apl.getReckoningByID(rid);

			if (l.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			apl.delReckoning(rid);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 9, "个人借支>>删除借款,编号：" + rid);
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
