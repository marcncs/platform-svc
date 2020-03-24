package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDrawShipmentBill;
import com.winsafe.drp.dao.AppFeeWasteBook;
import com.winsafe.drp.dao.DrawShipmentBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class CancelEndcaseDrawShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();

		super.initdata(request);try{
			String id = request.getParameter("id");
			AppDrawShipmentBill apb = new AppDrawShipmentBill();
			DrawShipmentBill pb = apb.getDrawShipmentBillByID(id);

			if (pb.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (pb.getIsendcase() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			
			AppFeeWasteBook apfwb = new AppFeeWasteBook();
			apfwb.delFeeWasteBookByBillno(pb.getId());

//			apb.updIsEndCase(id, userid, 0);
//			
//			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "取消结案领用出库,编号：" + id);
			
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
