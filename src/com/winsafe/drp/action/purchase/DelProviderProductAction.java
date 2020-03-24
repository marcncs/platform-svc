package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelProviderProductAction extends Action{

public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response) throws Exception {

	try{

		Long id = Long.valueOf(request.getParameter("ID"));
		AppProviderProduct app = new AppProviderProduct();
		app.DelProviderProduct(id);

	      request.setAttribute("result", "databases.del.success");
	      UsersBean users = UserManager.getUser(request);
	      Integer userid = users.getUserid();
	      //DBUserLog.addUserLog(userid, 2, "供应商资料>>删除供应商存货,编号:"+id); 

	      return mapping.findForward("delresult");
	}catch(Exception e){
		e.printStackTrace();
	}finally {

	    }
	
	return mapping.getInputForward();
}
	
}
