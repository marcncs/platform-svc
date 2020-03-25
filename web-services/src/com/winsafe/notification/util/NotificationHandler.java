package com.winsafe.notification.util;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppWlinkMan;
import com.winsafe.drp.dao.Wlinkman;
import com.winsafe.drp.metadata.SmsSendStatus;
import com.winsafe.drp.metadata.SmsType;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.SmsUtil;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.notification.dao.AppNotification;
import com.winsafe.notification.dao.AppNotificationDetail;
import com.winsafe.notification.pojo.Notification;
import com.winsafe.notification.pojo.NotificationDetail;
import com.winsafe.notification.pojo.NotificationLog;
import com.winsafe.sms.dao.AppSms;
import com.winsafe.sms.pojo.Sms;

public class NotificationHandler {
	
	private static Logger logger = Logger.getLogger(NotificationHandler.class);
	
	private AppNotification appNotification = new AppNotification();
	private AppNotificationDetail appNotificationDetail = new AppNotificationDetail();
	
	private AppSms appSms = new AppSms();
	
	private AppWlinkMan appLinkMan = new AppWlinkMan();
	private boolean hasError = false;
	
	
	//保存报错信息
	protected StringBuffer errMsg = new StringBuffer();
	
	private List<Notification> notifications = new ArrayList<Notification>();
	
	
	public void addNotification(Notification notification) {
		// tommy 去掉检查， 手机号检查发现错误
		//if(!doValidate(notification)) {
			notifications.add(notification);
		//}
	}
	
	private boolean doValidate(Notification notification) {
		
		if(!StringUtil.isEmpty(notification.getConsigneeMobile())) {
			if(notification.getConsigneeMobile().length() != 11 || notification.getConsigneeMobile().matches("[0-9]*")) {
				errMsg.append("联系人手机格式不正确");
				hasError = true;
			}
		} else {
			errMsg.append("数据不全：联系人手机为空");
			hasError = true;
		}
		
		return hasError;
		
	}

	/**
	 * 初始化Digester框架类并开始解析文件
	 * Create Time 2014-10-20 上午11:23:26
	 * @param file 要解析的文件
	 * @throws IOException, SAXException
	 * @author Ryan.xi
	 */
	public boolean parse(File file) {
		hasError = false;
		Digester digester = new Digester();
		digester.push(this);
		addRule(digester);
		//addConvertor();  tommy 不要日期转换 
		try {
			digester.parse(file);
		} catch (SAXParseException e) {
			WfLogger.error("failed to parse file :" + file.getName(), e);
			hasError = true;
			if(e.getException() instanceof ConversionException) {
				errMsg.append("日期格式错误, 可处理的格式为yyyy-MM-dd hh:mm(例:2015-01-23 13:20)").append("\r\n");
			} else {
				errMsg.append("解析文件时发生错误，请确认文件格式是否正确:"+e.getMessage()).append("\r\n");
			}
		} catch (IOException e) {
			WfLogger.error("failed to read file :" + file.getName(), e);
			hasError = true;
			errMsg.append("读取文件时错误:"+e.getMessage()).append("\r\n");

			e.printStackTrace();
		} catch (SAXException e) {
			WfLogger.error("failed to read file :" + file.getName(), e);
			hasError = true;
			errMsg.append("读取文件时错误:"+e.getMessage()).append("\r\n");

			e.printStackTrace();
		} 
		checkResult();
		return hasError;
	}
	
	public boolean parse(InputStream file) {
		hasError = false;
		Digester digester = new Digester();
		digester.push(this);
		addRule(digester);
		//addConvertor();  tommy 不要日期转换 
		try {
			digester.parse(file);
		} catch (SAXParseException e) {
			WfLogger.error("failed to parse file :", e);
			hasError = true;
			if(e.getException() instanceof ConversionException) {
				errMsg.append("日期格式错误, 可处理的格式为yyyy-MM-dd hh:mm(例:2015-01-23 13:20)").append("\r\n");
			} else {
				errMsg.append("解析文件时发生错误，请确认文件格式是否正确:"+e.getMessage()).append("\r\n");
			}
		} catch (IOException e) {
			WfLogger.error("failed to read file :", e);
			hasError = true;
			errMsg.append("读取文件时错误:"+e.getMessage()).append("\r\n");

			e.printStackTrace();
		} catch (SAXException e) {
			WfLogger.error("failed to read file :", e);
			hasError = true;
			errMsg.append("读取文件时错误:"+e.getMessage()).append("\r\n");
		} finally {
			if(file!=null) {
				try {
					file.close();
				} catch (IOException e) {
					WfLogger.error("", e);
				}
			}
		}
		checkResult();
		return hasError;
	}
	
	private void checkResult() {
		if(notifications.size() == 0 && !hasError) {
			hasError = true;
			errMsg.append("未从文件中读取到相关数据").append("\r\n");
		}
		
	}

