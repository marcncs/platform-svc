package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DbUtil;

public class ListSalesAwakeAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean usersBean = UserManager.getUser(request);
		Integer userid = usersBean.getUserid();
		try{
			int pagesize=1;

			String afficheCondition = " where so.makeid like '"+userid+"%' and so.isaudit=0 ";
			String afficheTable="SaleOrder so ";
			int saleorderaudit =DbUtil.getRecordCount(pagesize, afficheCondition, afficheTable);
			
			//String wheresql=" where approveid="+userid+" and approve=0 ";
			//int saleorder=DbUtil.getRecordCount(pagesize, wheresql, "SaleOrderApprove");
	    	//int withdraw=DbUtil.getRecordCount(pagesize, wheresql, "WithdrawApprove");
	    	//int outlay=DbUtil.getRecordCount(pagesize, wheresql, "OutlayApprove");
	    	
			request.setAttribute("saleorderaudit", saleorderaudit);
			//request.setAttribute("saleorder", saleorder);
	    	//request.setAttribute("withdraw", withdraw);
	    	//request.setAttribute("outlay", outlay);
	    	


//			String receivableCondition = " where r.makeid like '"+userid+"%' and r.isaudit=0 ";
//			String receivableTable="Receivable r ";
//			int receivableaudit =DbUtil.getRecordCount(pagesize, receivableCondition, receivableTable);
//			
//			request.setAttribute("receivableaudit", receivableaudit);
			

			String incomelogCondition = " where il.makeid like '"+userid+"%' and il.isaudit=0 ";
			String incomelogTable="IncomeLog il ";
			int incomelogaudit =DbUtil.getRecordCount(pagesize, incomelogCondition, incomelogTable);
			
			request.setAttribute("incomelogaudit", incomelogaudit);


//			String payableCondition = " where p.makeid like '"+userid+"%' and p.isaudit=0 ";
//			String payableTable="Payable p ";
//			int payableaudit =DbUtil.getRecordCount(pagesize, payableCondition, payableTable);
//			
//			request.setAttribute("payableaudit", payableaudit);
			

			String paymentlogCondition = " where pl.makeid like '"+userid+"%' and pl.isaudit=0 ";
			String paymentlogTable="PaymentLog pl ";
			int paymentlogaudit =DbUtil.getRecordCount(pagesize, paymentlogCondition, paymentlogTable);
			
			request.setAttribute("paymentlogaudit", paymentlogaudit);
			
			//String settlementCondition = " where  s.isratify=0 ";
			//String settlementTable="Settlement s ";
			//int settlementratify =DbUtil.getRecordCount(pagesize, settlementCondition, settlementTable);
			
			//String financewheresql=" where approveid="+userid+" and approve=0 ";
	    	
	    	//int receivable=DbUtil.getRecordCount(pagesize, financewheresql, "ReceivableApprove");
	    	//int incomelog=DbUtil.getRecordCount(pagesize, financewheresql, "IncomeLogApprove");
	    	//int settlement=DbUtil.getRecordCount(pagesize, financewheresql, "SettlementApprove");
	    	//int payable=DbUtil.getRecordCount(pagesize, financewheresql, "PayableApprove");
	    	//int paymentlog=DbUtil.getRecordCount(pagesize, financewheresql, "PaymentLogApprove");

	    	//request.setAttribute("receivable", receivable);
	    	//request.setAttribute("incomelog", incomelog);
	    	//request.setAttribute("settlement", settlement);
	    	//request.setAttribute("payable", payable);
	    	//request.setAttribute("paymentlog", paymentlog);
			
			//request.setAttribute("settlementratify", settlementratify);
	    	
			return mapping.findForward("salesawake");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
