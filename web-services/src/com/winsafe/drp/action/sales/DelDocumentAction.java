package com.winsafe.drp.action.sales;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCorrelationDocument;
import com.winsafe.drp.dao.CorrelationDocument;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-10-10 下午05:55:38 www.winsafe.cn
 */
public class DelDocumentAction extends BaseAction {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			
			initdata(request);
			Integer id = Integer.valueOf(request.getParameter("ID"));

			AppCorrelationDocument app = new AppCorrelationDocument();
			CorrelationDocument cDocument = app.getCorrelationDocument(id);
			String filePath = request.getRealPath("/");
			File file = new File(filePath+cDocument.getRealpathname());
			
			app.Del(cDocument);
			if(file.exists()){
				if(file.isFile()){
					file.delete();
				}
			}

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 5, "删除相关文档,编号:" + id, cDocument);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
