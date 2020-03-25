package com.winsafe.hbm.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.winsafe.hbm.util.PropertiesUtil;

/**
 * 系统初始化
 * Project:is->Class:InitServlet.java
 * <p style="font-size:16px;">Description：</p>
 * Create Time 2013-12-20 上午10:52:51 
 * @author <a href='peng.li@winsafe.cn'>lipeng</a>
 * @version 0.8
 */
public class InitServlet extends HttpServlet
{

	private static final long serialVersionUID = 1050326840288747548L;

	public InitServlet()
	{
		super();
	}

	@Override
	public void destroy()
	{
		super.destroy();
	}

	@Override
	public void init() throws ServletException
	{
		//设置配置文件绝对路径
		String realResourcePath = getServletContext().getRealPath("/WEB-INF/classes/");
		PropertiesUtil.setRealResourcePath(realResourcePath);
		//设置系统上下文绝对路径
		String realPath = getServletContext().getRealPath("/WEB-INF/");
		PropertiesUtil.setRealPath(realPath);
		//Tomcat项目的绝对路径
		String projectPath = getServletContext().getRealPath("/");
		PropertiesUtil.setProjectPath(projectPath);
	}
}
