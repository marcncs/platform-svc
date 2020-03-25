package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppConsignMachin;
import com.winsafe.drp.dao.AppConsignMachinDetail;
import com.winsafe.drp.dao.ConsignMachin;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelConsignMachinAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String id = request.getParameter("ID");
			AppConsignMachin aso = new AppConsignMachin();
			ConsignMachin so = aso.getConsignMachinByID(id);

			if (so.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppConsignMachinDetail asld = new AppConsignMachinDetail();
			aso.delConsignMachin(id);			
			asld.delConsignMachinDetail(id);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);

			DBUserLog.addUserLog(userid, 3, "委外加工>>删除委外加工单,编号:" + id, so);
	
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
