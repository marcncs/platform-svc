package com.winsafe.drp.keyretailer.action.phone;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.app.dao.AppUpdateDao;
import com.winsafe.app.dao.AppUpdateLogDao;
import com.winsafe.app.pojo.AppUpdate;
import com.winsafe.app.pojo.AppUpdateLog;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.util.SapConfig;


public class CheckVersionAction extends BaseAction {
	private Logger logger = Logger.getLogger(CheckVersionAction.class);
	
	private AppUpdateDao appUpdateDao = new AppUpdateDao();
	private AppUpdateLogDao appUpdateLogDao = new AppUpdateLogDao();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String scannerno = request.getParameter("IMEI_number"); 
		String appName = request.getParameter("appName"); 
		String appVersion = request.getParameter("appVersion");
		String appType = request.getParameter("appType");
		try {
			if(!StringUtil.isEmpty(appName) && !StringUtil.isEmpty(appVersion)) {
				if(appUpdateDao.checkAppVersion(appName.trim(), appVersion.trim())) {
					AppUpdate appUpdate = appUpdateDao.getLatestAppUpdate(appName.trim());
					String downloadUrl = getAppUrl(request, appUpdate.getFilePath(), appUpdate.getId());
					String returnMsg = Constants.CODE_HAS_UPDATE_MSG.replace("V", appUpdate.getAppVersion()) + appUpdate.getUpdateLog();
					ResponseUtil.writeAppUpdateJsonMsg(response, Constants.CODE_HAS_UPDATE, returnMsg, downloadUrl);
					AppUpdateLog appUpdateLog = new AppUpdateLog(scannerno, appUpdate.getAppVersion(), appName, new Date());
					appUpdateLogDao.addAppUpdateLog(appUpdateLog);
				} else {
					ResponseUtil.writeAppUpdateJsonMsg(response, Constants.CODE_LATEST_VERSION, Constants.CODE_LATEST_VERSION_MSG, null);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
//		DBUserLog.addUserLog(request, scannerno);
		return null;
	}
	
	/**
	 * 获取域
	 * @param appid 
	 * @return
	 */
	public String getAppUrl(HttpServletRequest request, String filePath, long appid) {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" +request.getServerPort() + request.getContextPath() + SapConfig.getSapConfig().getProperty("downloadUrl")+filePath+"&appid="+appid;
		return basePath;
	}
}
