package com.winsafe.drp.action.users;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.metadata.YesOrNo;

public class ValidateOrganAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String id = request.getParameter("id");
		String organname = request.getParameter("organname");
		
		try {
			AppOrgan ao = new AppOrgan();
			Organ d = ao.getOrganByID(id);
			d.setValidatestatus(YesOrNo.YES.getValue());
			d.setValidatedate(DateUtil.getCurrentDate());
			d.setValidateuserid(userid);
			ao.updOrgan(d);
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(request, "编号：" + d.getId());

			return mapping.findForward("validateResult");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "databases.audit.fail");

		} finally {

		}
		return mapping.findForward("updResult");
	}
}
