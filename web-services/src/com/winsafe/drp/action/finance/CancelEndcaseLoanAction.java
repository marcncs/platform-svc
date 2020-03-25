package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.AppLoan;
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.dao.Loan;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class CancelEndcaseLoanAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		super.initdata(request);
		try{
			String lid = request.getParameter("LID");
			AppLoan ar = new AppLoan(); 
			Loan r = ar.getLoanByID(lid);

			if(r.getIsendcase()==0){
	          	 String result = "databases.record.already";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
//			if(!String.valueOf(r.getAuditid()).contains(userid.toString())){
//	          	 String result = "databases.record.cancelaudit";
//	               request.setAttribute("result", result);
//	               return new ActionForward("/sys/lockrecordclose.jsp");
//	           }
		
			AppCashBank apcb = new AppCashBank();	
			AppCashWasteBook acwb = new AppCashWasteBook();
			
			CashWasteBook cwb = new CashWasteBook();
			cwb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("cash_waste_book",0,"")));
			cwb.setCbid(r.getFundsrc());
			cwb.setBillno(r.getId());
			cwb.setMemo("收入--取消个人借款");
			cwb.setCyclefirstsum(apcb.getCashBankById(r.getFundsrc()).getTotalsum());
			cwb.setCycleinsum(0d);
			cwb.setCycleoutsum(-r.getLoansum());
			cwb.setCyclebalancesum(cwb.getCyclefirstsum()-cwb.getCycleoutsum());
			cwb.setRecorddate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			acwb.addCashWasteBook(cwb);
						
			apcb.AdjustTotalSum(r.getFundsrc(), r.getLoansum());
		    
			ar.updIsEndcase(lid, userid,0);

		      request.setAttribute("result", "databases.cancel.success");

			DBUserLog.addUserLog(userid,9,"个人借支>>取消复核借款,编号："+lid); 
			return mapping.findForward("noaudit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
