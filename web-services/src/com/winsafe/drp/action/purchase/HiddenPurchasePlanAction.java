package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class HiddenPurchasePlanAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		
		try{
			String ppid = request.getParameter("ID");
			Integer iscomplete = 1;
			AppPurchasePlan app = new AppPurchasePlan();
			app.updIsComplete(ppid,iscomplete);
			
			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,2,"采购管理>>隐藏采购计划,编号："+ppid);
			
			return mapping.findForward("hidden");
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
