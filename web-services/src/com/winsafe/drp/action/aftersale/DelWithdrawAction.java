package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.dao.AppWithdrawDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Withdraw;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;

public class DelWithdrawAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String id = request.getParameter("id");			
			AppWithdraw aso = new AppWithdraw();
			AppWithdrawDetail appwd = new AppWithdrawDetail();
			AppIdcodeDetail apwi = new AppIdcodeDetail();
			Withdraw so= aso.getWithdrawByID(id);
			Withdraw oldso = (Withdraw)BeanUtils.cloneBean(so);
			if(so.getIsaudit()==1){
	          	 String result = "databases.record.nodel";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			 aso.delWithdraw(id);
			 appwd.delWithdrawDetailByWID(id);
			 apwi.delIdcodeDetailByBillid(id);
			 WarehouseBitDafService wbds = new WarehouseBitDafService("withdraw_idcode","wid",so.getWarehouseid());
			 wbds.del(so.getId());
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid,6, "零售退货>>删除零售退货，编号:"+id, oldso); 

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		} 
		return null;
	}

}
