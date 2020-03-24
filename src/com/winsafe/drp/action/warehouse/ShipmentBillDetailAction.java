package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.ShipmentBill;

public class ShipmentBillDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);try{
			AppShipmentBill asb = new AppShipmentBill();
			AppInvoiceConf aic = new AppInvoiceConf();
			ShipmentBill sb = asb.getShipmentBillByID(id);
			request.setAttribute("invmsgname", aic.getInvoiceConfById(sb.getInvmsg().intValue()).getIvname());
		
			AppShipmentBillDetail asbd = new AppShipmentBillDetail();
			List sals = asbd.getShipmentBillDetailBySbID(id);

			request.setAttribute("als", sals);
			request.setAttribute("sbf", sb);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
