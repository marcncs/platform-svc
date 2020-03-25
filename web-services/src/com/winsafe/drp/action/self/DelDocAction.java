package com.winsafe.drp.action.self;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDoc;
import com.winsafe.drp.dao.Doc;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelDocAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			AppDoc at = new AppDoc();
			
			Doc doc = at.getDocByID(id);
			
			String filePath = request.getRealPath("/");
			File file = new File(filePath+doc.getRealpathname());
			at.delDoc(id);
			if(file.exists()){
				if(file.isFile()){
					file.delete();
				}
			}
			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>删除文件,编号：" + id, doc);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
