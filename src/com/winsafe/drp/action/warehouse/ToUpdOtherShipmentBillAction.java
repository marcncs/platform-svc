package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OtherShipmentBill;

public class ToUpdOtherShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);try{
			AppOtherShipmentBill aosb = new AppOtherShipmentBill();

			OtherShipmentBill osb = aosb.getOtherShipmentBillByID(id);
			if (osb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.lock");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppOtherShipmentBillDetail asbd = new AppOtherShipmentBillDetail();
			List slls = asbd.getOtherShipmentBillDetailBySbID(id);

			AppOrgan appo=new AppOrgan();
			Organ o=appo.getOrganByWarehouseid(osb.getWarehouseid());
			request.setAttribute("o", o);
			request.setAttribute("osbf", osb);
			request.setAttribute("als", slls);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
