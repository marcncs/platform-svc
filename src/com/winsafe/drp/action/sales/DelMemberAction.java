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

public class DelMemberAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String cid = request.getParameter("CID");
			AppCustomer ac = new AppCustomer();
			Customer cus = ac.getCustomer(cid);
			 ac.updCustomerToDel(cid);
			
			request.setAttribute("result", "databases.del.success");
			
			initdata(request);
			DBUserLog.addUserLog(userid, 5,"会员/积分管理>>删除会员资料,编号："+cid,cus);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
