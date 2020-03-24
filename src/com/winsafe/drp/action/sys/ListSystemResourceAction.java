package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppSystemResource;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class ListSystemResourceAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		try{
			String tagName = request.getParameter("TagName");

			AppSystemResource abr = new AppSystemResource();

			List apls = null;
			if(!StringUtil.isEmpty(tagName)){
				apls = abr.getSystemResource(tagName);
			}else{
				apls = abr.getAllSystemResource();
			}

			UsersBean users = UserManager.getUser(request);
			int userid = users.getUserid();
			DBUserLog.addUserLog(userid,11, "基础设置>>列表资源");
			request.setAttribute("alpl",apls);
			request.setAttribute("tagName", tagName);
			return mapping.findForward("list");

	    }catch(Exception e){
	      e.printStackTrace();
	    }
		return new ActionForward(mapping.getInput());
	}
}
