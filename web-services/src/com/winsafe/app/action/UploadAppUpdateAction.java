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
import com.winsafe.app.pojo.AppUpdate;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.metadata.AppType;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.SapConfig;

public class UploadAppUpdateAction extends Action {
	private Logger logger = Logger.getLogger(UploadAppUpdateAction.class);

	private AppUpdateDao appUpdateDao = new AppUpdateDao();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			StringBuffer resultMsg = new StringBuffer();
			boolean hasError = false;
			
			AppUploadForm appUploadForm = (AppUploadForm) form;
			if(StringUtil.isEmpty(appUploadForm.getPublishName())) {
				hasError = true;
				resultMsg.append("发布名称未提供");
			}
			if(StringUtil.isEmpty(appUploadForm.getAppVersion())) {
				hasError = true;
				resultMsg.append("版本号未提供");
			}
			FormFile appFile = (FormFile) appUploadForm.getAppFile();
			if (appFile != null && !appFile.equals("") && !hasError && appFile.getContentType() != null) {
				if (!(appFile.getFileName().toLowerCase().indexOf("apk") >= 0)) {
					hasError = true;
					resultMsg.append("文件后缀名不正确");
					
				}
			}
			if(!hasError) {
				// 保存文件
				String fileName = appFile.getFileName();
				String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + fileName;
				String savePath = FileUploadUtil.getAppUpdateFilePath();
				FileUploadUtil.saveUplodedFile(appFile.getInputStream(), savePath, saveFileName);
				
				AppUpdate appUpdate = new AppUpdate();
				appUpdate.setAppName(fileName);
				appUpdate.setAppVersion(appUploadForm.getAppVersion());
				appUpdate.setUpdateLog(appUploadForm.getUpdateLog());
				appUpdate.setPublishName(appUploadForm.getPublishName());
				appUpdate.setPublishDate(DateUtil.getCurrentDate());
				appUpdate.setPublisher(userid);
				appUpdate.setFilePath(savePath+saveFileName);
				appUpdate.setDownloadCount(0);
				appUpdateDao.addAppUpdate(appUpdate);
				resultMsg.append("APP更新文件上传成功");
			} 
			request.setAttribute("result", resultMsg);
			
			DBUserLog.addUserLog(request, "上传APP文件");
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("result", "APP更新文件上传失败");
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * 获取域
	 * @return
	 */
	public String getAppUrl(HttpServletRequest request, String filePath) {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" +request.getServerPort() + "/" + request.getContextPath() + SapConfig.getSapConfig().getProperty("downloadUrl")+filePath;
		return basePath;
	}
}
