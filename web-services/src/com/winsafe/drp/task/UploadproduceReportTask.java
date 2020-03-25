package com.winsafe.drp.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.UploadPrLog;
import com.winsafe.drp.task.upr.IFile;
import com.winsafe.drp.task.upr.IFileFactory;
import com.winsafe.drp.task.upr.TFile;
import com.winsafe.drp.task.upr.TSmbFile;
import com.winsafe.drp.task.upr.UploadHandle;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 自动处理生产数据
 * @author Andy.liu
 *
 */
public class UploadproduceReportTask extends Thread {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private UploadHandle handle;
	
	private final static String FILE_PATH = "filePath";
	private final static String FILE_PATH_BAK = "filePathBak";
	private final static String IDENTITY = "identity";

	private String filePath; // 目录
	private String filePathBak; // 备份目录
	private int identity;
	private Properties p;
	private final String defaultMakeOrganId = "10000083"; // 默认制单机构 
	private final Integer defaultUserId = 1; // 默认制单人 系统管理员

	/** 日志Dao */
	private RecordDao rDao = new RecordDao();

	/**
	 * 初始化路径
	 */
	public void init() throws Exception{
		getProperties();
		filePath = p.getProperty(FILE_PATH);
		filePathBak = p.getProperty(FILE_PATH_BAK);
		identity =Integer.valueOf(p.getProperty(IDENTITY));
		handle = new UploadHandle();
	}

	@Override
	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					this.init();
					isRunning = true;
					DBUserLog.addUserLog1(1, "自动处理生产数据---开始---");
					IFile file = IFileFactory.getInstence(identity,filePath);
					if(file==null){
						return;
					}
					List obj = file.getAllFiles();
					for (Object object : obj) {
						File tmpfile = (File)object;
						//只读取mdb文件
						if(tmpfile == null || !tmpfile.getName().endsWith(".mdb")){
							continue;
						}
						if(object instanceof File){
							File f = (File)object;
							TFile.mkdir(filePathBak);
							String filefullpath = this.wsShareBakFile(f);
							this.execute(f.getName(), filefullpath, new FileInputStream(filefullpath));
						}
						if(object instanceof SmbFile){
							SmbFile f = (SmbFile)object;
							TSmbFile.mkdir(filePathBak);
							String filefullpath = this.wsShareBakFile(f);
							this.execute(f.getName(), filefullpath, new SmbFileInputStream(filefullpath));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						DBUserLog.addUserLog1(1, "自动处理生产数据发生异常");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					isRunning = false;
					try {
						DBUserLog.addUserLog1(1, "自动处理生产数据---结束---");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	/**
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void execute(String fileName, String fileFullPath, InputStream is) throws Exception{
		String extName = fileName.substring(fileName.indexOf("."), fileName.length());
		String firstName = fileName.substring(0, fileName.indexOf("."));
		String sDateTime = DateUtil.getCurrentDateTimeString();
		String saveFileName = firstName + "_" + sDateTime + extName;
		Integer id = addUploadPrLog(fileName,
				saveFileName);
		handle.produce(fileName, fileFullPath, id, saveFileName, is);
	}

	/**
	 * 备份文件(与源目录同级文件夹)
	 * 
	 * @param files
	 * @return BakPathfileName备份文件完整路径
	 */
	public String wsShareBakFile(File file) throws Exception {
		String fileName = file.getName();
		// 备份文件，
		String time = DateUtil.getCurrentDateYMD();
		//日期文件夹创建
		File f = new File(filePathBak+time);
		if(!f.exists()){
			f.mkdir();
		} 
		TFile.copyFile(file.getPath(), filePathBak + time + File.separator+ fileName);
		// 删除源文件 
		file.delete();
		return filePathBak + time +File.separator + fileName;
	}
	
	/**
	 * 备份文件(与源目录同级文件夹)
	 * 
	 * @param files
	 * @return BakPathfileName备份文件完整路径
	 */
	public String wsShareBakFile(SmbFile file) throws Exception {
		String fileName = file.getName();
		// 备份文件，
		String time = DateUtil.getCurrentDateYMD();
		//日期文件夹创建
		SmbFile f = new SmbFile(filePathBak+time);
		if(!f.exists()){
			f.mkdir();
		}
		file.copyTo(new SmbFile(filePathBak +time+ File.separator +fileName));
		// 删除源文件
		file.delete();
		return  filePathBak + time + File.separator + fileName;
	}

	/**
	 * 添加日志
	 * 
	 * @param fileName
	 * @param filePath
	 * @throws Exception
	 */
	public Integer addUploadPrLog(String fileName, String filePath)
			throws Exception {
		UploadPrLog record = new UploadPrLog();
		Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"Record", 0, ""));
		record.setId(id);
		record.setFilename(fileName);
		record.setFilepath(filePath);
		record.setMakeid(defaultUserId);
		record.setMakeorganid(defaultMakeOrganId);
		record.setMakedate(DateUtil.getCurrentDate());
		record.setIsdeal(0);
		rDao.save(record);
		return id;
	}
	
	/**
	 * 获取配置文件
	 * @throws Exception
	 */
	public void getProperties() throws Exception{
		p = JProperties.loadProperties("upr.properties", JProperties.BY_CLASSLOADER);
	}
	


}
