package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppReceiveIncome;
import com.winsafe.drp.dao.AppReceiveIncomeDetail;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.dao.ReceiveIncome;
import com.winsafe.drp.dao.ReceiveIncomeDetail;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;

public class ReceiveIncomeDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppReceiveIncome api = new AppReceiveIncome();
			ReceiveIncome pi = api.getReceiveIncomeByID(id);

			AppReceiveIncomeDetail aspb = new AppReceiveIncomeDetail();
			List<ReceiveIncomeDetail> spils = aspb.getReceiveIncomeDetailByPbId(id);

			request.setAttribute("als", spils);
			request.setAttribute("pif", pi);
			request.setAttribute("type", request.getParameter("type"));
			DBUserLog.addUserLog(userid, 7, "经销商签收入库>>经销商签收入库详情,编号:" + id);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
