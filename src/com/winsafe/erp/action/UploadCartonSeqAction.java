package com.winsafe.erp.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.CartonSeqUploadForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.services.DealCartonSeqService;
import com.winsafe.hbm.util.StringUtil;

/**
 * Update By yufeng.wang 后关联码文件上传及处理
 */
public class UploadCartonSeqAction extends Action {

	private Logger logger = Logger.getLogger(UploadCartonSeqAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			DealCartonSeqService upfs = new DealCartonSeqService();
			
			CartonSeqUploadForm csf = (CartonSeqUploadForm) form;
			FormFile csFile = (FormFile) csf.getCsfile();
			
			UsersBean users = UserManager.getUser(request);
			//上传文件
			String result = upfs.uploadCsFile(csFile, users, "txt", csf.getPlNo());
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
