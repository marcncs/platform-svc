package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.CashBank;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdCashBankAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			int id = RequestTool.getInt(request, "id");
			String cbname = request.getParameter("cbname");

			AppCashBank acb = new AppCashBank();
			CashBank cb = acb.getCashBankById(id);
			CashBank oldW = (CashBank) BeanUtils.cloneBean(cb);
			cb.setId(id);
			cb.setCbname(cbname);

			acb.updCashBankByID(cb);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 9, "修改现金银行,编号：" + id, oldW, cb);
			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
