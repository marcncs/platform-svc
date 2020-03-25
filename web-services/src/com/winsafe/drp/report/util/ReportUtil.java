package com.winsafe.drp.report.util;

import java.util.Date;
import java.util.Properties;

import com.winsafe.common.util.FileUtil;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.PropertiesUtil;

public class ReportUtil {
	
	/**
	 * 获取报表文件夹
	 * Create Time 2013-12-20 下午01:38:23 
	 * @return
	 * @author lipeng
	 */
	public static String getReportDir() {
		String dir ;
    	try{
    		Properties repProperties = PropertiesUtil.getReportProperties();
    		dir = PropertiesUtil.getProjectPath();
			dir += repProperties.getProperty("report.dir");
    	}catch(Exception e){
    		try {
				DBUserLog.addUserLog(1, "报表保存路径错误");
			} catch (Exception e1) {
				
			}
    		return null;
    	}
    	FileUtil.createDir(dir);
		return dir;
	}
	
	public static Properties getReportProperties()
	{
		Properties repProperties = PropertiesUtil.getReportProperties();
		String dateStr = repProperties.getProperty("dailyReport_date");
		Date date = DateUtil.StringToDate(dateStr,"yyyy-MM-dd HH:mm:ss");
		return PropertiesUtil.getReportProperties();
	}
}
