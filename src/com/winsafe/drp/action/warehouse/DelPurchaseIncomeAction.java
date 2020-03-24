package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.AppPurchaseIncomeIdcode;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;

public class DelPurchaseIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);try{
			AppPurchaseIncome apb = new AppPurchaseIncome();
			PurchaseIncome pb = apb.getPurchaseIncomeByID(id);
			if (pb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.approvestatus");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			apb.delPurchaseIncome(id);
			AppPurchaseIncomeDetail apoid = new AppPurchaseIncomeDetail();
			AppPurchaseIncomeIdcode apoii = new AppPurchaseIncomeIdcode();
			apoid.delPurchaseIncomeDetailByPiID(id);
			apoii.delPurchaseIncomeIdcodeByPiid(id);
			WarehouseBitDafService wbds = new WarehouseBitDafService("purchase_income_idcode","piid",pb.getWarehouseid());
			wbds.del(pb.getId());
			
			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), 7,"入库>>删除采购入库,编号："+id, pb);
			return mapping.findForward("delresult");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
