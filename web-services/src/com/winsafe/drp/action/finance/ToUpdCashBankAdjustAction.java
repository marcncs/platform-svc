package com.winsafe.drp.action.finance;

import java.util.List;

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
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdCashBankAdjustAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);super.initdata(request);try{

			AppCashBankAdjust apa = new AppCashBankAdjust();
			AppUsers au = new AppUsers();
			CashBankAdjust cba = apa.getCashBankAdjustById(id);

			if (cba.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			CashBankAdjustForm lf = new CashBankAdjustForm();
			lf.setId(id);
			lf.setCbid(cba.getCbid());
			// lf.setCbidname(acb.getCashBankById(cba.getCbid()).getCbname());
			lf.setAdjustsum(cba.getAdjustsum());
			lf.setMemo(cba.getMemo());
			lf.setMakeidname(au.getUsersByid(cba.getMakeid()).getRealname());
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

			AppCashBank appcb = new AppCashBank();
			List cblist = appcb.getAllCashBank();

			request.setAttribute("cblist", cblist);

			request.setAttribute("lf", lf);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
