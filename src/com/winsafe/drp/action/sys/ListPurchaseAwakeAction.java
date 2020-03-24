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

public class ListPurchaseAwakeAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean usersBean = UserManager.getUser(request);
		Integer userid = usersBean.getUserid();
		int pagesize=1;
		try{

			String planCondition = " where  pp.isratify=0 ";
			String planTable="PurchasePlan pp ";
			int purchaseplanratify =DbUtil.getRecordCount(pagesize, planCondition, planTable);

			String billCondition = " where  pb.isratify=0 ";
			String billTable="PurchaseBill pb ";
			int purchasebillratify =DbUtil.getRecordCount(pagesize, billCondition, billTable);
			
			
			String wheresql=" where approveid="+userid+" and approve=0 ";
	    	
	    	int purchaseplan =DbUtil.getRecordCount(pagesize, wheresql, "PurchasePlanApprove");
	    	int purchasebill=DbUtil.getRecordCount(pagesize, wheresql, "PurchaseBillApprove");
	    	int adsumgoods=DbUtil.getRecordCount(pagesize, wheresql, "AdsumGoodsApprove");
	    	int purchasewithdraw=DbUtil.getRecordCount(pagesize, wheresql, "PurchaseWithdrawApprove");

	    	request.setAttribute("purchaseplan", purchaseplan);
	    	request.setAttribute("purchasebill", purchasebill);
	    	request.setAttribute("purchasewithdraw", purchasewithdraw);
	    	request.setAttribute("adsumgoods", adsumgoods);
			
			request.setAttribute("purchaseplanratify", purchaseplanratify);
			request.setAttribute("purchasebillratify", purchasebillratify);
			
			return mapping.findForward("purchaseawake");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
