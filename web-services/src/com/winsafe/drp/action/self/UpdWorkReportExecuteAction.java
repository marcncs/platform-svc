package com.winsafe.drp.action.self;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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

public class UpdWorkReportExecuteAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		ValidateWorkReport vw = (ValidateWorkReport) form;
		AppWorkReport awr = new AppWorkReport();
		WorkReport wr = awr.getWorkReportByID(vw.getId());
		WorkReport oldwr = (WorkReport) BeanUtils.cloneBean(wr);
		try {
			wr.setReportcontent(vw.getReportcontent());
			wr.setReportsort(vw.getReportsort());
			
			
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
					String saveFileName = firstName	+ sDateTime + extName;

					OutputStream fos = new FileOutputStream(filePath + "/doc/"
							+ saveFileName);
					String fileAddress = "doc/" + saveFileName;
					byte[] buffer = new byte[8192];
					int bytesRead = 0;
					while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {

						fos.write(buffer, 0, bytesRead);
					}
					fos.close();
					fis.close();

					wr.setAffix(fileAddress);
				}
			}

			awr.updWorkReport(wr);
			
			request.getSession().setAttribute("ID", wr.getId());
			request.getSession().setAttribute("ReferType", "WorkReport");

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>修改工作报告,编号："+wr.getId(), oldwr, wr);
			return mapping.findForward("modify");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
