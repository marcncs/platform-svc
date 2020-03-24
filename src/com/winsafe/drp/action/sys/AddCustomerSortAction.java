package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.dao.CustomerSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddCustomerSortAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sortname = request.getParameter("sortname");
	
		try {
			CustomerSort cs = new CustomerSort();
			cs.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("customer_sort",
					0, "")));
			cs.setSortname(sortname);

			AppCustomerSort appcs = new AppCustomerSort();

			appcs.addCustomerSort(cs);

			
			request.setAttribute("result", "databases.add.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11,"基础设置>>新增客户分类,编号:"+cs.getId());
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
