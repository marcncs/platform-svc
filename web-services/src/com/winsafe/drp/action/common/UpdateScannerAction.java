package com.winsafe.drp.action.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.ScannerUser;
/**
 * 更新采集器程序
 * @author huangxy
 * @date Oct 12, 2011 9:40:48 AM
 * @version v1.0
 */
public class UpdateScannerAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
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
			String scannerid = request.getParameter("scannerid");	
			
			/*
			 * 判断采集器是否已注册
			 */
			AppOrgan appOrgan = new AppOrgan();
			ScannerUser scanner = appOrgan.getScannerUserScanner(scannerid);
			//判断采集器是否已与用户关联配置好
			if (scanner == null)
			{
				//采集器不可用！请联系供应商注册
				return null;
			}

			String realPath = request.getRealPath("/");
			String winposDir=realPath+"/winpos/"; 

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
