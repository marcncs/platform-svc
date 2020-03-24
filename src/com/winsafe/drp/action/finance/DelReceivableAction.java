package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.util.DBUserLog;

public class DelReceivableAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String rid = request.getParameter("RID");
			AppReceivable ar = new AppReceivable();
			Receivable r = new Receivable();
			r = ar.getReceivableByID(rid);

			ar.delReceivable(rid);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 9, "删除应收款,编号：" + rid, r);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
