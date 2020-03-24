package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.Outlay;
import com.winsafe.drp.util.DBUserLog;

public class AuditOutlayAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String oid = request.getParameter("OID");
			AppOutlay ao = new AppOutlay();
			Outlay ol = ao.getOutlayByID(oid);

			if (ol.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			ao.updIsAudit(oid, userid, 1);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 9, "费用申请/报销>>复核费用单,编号:"+oid);

			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
