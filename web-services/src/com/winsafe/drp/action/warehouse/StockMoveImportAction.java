package com.winsafe.drp.action.warehouse;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.exception.CustomException;
import com.winsafe.common.util.FileUtil;
import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.server.StockMoveImportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ReturnPage;
import com.winsafe.hbm.util.DateUtil;

/**
 * 
 * @Title: StockMoveImportAction.java
 * @Description: 处理转仓文件导入
 * @author: wenping
 * @CreateTime: Apr 24, 2012 1:30:17 PM
 * @version:
 */
public class StockMoveImportAction extends BaseAction {

	@SuppressWarnings("deprecation")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			super.initdata(request);

			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile file = (FormFile) mf.getIdcodefile();
			// 文件名
			String filerealname = "";
			// 备份上传文件路径
			String fileAddress = "";
			// 备份上传文件路径2
			String fileAddressBak = "";
			String realPath = request.getRealPath("/");

			/*
			 * 判断文件格式
			 */
			// 如果上传文件不为空，则在本地备份上传的文件
			if (file == null) {
				throw new CustomException("文件不存在！");
			}
			filerealname = file.getFileName();

			if (!StringUtil.isEmpty(filerealname) && filerealname.endsWith(".xls")) {
				// do nothing;
			} else {
				throw new CustomException("文件类型不正确！");
			}

			/*
			 * 备份文件
			 */
			// 如果上传的文件类型正确，则在本地备份
			InputStream fis = file.getInputStream();
			String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + filerealname;
			String uploadPath = "/upload/StockMove/" + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
			String uploadBakPath = "../upload/StockMove/" + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";

			FileUtil.createDir(realPath + uploadPath);
			FileUtil.createDir(realPath + uploadBakPath);

			// 保存原文件(2处备份)
			fileAddress = realPath + uploadPath + saveFileName;
			fileAddressBak = realPath + uploadBakPath + saveFileName;
			FileUtil.copyFile(fis, fileAddress, fileAddressBak);

			/*
			 * 处理文件
			 */
			StockMoveImportService service = new StockMoveImportService();
			String result = service.dealFile(fileAddress, users);

			DBUserLog.addUserLog(userid, 4, "渠道管理>>转仓导入");
			return ReturnPage.getReturn(request, "1", result, false);
		} catch (CustomException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
