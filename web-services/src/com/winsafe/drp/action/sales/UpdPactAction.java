package com.winsafe.drp.action.sales;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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

public class UpdPactAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValidatePact vp = (ValidatePact) form;
		
		initdata(request);
		AppPact ap = new AppPact();
		// //Connection conn=null;
		try {

			String cid = vp.getCid();
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", "databases.upd.success");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			Pact p = ap.getPactByID(vp.getId());
			Pact oldp = (Pact) BeanUtils.cloneBean(p);
			p.setPactcode(vp.getPactcode());
			p.setPacttype(Integer.valueOf(vp.getPacttype()));
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
			String extName = null;
			String fileName = null;
			String firstName ="";
			String filePath = request.getRealPath("/");
			if (documentFile.toString().trim() != null
					&& !documentFile.toString().trim().equals("")) {
				fileName = documentFile.getFileName().toLowerCase();
				extName = fileName.substring(fileName.indexOf("."), fileName
						.length());
				firstName=fileName.substring(0,fileName.indexOf("."));
				if (extName != null) {
					InputStream fis = documentFile.getInputStream();
					String sDateTime = DateUtil.getCurrentDateTimeString();
					String saveFileName = firstName+"_" + sDateTime
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
			if (p.getAffix() == null || p.getAffix().equals("")) {
				p.setAffix(request.getParameter("oldaffix"));
			}

			ap.updPact(p);

			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, 5, "会员/积分管理>>修改客户合同 ,编号：" + p.getId(),
					oldp, p);
			return mapping.findForward("result");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
