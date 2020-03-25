package com.winsafe.sap.util;

import java.util.Properties;

import com.winsafe.drp.util.JProperties;
/*******************************************************************************************  
 * 读取SAP配置文件信息工具类
 * @author: ryan.xi	  
 * @date：2014-12-10  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-12-10   ryan.xi                
 *******************************************************************************************  
 */
public class SapConfig {
	private static Properties sapConfig;
	public static Properties getSapConfig() {
		try {
			sapConfig = JProperties.loadProperties("sap.properties",
					JProperties.BY_CLASSLOADER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sapConfig;
	}
	
}
