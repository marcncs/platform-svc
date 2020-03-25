package com.winsafe.drp.util.fileListener;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat; 
import java.util.Date; 
import java.util.Properties; 

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.winsafe.drp.util.FileUtil;
import com.winsafe.drp.util.fileListener.UFIDA.ImportSysData;



/** 
 *  Created on 2010-04-20 
 *  用友接口数据文件夹监听器
 *  @author RichieYu 
 *  */
public class FileMonitor implements Runnable {
	private static final Logger log = Logger.getLogger(FileMonitor.class);
	//根目录
	private static String FILE_PATH_ROOT;
	//NC目录
	private static String FILE_PATH_ROOT_NC;
	//winsafe目录
	private static String FILE_PATH_ROOT_WINSAFE;
	//NC子目录
	private static String FILE_PATH_NC_FROM;   
	private static String FILE_PATH_NC_TO;   
	//winsafe子目录
	private static String FILE_PATH_WINSAFE_FROM;   
	private static String FILE_PATH_WINSAFE_TO;   
	//延迟间隔时间
	private long intervalTime;
	private Thread t = new Thread(this);
	 
	public void start(boolean isDamon) {
		t.setDaemon(isDamon);
		t.start();
	}

	//构造函数
	public FileMonitor(Properties p, long intervalTime) {
		//所有文件夹初始化
		FILE_PATH_ROOT = p.getProperty("local_file_path_root");
		FILE_PATH_ROOT_NC = p.getProperty("local_file_path_root_nc");
		FILE_PATH_ROOT_WINSAFE = p.getProperty("local_file_path_root_winsafe");
		FILE_PATH_NC_FROM = p.getProperty("local_file_path_nc_from");
		FILE_PATH_NC_TO = p.getProperty("local_file_path_nc_to");
		FILE_PATH_WINSAFE_FROM = p.getProperty("local_file_path_winsafe_from");
		FILE_PATH_WINSAFE_TO = p.getProperty("local_file_path_winsafe_to");
		//延迟时间配置
		this.intervalTime = intervalTime; 
		 
	}
   
	/**
	 * 线程自动启动方法
	 */
	public void run(){
		int k=0;

		//如服务器本地无文件夹,则新建文件夹
		this.createAllDir();
		while (true) {
			
			
			File fileFrom = new File(FILE_PATH_NC_FROM);
			try {
				t.sleep(intervalTime);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				
				e1.printStackTrace();
			}
			if (fileFrom.listFiles().length > 0) {
				try {
					//复制文件
					File[] fileList = fileFrom.listFiles();
					for (int i = 0; i < fileList.length; i++) {
						//构建子文件夹路径
						String nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
						String tempTargetDir = FILE_PATH_NC_TO+ "/"+nowDate;
						//创建以日期为子文件夹的目录
						File datefile = new File(tempTargetDir);
						//检查当天文件夹是否存在,如不存在创建
						if(!datefile.exists()){
							FileUtil.createDir(tempTargetDir);
						}
						
						//复制到指定目录
						FileUtil.copyFile(FILE_PATH_NC_FROM + "/" + fileList[i].getName(), tempTargetDir + "/" + fileList[i].getName());
						//处理完毕删除原目录文件
						fileList[i].delete();
						
						//XML数据解析
						try {
							Document document = FileUtil.readXml(tempTargetDir + "/" + fileList[i].getName());
							ImportSysData.importData(document,fileList[i].getName());
							break;
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						} 
						
					}
					
				} catch (IOException e) {
					
					e.printStackTrace();
					continue;
				}  
			}
			/*try {
			 	//检测的间隔时间
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
				 
			}*/
			
		}
	}
 
	/**
	 * 创建共享文件目录和所有文件夹
	 */
	private void createAllDir(){
		FileUtil.createDir(FILE_PATH_ROOT);
		FileUtil.createDir(FILE_PATH_ROOT_NC);
		FileUtil.createDir(FILE_PATH_ROOT_WINSAFE);
		FileUtil.createDir(FILE_PATH_NC_FROM);
		FileUtil.createDir(FILE_PATH_NC_TO);
		FileUtil.createDir(FILE_PATH_WINSAFE_FROM);
		FileUtil.createDir(FILE_PATH_WINSAFE_TO);
	}

}
