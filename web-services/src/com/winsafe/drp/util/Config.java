package com.winsafe.drp.util;

import java.util.Properties;

import com.winsafe.common.util.FileUtil;

public class Config {
    /**
     * 用于临时存放文件的目录
     */
    private static String _tempDir = "d:\\winsafe\\bright\\temp\\";
	/**
	 * ftp下载 目录
	 */
    private static String _importFileDir = "d:\\winsafe\\bright\\importFile\\";
    
    /**
     * 文件备份目录1
     */
    private static String _backupDir = "d:\\winsafe\\bright\\backup\\";
    
    /**
     * 文件保存根目录
     */
    private static String _rootDir = "d:\\winsafe\\bright\\";
    /**
     * 日志目录
     */
    private static String _logDir = "d:\\winsafe\\bright\\log\\";
    
    /**
     * 线程的最小间隔时间（单位：毫秒）
     */
    private static long _sleeptimeMin=1000;
    /**
     * 线程的最大间隔时间（单位：毫秒）
     */
    private static long _sleeptimeMax=30000;
    
    static{
    	try{
	    	Properties p = JProperties.loadProperties("ftpFilePath.properties", JProperties.BY_CLASSLOADER);
	    	_tempDir = p.getProperty("tempDir");
	    	_importFileDir = p.getProperty("importFileDir");
	    	_backupDir = p.getProperty("backupDir");
	    	_rootDir = p.getProperty("rootDir");	
	    	_logDir = p.getProperty("logDir");	
	    	_sleeptimeMin = Long.parseLong(p.getProperty("sleeptimeMin"));
	    	_sleeptimeMax = Long.parseLong(p.getProperty("sleeptimeMax"));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	FileUtil.createDir(_logDir);
    	FileUtil.createDir(_tempDir);
    	FileUtil.createDir(_importFileDir);
    	FileUtil.createDir(_backupDir);
    }

    /**
     * 获取临时存放文件的目录
     */
    public static String getTempDir(){
    	return _tempDir;
    }

	/**
	 * 获取ftp下载 目录
	 */
    public static String getImportFileDir(){
    	return _importFileDir;
    }
    /**
     * 获取文件备份目录1
     */
    public static String getBackupDir (){
    	return _backupDir;
    } 
    
    /**
     * 获取线程的最小间隔时间（单位：毫秒）
     * @return
     */
    public static long getSleeptimeMin(){
    	return _sleeptimeMin;
    }
    
    /**
     * 获取线程的最大间隔时间（单位：毫秒）
     * @return
     */
    public static long getSleeptimeMax(){
    	return _sleeptimeMax;
    }
    
    /**
     * 获取文件保存根目录
     * @return
     */
    public static String getRootDir(){
    	return _rootDir;
    }
    /**
     * 获取日志目录
     * @return
     */
    public static String getLogDir(){
    	return _logDir;
    }
}
