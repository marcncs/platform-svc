package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRepositoryType;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class AjaxUpdRepositoryTypeAction  extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String acode = request.getParameter("acode");
		String areaname=request.getParameter("areaname");
		
		
		try{
			AppRepositoryType aa=new AppRepositoryType();		
			aa.upd(acode, areaname);
			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid,"修改知识库类别");//日志 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return null;
	}

}
