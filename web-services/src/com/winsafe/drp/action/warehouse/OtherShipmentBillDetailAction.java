package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.OtherShipmentBill;

public class OtherShipmentBillDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);try{
			AppOtherShipmentBill aosb = new AppOtherShipmentBill();
			OtherShipmentBill osb = aosb.getOtherShipmentBillByID(id);
			if (osb == null) {
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppOtherShipmentBillDetail aosbd = new AppOtherShipmentBillDetail();
			List sals = aosbd.getOtherShipmentBillDetailBySbID(id);

			request.setAttribute("als", sals);
			request.setAttribute("osbf", osb);

			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
