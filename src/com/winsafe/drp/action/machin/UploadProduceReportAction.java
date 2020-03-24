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
import com.winsafe.drp.dao.UploadPrLog;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.util.FileUploadUtil;

/**
 * Update By ryan.xi 暗码上传处理
 */
public class UploadProduceReportAction extends Action {


	private Logger logger = Logger.getLogger(UploadProduceReportAction.class);

	AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
	RecordDao rDao = new RecordDao();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			StringBuffer resultMsg = new StringBuffer();
			boolean hasError = false;
			
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile convertCodeFile = (FormFile) mf.getIdcodefile();
			if (convertCodeFile != null && !convertCodeFile.equals("") && !hasError && convertCodeFile.getContentType() != null) {
				if (!(convertCodeFile.getFileName().toLowerCase().indexOf("txt") >= 0)) {
					hasError = true;
					resultMsg.append("文件后缀名不正确");
					
				}
			}
			
			if(!hasError) {
				// 保存文件
				String fileName = convertCodeFile.getFileName();
				String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + fileName;
				String savePath = FileUploadUtil.getCovertCodeFilePath() + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
				FileUploadUtil.saveUplodedFile(convertCodeFile.getInputStream(), savePath, saveFileName);
				
				
				//添加文件上传日志
				UploadPrLog record = new UploadPrLog();
				Integer id = Integer.valueOf(MakeCode
						.getExcIDByRandomTableName("Record", 0, ""));
				record.setId(id);
				record.setFilename(fileName);
				record.setFilepath(savePath + saveFileName);
				record.setMakeid(userid);
				record.setMakeorganid(users.getMakeorganid());
				record.setMakedeptid(users.getMakedeptid());
				record.setMakedate(DateUtil.getCurrentDate());
				record.setIsdeal(0);
				rDao.save(record);
				
				resultMsg.append("文件上传成功，请通过上传日志查看处理结果");
				
			} 
			request.setAttribute("result", resultMsg);
			
			DBUserLog.addUserLog(request,"[列表]");
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("result", "databases.input.fail");
		}
		
		return mapping.findForward("success");
	}
	
}
