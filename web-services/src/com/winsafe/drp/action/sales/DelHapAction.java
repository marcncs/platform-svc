package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppHap;
import com.winsafe.drp.dao.Hap;
import com.winsafe.drp.util.DBUserLog;

public class DelHapAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			
			initdata(request);
			Integer id = Integer.valueOf(request.getParameter("ID"));
			AppHap ah = new AppHap();
			Hap hap = ah.getHapByID(id);
			ah.delHap(id);
			
			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 5,"会员/积分管理>>删除销售机会,编号:" + id,hap);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
