package com.winsafe.drp.task;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.winsafe.drp.dao.AppMember;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

public class EncryptUserMobileTask {

	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(EncryptUserMobileTask.class);

//	private static List<Map<String,String>> userMobiles;
	
	private AppUsers appUsers = new AppUsers();
	
	Integer duplicateCount = 0;

	/**
	 * 初始化要处理的任务
	 */
	public void init() throws Exception {
//		userMobiles = appUsers.getUserMobiles();
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start processing sap upload file.");
					this.init();
					isRunning = true;
					logger.info(DateUtil.getCurrentDate()
							+ " 加密用户表手机号字段任务---开始---");
					execute();
				} catch (Exception e) {
					HibernateUtil.rollbackTransaction();
					logger.error(e);
					logger.info(DateUtil.getCurrentDate() + " 加密用户表手机号字段任务发生异常"
							+ e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 加密用户表手机号字段任务---结束---");
				}
			}
		}
	}

	private void execute() throws Exception {
		String sql1 = null;
		String sql2 = null;
		String sql3 = null;
		String sql4 = null;
		logger.debug("--------------start encrypt users table-----------------");
		encryptUsersTable();
		logger.debug("--------------end encrypt users table-----------------");
		
		logger.debug("--------------start encrypt MEMBER table-----------------");
		sql1 ="select id userid,MOBILE from MEMBER ";
		sql2 ="UPDATE MEMBER set MOBILE='";
		sql3 ="' where id = ";
		encryptMobile(sql1, sql2, sql3);
		logger.debug("--------------end encrypt MEMBER table-----------------");
		
		logger.debug("--------------start encrypt NOTIFICATION table-----------------");
		sql1 ="select id userid,CONSIGNEEMOBILE mobile from NOTIFICATION";
		sql2 ="update NOTIFICATION set CONSIGNEEMOBILE='";
		sql3 ="' where id = ";
		encryptMobile(sql1, sql2, sql3);
		logger.debug("--------------end encrypt NOTIFICATION table-----------------");
		
		logger.debug("--------------start encrypt OLINKMAN table-----------------");
		sql1 ="select id userid,mobile from OLINKMAN";
		sql2 ="update OLINKMAN set mobile='";
		sql3 ="' where id = ";
		encryptMobile(sql1, sql2, sql3);
		logger.debug("--------------end encrypt OLINKMAN table-----------------");
		
		logger.debug("--------------start encrypt S_BONUS_ACCOUNT table-----------------");
		sql1 ="select accountid userid,mobile from S_BONUS_ACCOUNT";
		sql2 ="update S_BONUS_ACCOUNT set mobile='";
		sql3 ="' where accountid = ";
		encryptMobile(sql1, sql2, sql3);
		logger.debug("--------------end encrypt S_BONUS_ACCOUNT table-----------------");
		
		logger.debug("--------------start encrypt SMS table-----------------");
		sql1 ="select id userid,mobileno mobile from SMS";
		sql2 ="update SMS set mobileno='";
		sql3 ="' where id = ";
		encryptMobile(sql1, sql2, sql3);
		logger.debug("--------------end encrypt SMS table-----------------");
		
		logger.debug("--------------start encrypt WLINKMAN table-----------------");
		sql1 ="select id userid,mobile from WLINKMAN";
		sql2 ="update WLINKMAN set mobile='";
		sql3 ="' where id = ";
		encryptMobile(sql1, sql2, sql3);
		logger.debug("--------------end encrypt WLINKMAN table-----------------");
		
		logger.debug("--------------start encrypt WAREHOUSE table-----------------");
		sql1 ="select id userid,WAREHOUSETEL mobile from WAREHOUSE";
		sql2 ="update WAREHOUSE set WAREHOUSETEL='";
		sql3 ="' where id = ";
		encryptMobile(sql1, sql2, sql3);
		logger.debug("--------------end encrypt WAREHOUSE table-----------------");
		
		logger.debug("--------------start encrypt ORGAN table-----------------");
		sql1 ="select id userid, omobile mobile from ORGAN ";
		sql2 ="update ORGAN set omobile='";
		sql3 ="' where id = ";
		encryptMobile(sql1, sql2, sql3);
		logger.debug("--------------end encrypt WAREHOUSE table-----------------");
		
		logger.debug("--------------start encrypt STOCK_ALTER_MOVE table-----------------");
		sql1 ="select id userid,otel mobile from STOCK_ALTER_MOVE ";
		sql2 ="update STOCK_ALTER_MOVE set otel='";
		sql3 ="' where id = '";
		sql4 ="'";
		encryptMobile(sql1, sql2, sql3, sql4);
		logger.debug("--------------end encrypt STOCK_ALTER_MOVE table-----------------");
		
		logger.debug("--------------start encrypt TAKE_TICKET table-----------------");
		sql1 ="select id userid,tel mobile from TAKE_TICKET ";
		sql2 ="update TAKE_TICKET set tel='";
		sql3 ="' where id = '";
		sql4 ="'";
		encryptMobile(sql1, sql2, sql3, sql4);
		logger.debug("--------------end encrypt TAKE_TICKET table-----------------");
		
		logger.debug("--------------start encrypt STOCK_MOVE table-----------------");
		sql1 ="select id userid,otel mobile from STOCK_MOVE ";
		sql2 ="update STOCK_MOVE set otel='";
		sql3 ="' where id = '";
		sql4 ="'";
		encryptMobile(sql1, sql2, sql3, sql4);
		logger.debug("--------------end encrypt STOCK_MOVE table-----------------");
		
		logger.debug("--------------start encrypt ORGAN_WITHDRAW table-----------------");
		sql1 ="select id userid,tel mobile from ORGAN_WITHDRAW ";
		sql2 ="update ORGAN_WITHDRAW set tel='";
		sql3 ="' where id = '";
		sql4 ="'";
		encryptMobile(sql1, sql2, sql3, sql4);
		logger.debug("--------------end encrypt ORGAN_WITHDRAW table-----------------");
		
		logger.debug("--------------start encrypt MOVE_APPLY table-----------------");
		sql1 ="select id userid,otel mobile from MOVE_APPLY ";
		sql2 ="update MOVE_APPLY set otel='";
		sql3 ="' where id = '";
		sql4 ="'";
		encryptMobile(sql1, sql2, sql3, sql4);
		logger.debug("--------------end encrypt MOVE_APPLY table-----------------");
	}

	private void encryptMobile(String sql1, String sql2, String sql3,
			String sql4) throws Exception {
		List<Map<String,String>> mobiles = EntityManager.jdbcquery(sql1);
		if (mobiles != null && mobiles.size() > 0) {
			for(Map<String,String> map : mobiles) {
				String userId = map.get("userid");
				String mobile = map.get("mobile");
				if(!StringUtil.isEmpty(mobile)) {
					if(mobile.length() < 24) {
						mobile = Encrypt.getSecret(mobile, 3);
						EntityManager.executeUpdate(sql2+mobile+sql3+userId+sql4);
					}
				}
			}
		}
		HibernateUtil.commitTransaction();
		
	}

	private void encryptMobile(String sql1, String sql2, String sql3) throws Exception {
		List<Map<String,String>> mobiles = EntityManager.jdbcquery(sql1);
		if (mobiles != null && mobiles.size() > 0) {
			for(Map<String,String> map : mobiles) {
				String userId = map.get("userid");
				String mobile = map.get("mobile");
				if(!StringUtil.isEmpty(mobile)) {
					if(mobile.length() < 24) {
						mobile = Encrypt.getSecret(mobile, 3);
						EntityManager.executeUpdate(sql2+mobile+sql3+userId);
					}
				}
			}
		}
		HibernateUtil.commitTransaction();
	}

	private void encryptUsersTable() throws Exception {
		List<Map<String,String>> mobiles = appUsers.getUserMobiles();
		if (mobiles != null && mobiles.size() > 0) {
			for(Map<String,String> map : mobiles) {
				String userId = map.get("userid");
				String mobile = map.get("mobile");
				if(!StringUtil.isEmpty(mobile)) {
					if(mobile.length() < 24) {
						mobile = Encrypt.getSecret(mobile, 3);
						appUsers.updateUserMobile(userId, mobile);
					}
				}
			}
		}
		HibernateUtil.commitTransaction();
	}

}
