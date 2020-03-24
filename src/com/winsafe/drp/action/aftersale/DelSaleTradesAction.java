package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.dao.AppSaleTradesDetail;
import com.winsafe.drp.dao.SaleTrades;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;

public class DelSaleTradesAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
		
			String id = request.getParameter("id");			
			AppSaleTrades aso = new AppSaleTrades();
			AppSaleTradesDetail appstd = new AppSaleTradesDetail();
			AppIdcodeDetail appwi = new AppIdcodeDetail();
			SaleTrades so= aso.getSaleTradesByID(id);
			SaleTrades oldso = (SaleTrades)BeanUtils.cloneBean(so);
			if(so.getIsaudit()==1){
	          	 String result = "databases.record.nodel";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			if ( so.getIsendcase() ==1 ){
				 String result = "databases.record.nodel";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			 aso.delSaleTrades(id);
			 appstd.delSaleTradesDetailByRID(id);
			 appwi.delIdcodeDetailByBillid(id);
			WarehouseBitDafService wbds = new WarehouseBitDafService("sale_trades_idcode","stid",so.getWarehouseinid());
			wbds.del(so.getId());
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid,6,"零售换货>>删除零售换货,编号："+id,oldso); 

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
