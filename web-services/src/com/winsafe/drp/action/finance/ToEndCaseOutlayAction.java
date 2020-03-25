package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppLoan;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.Outlay;

public class ToEndCaseOutlayAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String oid = request.getParameter("OID");
			AppOutlay aso = new AppOutlay();
			Outlay so = aso.getOutlayByID(oid);

			if (so.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.noaudit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (so.getIsendcase() == 1) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AppCashBank apcb = new AppCashBank();
			List cblist = apcb.getAllCashBank();
			
			AppLoan appl = new AppLoan();
			AppReckoning appr = new AppReckoning();
			double waitbacksum = appl.getLoanSumByUID(so.getOutlayid())-appr.getReckoningSumByUID(so.getOutlayid());
			

			request.setAttribute("id", oid);
			request.setAttribute("cblist", cblist);
			request.setAttribute("waitbacksum", waitbacksum);
			request.setAttribute("totaloutlay", so.getTotaloutlay());

			return mapping.findForward("toendcase");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
