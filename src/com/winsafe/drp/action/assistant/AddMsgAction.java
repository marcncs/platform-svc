package com.winsafe.drp.action.assistant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppMsg;
import com.winsafe.drp.dao.Msg;
import com.winsafe.drp.dao.MsgForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.SendMsg;

public class AddMsgAction extends Action {


  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    int userid = users.getUserid();


		try {
			MsgForm mf = (MsgForm)form;
			String mobileno ="";
			String msgcontent = mf.getMsgcontent();
			int msgid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("msg", 0, ""));
			
				
			if ( mf.getSeltel() == 1 ){
				mobileno =  mf.getMobileno();
			}else if ( mf.getSeltel() == 2 ){			
				//上传图片
				FormFile phonefile = (FormFile) mf.getPhonefile();
				StringBuffer sb = new StringBuffer();
				String extName = null;
				String contentType = null;
				String filePath = request.getRealPath("/");
				if (phonefile != null && !phonefile.equals("")) {
					contentType = phonefile.getContentType();
					if (contentType != null) {
						if (contentType.indexOf("text") >= 0){
							extName = ".txt";
						}				
					}
					if (extName != null) {
						InputStream fis = phonefile.getInputStream();
						String saveFileName = String.valueOf(msgid)
								+ "msg" + extName;						
						
						// 建立一个上传文件的输出流
						OutputStream fos = new FileOutputStream(filePath
								+ "/upload/" +saveFileName);
						String fileAddress = "/upload/" + saveFileName;
						byte[] buffer = new byte[2048];
						int bytesRead = 0;
						while ((bytesRead = fis.read(buffer, 0, 2048)) != -1) {
							// 把上传的文件放到服务器的指定目录下
							fos.write(buffer, 0, bytesRead);
						}
						fos.close();
						fis.close();
						
						File ff = new File(filePath + fileAddress);	
						BufferedReader br = new BufferedReader(new FileReader(ff));
						String tel =br.readLine();
						while ( tel != null ){
							if ( !"".equals(tel)){
								sb.append(",").append(tel.trim());
							}
							tel =br.readLine();
						}
						br.close();
						ff.delete();
						mobileno = sb.toString().substring(1);
					}
					
				}
			}
			if ( mobileno == null || mobileno.equals("")) {				
				return new ActionForward(mapping.getInput());
			}	
			
			AppMsg appmsg = new AppMsg();
			Msg msg = new Msg();
			msg.setId(msgid);
			msg.setMsgsort(1);
			msg.setMsgcontent(msgcontent);
			msg.setMakeorganid(users.getMakeorganid());
			msg.setMakedeptid(users.getMakedeptid());
			msg.setMakeid(userid);
			msg.setMakedate(DateUtil.getCurrentDate());
			msg.setMobileno(mobileno);
			msg.setIsdeal(0);
			msg.setIsaudit(0);
			msg.setAuditid(0);
			if ( mf.getQuicksend() == 2 ){
				SendMsg.sendMsg(msg, userid);
			}		
			appmsg.addMsg(msg);
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 12,"新增短信,编号："+msgid);// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
