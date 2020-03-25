package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.AppLoanObject;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.dao.LoanObject;
import com.winsafe.drp.dao.Outlay;
import com.winsafe.drp.dao.Reckoning;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class EndcaseOutlayAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String oid = request.getParameter("id");
			AppOutlay aso = new AppOutlay();
			Outlay so = aso.getOutlayByID(oid);

			if (so.getIsaudit() == 0) {
				String result = "databases.record.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (so.getIsendcase() == 1) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			int fundsrc = RequestTool.getInt(request,"fundsrc");
			String thisresist = request.getParameter("thisresist");
			String factpay = request.getParameter("factpay");
			so.setFundsrc(fundsrc);
			so.setThisresist(DataValidate.IsDouble(thisresist) ? Double
					.valueOf(thisresist) : 0d);
			so.setFactpay(DataValidate.IsDouble(factpay) ? Double
					.valueOf(factpay) : 0d);
			so.setIsendcase(1);
			so.setEndcaseid(userid);
			so.setEndcasedate(DateUtil.getCurrentDate());
			aso.updOutlay(so);

			
			if (so.getThisresist() > 0) {
				AppLoanObject aplo = new AppLoanObject();
				LoanObject lo = new LoanObject();
				lo.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"loan_object", 0, "")));
				lo.setUid(so.getOutlayid());
				lo.setMakeorganid(users.getMakeorganid());
		    	lo.setMakedeptid(users.getMakedeptid());
		    	lo.setMakeid(userid);
		    	lo.setMakedate(DateUtil.getCurrentDate());
				aplo.noExistsToAdd(lo);

				AppReckoning apr = new AppReckoning();
				Reckoning r = new Reckoning();
				r.setId(MakeCode.getExcIDByRandomTableName("reckoning", 0, ""));
				r.setUid(so.getOutlayid());
				r.setLoandate(DateUtil.getCurrentDate());
				r.setPurpose("");
				r.setLoansum(0d);
				r.setBacksum(so.getThisresist());
				r.setMemo("费用本次冲借生成个人借款清算单");
				r.setIscash(0);
				r.setFundattach(so.getFundsrc());
				r.setMakeid(userid);
				r.setMakedate(DateUtil.getCurrentDate());
				r.setMakeorganid(users.getMakeorganid());
			    r.setMakedeptid(users.getMakedeptid());
				r.setIsaudit(0);
				r.setAuditid(0);
				apr.addReckoning(r);
			}
			AppCashBank apcb = new AppCashBank();
			AppCashWasteBook acwb = new AppCashWasteBook();
			
			CashWasteBook cwb = new CashWasteBook();
			cwb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"cash_waste_book", 0, "")));
			cwb.setCbid(so.getFundsrc());
			cwb.setBillno(so.getId());
			cwb.setMemo("支出--费用报销");
			cwb.setCyclefirstsum(apcb.getCashBankById(so.getFundsrc())
					.getTotalsum());
			cwb.setCycleinsum(0d);
			cwb.setCycleoutsum(so.getFactpay());
			cwb.setCyclebalancesum(cwb.getCyclefirstsum()
					- cwb.getCycleoutsum());
			cwb.setRecorddate(DateUtil.getCurrentDate());
			acwb.addCashWasteBook(cwb);

			
			apcb.CancelAdjustTotalSum(so.getFundsrc(), so.getFactpay());

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 9,"费用申请/报销>>结款费用单,编号："+oid);

			return mapping.findForward("audit");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
