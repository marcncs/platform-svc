package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppGatherNotify;
import com.winsafe.drp.dao.GatherNotify;

public class CancelEndcaseGatherNotifyAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {
			Long id = Long.valueOf(request.getParameter("id"));
			AppGatherNotify apb = new AppGatherNotify();
			GatherNotify pb = apb.getGatherNotifyByID(id);

			if (pb.getIsendcase() == 0) {
				String result = "databases.record.nooperator";
				request.setAttribute("result", "databases.audit.success");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			String upd = apb.updIsEndcase(id, userid, 0);
			String result = "";

//			if (upd.equals("s")) {
//				result = "databases.audit.success";
//			} else {
//				result = "databases.audit.fail";
//			}
			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "取消结案打款通知,编号：" + id);
			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
