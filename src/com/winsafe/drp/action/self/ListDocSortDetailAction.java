package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDocSort;
import com.winsafe.drp.dao.DocSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class ListDocSortDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strid = request.getParameter("ID");
		Integer id = Integer.valueOf(strid);
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DocSort pbs = new DocSort();
			AppDocSort apbs = new AppDocSort();
			pbs = apbs.getDocSortByID(id);
			if(!pbs.getUserid().equals(userid)){
				request.setAttribute("result", "修改失败!您没有权限修改此文件类型!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			request.setAttribute("pbs", pbs);
			
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>列表文件柜详情");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
