package com.winsafe.drp.action.assistant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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

import com.winsafe.drp.dao.AppMsg;
import com.winsafe.drp.dao.Msg;
import com.winsafe.drp.dao.MsgForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.SendMsg;

public class UpdMsgAction extends Action {


  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    int userid = users.getUserid();


		try {

			int id = Integer.valueOf(request.getParameter("ID"));			
			MsgForm mf = (MsgForm)form;
			String mobileno ="";
			String msgcontent = mf.getMsgcontent();			
				
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
						String saveFileName = String.valueOf(id)
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
			Msg msg = appmsg.getMsgById(id);
			Msg oldmsg = (Msg)BeanUtils.cloneBean(msg);
			msg.setMsgcontent(msgcontent);
			msg.setMobileno(mobileno);			
			if ( mf.getQuicksend() == 2 ){
				SendMsg.sendMsg(msg, userid);
			}		
			appmsg.updMsg(msg);
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, 12, "修改短信,编号："+id, oldmsg, msg);// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
