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
import com.winsafe.drp.dao.SampleBillDetail;

public class SampleBillDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			AppSampleBill asb = new AppSampleBill();
			SampleBill sb = asb.findByID(id);

			AppSampleBillDetail asbd = new AppSampleBillDetail();
			List<SampleBillDetail> list = asbd.findBySbid(id);

			request.setAttribute("als", list);
			request.setAttribute("sb", sb);
			
			initdata(request);
			//DBUserLog.addUserLog(userid, "样品记录详细");
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
