package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWorkReport;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WorkReport;

public class UpdWorkReportAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String strid = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		Integer id = Integer.valueOf(strid);
		try {
			AppWorkReport awr = new AppWorkReport();
			WorkReport wr = awr.getWorkReportByID(id);
			if (wr.getApprovestatus()== 1 || wr.getApprovestatus() == 2) {
				request.setAttribute("result", "databases.record.approvestatus");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			
			if(!wr.getMakeid().equals(userid)){
				request.setAttribute("result", "databases.del.nosuccess");
				return new ActionForward("/sys/lockrecord.jsp");
			}			

			request.setAttribute("id", id);
			request.setAttribute("reportsort", wr.getReportsort());
			request.setAttribute("reportcontent", wr.getReportcontent());
			request.setAttribute("affix", wr.getAffix());
			return mapping.findForward("toedit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
