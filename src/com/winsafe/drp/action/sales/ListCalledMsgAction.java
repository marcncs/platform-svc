package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.Customer;
import com.winsafe.hbm.util.Internation;

public class ListCalledMsgAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		int userid = users.getUserid();
		try {
			String telphone = request.getParameter("telphone");
			telphone=telphone.trim();
			
			
			AppCustomer ac = new AppCustomer();
			Customer customer = ac.getCustomerByMobile(telphone);
			if ( customer == null ){
				customer = ac.getCustomerByTel(telphone);
			}
			String ratename="";
			if ( customer != null ){
				ratename=Internation.getStringByKeyPositionDB("PricePolicy",
						customer.getRate());
			}
			
			request.setAttribute("telphone", telphone);
			request.setAttribute("customer", customer);
			request.setAttribute("ratename", ratename);

//			request.setAttribute("result", "databases.add.success");
//			DBUserLog.addUserLog(userid, "新增呼叫中心事件");
			return mapping.findForward("show");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
