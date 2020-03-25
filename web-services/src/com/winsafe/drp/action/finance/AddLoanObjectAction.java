package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLoanObject;
import com.winsafe.drp.dao.LoanObject;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddLoanObjectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {

			String struid = request.getParameter("uid");
			if (struid == null || struid.equals("null") || struid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			LoanObject lo = new LoanObject();
			lo.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"loan_object", 0, "")));
			lo.setMakeorganid(users.getMakeorganid());
			lo.setMakedeptid(users.getMakedeptid());
			lo.setMakeid(userid);
			lo.setMakedate(DateUtil.getCurrentDate());
			lo.setUid(Integer.valueOf(struid));

			AppLoanObject aro = new AppLoanObject();
			aro.noExistsToAdd(lo);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 9, "个人借支>>新增借款对象,编号：" + struid);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			request.setAttribute("result", "databases.add.fail");
			e.printStackTrace();
		}

		return mapping.findForward("addresult");
	}
}
