package com.winsafe.drp.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.winsafe.drp.task.CreateTransferFileTask;
import com.winsafe.drp.util.JProperties; 

public class SysConfig {
	private static Logger logger = Logger
		.getLogger(SysConfig.class);
	private static Properties sysConfig = null;
	public static Properties getSysConfig() throws IOException {
		if(sysConfig == null) {
			logger.debug("获取配置文件");
			sysConfig = JProperties.loadProperties("system.properties",
					JProperties.BY_CLASSLOADER);
		}
		return sysConfig;
	}
}
