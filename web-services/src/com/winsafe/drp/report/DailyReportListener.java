package com.winsafe.drp.report;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.winsafe.drp.entity.EntityManager;

public class DailyReportListener implements Runnable, ServletContextListener  {

	private Thread t = new Thread(this);
	
	private static Long sleepTime= null;
	private static Long delSleepTime = null;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//启动文件夹监听器线程 
		try {
			
			sleepTime = 60000L;
			delSleepTime = 10000L;
			
			DailyReportListener listener = new DailyReportListener();
			
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
				Thread.sleep(sleepTime);
				try {
					//调用处理打开vba接口
					autoDailyReport();
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	public  void autoDailyReport() throws Exception {      //程序入口
		try {
			String sql ="select id,cname from task where cname is null or cname=''";
			List taskList = null;
			String cname = "";
			Map map = null;
			//首先判断filepathlist是否为空
			while(!DealDailyReport.filepathlist.isEmpty()){
				//查询数据库查看是否有空闲的资源
				taskList = EntityManager.jdbcquery(sql);
				if(taskList!=null && !taskList.isEmpty()){
					for(int i=0;i<taskList.size()&&!DealDailyReport.filepathlist.isEmpty();i++){
						map = (Map) taskList.get(i);
						cname = map.get(cname)==null?"":map.get(cname).toString();
						//存在空闲的资源则占用（即打开excel）
						if(cname==null || "".equals(cname)){
							//取得第list中第0个数据调用打开方法
							DealOpenFile(DealDailyReport.filepathlist.get(0));
							//睡眠10秒
							Thread.sleep(delSleepTime);
							//删除list集合中的数据
							DealDailyReport.filepathlist.remove(0);
						}
					}
				}else{
					//睡眠10秒
					Thread.sleep(delSleepTime);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void DealOpenFile(String filepath) throws Exception{
		Runtime r = Runtime.getRuntime();
		r.exec("cmd /c start " + filepath);
	}

}
