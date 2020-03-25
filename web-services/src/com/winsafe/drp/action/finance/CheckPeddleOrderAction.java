package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;

public class CheckPeddleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		

		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		super.initdata(request);try{
			
			String[] fundattachs = request.getParameterValues("fundattach") ;
			String[] incomesums = request.getParameterValues("incomesum");
			String poids = request.getParameter("poids");
			//System.out.println("=============="+poids);
			AppCashBank apcb = new AppCashBank();
			AppCashWasteBook acwb = new AppCashWasteBook();
			for ( int i=0; i<fundattachs.length; i++ ){
				Long fundattach = Long.valueOf(fundattachs[i]);
				double incomesum = Double.valueOf(incomesums[i]);
				if ( incomesum == 0 ){
					continue;
				}
				
				CashWasteBook cwb = new CashWasteBook();
//				cwb.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("cash_waste_book",0,"")));
//				cwb.setCbid(fundattach);
				cwb.setBillno("");
				cwb.setMemo("收入--零售对帐收款");
//				cwb.setCyclefirstsum(apcb.getCashBankById(fundattach).getTotalsum());
				cwb.setCycleinsum(incomesum);
				cwb.setCycleoutsum(0d);
				cwb.setCyclebalancesum(cwb.getCyclefirstsum()+cwb.getCycleinsum());
				cwb.setRecorddate(DateUtil.getCurrentDate());
				acwb.addCashWasteBook(cwb);
				
				
//				apcb.AdjustTotalSum(fundattach, incomesum);
			}
			
		    AppPeddleOrder appo = new AppPeddleOrder();
		    String[] poid = poids.split(",");
		    for ( int i=0; i<poid.length; i++ ){
		    	appo.updIsDayBalance(poid[i]);
		    }
		    
		    request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid,"零售对帐："+userid); 
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
