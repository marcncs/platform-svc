package com.winsafe.drp.action.sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class DownLoadMarubiAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		super.initdata(request);
		try {
			//从页面上得到下载路径
			String realPath = request.getRealPath("/");
			String fileName = request.getParameter("filedownselect");
			//转换
			fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
			//声明下载时的头信息
			response.reset();
			response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "ISO-8859-1"));
			response.setContentType("application/x-msdownload");
			java.io.OutputStream outp = null;
			java.io.FileInputStream in = null;
			//下载
			try {
				outp = response.getOutputStream();
				String filePath = realPath + "../data/" + fileName.substring(0, fileName.length()-6) + "/" + fileName; 
				File file = new File(filePath);
				in = new FileInputStream(file.getCanonicalPath());
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0) {
					outp.write(b, 0, i);
				}
				outp.flush();
				outp.close();
			} catch (Exception e) {
				System.out.println("已经取消下载!");
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					in = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
