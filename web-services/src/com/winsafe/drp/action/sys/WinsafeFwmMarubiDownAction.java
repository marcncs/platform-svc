package com.winsafe.drp.action.sys;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.FwmDown;

public class WinsafeFwmMarubiDownAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
	super.initdata(request);
		//声明变量和得到页面上的参数
		String realPath = request.getRealPath("/");
		String productname = request.getParameter("productname");
		String date = request.getParameter("date");
		String clientname = request.getParameter("clientname");
		String clientcode = request.getParameter("clientcode");
		try {
			clientname = java.net.URLDecoder.decode(clientname, "UTF-8");
			productname = java.net.URLDecoder.decode(productname, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace(); 
		}
		//得到下载数据的名称
		date = date.replace("-", "").replace(" ", "").replace(":", "");
		String fileName = "../data/" + clientname + "_" + productname + "_" +  date.substring(0, 8) + "_" + date.substring(8, 12) + "/";
		fileName = new String(fileName.getBytes("UTF-8"),"UTF-8");
		List<FwmDown> list = new ArrayList<FwmDown>();
		try {
			File filePath = this.saveFilePath(fileName, realPath);
			File[] files = filePath.listFiles();
			for (int i = 0; i < files.length; i++) {
				FwmDown fd = new FwmDown();
				fd.setId(i + 1);
				fd.setFilePath(String.valueOf(files[i].getCanonicalPath()));
				list.add(fd);
			}
			List<String> pathList = new ArrayList<String>();
			for (FwmDown fwmdown : list) {
				if (fwmdown.getFilePath().substring(
						fwmdown.getFilePath().lastIndexOf("."),
						fwmdown.getFilePath().length()).equals(".zip")
						|| fwmdown.getFilePath().substring(
								fwmdown.getFilePath().lastIndexOf("."),
						 		fwmdown.getFilePath().length()) == ".zip") {
					pathList.add(fwmdown.getFilePath().substring(fwmdown.getFilePath().lastIndexOf("/")+1, fwmdown.getFilePath().length()));
				}
			}
			request.setAttribute("pathList", pathList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapping.findForward("download");
	}
	// 生成服务器上的目录
	private File saveFilePath(String fileName, String realPath)
			throws IOException {
		File dir = new File(realPath + fileName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
}
