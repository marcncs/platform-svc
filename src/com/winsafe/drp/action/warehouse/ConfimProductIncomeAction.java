package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class ConfimProductIncomeAction extends BaseAction {
	
	AppProductIncome api = new AppProductIncome();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);
		try{
			ProductIncome pi = api.getProductIncomeByID(id);
			if(pi.getIsaudit()!=1){
				request.setAttribute("result", "该单据未复核，不能确认！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			if(pi.getConfimState()==1){
				request.setAttribute("result", "该单据已确认，无法重复确认！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			pi.setConfimDate(DateUtil.getCurrentDate());
			pi.setConfimUserId(users.getUserid().toString());
			pi.setConfimState(1);
			api.updProductIncomeByID(pi);
			request.setAttribute("result", "databases.isaffirm.success");
			DBUserLog.addUserLog(users.getUserid(), 7, "产成品入库>>生产确认");
			return mapping.findForward("result");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
