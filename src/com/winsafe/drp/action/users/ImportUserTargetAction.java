package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.UsersUploadForm;
import com.winsafe.drp.server.ImportUserTargetService;

public class ImportUserTargetAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ImportUserTargetService uts = new ImportUserTargetService();
		
		int CCount = 0, ECount = 0;
		Integer[] result = null;
		String targettype = request.getParameter("TargetType");
		try {
			UsersUploadForm mf = (UsersUploadForm) form;
			FormFile usersfile = (FormFile) mf.getUsrsfile();
			
			boolean bool = false;
			if (usersfile != null && !usersfile.equals("")) {

				if (usersfile.getContentType() != null) {
					if (usersfile.getFileName().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				//主管销售指标
	        	if(targettype==null || "".equals(targettype) || "0".equals(targettype)){
	        		targettype = "0"; 
	        		result = uts.dealImportUserTarget(usersfile, targettype);
	        	}
	        	//主管有效网点指标
	        	else if("1".equals(targettype)){
	        		result = uts.dealImportUserTarget(usersfile, targettype);
	        	}
	        	//经销商指标、网点指标
	        	else if("2".equals(targettype) || "3".equals(targettype)){
	        		result = uts.dealImportOrganTarget(usersfile, targettype);;
	        	}
	        	//办事处、大区
	        	else if("4".equals(targettype) || "5".equals(targettype)){
	        		result = uts.dealImportRegionTarget(usersfile, targettype);
	        	}
	        	
	        	ECount = result[0];
	        	CCount = result[1];
			} else {
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			request.setAttribute("result", "上传主管目标资料成功,本次总共添加 :"
					+ (CCount + ECount) + "条! 成功:" + CCount + "条! 失败：" + ECount
					+ "条!" + uts.errorMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
	}
}
