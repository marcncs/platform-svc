package com.winsafe.drp.action.machin;


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
public class UploadProduceAction extends Action {


	private Logger logger = Logger.getLogger(UploadProduceAction.class);

	AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
	RecordDao rDao = new RecordDao();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			UsersBean users = UserManager.getUser(request);
			
			IdcodeUploadForm mf = (IdcodeUploadForm) form; 
			FormFile convertCodeFile = (FormFile) mf.getIdcodefile();
			UploadFileService upfs = new UploadFileService();
			//上传文件
			String result = upfs.uploadFile(convertCodeFile, users, ProduceFileType.HZ_PLANT, "zip");
			if(StringUtil.isEmpty(result)) {
				request.setAttribute("result", "上传成功,请通过日志查看处理结果");
			} else {
				request.setAttribute("result", "上传失败:"+result);
			}
			
			DBUserLog.addUserLog(request,"[列表]");
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("result", "databases.input.fail");
		}
		
		return mapping.findForward("success");
	}
	
}
