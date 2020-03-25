package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppRepositoryType;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class AjaxDelRepositoryTypeAction  extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String dcode = request.getParameter("acode");
		String dname=request.getParameter("areaname");

		try{
			AppRepositoryType aa = new AppRepositoryType();
			AppProduct ap = new AppProduct();
			int pc = ap.getCountProductByPSID(dcode);
			
			if(pc<=0){			
			aa.del(dcode, dname);
			}
			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid,"删除知识库类别");//日志 
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
