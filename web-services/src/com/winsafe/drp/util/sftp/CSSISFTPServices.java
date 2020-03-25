package com.winsafe.drp.util.sftp;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp; 
import com.winsafe.drp.util.SysConfig;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

public class CSSISFTPServices {
	
	private static Logger logger = Logger.getLogger(CSSISFTPServices.class);
	private ChannelSftp chSftp = null;
	private SFTPChannel channel = null;
	private String filePath = null;
	
	private ChannelSftp getChannelSftp() throws Exception {
		if(chSftp == null) {
			filePath = SysConfig.getSysConfig().getProperty("cssi_sftp_filepath");
			if(StringUtil.isEmpty(filePath)) {
				filePath = "/";
			}
			Map<String, String> sftpDetails = new HashMap<String, String>();
	        // 设置主机ip，端口，用户名，密码
			String pwd = Encrypt.getSecret(SysConfig.getSysConfig().getProperty("cssi_sftp_password"), 2);
	        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, SysConfig.getSysConfig().getProperty("cssi_sftp_host"));
	        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, SysConfig.getSysConfig().getProperty("cssi_sftp_username"));
	        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, pwd);
	        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, SysConfig.getSysConfig().getProperty("cssi_sftp_port"));
	        //获取代理配置
	        String proxyUser = Encrypt.getSecret(SysConfig.getSysConfig().getProperty(SFTPConstants.SFTP_PROXY_USERNAME), 2);
	        String proxyPwd = Encrypt.getSecret(SysConfig.getSysConfig().getProperty(SFTPConstants.SFTP_PROXY_PASSWORD), 2);
	        sftpDetails.put(SFTPConstants.SFTP_PROXY_HOST, SysConfig.getSysConfig().getProperty(SFTPConstants.SFTP_PROXY_HOST));
	        sftpDetails.put(SFTPConstants.SFTP_PROXY_PORT, SysConfig.getSysConfig().getProperty(SFTPConstants.SFTP_PROXY_PORT));
	        sftpDetails.put(SFTPConstants.SFTP_PROXY_USERNAME, proxyUser);
	        sftpDetails.put(SFTPConstants.SFTP_PROXY_PASSWORD, proxyPwd);
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
			logger.error("传输文件异常",e);
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
			logger.error("关闭sftp连接异常",e);
		} 
	}
}
