package com.winsafe.drp.action.self;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppDoc;
import com.winsafe.drp.dao.Doc;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.ValidateDoc;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddDocAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		ValidateDoc vc = (ValidateDoc) form;

		try {
			Doc pb = new Doc();
			FormFile docFile = null;
			String realpathname = "";
			docFile = (FormFile) vc.getDoc();
			String filePath = request.getRealPath("/");
			pb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("Doc",
					0, "")));
			
			pb.setSortid(Integer.valueOf(vc.getSortid()==null?0:Integer.valueOf(vc.getSortid())));
			pb.setDescribe(vc.getDescribe());
			pb.setMakeorganid(users.getMakeorganid());
			pb.setMakedeptid(users.getMakedeptid().intValue());
			pb.setMakeid(userid.intValue());
			pb.setMakedate(DateUtil.getCurrentDate());

			
			String extName = "";
			String fileName = "";
			if (docFile != null && !docFile.equals("")) {
				fileName = docFile.getFileName().toLowerCase();
				extName = fileName.substring(fileName.indexOf("."), fileName
						.length());
				
				String sDateTime = DateUtil.getCurrentDateTimeString();
				String firstName = fileName.substring(0, fileName.indexOf("."));
				String saveFileName = firstName	+"_"+ sDateTime + extName;
				InputStream fis = docFile.getInputStream();
				realpathname = "docdir/" + saveFileName;

				OutputStream fos = new FileOutputStream(filePath + realpathname);

				byte[] buffer = new byte[1048576];// 上限一MB
				int bytesRead = 0;
				while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
					
					fos.write(buffer, 0, bytesRead);
				}
				fos.close();
				fis.close();
			}

			pb.setRealpathname(realpathname);
			AppDoc apb = new AppDoc();
			apb.addDoc(pb);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>新增文件,编号：" + pb.getId());

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
