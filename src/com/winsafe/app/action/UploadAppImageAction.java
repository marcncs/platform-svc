package com.winsafe.app.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.app.action.form.AppUploadForm;
import com.winsafe.app.dao.AppUpdateDao;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.util.FileUploadUtil;

public class UploadAppImageAction extends Action {
	private Logger logger = Logger.getLogger(UploadAppImageAction.class);

	private AppUpdateDao appUpdateDao = new AppUpdateDao();
	private AppBaseResource appBr = new AppBaseResource();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String realPath = request.getRealPath("/");
			String path = "images\\CN\\";
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			StringBuffer resultMsg = new StringBuffer();
			boolean hasError = false;
			
			AppUploadForm appUploadForm = (AppUploadForm) form;
			
			FormFile appFile = (FormFile) appUploadForm.getAppFile();
			if (appFile != null && !appFile.equals("") && !hasError && appFile.getContentType() != null) {
				if (!(appFile.getFileName().toLowerCase().indexOf("png") >= 0)
						&& !(appFile.getFileName().toLowerCase().indexOf("jpg") >= 0)
						&& !(appFile.getFileName().toLowerCase().indexOf("jpeg") >= 0)
						&& !(appFile.getFileName().toLowerCase().indexOf("gif") >= 0)) {
					hasError = true;
					resultMsg.append("文件后缀名不正确");
					
				}
			}
			if(!hasError) {
				// 保存文件
				String fileName = appFile.getFileName();
				String savePath = realPath + path;
				FileUploadUtil.saveUplodedFile(appFile.getInputStream(), savePath, fileName);
				BaseResource pImage = appBr.getBaseResourceValue("productPicture", 1);
				if(pImage == null) {
					Long brid = Long.valueOf(MakeCode.getExcIDByRandomTableName("base_resource",0,""));
					pImage = new BaseResource();
					pImage.setId(brid);
					pImage.setTagname("productPicture");
					pImage.setTagsubkey(1);
					pImage.setTagsubvalue(appFile.getFileName());
					appBr.addBaseResource(pImage);
				} else {
					pImage.setTagsubvalue(appFile.getFileName());
					appBr.updBaseResource(pImage);
				}
				resultMsg.append("产品盾牌图片上传成功");
			} 
			request.setAttribute("result", resultMsg);
			
			DBUserLog.addUserLog(request, "上传产品盾牌图片");
		} catch (Exception e) {
			logger.error("",e);
			request.setAttribute("result", "产品盾牌图片上传失败");
		}
		
		return mapping.findForward("success");
	}
}
