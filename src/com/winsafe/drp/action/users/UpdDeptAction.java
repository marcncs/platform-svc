package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.Dept;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.DeptService;
import com.winsafe.drp.util.DBUserLog;

public class UpdDeptAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("ID"));
		String deptname = request.getParameter("deptname");

		try {

			DeptService ad = new DeptService();
			Dept dept = ad.getDeptByID(id);
			Dept olddept = (Dept)BeanUtils.cloneBean(dept);
			dept.setDeptname(deptname);
			ad.modifyDept(dept);

			
			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,11,"机构设置>>部门设置>>修改部门,编号:"+id, olddept, dept);
			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
				request.setAttribute("result", "databases.upd.fail");
		} finally {
		}
		return mapping.findForward("updResult");
	}
}
