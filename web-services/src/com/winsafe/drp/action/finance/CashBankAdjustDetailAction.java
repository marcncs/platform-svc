package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashBankAdjust;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CashBankAdjust;
import com.winsafe.drp.dao.CashBankAdjustForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class CashBankAdjustDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppCashBankAdjust al = new AppCashBankAdjust();
			CashBankAdjust cba = new CashBankAdjust();
			AppCashBank acb = new AppCashBank();
			AppUsers au = new AppUsers();

			cba = al.getCashBankAdjustById(id);
			CashBankAdjustForm lf = new CashBankAdjustForm();
			lf.setId(id);
			lf.setCbid(cba.getCbid());
			lf.setCbidname(acb.getCashBankById(cba.getCbid()).getCbname());
			lf.setAdjustsum(cba.getAdjustsum());
			lf.setMemo(cba.getMemo());
			lf.setMakeorganid(cba.getMakeorganid());
			lf.setMakedeptid(cba.getMakedeptid());
			lf.setMakeidname(au.getUsersByid(cba.getMakeid())
					.getRealname());
			lf.setMakedate(cba.getMakedate().toString());
			lf.setIsaudit(cba.getIsaudit());
			lf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, cba.getIsaudit(), "global.sys.SystemResource"));
			if (cba.getAuditid() != null && cba.getAuditid() > 0) {
				lf.setAuditidname(au.getUsersByid(cba.getAuditid())
						.getRealname());
			} else {
				lf.setAuditidname("");
			}
			lf.setAuditdate(DateUtil.formatDate(cba.getAuditdate()));

			request.setAttribute("lf", lf);

			DBUserLog.addUserLog(userid,9,"现金银行>>现金银行调整单详情,编号："+id); 
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
