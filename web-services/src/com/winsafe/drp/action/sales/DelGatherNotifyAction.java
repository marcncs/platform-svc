package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppGatherNotify;
import com.winsafe.drp.dao.GatherNotify;

public class DelGatherNotifyAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			Long id = Long.valueOf(request.getParameter("id"));
			AppGatherNotify aso = new AppGatherNotify();
			GatherNotify so = aso.getGatherNotifyByID(id);

			if (so.getIsendcase() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", "databases.del.success");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			String upd = aso.delGatherNotify(id);
			String result = "";

//			if (upd.equals("s")) {
//				result = "databases.del.success";
//			} else {
//				result = "databases.del.fail";
//			}
			request.setAttribute("result", "databases.del.success");
			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "删除打款通知");

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
