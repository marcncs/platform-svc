package com.winsafe.sap.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.WfLogger;


public class FileUploadUtil {
	
	private static Logger logger = Logger.getLogger(FileUploadUtil.class);
	
	public static String getSapLogFilePath() {
		return SapConfig.getSapConfig().getProperty("sapLogFilePath");
	}
	
	public static String getSapSaveFilePath() {
		return SapConfig.getSapConfig().getProperty("sapFilePath");
	}
	
	public static String getLotterTempPath() {
		return SapConfig.getSapConfig().getProperty("lotterTempPath");
	}
	
	public static String getPrimayCodeFilePath() {
		return SapConfig.getSapConfig().getProperty("primayCodeFilePath");
	}
	
	public static String getCovertCodeFilePath() {
		return SapConfig.getSapConfig().getProperty("covertCodeFilePath");
	}
	
	public static String getCovertCodeLogFilePath() {
		return SapConfig.getSapConfig().getProperty("covertCodeLogPath");
	}
	
	public static String getCommonCodeFilePath() {
		return SapConfig.getSapConfig().getProperty("commonCodeFilePath");
	}
	
	public static String getAppUpdateFilePath() {
		return SapConfig.getSapConfig().getProperty("appUpdateFilePath");
	}
	public static String getOrganUploadFilePath() {
		return SapConfig.getSapConfig().getProperty("organUploadPath");
	}
	
	public static String getInventoryUpdateFilePath() {
		return SapConfig.getSapConfig().getProperty("inventoryUpdateFilePath");
	}
	public static String getInventoryUpdateLogFilePath() {
		return SapConfig.getSapConfig().getProperty("inventoryUpdateLogFilePath");
	}
	
	public static String getDupontCodeFilePath() {
		return SapConfig.getSapConfig().getProperty("dupontCodeFilePath");
	}
	
	public static String getDupontCodeLogFilePath() {
		return SapConfig.getSapConfig().getProperty("dupontCodeLogPath");
	}
	
	/*public static String getProduceFilePath() {
		return SapConfig.getSapConfig().getProperty("produceFilePath");
	}
	public static String getProduceLogFilePath() {
		return SapConfig.getSapConfig().getProperty("produceLogFilePath");
	}*/
	/**
     * 主要功能:以文件流的方式复制文件
     * 
     * @param src:文件源流
     * @param dest:文件目的目录
     * @throws IOException
     */
    public static File saveUplodedFile(InputStream src ,String savePath, String fileName) throws Exception {
		File filePath = new File(savePath);
		if (!filePath.exists())
			filePath.mkdirs();
		File file = new File(savePath + fileName);
		if(!file.exists()) 
			file.createNewFile();
		BufferedOutputStream bo = null;
		try {
			bo = new BufferedOutputStream(new FileOutputStream(file));
			int c;
			byte buffer[] = new byte[1024*8];
			while ((c = src.read(buffer)) != -1) {
			    for (int i = 0; i < c; i++)
			    bo.write(buffer[i]);
			}
		} catch (Exception e) {
			logger.debug("failed to create file :" +savePath+ fileName);
			throw e;
		} finally {
			if(src != null) {src.close();}
			if(bo != null) {bo.close();}
		}
		return file;
    }
    
    public static void CreateLogFile(String logMessage, String savePath, String fileName) throws Exception {
    	CreateFileWithMessage(logMessage, savePath, fileName);
    }

    public static void CreatePrimayCodeFile(String logMessage, String savePath, String fileName) throws Exception {
    	CreateFileWithMessage(logMessage, savePath, fileName);
    }
    /**
     * 主要功能:以文件流的方式复制文件
     * 
     * @param src:文件源流
     * @param dest:文件目的目录
     * @throws IOException
     */
    public static void CreateFileWithMessage(String logMessage, String savePath, String fileName) throws Exception {
    	OutputStreamWriter ow = null;
    	BufferedWriter bw = null;
    	try {
			File filePath = new File(savePath);
			if (!filePath.exists())
				filePath.mkdirs();
			File file = new File(savePath + fileName);
			if(!file.exists()) 
				file.createNewFile();
			ow = new OutputStreamWriter(new FileOutputStream(file), Constants.DEFAULT_FILE_ENCODE);
			bw = new BufferedWriter(ow);
			bw.write(logMessage.toString());
		} catch (Exception e) {
			logger.debug("failed to create file :" +savePath+ fileName);
			throw e;
		} finally {
			if(bw != null) {bw.close();}
		}
    }
    
    /**
     * 创建空文件
     * 
     * @param src:文件源流
     * @param dest:文件目的目录
     * @throws IOException
     */
    public static File createEmptyFile(String savePath, String fileName) throws Exception {
    	File file = null;
    	try {
    		File filePath = new File(savePath);
    		if (!filePath.exists())
    			filePath.mkdirs();
    		file = new File(savePath + fileName);
    		if(!file.exists()) 
    			file.createNewFile();
		} catch (Exception e) {
			logger.error("生成文件异常",e);
			throw e;
		}
		return file; 
    }

	public static String getDupontOrganId() {
		return SapConfig.getSapConfig().getProperty("dupontOrganId");
	}

	public static List<File> unZipFiles(File zipfile, String descDir) throws Exception {
		List<File> files = new ArrayList<File>();
		InputStream in = null;
		OutputStream out = null;
		try {
			ZipFile zf = new ZipFile(zipfile);
			for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				try {
					ZipEntry entry = (ZipEntry) entries.nextElement();
					String zipEntryName = entry.getName();
					File file = new File(descDir + zipEntryName);
					in = zf.getInputStream(entry);
					out = new FileOutputStream(file);
					byte[] buf1 = new byte[1024];
					int len;
					while ((len = in.read(buf1)) > 0) {
						out.write(buf1, 0, len);
					}
					out.flush();
					files.add(file);
				} finally {
					if(in!=null) {
						in.close();
					}
					if(out!=null) {
						out.close();
					}
				}
			}
		} catch (Exception e) {
			if(in!=null) {
				in.close();
			}
			if(out!=null) {
				out.close();
			}
		}
		return files;
	}
	
	public static void CreateFileWithMessage(String logMessage, File file) throws Exception {
    	OutputStreamWriter ow = null;
    	BufferedWriter bw = null;
    	try {
			if(!file.exists()) 
				file.createNewFile();
			ow = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			bw = new BufferedWriter(ow);
			bw.write(logMessage.toString());
		} catch (Exception e) {
			WfLogger.error("failed to create file :" +file.getName());
			throw e;
		} finally {
			if(bw != null) {bw.close();}
		}
    }

}
