package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppApproveFlowService;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.AppOutlayDetail;
import com.winsafe.drp.dao.Outlay;
import com.winsafe.drp.util.DBUserLog;

public class DelOutlayAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String id = request.getParameter("id");
			AppOutlay aso = new AppOutlay();
			AppOutlayDetail appstd = new AppOutlayDetail();
			Outlay so = aso.getOutlayByID(id);
			// if (so.getApprovestatus()!=0) {
			// String result = "databases.record.approvestatus";
			// request.setAttribute("result", "databases.del.success");
			// return new ActionForward("/sys/lockrecord.jsp");
			// }

			if (so.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (so.getIsendcase() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			aso.delOutlay(id);
			appstd.delOutlayByOID(id);
			AppApproveFlowService apfs = new AppApproveFlowService();
			apfs.delApproveFlowLogByBillno(id);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 9, "费用报销>>删除费用报销,编号：" + id, so);
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
