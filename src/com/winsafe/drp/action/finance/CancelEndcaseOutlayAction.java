package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.dao.Outlay;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class CancelEndcaseOutlayAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String oid = request.getParameter("OID");
			AppOutlay aso = new AppOutlay(); 
			Outlay so = aso.getOutlayByID(oid);

			if(so.getIsendcase()==0){
	          	 String result = "databases.record.already";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
		    
		    
			AppCashBank apcb = new AppCashBank();
			AppCashWasteBook acwb = new AppCashWasteBook();
			
			CashWasteBook cwb = new CashWasteBook();
			cwb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("cash_waste_book",0,"")));
			cwb.setCbid(so.getFundsrc());
			cwb.setBillno(so.getId());
			cwb.setMemo("收入--取消结款费用报销");
			cwb.setCyclefirstsum(apcb.getCashBankById(so.getFundsrc()).getTotalsum());
			cwb.setCycleinsum(0d);
			cwb.setCycleoutsum(-so.getFactpay());
			cwb.setCyclebalancesum(cwb.getCyclefirstsum()-cwb.getCycleoutsum());
			cwb.setRecorddate(DateUtil.getCurrentDate());
			acwb.addCashWasteBook(cwb);
					
			apcb.AdjustTotalSum(so.getFundsrc(), so.getFactpay());
			aso.updIsEndcase(oid, userid,0);

		      request.setAttribute("result", "databases.cancel.success");
		      DBUserLog.addUserLog(userid,9,"费用申请/报销>>取消结款费用单,编号："+oid); 
			
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
