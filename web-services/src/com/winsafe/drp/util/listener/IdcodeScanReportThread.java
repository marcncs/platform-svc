package com.winsafe.drp.util.listener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppSystemResource;
import com.winsafe.drp.dao.SystemResource;
import com.winsafe.drp.server.IdcodeScanRateService;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MailService;

public class IdcodeScanReportThread extends Thread{ 
	private Logger logger=Logger.getLogger(IdcodeScanReportThread.class);
	private static boolean isRunning=false;
	private static Object lock = new Object();
	private AppSystemResource appsr=new AppSystemResource();

	private String tagName="扫描率报表定时发送"; 
	private String lastSendDate;
	private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat sdfHHmm = new SimpleDateFormat("HH:mm");
	
	public IdcodeScanReportThread(){
		//获取初始数据
		try{
			SystemResource sr=appsr.getSystemResourceValue(tagName, "保存上一次发送日期");
			lastSendDate=sr.getTagvalue();
		}catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public void run(){
		if (!isRunning) {
			synchronized (lock) {
				isRunning=true;
				
				//间隔
				long interval=5*60*1000;
				//long interval=1000;
				while (isRunning) {
					try {
						
						doRun();
						
					} catch (Exception e) {
						logger.error("", e);
					} finally {
						try {
							Thread.sleep(interval);
						} catch (Exception e) {
							logger.error("", e);
						}
					}

				}
				
				isRunning=false;
			}
		}
	}

	private void doRun() throws Exception{
		Date now=new Date();
		SystemResource sr;

		HibernateUtil.currentSession().clear();
		//--------for debug--------
		sr=appsr.getSystemResourceValue(tagName, "保存上一次发送日期");
		lastSendDate=sr.getTagvalue();
		//-------------------------
		
		if(StringUtil.isEmpty(lastSendDate)){
			Date yesterday = DateUtil.addDay2Date(-1, now);
			lastSendDate=df.format(yesterday);
		}
		sr=appsr.getSystemResourceValue(tagName, "发送时间");
		String sendTime=sr.getTagvalue();	

		if(StringUtil.isEmpty(sendTime)){
			return;
		}
		
		String hhmm=sdfHHmm.format(now);
		String strToday=df.format(now);
		//判断是否已经到达发送时间
		if(lastSendDate.compareTo(strToday)<0 && hhmm.compareTo(sendTime)>=0){
			
		}else{
			return;
		}
		lastSendDate=strToday;

		MailService mailService = new MailService();

		sr=appsr.getSystemResourceValue(tagName, "邮件标题");
		String title=sr.getTagvalue();
		sr=appsr.getSystemResourceValue(tagName, "邮件内容");
		String content=sr.getTagvalue();
		
		List<SystemResource> srList=appsr.querySystemResourceValue(tagName, "收件人");
		for(SystemResource s:srList){
			mailService.addMailTo(s.getTagvalue(),",","TO");
		}

		mailService.setSubject(title);
		
		IdcodeScanRateService reportService=new IdcodeScanRateService();
		File file=reportService.getFile();
		if(!file.exists()){
			logger.error("扫描率报表没有正确生成！");
			return;
		}
		content=content.replaceAll("\\{filename\\}", file.getName());
		mailService.setContent(content);
		
		mailService.addAttachment(file.getAbsolutePath());
		mailService.send();
		
		try {
			sr=appsr.getSystemResourceValue(tagName, "保存上一次发送日期");
			sr.setTagvalue(lastSendDate);
			appsr.updSystemResource(sr);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			try{
				HibernateUtil.rollbackTransaction();
			} catch (Exception ex) {
			}
			String msg="报表线程更新日期错误";
			logger.error(msg, e);
		}
	}
}
