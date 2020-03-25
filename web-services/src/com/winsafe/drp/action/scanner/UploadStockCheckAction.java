package com.winsafe.drp.action.scanner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppBarcodeUpload;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.BarcodeUpload;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UploadStockCheckAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsersBean users = UserManager.getUser(request);
		response.setCharacterEncoding("utf-8");
		AppBaseResource appBS = new AppBaseResource();
		AppUsers appUsers = new AppUsers();
		PrintWriter writer = response.getWriter();
		String username = request.getParameter("Username"); // 登陆名
		String password = request.getParameter("Password"); // 密码
		String filestream = request.getParameter("Filestream"); // 文件

		try {

			// 将ActionForm强制转换成IdcodeUploadForm（该对象中包含一个FormFile类型的属性，即条码文件）
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			// 文件扩展名
			String extName = null;
			// 文件名
			String filerealname = "";
			// 备份上传文件路径
			String fileAddress = "";
			// 备份上传文件路径2
			String fileAddressBak = "";
			String realPath = request.getSession().getServletContext().getRealPath("/");
			// IdcodeUpload的ID
			String iuId = "";
			Integer billsort = mf.getBillsort();

			// 如果上传文件不为空
			if (idcodefile != null && !idcodefile.equals("")) {

				filerealname = idcodefile.getFileName();
				// 如果上传的文件类型为TXT，则设置extName值
				if (idcodefile.getContentType() != null) {
					// if (idcodefile.getContentType().indexOf("text") >= 0)
					// {
					extName = ".txt";
					// }
				}
				// 如果上传的文件类型正确，则在本地备份
				if (extName != null) {
					iuId = MakeCode.getExcIDByRandomTableName("idcode_upload", 0, "");
					InputStream fis = idcodefile.getInputStream();
					String saveFileName = iuId+"_"+DateUtil.getCurrentDateTimeString() + "_" + filerealname;
					//bill为99但是从数组下标为22的地方获取数据
					String uploadPath = Constants.IDCODE_UPLOAD_PATH[billsort-77]
							+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
					String uploadBakPath = "../" + Constants.IDCODE_UPLOAD_PATH[billsort-77]
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
					fileAddress = uploadPath + saveFileName;
					fileAddressBak = uploadBakPath + saveFileName;
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
				}
			}
			if (!iuId.equals("")) { // 新增上传日志
				BarcodeUpload iu = new BarcodeUpload();
				if (null != users) {
					iu.setMakeorganid(users.getMakeorganid());
					iu.setMakedeptid(users.getMakedeptid());
					iu.setMakeid(users.getUserid());

					iu.setUpusername(users.getLoginname());
				}
				if (null != username && !"".equals(username)) {
					Users user = appUsers.getUsers(username);
					if (user != null) {
						iu.setMakeorganid(user.getMakeorganid());
						iu.setMakedeptid(user.getMakedeptid());
						iu.setMakeid(user.getUserid());

						iu.setUpusername(user.getLoginname());
					}
				}
				
				iu.setId(Integer.valueOf(iuId));
				iu.setFilename(filerealname);
				iu.setBillsort(billsort);
				//TODO:已复核单据处理
				iu.setIsdeal(0);
				iu.setValinum(0);
				iu.setFailnum(0);
				iu.setFilepath(fileAddress);
				iu.setFailfilepath("");
				iu.setMakedate(DateUtil.getCurrentDate());
				iu.setIsupload(0);
				iu.setPhysicalpath(realPath);
				iu.setIsticket(0); // 0为有单1为无单
				AppBarcodeUpload abu = new AppBarcodeUpload();
				abu.addBarcodeUpload(iu);
				HibernateUtil.commitTransaction();
				// 返回信息
				if (null != username && !"".equals(username)) {
					return output2Scanner("0:文件已上传，正在处理中，条码上传详细信息请查看条码上传日志", writer);
				} else {
					request.setAttribute("result", "文件已上传，正在处理中，条码上传详细信息请查看条码上传 日志");
					return mapping.findForward("uploadmsg");
				}

			} else {
				if (null != username && !"".equals(username)) {
					return output2Scanner("0:上传文件失败", writer);
				} else {
					request.setAttribute("result", "上传文件失败，请重试");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (null != username && !"".equals(username)) {
				return output2Scanner("0:上传文件失败", writer);
			} else {
				request.setAttribute("result", "上传文件失败，请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}

		}
	}

	/**
	 * 输出信息到采集器 Create Time: Oct 12, 2011 5:54:28 PM
	 * 
	 * @param billErrorList
	 * @param writer
	 * @return
	 * @author dufazuo
	 */
	private ActionForward output2Scanner(String msg, PrintWriter writer) {
		writer.write(msg);
		writer.flush();
		return null;
	}
}
