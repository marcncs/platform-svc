package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssemble;
import com.winsafe.drp.dao.Assemble;
import com.winsafe.drp.util.DBUserLog;

public class AuditAssembleAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppAssemble aso = new AppAssemble();
			Assemble so = aso.getAssembleByID(id);

			if (so.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			//so.setIsaudit(1);
			//so.setAuditid(userid);

			aso.updIsAudit(id,userid,1);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid,3, "复核组装单,编号："+id);

			return mapping.findForward("audit");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
