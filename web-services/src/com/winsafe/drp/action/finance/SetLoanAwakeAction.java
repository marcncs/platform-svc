package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLoanObject;
import com.winsafe.drp.dao.LoanObject;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RequestTool;

public class SetLoanAwakeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);super.initdata(request);try{

			Integer id = RequestTool.getInt(request, "id");

			AppLoanObject aro = new AppLoanObject();
			LoanObject lo = aro.getLoanObjectByID(id);
			lo.setPromisedate(DateUtil.StringToDate(request
					.getParameter("promisedate")));
			aro.updLoanObject(lo);

			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("setawake");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
