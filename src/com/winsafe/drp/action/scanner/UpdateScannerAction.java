package com.winsafe.drp.action.scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;

public class UpdateScannerAction  extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			//判断操作类型
				//导出当前库存报表
				return download(mapping,form,request,response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
				
		OutputStream os=null;
		FileInputStream fis=null;
		try {

			String filename = request.getParameter("filename");	
//			String username = request.getParameter("username");
//			//根据用户名称查询用户信息
//			AppUsers au = new AppUsers();
//			Users users = au.getUsers(username);
//			if (users == null)
//			{
//				//采集器不可用！请联系供应商注册
//				return null;
//			}

			String realPath = request.getRealPath("/");
			String winposDir=realPath+"/scannerUpdate/"; 

			File file = new File(winposDir+filename);
			if(!file.exists()){
				return null;
			}
			
			request.setCharacterEncoding("utf-8");
			response.reset(); 
			response.setContentLength((int)file.length());
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setContentType("unknown");
			response.setHeader("Content-Disposition","attachment; filename="+file.getName()); 
			
			os = response.getOutputStream();
			fis = new FileInputStream(file);

			byte [] buff = new byte[2048];
			int len = 0;
			
			while ( (len = fis.read(buff)) > 0 )
			{
				os.write(buff, 0, len);
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try{
					fis.close();
				}catch(Exception e){}
			}
			if(os!=null){
				try{
					os.close();
				}catch(Exception e){}
			}
		}

		return null;
	}
}