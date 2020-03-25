package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppConsignMachin;
import com.winsafe.drp.dao.ConsignMachin;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditConsignMachinAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppConsignMachin aso = new AppConsignMachin();
			ConsignMachin so = aso.getConsignMachinByID(id);

			if (so.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			if (!String.valueOf(so.getAuditid()).contains(userid.toString())) {
//				String result = "databases.record.cancelaudit";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}

			aso.updIsAudit(id, userid, 0);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid,3, "委外加工>>取消复核委外加工单，编号："+id);

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
