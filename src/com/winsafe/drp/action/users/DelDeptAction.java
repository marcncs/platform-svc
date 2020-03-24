package com.winsafe.drp.action.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.Dept;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.DeptService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;

public class DelDeptAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			
			
			
			
			UsersService us = new UsersService();
			List ulist = us.getUsersByDept(id);
			if ( ulist!=null && !ulist.isEmpty() ){
				request.setAttribute("result", "该部门已被使用，不能删除！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			DeptService ad = new DeptService();
			Dept u = ad.getDeptByID(id);
			
	
			ad.delDeptByID(id);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "机构设置>>删除部门,编号：" + id, u);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
}
