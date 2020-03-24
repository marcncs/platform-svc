package com.winsafe.hbm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件工具类
 * Project:is->Class:PropertiesUtil.java
 * <p style="font-size:16px;">Description：</p>
 * Create Time 2013-12-20 上午10:56:02 
 * @author <a href='peng.li@winsafe.cn'>lipeng</a>
 * @version 0.8
 */
public class PropertiesUtil
{
	/** Tomcat项目的绝对路径 */
	public static String projectPath;
	/** 系 统上下文绝对路径 */
	public static String realPath;
	/** 系统配置文件上下文绝对路径 */
	public static String realResourcePath;

	/**
	 * 获取报表配置项
	 * Create Time 2013-12-20 上午10:56:10 
	 * @return
	 * @author lipeng
	 */
	public static Properties getReportProperties()
	{
		Properties properties = null;
		properties = getPropertiesByRealPath(realResourcePath + "/report.properties");
		return properties;
	}

	/**
	 * 通过资源绝对路径，获取资源文件
	 * Create Time 2013-12-20 上午10:56:28 
	 * @param realPath
	 * @return
	 * @author lipeng
	 */
	public static Properties getPropertiesByRealPath(String realPath)
	{
		Properties properties = new Properties();
		InputStream is=null;
		try
		{
			is = new FileInputStream(realPath);
			properties.load(is);
		}
		catch(IOException e)
		{
			throw new RuntimeException("couldn't load properties file '" + realPath + "'", e);
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return properties;
	}

	public static String getProjectPath() {
		return projectPath;
	}

	public static void setProjectPath(String projectPath) {
		PropertiesUtil.projectPath = projectPath;
	}

	public static String getRealPath() {
		return realPath;
	}

	public static void setRealPath(String realPath) {
		PropertiesUtil.realPath = realPath;
	}
	public static String getRealResourcePath() {
		return realResourcePath;
	}

	public static void setRealResourcePath(String realResourcePath) {
		PropertiesUtil.realResourcePath = realResourcePath;
	}

}
