package com.winsafe.drp.action.users;

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
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddDeptAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String deptname=request.getParameter("deptname");
		String oid = request.getParameter("oid");
        try {

        	DeptService aba=new DeptService();
        	Dept dt=new Dept();
        	dt.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("dept",0,"")));
        	dt.setDeptname(deptname);
        	dt.setOid(oid);
        	
        	aba.InsertDept(dt);

              request.setAttribute("result", "databases.add.success");
              UsersBean users = UserManager.getUser(request);
              Integer userid = users.getUserid();
              DBUserLog.addUserLog(userid,11,"机构设置>>部门设置>>新增部门,编号："+dt.getId());
           	 return mapping.findForward("addresult");
        	
        } catch (Exception e) {
          	 e.printStackTrace();
           } finally {
           }
		return mapping.getInputForward();
	}

}
