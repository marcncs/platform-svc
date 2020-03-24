package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProject;

public class DelProjectAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			Long id = Long.valueOf(request.getParameter("ID"));
			AppProject ap = new AppProject();

//			String del = ap.delProject(id);
			String result = "";

//			if (del.equals("s")) {
//				result = "databases.del.success";
//			} else {
//				result = "databases.del.fail";
//			}
			request.setAttribute("result", "databases.del.success");
			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "删除项目,编号:" + id);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
