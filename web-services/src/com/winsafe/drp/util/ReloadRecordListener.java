package com.winsafe.drp.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.winsafe.drp.action.machin.ReloadRecordAction;
import com.winsafe.hbm.entity.HibernateUtil;

public class ReloadRecordListener implements Runnable, ServletContextListener {

	private Thread t = new Thread(this);
	
	private static Long recordSleepTime= null;
	
	private ReloadRecordAction rra = new ReloadRecordAction();
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//启动文件夹监听器线程 
		try {
			/*//读取shjyfwm.properties配置文件中的IdcodeUploadSleepTime属性
			Properties p = JProperties.loadProperties(
					"shjyfwm.properties", JProperties.BY_CLASSLOADER);
			//配置文件中存在IdcodeUploadSleepTime属性，取其职
			if(p.getProperty("IdcodeUploadSleepTime")!=null && !"".equals(p.getProperty("IdcodeUploadSleepTime")))
				IdcodeUploadSleepTime = Long.parseLong(p.getProperty("IdcodeUploadSleepTime"));
			//配置文件中不存在IdcodeUploadSleepTime属性，则设置IdcodeUploadSleepTime为1分钟（60000）
			if(IdcodeUploadSleepTime==null)
				IdcodeUploadSleepTime = 60000L;*/
			recordSleepTime = 10000L;
			
			ReloadRecordListener listener = new ReloadRecordListener();
			
			listener.start(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void start(boolean isDamon) {
		t.setDaemon(isDamon);
		t.start();
	}
	
	@Override
	public void run() {
		while(true){
			try {
				// 每隔1分钟处理一次
				Thread.sleep(recordSleepTime);
				try {
					//调用处理上传文件入口
					autoReloadRecode();
					
				} catch (Exception e) {
					HibernateUtil.rollbackTransaction();
					e.printStackTrace();
				} finally {
					HibernateUtil.closeSession();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public  void autoReloadRecode() throws Exception {      //程序入口
		try {
			rra.ReloadRecorStart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
