package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Reckoning;
import com.winsafe.drp.dao.ReckoningForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ReckoningDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppReckoning al = new AppReckoning();
			AppCashBank acb = new AppCashBank();
			AppUsers au = new AppUsers();

			Reckoning l = al.getReckoningByID(id);
			ReckoningForm lf = new ReckoningForm();
			lf.setId(id);
			lf.setUid(l.getUid());
			lf.setUidname(au.getUsersByid(l.getUid()).getRealname());
			lf.setLoandate(String.valueOf(l.getLoandate()));
			lf.setPurpose(l.getPurpose());
			lf.setLoansum(l.getLoansum());
			lf.setBacksum(l.getBacksum());
			lf.setMemo(l.getMemo());
			lf.setFundattachname(acb.getCashBankById(l.getFundattach()).getCbname());
			lf.setMakeorganid(l.getMakeorganid());
			lf.setMakedeptid(l.getMakedeptid());
			lf.setMakeidname(au.getUsersByid(l.getMakeid())
					.getRealname());
			lf.setMakedate(l.getMakedate().toString());
			lf.setIsaudit(l.getIsaudit());
			lf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, l.getIsaudit(), "global.sys.SystemResource"));
			if (l.getAuditid() != null && l.getAuditid() > 0) {
				lf.setAuditidname(au.getUsersByid(l.getAuditid())
						.getRealname());
			} else {
				lf.setAuditidname("");
			}
			lf.setAuditdate(DateUtil.formatDate(l.getAuditdate()));

			request.setAttribute("lf", lf);

			//DBUserLog.addUserLog(userid,9,"个人借支>>还款详情,编号："+id); 
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
