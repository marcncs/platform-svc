package com.winsafe.sap.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.action.form.SapUploadForm;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.service.SapFileUploadService;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.MD5BigFileUtil;

public class UploadSapOrderAction extends BaseAction {
	private static Logger logger = Logger.getLogger(UploadSapOrderAction.class);

	SapFileUploadService sapFileUploadService = new SapFileUploadService();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		boolean hasError = false;
		String resultMsg = "";
		
		try {
			SapUploadForm sapUploadFile = (SapUploadForm) form;
			//检查是否选择了工厂
			if(StringUtil.isEmpty(sapUploadFile.getPlantType()) && !hasError) {
				hasError = true;
				resultMsg = "请选择上传文件类型";
			}
			//检查上传的文件
			FormFile sapFile = (FormFile) sapUploadFile.getSapFile();
			if (sapFile != null && !sapFile.equals("") && !hasError && sapFile.getContentType() != null) {
				if (!(sapFile.getFileName().toLowerCase().indexOf("xml") >= 0)) {
					hasError = true;
					resultMsg = "文件上传失败,文件后缀名不正确!";
				}
			} else {
				hasError = true;
				resultMsg = "文件未上传";
			}
			//检查是否已上传过
			String md5 = MD5BigFileUtil.md5(sapFile.getInputStream());
			if(sapFileUploadService.isFileAlreadyExists(md5)) {
				hasError = true;
				resultMsg = "上传失败，已上传过相同的文件";
			}
			
			DBUserLog.addUserLog(request, "文件：" + sapFile.getFileName());
			
			if(hasError) {
				request.setAttribute("result", resultMsg);
				return mapping.findForward("failure");
			} else {
				SapFileType fileType = SapFileType.parse(sapUploadFile.getPlantType());
				String savePath = FileUploadUtil.getSapSaveFilePath() + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/" + fileType.getSapName() + "/";
		    	String saveName = DateUtil.getCurrentDateTimeString() + "_" + sapUploadFile.getPlantCode() + "_" + sapUploadFile.getPlantName()+ "_" + sapFile.getFileName().replace("_", "");
				sapFileUploadService.processSapFile(sapFile.getInputStream(), sapFile.getFileName(), fileType, savePath, saveName,  users, md5);
				resultMsg = "文件上传成功， 请通过上传日志记录查看处理结果!";
				request.setAttribute("result", resultMsg);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			logger.error("文件上传失败", e);
			resultMsg = "文件上传失败!";
			request.setAttribute("result", resultMsg);
			return mapping.findForward("failure");
		}
	}
	
}
