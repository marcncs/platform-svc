package com.winsafe.drp.util.sftp;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp; 
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

public class NotificationSFTPServices {
	
	private static Logger logger = Logger.getLogger(NotificationSFTPServices.class);
	private ChannelSftp chSftp = null;
	private SFTPChannel channel = null;
	private String filePath = null;
	
	public ChannelSftp getChannelSftp() throws Exception {
		if(chSftp == null) {
			filePath = SysConfig.getSysConfig().getProperty("sftp_filepath");
			if(StringUtil.isEmpty(filePath)) {
				filePath = "/";
			}
			Map<String, String> sftpDetails = new HashMap<String, String>();
	        // 设置主机ip，端口，用户名，密码
			String pwd = Encrypt.getSecret(SysConfig.getSysConfig().getProperty("noti.sftp.password"), 2);
	        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, SysConfig.getSysConfig().getProperty("noti.sftp.host"));
	        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, SysConfig.getSysConfig().getProperty("noti.sftp.username"));
	        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, pwd);
	        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, SysConfig.getSysConfig().getProperty("noti.sftp.port"));
	        //获取代理配置
	        /*String proxyUser = Encrypt.getSecret(SysConfig.getSysConfig().getProperty(SFTPConstants.SFTP_PROXY_USERNAME), 2);
	        String proxyPwd = Encrypt.getSecret(SysConfig.getSysConfig().getProperty(SFTPConstants.SFTP_PROXY_PASSWORD), 2);
	        sftpDetails.put(SFTPConstants.SFTP_PROXY_HOST, SysConfig.getSysConfig().getProperty(SFTPConstants.SFTP_PROXY_HOST));
	        sftpDetails.put(SFTPConstants.SFTP_PROXY_PORT, SysConfig.getSysConfig().getProperty(SFTPConstants.SFTP_PROXY_PORT));
	        sftpDetails.put(SFTPConstants.SFTP_PROXY_USERNAME, proxyUser);
	        sftpDetails.put(SFTPConstants.SFTP_PROXY_PASSWORD, proxyPwd);*/
	        channel = new SFTPChannel();
	        chSftp = channel.getChannel(sftpDetails, 60000);
	        return chSftp;
		} else {
			return chSftp;
		}
	}
	
	public boolean uploadFile(String src, String destFileName) {
		try {
			chSftp = getChannelSftp();
//			chSftp.put(src, filePath+destFileName, new SinpleSftpProgressMonitor(), ChannelSftp.OVERWRITE);
			chSftp.put(src, filePath+destFileName, ChannelSftp.OVERWRITE);
		} catch (Exception e) {
			WfLogger.error("传输文件异常",e);
			return false;
		} 
		return true;
	}
	
	public boolean listFiles(String path) {
		try {
			chSftp = getChannelSftp();
			chSftp.cd(path);
			Vector v = chSftp.ls("*");
			for (int i = 0; i < v.size(); i++) {
				String filePath = path+"/"+v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1);
				WfLogger.error(v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1));
			}
		} catch (Exception e) {
			WfLogger.error("获取文件列表异常",e);
			return false;
		} 
		return true;
	}
	
	public boolean moveFiles(String src, String dest) {
		try {
			chSftp = getChannelSftp();
			chSftp.cd(src);
			Vector v = chSftp.ls("*");
			for (int i = 0; i < v.size(); i++) {
				String srcFilePath = src+"/"+v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1);
				String destFilePath = dest+"/"+v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1);
				WfLogger.error("srcFilePath:"+srcFilePath);
				WfLogger.error("destFilePath:"+destFilePath);
				InputStream is = chSftp.get(srcFilePath);
				chSftp.put(is, destFilePath);
			}
		} catch (Exception e) {
			WfLogger.error("获取文件列表异常",e);
			return false;
		} 
		return true;
	}
	
	public boolean processFile(String srcPath, String logPath, String bakPath) {
		try {
			chSftp = getChannelSftp();
			chSftp.cd(srcPath);
			Vector v = chSftp.ls("*");
			for (int i = 0; i < v.size(); i++) {
				String fileName = v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1);
				String srcFilePath = srcPath+"/"+v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1);
				String logFilePath = logPath+"/"+v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1);
				String bakFilePath = bakPath+"/"+v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1);
				WfLogger.error("srcFilePath:"+srcFilePath);
				WfLogger.error("logFilePath:"+logFilePath);
				WfLogger.error("bakFilePath:"+bakFilePath);
				InputStream is = chSftp.get(srcFilePath);
				
			}
		} catch (Exception e) {
			WfLogger.error("获取文件列表异常",e);
			return false;
		} 
		return true;
	}
	
	public void close() throws Exception {
		try {
			if(chSftp != null) {
				chSftp.quit();
			}
			if(channel !=null) {
				channel.closeChannel();
			}
		} catch (Exception e) {
			WfLogger.error("关闭sftp连接异常",e);
		} 
	}
}
