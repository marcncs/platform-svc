package com.winsafe.drp.util;

import java.io.IOException;
import java.util.Properties;

import com.winsafe.drp.util.JProperties; 

public class PlantConfig { 
	private static Properties sysConfig = null;
	public static Properties getConfig() throws IOException {
		if(sysConfig == null) { 
			sysConfig = JProperties.loadProperties("system.properties",
					JProperties.BY_CLASSLOADER);
		}
		return sysConfig;
	}
}
