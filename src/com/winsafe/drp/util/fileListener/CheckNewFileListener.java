package com.winsafe.drp.util.fileListener;
 
import java.io.IOException; 
import java.util.Properties; 

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.fileListener.UFIDA.SendXml;
 
 

/**
 * @author RichieYu
 * 文件监听器
 */
public class CheckNewFileListener implements ServletContextListener {
	private static final Logger log = Logger.getLogger(CheckNewFileListener.class);
	private static FileMonitor FILEMONITOR;
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//启动文件夹监听器线程 
		try {
//			Properties p = JProperties.loadProperties("ftpFilePath.properties", JProperties.BY_CLASSLOADER);
//			FILEMONITOR = new FileMonitor(p,1000);
//			FILEMONITOR.start(false);
			
			//导入单据
			ImportFile importFile = new ImportFile();
			importFile.setDaemon(false);
			importFile.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.toString());
		}
		
	} 

}
