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

public class ListWarehouseAwakeAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean usersBean = UserManager.getUser(request);
		Integer userid = usersBean.getUserid();
		int pagesize=1;
		try{
			String othershipmentCondition = " where  osb.isratify=0 ";
			String othershipmentTable="OtherShipmentBill osb ";
			int othershipmentratify =DbUtil.getRecordCount(pagesize, othershipmentCondition, othershipmentTable);
			String otherincomeCondition = " where  oi.isratify=0 ";
			String otherincomeTable="OtherIncome oi ";
			int otherincomeratify =DbUtil.getRecordCount(pagesize, otherincomeCondition, otherincomeTable);
			String stockmoveSCondition = " where  sm.isshipment=0 ";
			String stockmoveSTable="StockMove sm ";
			int stockmoveshipment =DbUtil.getRecordCount(pagesize, stockmoveSCondition, stockmoveSTable);
			String stockmoveRCondition = " where  sm.iscomplete=0 ";
			String stockmoveRTable="StockMove sm ";
			int stockmovecomplete =DbUtil.getRecordCount(pagesize, stockmoveRCondition, stockmoveRTable);
			String checkratifyCondition = " where  sc.isratify=0 ";
			String checkratifyTable="StockCheck sc ";
			int checkratify =DbUtil.getRecordCount(pagesize, checkratifyCondition, checkratifyTable);
			String wheresql=" where approveid="+userid+" and approve=0 ";
	    	int stuffshipment=DbUtil.getRecordCount(pagesize, wheresql, "StuffShipmentBillApprove");
	    	int othershipment=DbUtil.getRecordCount(pagesize, wheresql, "OtherShipmentBillApprove");
	    	int purchaseincome=DbUtil.getRecordCount(pagesize, wheresql, "PurchaseIncomeApprove");
	    	int productincome=DbUtil.getRecordCount(pagesize, wheresql, "ProductIncomeApprove");
	    	int otherincome=DbUtil.getRecordCount(pagesize, wheresql, "OtherIncomeApprove");
	    	int stockmove=DbUtil.getRecordCount(pagesize, wheresql, "StockMoveApprove");
	    	
	    	request.setAttribute("stuffshipment", stuffshipment);
	    	request.setAttribute("othershipment", othershipment);
	    	request.setAttribute("purchaseincome", purchaseincome);
	    	request.setAttribute("productincome", productincome);
	    	request.setAttribute("otherincome", otherincome);
	    	request.setAttribute("stockmove", stockmove);
			
			request.setAttribute("othershipmentratify", othershipmentratify);
			request.setAttribute("otherincomeratify", otherincomeratify);
			request.setAttribute("stockmoveshipment", stockmoveshipment);
			request.setAttribute("stockmovecomplete", stockmovecomplete);
			request.setAttribute("checkratify", checkratify);
			
			return mapping.findForward("warehouseawake");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