	private void addConvertor() {
		//String pattern = "MM/dd/yyyy hh:mm:ss a";
		String pattern = "yyyy-MM-dd hh:mm";
        Locale locale = Locale.US;
        DateLocaleConverter converter = new DateLocaleConverter(locale, pattern);
        converter.setLenient(true);
        ConvertUtils.register(converter, java.util.Date.class);
	}

	public StringBuffer getErrMsg() {
		return errMsg;
	}

	public void addRule(Digester digester) {
		digester.setValidating(false);  
		// 指明匹配模式和要创建的类   
		digester.addObjectCreate("DATA/Notification", Notification.class);  
		digester.addBeanPropertySetter("DATA/Notification/DeliveryNo", "deliveryNo");  
		digester.addBeanPropertySetter("DATA/Notification/DeliveryOrderType", "deliveryOrderType");  
		digester.addBeanPropertySetter("DATA/Notification/LogisticCompany", "logisticCompany");  
		digester.addBeanPropertySetter("DATA/Notification/DeliveryPlace", "deliveryPlace");  
		//tommy 修改， 直接接受字符串 
		//digester.addBeanPropertySetter("DATA/Notification/DeliveryDate", "deliveryDate");  
		//digester.addBeanPropertySetter("DATA/Notification/EstimateDate", "estimateDate");  
		digester.addBeanPropertySetter("DATA/Notification/DeliveryDate", "deliveryDateStr");  
		digester.addBeanPropertySetter("DATA/Notification/EstimateDate", "estimateDateStr");  

		digester.addBeanPropertySetter("DATA/Notification/Quantity", "quantity");
		digester.addBeanPropertySetter("DATA/Notification/CasesNo", "casesNo");
		digester.addBeanPropertySetter("DATA/Notification/ShipToCode", "shipToCode");
		digester.addBeanPropertySetter("DATA/Notification/ShipToCompany", "shipToCompany");
		digester.addBeanPropertySetter("DATA/Notification/ShipToAddress", "shipToAddress");
		digester.addBeanPropertySetter("DATA/Notification/ConsigneeName", "consigneeName");
		digester.addBeanPropertySetter("DATA/Notification/ConsigneeMobile", "consigneeMobile");
		digester.addBeanPropertySetter("DATA/Notification/Delivery_VerifyCode", "deliveryVerifyCode");
		digester.addSetNext("DATA/Notification", "addNotification");
		digester.addObjectCreate("DATA/Notification/Notification_Detail", NotificationDetail.class );
		digester.addBeanPropertySetter("DATA/Notification/Notification_Detail/LineItem", "lineItem");
		digester.addBeanPropertySetter("DATA/Notification/Notification_Detail/MaterialCode", "materialCode");
		digester.addBeanPropertySetter("DATA/Notification/Notification_Detail/Products", "products");
		digester.addBeanPropertySetter("DATA/Notification/Notification_Detail/PackSize", "packSize");
		digester.addBeanPropertySetter("DATA/Notification/Notification_Detail/Quantity", "quantity");
		digester.addBeanPropertySetter("DATA/Notification/Notification_Detail/CasesNo", "casesNo");
		digester.addSetNext("DATA/Notification/Notification_Detail", "addNotificationDetails");
	}
	//tommy add for cheng date format
	public String getDateFormat(String inputDateStr){
		String dtformat="yyyy-MM-dd HH:mm";
		if (inputDateStr.length()==10) {
			dtformat="yyyy-MM-dd";
		} 		
		if (inputDateStr.length()==16) {
			dtformat="yyyy-MM-dd HH:mm";
		} 
		if (inputDateStr.length()==19) {
			dtformat="yyyy-MM-dd HH:mm:ss";
		} 
		if (inputDateStr.length()==22) {
			dtformat="yyyy-MM-dd h:mm:ss a";
		} 
		
		return dtformat;
	}
	
	public boolean handle(String fullFilePathErr, File file, String serviceCall){
		return handle(fullFilePathErr, file.getName(), file.getPath(), serviceCall);
	}
	
