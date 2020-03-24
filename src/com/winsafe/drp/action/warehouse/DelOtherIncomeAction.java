package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;

public class DelOtherIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);try{
			AppOtherIncome apb = new AppOtherIncome();
			OtherIncome pb = apb.getOtherIncomeByID(id);
			if (pb.getIsaudit() == 1) {
				request.setAttribute("result","databases.record.nodel");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			apb.delOtherIncome(id);
			AppOtherIncomeDetail apoid = new AppOtherIncomeDetail();
			AppIdcodeDetail apoii = new AppIdcodeDetail();
			apoid.delOtherIncomeDetailByPbID(id);
			apoii.delIdcodeDetailByBillid(id);
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_income_idcode","oiid",pb.getWarehouseid());
			wbds.del(pb.getId());
			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), 7, "库存盘点>>删除盘盈单,编号:id", pb);
			return mapping.findForward("delresult");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
