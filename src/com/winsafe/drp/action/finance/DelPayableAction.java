package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.util.DBUserLog;

public class DelPayableAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String pid = request.getParameter("PID");
			AppPayable apl = new AppPayable();
			Payable pl = apl.getPayableByID(pid);

			
			// AppPayableObject apo = new AppPayableObject();
			// String rupd = apo.removePayableSum(pl.getPoid(),
			// pl.getPayablesum());

			apl.delPayable(pid);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 9, "应付款管理>>删除应付款,编号：" + pid, pl);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
