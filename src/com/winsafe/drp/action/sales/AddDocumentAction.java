package com.winsafe.drp.action.sales;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCorrelationDocument;
import com.winsafe.drp.dao.CorrelationDocument;
import com.winsafe.drp.dao.ValidateCorrelationDocument;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddDocumentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ValidateCorrelationDocument vcd = (ValidateCorrelationDocument) form;

		try {

			
			initdata(request);

			// ----------------------------
			CorrelationDocument cd = new CorrelationDocument();
			cd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"correlation_document", 0, "")));
			cd.setObjsort(vcd.getObjsort());
			cd.setCid(vcd.getCid());
			cd.setCname(vcd.getCname());
			cd.setDocumentname(vcd.getDocumentname());
			cd.setMakeorganid(users.getMakeorganid());
			cd.setMakedeptid(users.getMakedeptid());
			cd.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			cd.setMakeid(userid);

			FormFile documentFile = null;
			documentFile = (FormFile) vcd.getDoc();

			String extName = null;
			String fileName = null;
			String firstName = null;
			// String contentType = null;
			String filePath = request.getRealPath("/");
			if (documentFile != null && !documentFile.equals("")) {
				fileName = documentFile.getFileName().toLowerCase();
				firstName = fileName.substring(0, fileName.indexOf("."));
				extName = fileName.substring(fileName.indexOf("."), fileName
						.length());
				if (extName != null) {
					InputStream fis = documentFile.getInputStream();
					String sDateTime = DateUtil.getCurrentDateTimeString();
					String saveFileName = firstName+"_" + sDateTime + extName;

					
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
					

					cd.setRealpathname(fileAddress);
				}
			}
			// -----------------------------------------------------------
			AppCorrelationDocument acd = new AppCorrelationDocument();
			 acd.addCorrelationDocument(cd);
			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, "上传客户文档");

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
