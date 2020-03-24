package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class ActivateCustomerAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {

			String cid = request.getParameter("CID");
			AppCustomer ac = new AppCustomer();
			Customer c = ac.getCustomer(cid);

			if (c.getIsactivate() == 1) {
				String result = "databases.record.activate";
				request.setAttribute("result", "databases.upd.success");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			c.setIsactivate(1);
			c.setActivatedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			c.setActivateid(userid);
			ac.updateCustomer(c);
			
			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, "激活客户");

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
