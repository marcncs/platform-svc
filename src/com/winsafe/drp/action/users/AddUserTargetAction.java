package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.server.AddUserTargetService;
import com.winsafe.drp.util.DBUserLog;

public class AddUserTargetAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			AddUserTargetService us = new AddUserTargetService();
			String targettype = request.getParameter("TargetType");
			//主管销售指标
        	if(targettype==null || "".equals(targettype) || "0".equals(targettype)){
        		targettype = "0"; 
        		us.dealAddUserTarget(request, targettype);
        	}
        	//主管有效网点指标
        	else if("1".equals(targettype)){
        		us.dealAddUserTarget(request, targettype);
        	}
        	//经销商指标、网点指标
        	else if("2".equals(targettype) || "3".equals(targettype)){
        		us.dealAddOrganTarget(request, targettype);
        		//utList = us.dealUser(request,targettype);
        	}
        	//办事处、大区
        	else if("4".equals(targettype) || "5".equals(targettype)){
        		us.dealAddRegionTarget(request, targettype);
        	}
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog( 11, "对象目标设置>>新增对象目标");

			return mapping.findForward("addresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
		}

		return mapping.getInputForward();
	}
}
