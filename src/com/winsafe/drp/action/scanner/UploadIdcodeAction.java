package com.winsafe.drp.action.scanner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.aws.util.S3Util;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.IdcodeUpload;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.util.MD5BigFileUtil;

public class UploadIdcodeAction extends BaseAction {

	private Logger logger = Logger.getLogger(UploadIdcodeAction.class);

	@SuppressWarnings("deprecation")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		users = UserManager.getUser(request);
		AppUsers au = new AppUsers();
		AppIdcodeUpload app = new AppIdcodeUpload();

		// String scannerId = request.getParameter("scannerid");
		String username = request.getParameter("Username");
		if (users == null) {
			users = UserManager.getBeanFromUsers(au.getUsers(username));
		}

		try {
			// 将ActionForm强制转换成IdcodeUploadForm（该对象中包含一个FormFile类型的属性，即上传文件）
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			String realPath = request.getRealPath("/");
			Integer billsort = mf.getBillsort();

			if (checkFile(idcodefile)) {
				
				//检查是否已上传过
				String md5 = MD5BigFileUtil.md5(idcodefile.getInputStream());
				String fileAddress = backFile(idcodefile, billsort, realPath);
				IdcodeUpload iu = new IdcodeUpload();
				String iuId = MakeCode.getExcIDByRandomTableName("idcode_upload", 0, "");
				iu.setMakeorganid(users.getMakeorganid());
				iu.setMakedeptid(users.getMakedeptid());
				iu.setMakeid(users.getUserid());
				iu.setMakeorganid(users.getMakeorganid());
				iu.setMakedeptid(users.getMakedeptid());
				iu.setMakeid(users.getUserid());
				iu.setId(Integer.valueOf(iuId));
				iu.setFilename(idcodefile.getFileName());
				iu.setBillsort(mf.getBillsort());
				iu.setIsdeal(0);
				iu.setValinum(0);
				iu.setFailnum(0);
				iu.setFilepath(fileAddress);
				iu.setFailfilepath("");
				iu.setMakedate(DateUtil.getCurrentDate());
				iu.setIsupload(0);
				iu.setUpusername(users.getLoginname());
				iu.setPhysicalpath(realPath);
				iu.setIsticket(getIsTicket(mf.getBillsort())); // 1为有单 0为无单
				iu.setFileHashCode(md5);
				app.addIdcodeUpload(iu);
				// 返回信息
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS,
						Constants.CODE_SUCCESS_MSG, "");
			} else {
				ResponseUtil.writeJsonMsg(response, "-4", "上传文件失败,请重试", "");
			}
		} catch (Exception e) {
			WfLogger.error("", e);
		}
		return null;
	}

	/**
	 * 检查文件是否正确
	 */
	private boolean checkFile(FormFile idcodefile) {
		if (idcodefile == null) {
			return false;
		}
		if (idcodefile.getFileName() == null) {
			return false;
		}
		if (!idcodefile.getFileName().toUpperCase().endsWith(".TXT")) {
			return false;
		}
		if (idcodefile.getContentType() == null) {
			return false;
		}
		if (idcodefile.getContentType().indexOf("text") < 0) {
			return false;
		}
		return true;
	}

	/**
	 * 备份文件
	 */
	private String backFile(FormFile idcodefile, int billsort, String realPath) throws Exception {
		String filerealname = idcodefile.getFileName();
		//InputStream fis = idcodefile.getInputStream();

		String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + filerealname;

		String uploadPath = Constants.IDCODE_UPLOAD_PATH[billsort]
				+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM");
		/*String uploadBakPath = "../" + Constants.IDCODE_UPLOAD_PATH[billsort]
				+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";*/

		S3Util.uploadToS3(idcodefile.getInputStream(), uploadPath, saveFileName);
		
		/*File dirFile = new File(realPath + uploadPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File dirFile2 = new File(realPath + uploadBakPath);
		if (!dirFile2.exists()) {
			dirFile2.mkdirs();
		}
		// 保存原文件(2处备份)
		// 备份上传文件路径
		String fileAddress = uploadPath + saveFileName;
		// 备份上传文件路径2
		String fileAddressBak = uploadBakPath + saveFileName;
		OutputStream fos = new FileOutputStream(realPath + fileAddress);
		OutputStream fos2 = new FileOutputStream(realPath + fileAddressBak);
		byte[] buffer = new byte[2048];
		int bytesRead = 0;
		while ((bytesRead = fis.read(buffer, 0, 2048)) != -1) {
			fos.write(buffer, 0, bytesRead);
			fos2.write(buffer, 0, bytesRead);
		}
		fos.close();
		fos2.close();
		fis.close();*/
		return uploadPath +"/"+ saveFileName;
	}

	// 根据billsort获取有单还是无单
	private Integer getIsTicket(Integer billSort) {
		// 有单
		Set<Integer> haveTicketSet = new HashSet<Integer>();
		Collections.addAll(haveTicketSet, Constants.HAVE_TICKET);
		if (haveTicketSet.contains(billSort)) {
			return 1;
		}
		// 无单
		Set<Integer> noTicketSet = new HashSet<Integer>();
		Collections.addAll(noTicketSet, Constants.NO_TICKET);
		if (noTicketSet.contains(billSort)) {
			return 0;
		}
		return null;

	}

}
