package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganTarget;
import com.winsafe.drp.dao.AppRegionTarget;
import com.winsafe.drp.dao.AppUserTarget;
import com.winsafe.drp.util.DBUserLog;

public class DelUserTargetAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer tid = Integer.valueOf(request.getParameter("tid"));
		String targettype = request.getParameter("TargetType");
		try {
			//主管销售指标
        	if(targettype==null || "".equals(targettype) || "0".equals(targettype)||"1".equals(targettype)){
        		AppUserTarget apput = new AppUserTarget();
        		apput.delUserTargetById(tid);
        	}
        	//经销商指标、网点指标
        	else if("2".equals(targettype) || "3".equals(targettype)){
        		AppOrganTarget appot = new AppOrganTarget();
        		appot.delOrganTargetById(tid);
        	}
        	//办事处、大区
        	else if("4".equals(targettype) || "5".equals(targettype)){
        		AppRegionTarget apprt = new AppRegionTarget();
        		apprt.delRegionTargetById(tid);
        	}
			
			DBUserLog.addUserLog( 11, "对象目标设置>>删除对象目标编号：" + tid);
			request.setAttribute("result", "databases.del.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
}
