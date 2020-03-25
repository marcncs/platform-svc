package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganPrice;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CopyOrganPriceAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		String tagoid = request.getParameter("tagoid");
		try {
			if ( id.equals("") ){
		    	request.setAttribute("result", "databases.upd.fail");
		    	return new ActionForward("/sys/lockrecord.jsp");
			}
			if ( tagoid.equals("") ){
		    	request.setAttribute("result", "databases.upd.fail");
		    	return new ActionForward("/sys/lockrecord.jsp");
			}
			AppOrganPrice ao = new AppOrganPrice();
			
			ao.delOrganPriceByOID(tagoid);
			
			ao.copyOrganPrice(id, tagoid);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,11, "机构设置>>克隆机构价格");

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapping.findForward("updResult");
	}

}
