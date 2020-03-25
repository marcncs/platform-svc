package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIntegralRule;
import com.winsafe.drp.dao.AppPaymentMode;
import com.winsafe.drp.dao.PaymentMode;
import com.winsafe.drp.dao.PaymentModeForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdPaymentModeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer id = Integer.valueOf(request.getParameter("ID"));

		try {

			PaymentMode w = new PaymentMode();
			AppIntegralRule air = new AppIntegralRule();
			AppPaymentMode aw = new AppPaymentMode();
			w = aw.getPaymentModeByID(id);

			PaymentModeForm wf = new PaymentModeForm();
			wf.setId(w.getId());
			wf.setIrid(w.getIrid());
			wf.setIridname(air.getIntegralRuleByID(w.getIrid()).getRmode());
			wf.setPaymentname(w.getPaymentname());
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
