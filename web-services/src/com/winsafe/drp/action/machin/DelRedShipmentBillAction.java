package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.AppOtherShipmentBillIdcode;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;

public class DelRedShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.initdata(request);
		try {

			String osid = request.getParameter("OSID");
			AppOtherShipmentBillAll asb = new AppOtherShipmentBillAll();
			OtherShipmentBillAll sb = asb.getOtherShipmentBillByID(osid);
			if (sb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.nodel");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppOtherShipmentBillDAll asbd = new AppOtherShipmentBillDAll();
			AppOtherShipmentBillIdcode apposi = new AppOtherShipmentBillIdcode();
			asb.delOtherShipmentBill(osid);
			asbd.delOtherShipmentBillDetailBySbID(osid);
			apposi.delOtherShipmentBillIdcodeByTiid(osid);
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_shipment_bill_i_all",
					"osid", sb.getWarehouseid());
			wbds.del(sb.getId());

			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(request, "编号:id", sb);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
