package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWorkReport;
import com.winsafe.drp.dao.AppWorkReportApprove;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WorkReport;
import com.winsafe.drp.dao.WorkReportApproveForm;
import com.winsafe.drp.server.UsersService;
import com.winsafe.hbm.util.HtmlSelect;

public class WorkReportDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		String strid = request.getParameter("ID");
		Integer id = Integer.valueOf(strid);
		try {
			AppWorkReport awr = new AppWorkReport();
			WorkReport wr = new WorkReport();
			UsersService au = new UsersService();

			wr = awr.getWorkReportByID(id);

			AppWorkReportApprove awre = new AppWorkReportApprove();
			
			List wrels = awre.getWorkReportApprove(id);
			ArrayList alls = new ArrayList();
			for (int w = 0; w < wrels.size(); w++) {
				WorkReportApproveForm wref = new WorkReportApproveForm();
				Object[] o = (Object[]) wrels.get(w);
				if (wr.getMakeid().equals(Integer.valueOf(o[1].toString()))) {
					continue;
				}
				wref.setApprove(HtmlSelect.getNameByOrder(request, "IsApprove",
						Integer.parseInt(o[0].toString())));
				wref.setApproveuser(au.getUsersName(Integer.valueOf(o[1].toString())));
				wref.setApprovecontent(o[2] == null ? "" : o[2].toString());
				
				if(userid.equals(Integer.valueOf(o[1].toString()))){
					request.setAttribute("isApprove", Integer.parseInt(o[0].toString()));
				}
				alls.add(wref);
			}
			request.setAttribute("content", wr.getReportcontent().replace("\n", "<br>"));
			request.setAttribute("tpid", id);
			request.setAttribute("userid", userid);
			request.setAttribute("alls", alls);
			request.setAttribute("wr", wr);

			return mapping.findForward("reportdetail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
