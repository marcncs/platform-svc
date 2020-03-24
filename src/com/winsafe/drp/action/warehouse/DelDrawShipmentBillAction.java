package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDrawShipmentBill;
import com.winsafe.drp.dao.AppDrawShipmentBillDetail;
import com.winsafe.drp.dao.DrawShipmentBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelDrawShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{

			String osid = request.getParameter("OSID");
			AppDrawShipmentBill asb = new AppDrawShipmentBill();
			AppDrawShipmentBillDetail asbd = new AppDrawShipmentBillDetail();
			DrawShipmentBill sb = asb.getDrawShipmentBillByID(osid);
			if (sb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.nodel");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			asb.delDrawShipmentBill(osid);
			asbd.delDrawShipmentBillDetailByDsid(osid);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), 7, "库存处理>>删除领用出库单,编号:" + osid, sb);
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
