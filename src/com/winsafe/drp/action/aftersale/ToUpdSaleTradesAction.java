package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.dao.AppSaleTradesDetail;
import com.winsafe.drp.dao.SaleTrades;

public class ToUpdSaleTradesAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {

			AppSaleTrades aso = new AppSaleTrades();
			SaleTrades so = aso.getSaleTradesByID(id);
			if (so.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.lock");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			if (so.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecord.jsp");
			}


			AppSaleTradesDetail asld = new AppSaleTradesDetail();
			List slls = asld.getSaleTradesDetailByStid(id);


			request.setAttribute("sof", so);
			request.setAttribute("als", slls);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
