package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppPurchaseTrades;
import com.winsafe.drp.dao.AppPurchaseTradesDetail;
import com.winsafe.drp.dao.PurchaseTrades;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;

public class DelPurchaseTradesAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
	
			String id = request.getParameter("id");			
			AppPurchaseTrades aso = new AppPurchaseTrades();
			AppPurchaseTradesDetail appsrd = new AppPurchaseTradesDetail();
			PurchaseTrades so= aso.getPurchaseTradesByID(id);
			PurchaseTrades oldso = (PurchaseTrades)BeanUtils.cloneBean(so);
			if(so.getIsaudit()==1){
	          	 String result = "databases.record.nodel";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			if ( so.getIsreceive() ==1 ){
				 String result = "databases.record.nodel";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			aso.delPurchaseTrades(id);
			appsrd.delPurchaseTradesDetailByPtid(id);
			AppIdcodeDetail apti = new AppIdcodeDetail();
			apti.delIdcodeDetailByBillid(id);
			WarehouseBitDafService wbds = new WarehouseBitDafService("purchase_trades_idcode","ptid",so.getWarehouseinid());
			wbds.del(so.getId());
			
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid, 2, "采购换货>>删除采购换货,编号："+id,oldso); 

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return null;
	}

}
