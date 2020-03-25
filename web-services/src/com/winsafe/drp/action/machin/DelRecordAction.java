package com.winsafe.drp.action.machin;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.UploadPrLog;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-10-14 上午10:19:43 www.winsafe.cn
 */
public class DelRecordAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			Integer id = Integer.valueOf(request.getParameter("ID"));
			RecordDao appRecord = new RecordDao();
			UploadPrLog uplog = appRecord.getRecordById(id);
			File file = new File(request.getRealPath("/")+uplog.getFilepath());
			if(file.exists()){
				if(file.isFile()){
					file.delete();
				}
			}
			appRecord.del(uplog);
			 
			DBUserLog.addUserLog(request,"编号："+id,uplog);
			request.setAttribute("result", "databases.del.success");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "databases.del.fail");
		}
		return mapping.findForward("success");
	}
}
