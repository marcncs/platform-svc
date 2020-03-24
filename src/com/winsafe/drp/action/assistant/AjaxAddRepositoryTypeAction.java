package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRepositoryType;
import com.winsafe.drp.dao.RepositoryType;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.MakeCode;

public class AjaxAddRepositoryTypeAction  extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strparentid = request.getParameter("parentid");
		String areaname=request.getParameter("areaname");
		try{
			AppRepositoryType aa=new AppRepositoryType();
			
			String acode=aa.getAcodeByParent(strparentid);
			
		
			RepositoryType as=new RepositoryType ();
			as.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("repository_type",0,"")));
			as.setStructcode(acode);
			as.setSortname(areaname);
			
			aa.addNewRepositoryType(as);
			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid,"新增知识库类别");//日志 

		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return null;
	}

}
