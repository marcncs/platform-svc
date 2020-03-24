package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.service.ImportSapIdcodeServices;

public class ImportSapIdcodeAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ImportSapIdcodeAction.class);
	private ImportSapIdcodeServices sapIdcodeServices = new ImportSapIdcodeServices();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		initdata(request);
		// TTid编号
		String ttid = request.getParameter("billid");
		// 保存报错信息
		StringBuffer errMsg = new StringBuffer();
		try {
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (idcodefile != null && !idcodefile.equals("")) {
				if (idcodefile.getContentType() != null) {
					if (idcodefile.getFileName().toLowerCase().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				sapIdcodeServices.dealFile(ttid, idcodefile.getInputStream(), idcodefile.getFileName());
				errMsg.append("导入成功");
			} else {
				errMsg.append("文件格式不正确");
			}
			request.setAttribute("result", errMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("", e); 
			request.setAttribute("result", e.getMessage());
			return new ActionForward("/warehouse/importSapIdcodeResult.jsp");
//			return new ActionForward("/sys/lockrecord2.jsp");
		}
	}
}