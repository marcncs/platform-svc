package com.winsafe.erp.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.service.UploadFileService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.metadata.ProduceFileType;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppPrimaryCode;

/**
 * Update By ryan.xi 生产数据文件上传处理
 */
public class UploadReplaceCodeAction extends Action {


	private Logger logger = Logger.getLogger(UploadReplaceCodeAction.class);

	AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
	RecordDao rDao = new RecordDao();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			UploadFileService upfs = new UploadFileService();
			UsersBean users = UserManager.getUser(request);
			
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile convertCodeFile = (FormFile) mf.getIdcodefile();
			
			//上传文件
			String result = upfs.uploadFile(convertCodeFile, users, ProduceFileType.TOLLING_REPLACE, "txt");
			if(StringUtil.isEmpty(result)) {
				request.setAttribute("result", "文件上传成功，请通过上传日志查看处理结果");
			} else {
				request.setAttribute("result", result);
			}
			
			DBUserLog.addUserLog(request,"[列表]");
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("result", "网络异常");
		}
		
		return mapping.findForward("success");
	}
	
}