	public boolean handle(String fullFilePathErr, String fileName, String filePath, String serviceCall){
		logger.debug("save notification and detail info into DB");
		Properties sysPro = null;
		try {
			sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		} catch (IOException e1) {
			WfLogger.error("加载配置文件system.properties出错", e1);
		}
		for(Notification notification :notifications) {
			try {
				notification.setFileName(fileName);
				notification.setFilePath(filePath);
				
				//tommy add 转换日期
				String dtformat="yyyy-MM-dd HH:mm";
				Date date =null;
				SimpleDateFormat dateFormat = new SimpleDateFormat(dtformat);
				if (null!=notification.getDeliveryDateStr()) {
					dtformat=getDateFormat(notification.getDeliveryDateStr());
					dateFormat = new SimpleDateFormat(dtformat);
					try { 
					  dateFormat.setLenient(false); 
			           date = dateFormat.parse(notification.getDeliveryDateStr()); 
			          
					} catch (Exception e) { 
			    	   date= null;
					} 
					notification.setDeliveryDate(date);
				}
				if (null!=notification.getEstimateDateStr()) {
					dtformat=getDateFormat(notification.getEstimateDateStr());
					dateFormat = new SimpleDateFormat(dtformat);
					try { 
					  dateFormat.setLenient(false); 
			           date = dateFormat.parse(notification.getEstimateDateStr()); 
			          
					} catch (Exception e) { 
			    	   date= null;
					}
					notification.setEstimateDate(date);
				}
				// 修改结束 
				//#start add by ryan.xi at 20150518 
				if(sysPro != null && "1".equals(sysPro.get("sendNotificationToWarehouseLinkMan"))) {
					String mobiles = "";
					//根据shiptoCode获取仓库联系人
					List<Wlinkman> linkMans = appLinkMan.getWlinkmanByNccode(notification.getShipToCode());
					if(linkMans != null && linkMans.size() > 0) {
						for(Wlinkman wl : linkMans) {
							mobiles = mobiles + wl.getMobile() + ",";
						}
					} 
//					else {
//						//根据单据中的收获仓库获取仓库联系人
//						StockAlterMove sam = appStockAlterMove.getStockAlterMoveByNCcode(notification.getDeliveryNo());
//						if(sam != null) {
//							linkMans = appLinkMan.getWlinkmanByWid(sam.getInwarehouseid());
//							if(linkMans != null && linkMans.size() > 0) {
//								for(Wlinkman wl : linkMans) {
//									mobiles = mobiles + wl.getMobile() + ",";
//								}
//							} 
//						}
//					}
					if(mobiles.length() > 0) {
						mobiles = mobiles.substring(0,mobiles.length() -1);
						notification.setConsigneeMobile(mobiles);
					}
				}
				//#end
				notification.setServiceCall(serviceCall);
				
				Long smsId = createSmsMessage(notification);
				notification.setSmsId(smsId);
				appNotification.addNotification(notification);
				for(NotificationDetail nd : notification.getNotificationDetails()) {
					nd.setDeliveryNo(Long.toString(notification.getId()));
				}
				appNotificationDetail.addNotificationDetails(notification.getNotificationDetails());
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				WfLogger.error("errpr occured when saving or updating notification", e);
				hasError = true;
				errMsg.append("保存或更新物流信息到系统时发生错误").append("\r\n");
				HibernateUtil.rollbackTransaction();
			}
		}
		HibernateUtil.closeSession();
		return hasError;
	}

	private Long createSmsMessage(Notification notification) throws Exception {
		logger.debug("create sms message for notification with id :" + notification.getId());
		Sms sms = new Sms(); 
		sms.setType(SmsType.GOODS_DELIVERT.getDbValue());
		sms.setCreateDate(DateUtil.getCurrentDate());
		sms.setMobileNo(notification.getConsigneeMobile());
		sms.setSendStatus(SmsSendStatus.NOT_SEND.getDbValue());
		sms.setTryCount(Integer.parseInt(SmsUtil.getSmsConfig().getString("tryCount")));
		String template = "";
		if(isFirstMessage(notification)) {
			template = SmsUtil.getSmsConfig().getString(SmsSendStatus.NOT_SEND.getDbValue().toString());
		} else {
			template = SmsUtil.getSmsConfig().getString("1");
		}
		template = SmsUtil.converTemplate(template, MapUtil.objectToMap(notification));
		sms.setContent(template);
		appSms.addSms(sms);
		return sms.getId();
	}
	
	private boolean isFirstMessage(Notification notification) throws IOException {
		boolean isFirst = false;
		NotificationLog log = appNotification.getNotificationLog(notification.getDeliveryNo());
		String url = SysConfig.getSysConfig().getProperty("sms_delivery_url_prefix");
		if(url==null || StringUtil.isEmpty(url)) {
			url = "https://rtci.bayer.cn/RTCI/cm/de/";
		}
		if(log == null) {
			isFirst = true;
			log = new NotificationLog();
			log.setDeliveryNo(notification.getDeliveryNo());
			if(!StringUtil.isEmpty(notification.getDeliveryVerifyCode())) {
				log.setCode(notification.getDeliveryVerifyCode());
			} else {
				log.setCode(UUID.randomUUID().toString().substring(25));
			}
			log.setMakeDate(DateUtil.getCurrentDate());
			appNotification.addNotificationLog(log);
		}
		url = url+log.getCode()+"/"+log.getDeliveryNo();
		notification.setUrl(url);
		return isFirst;
	}

}
