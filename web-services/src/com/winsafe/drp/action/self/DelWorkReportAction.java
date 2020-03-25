package com.winsafe.drp.action.self;

import java.io.File;

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
import com.winsafe.drp.util.DBUserLog;

public class DelWorkReportAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			AppWorkReport at = new AppWorkReport();
			AppWorkReportApprove ate = new AppWorkReportApprove();
			WorkReport wr  = at.getWorkReportByID(id);
			if(!wr.getMakeid().equals(userid) && userid.intValue() !=1){
				request.setAttribute("result", "databases.del.nosuccess");
				return mapping.findForward("del");
			}
			String filePath = request.getRealPath("/");
			File file = new File(filePath+wr.getAffix());
			
			
			ate.delWorkReportApproveByReportID(id);
			
			at.delWorkReport(id);
			if(file.exists()){
				if(file.isFile()){
					file.delete();
				}
			}
			
			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>删除工作报告,编号：" + id,wr);
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return new ActionForward(mapping.getInput());
	}
}
