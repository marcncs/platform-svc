package com.winsafe.drp.action.machin;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm; 
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.UploadCovertCodeForm;
import com.winsafe.drp.service.UploadFileService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.erp.metadata.ProduceFileType;
import com.winsafe.hbm.util.StringUtil;

/**
 * Update By ryan.xi 暗码上传处理
 */
public class UploadProduceFileAction extends BaseAction {


	private Logger logger = Logger.getLogger(UploadProduceFileAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		initdata(request);
		try {
			
			UploadFileService upfs = new UploadFileService();
			
			UploadCovertCodeForm mf = (UploadCovertCodeForm) form;
			FormFile convertCodeFile = (FormFile) mf.getFilestream();
			
			//上传文件
			String result = upfs.uploadFile(convertCodeFile, users, ProduceFileType.HZ_PLANT, "zip");
			if(StringUtil.isEmpty(result)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS,
				Constants.CODE_SUCCESS_MSG);
			} else {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, result);
			}
			
		} catch (Exception e) {
			logger.error("文件上传发生异常：",e);
			try {
				ResponseUtil.writeJsonMsg(response, "-2", "失败");
			} catch (Exception e1) {
				logger.error(e1);
			} 
		}
		return null;
	}
	
	
}
