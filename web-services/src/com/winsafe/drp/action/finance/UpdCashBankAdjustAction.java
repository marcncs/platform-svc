package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBankAdjust;
import com.winsafe.drp.dao.CashBankAdjust;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdCashBankAdjustAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("id");
			AppCashBankAdjust apa = new AppCashBankAdjust();
			CashBankAdjust l = apa.getCashBankAdjustById(id);

			l.setId(id);
			l.setCbid(RequestTool.getInt(request, "cbid"));
			l.setAdjustsum(RequestTool.getDouble(request, "adjustsum"));
			l.setMemo(request.getParameter("memo"));

			apa.updCashBankAdjust(l);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 9, "现金银行>>修改现金银行调整单,编号：" + id);

			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
