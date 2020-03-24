package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppHarmShipmentBill;
import com.winsafe.drp.dao.AppHarmShipmentBillDetail;
import com.winsafe.drp.dao.HarmShipmentBill;
import com.winsafe.drp.util.DBUserLog;

public class DelHarmShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String osid = request.getParameter("OSID");
			AppHarmShipmentBill asb = new AppHarmShipmentBill();
			AppHarmShipmentBillDetail asbd = new AppHarmShipmentBillDetail();
			HarmShipmentBill sb = asb.getHarmShipmentBillByID(osid);
			if (sb.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			asb.delHarmShipmentBill(osid);
			asbd.delHarmShipmentBillDetailBySbID(osid);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 7, "报损>>删除报损出库单,编号：" + osid, sb);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
