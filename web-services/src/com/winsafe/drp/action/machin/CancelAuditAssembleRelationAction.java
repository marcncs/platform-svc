package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssembleRelation;
import com.winsafe.drp.dao.AssembleRelation;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditAssembleRelationAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppAssembleRelation aso = new AppAssembleRelation();
			AssembleRelation so = aso.getAssembleRelationByID(id);

			if (so.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}


			if (!String.valueOf(so.getAuditid()).contains(userid.toString())) {
				String result = "databases.record.cancelaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			//so.setIsaudit(0);
			//so.setAuditdate(DateUtil.getCurrentDate());
			//so.setAuditid(userid);
			aso.updIsAudit(id, userid, 0);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 3,"组装关系>>取消组装关系，编号："+id);

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
