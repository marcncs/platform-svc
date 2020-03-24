package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;

public class DelProductIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppProductIncome apb = new AppProductIncome();
			ProductIncome pb = apb.getProductIncomeByID(id);
			if (pb.getIsaudit() == 1) {
				String result = "databases.record.approvestatus";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			apb.delProductIncome(id);
			AppProductIncomeDetail apid= new AppProductIncomeDetail();
			apid.delProductIncomeDetailByPbID(id);
			WarehouseBitDafService wbds = new WarehouseBitDafService("product_income_idcode","piid",pb.getWarehouseid());
			wbds.del(pb.getId());
			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 7,"产成品入库>>删除产成品入库,编号："+id,pb);
			return mapping.findForward("delresult");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
