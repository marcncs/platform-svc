package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSampleBill;
import com.winsafe.drp.dao.AppSampleBillDetail;
import com.winsafe.drp.dao.SampleBill;

public class ToUpdSampleBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		String id = request.getParameter("ID");
		try {
			AppSampleBill asb = new AppSampleBill();
//			AppCustomer ac = new AppCustomer();

			SampleBill sb = asb.findByID(id);
			if (sb.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			
			AppSampleBillDetail asld = new AppSampleBillDetail();
			List slls = asld.findBySbid(id);

			request.setAttribute("oid", users.getMakeorganid());
			request.setAttribute("sbf", sb);
			request.setAttribute("als", slls);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
