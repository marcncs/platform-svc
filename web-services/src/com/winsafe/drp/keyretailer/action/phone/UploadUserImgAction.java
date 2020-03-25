package com.winsafe.drp.keyretailer.action.phone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ImgUploadForm;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.DateUtil;

public class UploadUserImgAction extends BaseAction {

	private Logger logger = Logger.getLogger(UploadUserImgAction.class);
	private AppUsers appUsers = new AppUsers();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("Username");
		// 判断用户是否存在
		Users loginUsers = appUsers.getUsers(username);
		try {
			// 将ActionForm强制转换成ImgUploadForm（该对象中包含一个FormFile类型的属性，即上传文件）
			ImgUploadForm mf = (ImgUploadForm) form;
			FormFile imgFile = (FormFile) mf.getIdcodefile();
			String fileName = imgFile.getFileName();
			String realPath = request.getSession().getServletContext().getRealPath("/");
			
			if (fileName != null && !fileName.equals("") ) {
				InputStream fis = imgFile.getInputStream();
				String realpathname = "/upload/imglog/" +loginUsers.getLoginname()+"_"+System.currentTimeMillis()+ fileName.substring(fileName.indexOf('.'));
				File dirFile = new File(realPath + "/upload/imglog/");
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				OutputStream fos = new FileOutputStream(realPath + realpathname);
				byte[] buffer = new byte[1048576];
				int bytesRead = 0;
				while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}
				fos.close();
				fis.close();
				String fullurl = request.getRequestURL().toString();
				String pojectname = request.getContextPath();
				String realpathnamexx = fullurl.substring(0, fullurl.indexOf(pojectname))+ pojectname +realpathname;
				loginUsers.setImgurl(realpathname);
				appUsers.updUsers(loginUsers);
				Map map = new HashMap();
				map.put("imgurl", realpathnamexx);
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS,
						Constants.CODE_SUCCESS_MSG, map);
			} else {
				ResponseUtil.writeJsonMsg(response, "1", "上传头像失败,请重试", "");
			}
		} catch (Exception e) {
			logger.error("", e);
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
		InputStream fis = idcodefile.getInputStream();

		String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + filerealname;

		String uploadPath = Constants.IDCODE_UPLOAD_PATH[billsort]
				+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
		String uploadBakPath = "../" + Constants.IDCODE_UPLOAD_PATH[billsort]
				+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";

		File dirFile = new File(realPath + uploadPath);
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
		fis.close();
		return fileAddress;
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
