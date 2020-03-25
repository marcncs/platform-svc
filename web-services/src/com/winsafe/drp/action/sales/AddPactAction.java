package com.winsafe.drp.action.sales;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPact;
import com.winsafe.drp.dao.Pact;
import com.winsafe.drp.dao.ValidatePact;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddPactAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValidatePact vp = (ValidatePact) form;

		try {
			
			initdata(request);
			Pact p = new Pact();
			p.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("pact", 0,
					"")));
			p.setPactcode(vp.getPactcode());
			p.setPacttype(vp.getPacttype());
			p.setUserid(userid);
			p.setObjsort(vp.getObjsort());
			p.setCid(vp.getCid());
			p.setCname(vp.getCname());
			p.setCdeputy(vp.getCdeputy());
			p.setSigndate(DateUtil.StringToDate(vp.getSigndate()));
			p.setProvide(vp.getProvide());
			p.setPdeputy(vp.getPdeputy());
			p.setSignaddr(vp.getSignaddr());
			p.setPactscopy(vp.getPactscopy());
			p.setMakeorganid(users.getMakeorganid());
			p.setMakedeptid(users.getMakedeptid());
			p.setMakeid(userid);
			p.setMakedate(DateUtil.getCurrentDate());

			// 上传图片
			FormFile documentFile = null;
			documentFile = (FormFile) vp.getAffix();
			// System.out.println("------------"+documentFile);
			String extName = null;
			String fileName = null;
			// String contentType = null;
			String filePath = request.getRealPath("/");
			if (documentFile.toString().trim() != null
					&& !documentFile.toString().trim().equals("")) {
				fileName = documentFile.getFileName().toLowerCase();
				extName = fileName.substring(fileName.indexOf("."), fileName
						.length());
				if (extName != null) {
					InputStream fis = documentFile.getInputStream();
					String sDateTime = DateUtil.getCurrentDateTimeString();
					String firstname = fileName.substring(0,fileName.indexOf("."));
					String saveFileName = firstname+"_" + sDateTime
							+ extName;

					
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
					

					p.setAffix(fileAddress);
				}
			}

			AppPact ap = new AppPact();
			ap.addNewPact(p);
			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid,5, "会员/积分管理>>新增合同,编号："+p.getId());

			return mapping.findForward("result");

		} catch (Exception e) {

			e.printStackTrace();
		}

		return mapping.getInputForward();
	}
}
