package com.winsafe.drp.action.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppRegionUsers;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.RegionUsers;
import com.winsafe.drp.dao.Users;

public class DownLoadReportAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
		//设置消息头
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/octet-stream; charset=utf-8");
		File lastFile = null;
		//-----------------获取服务器上的的所有文件----------------------------------------
		Map<String, File> map = new HashMap<String, File>();// key:文件名 value:文件
		// 获取文件存在的根目录
		String rootpath = request.getSession().getServletContext().getRealPath("/reports/save");
		// 递归遍历其中文件
		File file = new File(rootpath);
		treeWalk(file, map);
		//-----------------分布获取文件名字---------------------------------
		// 文件名字_时间：
		String timepath="";
		String datetime = request.getParameter("DateTime");
		if(datetime!=null && datetime.length() >0){
			String[] str = datetime.split("-");
			timepath = str[0] + str[1] + str[2];
		}else{
			request.setAttribute("result", "下载失败！请选择具体日期");
			return new ActionForward("/sys/lockrecordclose_download.jsp");
		}
		// 文件名字_前缀
		String prefixname = "Daily_Report";
		// 文件名字_大区或者办事处名字
		String middleFilename=null;
		// 文件名_后缀名
		String postfixname = ".xlsm";
	     //页面获取数据大区经理数据
		 String flag=request.getParameter("flag");
	    //管理员用户
	    String regionareaid=request.getParameter("regionareaid");
	    String regionareaname=request.getParameter("regionarea");
	    
	     //办事处经理
	   String officemanagername= request.getParameter("officemanagername");
	   if(officemanagername!=null && officemanagername.length() >0){
		   middleFilename= officemanagername.split("_")[1];
	   }
	   //大区经理
	   if(regionareaid!=null &&  regionareaid.length() >0 &&  "5".equals(flag)){
		   middleFilename=regionareaname;
	   }
		//管理员信息
	   if(regionareaid!=null && regionareaid.length() >0 && "0".equals(flag)){
		   middleFilename=regionareaname;
      }
	   
		
		//构造文件名字
		String filename = prefixname + "_" + middleFilename + "_" + timepath+ postfixname;
		//遍历map
		for (String key : map.keySet()) {
			File value = map.get(key);
			if (filename.equalsIgnoreCase(key)) {
				lastFile = value;
			}
		}
		if(lastFile==null){
			     // 判断文件是否存在
				request.setAttribute("result", "对不起!你要下载的文件可能已经不存在了");
				return new ActionForward("/sys/lockrecordclose_download.jsp");
		}
	//----------------------文件下载---------------------------------------------
		OutputStream out = response.getOutputStream();
		InputStream in = new FileInputStream(lastFile);
		// 通知客户端以下载的方式打开
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename, "UTF-8"));
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		in.close();
		out.flush();
		out.close();
		response.flushBuffer();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	//递归文件
	private void treeWalk(File file, Map<String, File> map) {
	    //文件
		if (file.isFile()) {
			String filename = file.getName();// 
			map.put(filename, file);
		} else {
			// 是一个目录
			File[] fs = file.listFiles();
			for (File f : fs) {
				treeWalk(f, map);
			}
		}
	}
}
