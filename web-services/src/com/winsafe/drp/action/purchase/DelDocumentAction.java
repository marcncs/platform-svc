package com.winsafe.drp.action.purchase;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCorrelationDocument;
import com.winsafe.drp.dao.CorrelationDocument;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

/** 
 * @author: jelli 
 * @version:2009-10-12 下午12:04:49 
 * @copyright:www.winsafe.cn
 */
public class DelDocumentAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			Integer ID =Integer.valueOf(request.getParameter("ID"));
			AppCorrelationDocument app = new AppCorrelationDocument();
			CorrelationDocument cd = app.getCorrelationDocument(ID);
			String filePath = request.getRealPath("/");
			File file = new File(filePath+cd.getRealpathname());
			
			app.Del(cd);
			if(file.exists()){
				if(file.isFile()){
					file.delete();
				}
			}
			
			DBUserLog.addUserLog(userid,2, "采购管理>>删除相关文档!,编号："+ID,cd);
			request.setAttribute("result", "databases.del.success");
			return mapping.findForward("success");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return super.execute(mapping, form, request, response);
	}

}
