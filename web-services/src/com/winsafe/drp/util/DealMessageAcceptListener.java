package com.winsafe.drp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.HibernateException;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.assistant.FwIdcodeService;
import com.winsafe.drp.dao.AppMsgReceive;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppQuery;
import com.winsafe.drp.dao.MsgReceive;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Query;
import com.winsafe.drp.metadata.SmsType;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.sms.dao.AppSms;
import com.winsafe.sms.pojo.Sms;

public class DealMessageAcceptListener {
	private static final Properties jfSource = getJFProperties();
	private static final String MSG_ORGAN_NO = jfSource.getProperty("sms_organNo");
	private static final String MSG_PASSWORD = jfSource.getProperty("sms_password");
	private static final String MSG_HOST_REC = jfSource.getProperty("sms_urlprefix");
	private static final String RIGHT_MSG = jfSource.getProperty("rightMsg");
	private static final String ERROR_MSG = jfSource.getProperty("errorMsg");

	private static final String TRY_COUNT = jfSource.getProperty("tryCount");
	private AppCartonCode acc = new AppCartonCode();
	private AppMsgReceive amr = new AppMsgReceive();
	private AppSms as = new AppSms();
	// 日志
	private static Logger logger = Logger.getLogger(DealMessageAcceptListener.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	public void run() {
		logger.debug("dealmessag step ..."+isRunning);
		if (!isRunning) {
			logger.debug("dealmessag step   not  running try to start ...");
			synchronized (lock) {
				try {
					isRunning = true;
					// 调用处理上传文件入口
					executeTask();
				} catch (Exception e) {
					logger.error(e);
				} finally {
					isRunning = false;
				}
			}
			logger.debug("dealmessag step  running end  ..."+isRunning);
		}
	}

	private void executeTask() throws HibernateException, SQLException, Exception {
		// 处理新的短信
		logger.debug("开始接受短信 (start accept SMS) ...");
		handle();
	}

	/**
	 * 处理新的短信
	 * 
	 * @throws IOException
	 */
	private void handle() throws IOException {
		SAXReader reader = new SAXReader();
		String recIS = null;
//		 InputStream recIS = getTestIS();
		try {
			Element rootElement = null;
			recIS = getRecMsgString();
			try {
				Document doc = reader.read(recIS);
				rootElement = doc.getRootElement();
			} catch (DocumentException e) {
				logger.debug("非短信信息 (no SMS message) ...");
			} 
			if (rootElement != null) {
				for (Iterator<?> moIterator = rootElement.elementIterator(); moIterator.hasNext();) {
					try {
						Element mo = (Element) moIterator.next();
						String mobile = mo.element("Msisdn").getTextTrim();
						String message = mo.element("Message").getTextTrim();
						String longCode = mo.element("LongCode").getTextTrim();
						String receiveTime = mo.element("ReceiveTime").getTextTrim();
						String msgType = mo.element("MsgType").getTextTrim();
						// 增加一条内部短信记录
						MsgReceive mr = addMsgReceive(mobile, message, longCode, receiveTime, msgType);

						HibernateUtil.commitTransaction();
						// 新事务

						// 处理业务逻辑
						dealMsgReceive(mobile, message, longCode, receiveTime, msgType, mr);
						HibernateUtil.commitTransaction();
					} catch (Exception e) {
						HibernateUtil.rollbackTransaction();
						logger.error("", e);
					} finally {
						HibernateUtil.closeSession();
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		} 
	}

	/*
	 * 获取数据
	 */
	private InputStream getRecMsgIS() throws IOException {
		String md5Str = MSG_ORGAN_NO + DigestUtils.md5Hex(MSG_PASSWORD);
		String urlStr = MSG_HOST_REC + "?organNo=" + MSG_ORGAN_NO + "&password=" + DigestUtils.md5Hex(MSG_PASSWORD) + "&md5Str=" + DigestUtils.md5Hex(md5Str.getBytes("UTF-8"));
		URL url = new URL(urlStr);
		return url.openStream();
	}
	
	/*
	 * 获取数据
	 */
	private String getRecMsgString() throws IOException {
		//设置请求参数
		String md5Str = MSG_ORGAN_NO + DigestUtils.md5Hex(MSG_PASSWORD);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("organNo", MSG_ORGAN_NO);
		params.put("password", DigestUtils.md5Hex(MSG_PASSWORD));
		params.put("md5Str", DigestUtils.md5Hex(md5Str.getBytes("UTF-8")));
		return HttpUtils.URLGetForSMS(MSG_HOST_REC, params);// 执行查询,得到返回值
	}

	/*
	 * 处理返回结果
	 */
	private void getSendMsgIS(String mobile, String content, String longCode, String receiveTime, String msgType, MsgReceive mr, boolean stateFlag) throws Exception {
		// 处理返回结果
		// 短信信息记录
		Long idLong = addSms(mobile, content);
		if (stateFlag && idLong != 0) {
			// 更新状态
			mr.setSmsid(String.valueOf(idLong));
			mr.setIssend("1");
			amr.updMsgReceive(mr);
		}
	}

	/**
	 * 读取配置文件
	 * 
	 * @return
	 */
	private static Properties getJFProperties() {
		InputStream is = DealMessageAcceptListener.class.getClassLoader().getResourceAsStream("MsgReceive.properties");
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	/**
	 * 短信产品验证查询
	 * 
	 * @param mobile
	 * @param message
	 * @param longCode
	 * @param receiveTime
	 * @param msgType
	 * @throws Exception
	 */
	private void dealMsgReceive(String mobile, String message, String longCode, String receiveTime, String msgType, MsgReceive mr) throws Exception {

		message = message == null ? "" : message;
		// 信息格式错误
		// if (!checkFormat(message)) {
		// sendResponseMsg(mobile, MessageFormat.format(ERROR_MSG, message),
		// longCode,
		// receiveTime, msgType, mr, true);
		// return;
		// }
		// 产品验证查询
		// tommy add(小包装唯一码，应该是大写的，去掉空格。
		searchCode(mobile, message.trim().toUpperCase(), longCode, receiveTime, msgType, mr);

	}

	/**
	 * 模拟获取短信
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	private InputStream getTestIS() throws FileNotFoundException {
		File file = new File("d:\\motest.xml");
		InputStream is = new FileInputStream(file);
		return is;
	}

	/**
	 * 处理发送
	 * 
	 * @param mobile
	 * @param content
	 * @param longCode
	 * @param receiveTime
	 * @param msgType
	 * @param mr
	 * @param stateFlag
	 * @throws Exception
	 */
	private void sendResponseMsg(String mobile, String content, String longCode, String receiveTime, String msgType, MsgReceive mr, boolean stateFlag) throws Exception {
		getSendMsgIS(mobile, content, longCode, receiveTime, msgType, mr, stateFlag);
	}

	/**
	 * 格式判断
	 * 
	 * @param message
	 * @return
	 */
	private boolean checkFormat(String message) {
		if (com.winsafe.hbm.util.StringUtil.isEmpty(message)) {
			return false;
		}

		if (message.trim().length() != 10 && message.trim().length() != 13 && message.trim().length() != 50 && message.trim().length() != 53) {
			return false;
		}
		return true;
	}

	/**
	 * 查询该查询码
	 * 
	 * @param mobile
	 * @param primaryCode
	 * @param longCode
	 * @param receiveTime
	 * @param msgType
	 * @param mr
	 * @throws Exception
	 */
	private void searchCode(String mobile, String primaryCode, String longCode, String receiveTime, String msgType, MsgReceive mr) throws Exception {
		String fwnidcode = "";
		CartonCode cc = new CartonCode();
		FwIdcodeService fis = new FwIdcodeService();
		int query_mode = Constants.CASE_QUERY_MODE_MOBILE;
		boolean showResult = false;
		PrimaryCode pc = null;
		PrintJob printJob = null;
		Product product = null;
		Query query = null;

		Map<String, Object> queryMap = fis.fwIdcodeQuery(primaryCode, query_mode, showResult, null, mobile);
		if (queryMap.get("primaryCode") != null) {
			pc = (PrimaryCode) queryMap.get("primaryCode");
		}
		if (queryMap.get("query") != null) {
			query = (Query) queryMap.get("query");
		}
		if (queryMap.get("product") != null) {
			product = (Product) queryMap.get("product");
		}
		if (queryMap.get("printJob") != null) {
			printJob = (PrintJob) queryMap.get("printJob");
		}

		// 判断输入的查询码是否存在
		if (pc != null && pc.getId() != null) {
			fwnidcode = fis.getFwidcode(primaryCode);
			if (queryMap.get("query") != null) {
				query = (Query) queryMap.get("query");
			}

			// 正确结果
			if (printJob != null && printJob.getPrintJobId() != null) {
				sendResponseMsg(mobile, MessageFormat.format(RIGHT_MSG, primaryCode, query.getQueryNum(), printJob.getMaterialName(), product.getSpecmode(), Dateutil.formatDate(DateUtil
						.formatStrDate(printJob.getProductionDate())), printJob.getBatchNumber()), longCode, receiveTime, msgType, mr, true);
			} else {
				sendResponseMsg(mobile, MessageFormat.format(RIGHT_MSG, primaryCode, query.getQueryNum(), "", "", "", ""), longCode, receiveTime, msgType, mr, true);
			}
		} else {
			// 查询不到处理
			sendResponseMsg(mobile, MessageFormat.format(ERROR_MSG, primaryCode), longCode, receiveTime, msgType, mr, true);
		}

	}

	/**
	 * 增加一条发送短息记录
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
	private Long addSms(String mobile, String content) {
		Sms sms = new Sms();
		sms.setType(SmsType.VALIDATE.getDbValue());
		sms.setMobileNo(mobile);
		sms.setContent(content);
		sms.setSendStatus(0);
		sms.setCreateDate(new Date());
		sms.setTryCount(Integer.valueOf(TRY_COUNT));
		as.addSms(sms);
		return sms.getId();
	}

	/**
	 * 增加一条内部短息产品验证记录
	 * 
	 * @param mobile
	 * @param message
	 * @param longCode
	 * @param receiveTime
	 * @param msgType
	 * @return
	 */
	private MsgReceive addMsgReceive(String mobile, String message, String longCode, String receiveTime, String msgType) {

		MsgReceive mr = new MsgReceive();
		mr.setMobileno(mobile);
		mr.setContent(message);
		mr.setTrycount(Integer.valueOf(TRY_COUNT));
		if (!StringUtil.isEmpty(receiveTime)) {
			mr.setReceivedate(Dateutil.StringToDatetime(receiveTime));
		}
		mr.setCreatedate(new Date());
		mr.setMsgtype(msgType);
		mr.setLongcode(longCode);
		mr.setIssend("0");
		amr.addMsgReceive(mr);
		return mr;
	}
}
