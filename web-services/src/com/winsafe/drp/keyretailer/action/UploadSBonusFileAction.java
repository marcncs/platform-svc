package com.winsafe.drp.keyretailer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.sap.util.FileUploadUtil;

public class UploadSBonusFileAction extends BaseAction {
	private static Logger logger = Logger.getLogger(UploadSBonusFileAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (idcodefile != null && !idcodefile.equals("")) {
				if (idcodefile.getContentType() != null) {
					if (idcodefile.getFileName().indexOf("doc") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				String path = request.getRealPath("/");
				FileUploadUtil.saveUplodedFile(idcodefile.getInputStream(), path+"templates\\", "积分计划协议.doc");
			} else {
				request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			request.setAttribute("result", "文件上传成功");
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("文件上传失败", e);
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			return new ActionForward("/sys/lockrecord2.jsp");
		} 
	}
	
}
