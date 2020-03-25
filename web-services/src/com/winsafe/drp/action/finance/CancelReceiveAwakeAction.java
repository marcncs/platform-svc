package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivableObject;

public class CancelReceiveAwakeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String id = request.getParameter("ID");
			
			AppReceivableObject aro = new AppReceivableObject();
//			aro.cancelRecievableAwake(id);
			
		      request.setAttribute("result", "databases.cancel.success");

			//DBUserLog.addUserLog(userid,"取消提醒日期"); 

			return mapping.findForward("cancelawake");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
