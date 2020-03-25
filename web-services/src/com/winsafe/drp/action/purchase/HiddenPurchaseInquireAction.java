package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class HiddenPurchaseInquireAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		
		try{
			String ppid = request.getParameter("ID");
			Integer iscomplete = 1;
			AppPurchaseInquire app = new AppPurchaseInquire();
			app.updIsComplete(ppid,iscomplete);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//DBUserLog.addUserLog(userid,"隐藏采购询价");
			
			return mapping.findForward("hidden");
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
