package com.winsafe.drp.action.self;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppWorkReport;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.ValidateWorkReport;
import com.winsafe.drp.dao.WorkReport;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddNewWorkReportAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		ValidateWorkReport vw = (ValidateWorkReport) form;
		try {
			WorkReport wr = new WorkReport();
			wr.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"work_report", 0, "")));

			wr.setReportcontent(vw.getReportcontent());
			wr.setReportsort(vw.getReportsort());
			wr.setReferdate(new Date());
			wr.setIsrefer(Integer.valueOf("0"));
			wr.setApprovestatus(Integer.valueOf(0));
			wr.setRemark("");

			wr.setMakeid(userid);
			wr.setMakeorganid(users.getMakeorganid());
			wr.setMakedeptid(users.getMakedeptid());
			wr.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			wr.setIsrefer(1);
			// 上传图片
			FormFile documentFile = null;
			documentFile = (FormFile) vw.getAffix();
			String extName = null;
			String fileName = null;
			String filePath = request.getRealPath("/");
			if (documentFile.toString().trim() != null
					&& !documentFile.toString().trim().equals("")) {
				fileName = documentFile.getFileName().toLowerCase();
				
				extName = fileName.substring(fileName.indexOf("."), fileName
						.length());
				String firstName = fileName.substring(0, fileName.indexOf("."));
				if (extName != null) {
					InputStream fis = documentFile.getInputStream();
					String sDateTime = DateUtil.getCurrentDateTimeString();
					String saveFileName = firstName+"_"+ sDateTime + extName;

					
					OutputStream fos = new FileOutputStream(filePath + "/doc/"
							+ saveFileName);
					String fileAddress = "doc/" + saveFileName;
					byte[] buffer = new byte[8192];
					int bytesRead = 0;
					while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {

						// 把上传的文件放到服务器的指定目录下
						fos.write(buffer, 0, bytesRead);
					}
					fos.close();
					fis.close();
					// 构造product

					wr.setAffix(fileAddress);
				}
			}
			
			AppWorkReport awr = new AppWorkReport();
			awr.addNewWorkReport(wr);
			
			request.getSession().setAttribute("ID", wr.getId());
			request.getSession().setAttribute("ReferType", "WorkReport");
			DBUserLog.addUserLog(userid,0, "我的办公桌>>新增工作报告,编号："+wr.getId());
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
}
