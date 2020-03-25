package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.Customer;
import com.winsafe.hbm.util.DateUtil;

public class CancelActivateCustomerAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {

			String cid = request.getParameter("CID");
			AppCustomer ac = new AppCustomer();
			Customer c = ac.getCustomer(cid);

			if (c.getIsactivate() == 0) {
				String result = "databases.record.noactivate";
				request.setAttribute("result", "databases.upd.success");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			c.setIsactivate(0);
			c.setActivatedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//			c.setActivateid(userid);
			ac.updateCustomer(c);
			
			request.setAttribute("result", "databases.upd.success");

//			DBUserLog.addUserLog(userid, "停用客户");

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
