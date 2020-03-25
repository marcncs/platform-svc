package com.winsafe.drp.util.sftp;

import java.util.Map;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.winsafe.drp.util.WfLogger;

public class SFTPChannel {
    Session session = null;
    Channel channel = null;
    ChannelExec execChannel = null;

    public ChannelSftp getChannel(Map<String, String> sftpDetails, int timeout) throws JSchException {

        String ftpHost = sftpDetails.get(SFTPConstants.SFTP_REQ_HOST);
        String port = sftpDetails.get(SFTPConstants.SFTP_REQ_PORT);
        String ftpUserName = sftpDetails.get(SFTPConstants.SFTP_REQ_USERNAME);
        String ftpPassword = sftpDetails.get(SFTPConstants.SFTP_REQ_PASSWORD);

        int ftpPort = SFTPConstants.SFTP_DEFAULT_PORT;
        if (port != null && !port.equals("")) {
            ftpPort = Integer.valueOf(port);
        }

        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
        WfLogger.error("Session created.");
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        //设置HTTP代理 
        /*ProxyHTTP proxy = new ProxyHTTP(sftpDetails.get(SFTPConstants.SFTP_PROXY_HOST), Integer.parseInt(sftpDetails.get(SFTPConstants.SFTP_PROXY_PORT)));
        if(!StringUtil.isEmpty(sftpDetails.get(SFTPConstants.SFTP_PROXY_USERNAME))
        		&& !StringUtil.isEmpty(sftpDetails.get(SFTPConstants.SFTP_PROXY_PASSWORD))) {
        	proxy.setUserPasswd(sftpDetails.get(SFTPConstants.SFTP_PROXY_USERNAME), sftpDetails.get(SFTPConstants.SFTP_PROXY_PASSWORD));
        }
        session.setProxy(proxy);*/
        
        session.connect(); // 通过Session建立链接
        WfLogger.error("Session connected.");
        execChannel = (ChannelExec) session.openChannel("exec");
        
        WfLogger.error("Opening Channel.");
        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        WfLogger.error("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
                + ", returning: " + channel);
        return (ChannelSftp) channel;
    }
    
    public ChannelExec getChannelExec() {
    	return execChannel;
    }

    public void closeChannel() {
        if (channel != null) {
            channel.disconnect();
        }
        if(execChannel != null) {
        	execChannel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
}