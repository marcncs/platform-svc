package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppOtherIncomeAll;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncomeAll;
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
			AppOtherIncomeAll apb = new AppOtherIncomeAll();
			OtherIncomeAll pb = apb.getOtherIncomeByID(id);
			if (pb.getIsaudit() == 1) {
				request.setAttribute("result","databases.record.nodel");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			apb.delOtherIncomeAll(id);
			AppOtherIncomeDetail apoid = new AppOtherIncomeDetail();
			AppIdcodeDetail apoii = new AppIdcodeDetail();
			apoid.delOtherIncomeDetailByPbID(id);
			//删除idcodedetail表中数据
			apoii.delIdcodeDetailByBillid(id);
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_income_idcode_all","oiid",pb.getWarehouseid());
			wbds.del(pb.getId());
			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(request, "编号:id", pb);
			return mapping.findForward("delresult");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
