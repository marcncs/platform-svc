package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSendTime;
import com.winsafe.drp.dao.SendTime;
import com.winsafe.drp.dao.SendTimeForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdSendTimeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer id = Integer.valueOf(request.getParameter("ID"));

		try {

			SendTime w = new SendTime();
			//AppIntegralRule air = new AppIntegralRule();
			AppSendTime aw = new AppSendTime();
			w = aw.getSendTimeByID(id);

			SendTimeForm wf = new SendTimeForm();
			wf.setId(w.getId());
			wf.setIrid(w.getIrid());
			wf.setStime(w.getStime());
			wf.setEtime(w.getEtime());
			wf.setIridname(Internation.getSelectTagByKeyAllDBDef(
					"RKey", "rkey", w.getIrid()));
			wf.setIntegralrate(w.getIntegralrate());

			request.setAttribute("wf", wf);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return mapping.findForward("updDept");
	}

}
