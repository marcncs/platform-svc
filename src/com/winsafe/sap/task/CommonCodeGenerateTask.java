package com.winsafe.sap.task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.winsafe.hbm.util.StringUtil;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppCommonCodeLog;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.metadata.CodeType;
import com.winsafe.sap.metadata.CommonCodeStatus;
import com.winsafe.sap.metadata.SeedType;
import com.winsafe.sap.pojo.CommonCodeLog;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.sap.service.LabelGenerateService;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.SapConfig;

/*******************************************************************************************  
 * 定时任务类,生成通用码信息
 * @author: ryan.xi	  
 * @date：2014-11-30  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-11-30   ryan.xi   
 * 1.1      2017-10-23   ryan.xi            新增新规QR码的生成流程                  
 *******************************************************************************************  
 */  
public class CommonCodeGenerateTask {

	private static Logger logger = Logger
			.getLogger(CommonCodeGenerateTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private AppCommonCodeLog appCommonCodeLog = new AppCommonCodeLog();
	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
	private AppPrintJob appPrintJob = new AppPrintJob();
	private AppProduct appProduct = new AppProduct();
	//通用码
	private String commonCode;
	//需要分配10为小包装码的物料号
	private String materialCodes;
	//待处理的通用码生成日志
	private static List<CommonCodeLog> commonCodeLogs;

	/**
	 * 初始化要处理的任务
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		commonCode = SapConfig.getSapConfig().getProperty("commonCode");
		materialCodes = SapConfig.getSapConfig().getProperty("materialCodes");
		String whereSql = " where status = "
				+ CommonCodeStatus.NOT_GENERATED.getDatabaseValue() + " order by id";
		commonCodeLogs = appCommonCodeLog.getCommonCodeLogByWhere(whereSql);
	}

	/**
	 * 定时任务入口方法
	 */
	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start genernate primary code.");
					logger.info(DateUtil.getCurrentDate() + " 通用码生成任务---开始---");
					this.init();
					isRunning = true;
					if (commonCodeLogs != null) {
						for (int i = 0; i < commonCodeLogs.size(); i++) {
							long startTime = System.currentTimeMillis();
							if(startProcess(commonCodeLogs.get(i))){
								execute(commonCodeLogs.get(i));
							}
							long endTime = System.currentTimeMillis();
							logger.debug("编号为"
									+ commonCodeLogs.get(i).getPrintJobId()
									+ "的打印任务小包装码生成时间为" + (endTime - startTime)
									+ "ms " + (endTime - startTime) / 1000
									+ "s");
							commonCodeLogs.remove(i);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						logger.info(DateUtil.getCurrentDate() + " 通用码生成任务发生异常:"
								+ e.getMessage());
						logger.error(e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate() + " 通用码生成任务---结束---");
				}
			}
		}

	}
	
	/**
	 * 更新通用码日志状态
	 * @param commonCodeLog
	 * @return
	 */
	private boolean startProcess(CommonCodeLog commonCodeLog) {
		String sql = "update COMMON_CODE_LOG set status = "
				+ CommonCodeStatus.GENERATING.getDatabaseValue()
				+ " where (status = " + CommonCodeStatus.NOT_GENERATED.getDatabaseValue()
				+ " or status = " + CommonCodeStatus.GENERATE_ERROR.getDatabaseValue()
				+ ") and id = " + commonCodeLog.getId();
		try {
			int result = appCommonCodeLog.updCommonCodeLogBySql(sql);
			HibernateUtil.commitTransaction();
			if (result > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(
					"hibernate rollback error when update commonCodeLog's status: "
							+ e.getMessage(), e);
			HibernateUtil.rollbackTransaction();
			return false;
		}
	}

	/**
	 * 执行通用码生成逻辑
	 * @param commonCodeLog
	 */
	private void execute(CommonCodeLog commonCodeLog) {
		logger.debug("start genernate common Code for print job : " + commonCodeLog.getPrintJobId());
		Integer codeLength = 13;
		try {
			
			PrintJob printJob = appPrintJob.getPrintJobByID(commonCodeLog
					.getPrintJobId());
			//判断是否要生成新规QR码
			if(CodeType.QR.getValue().equals(printJob.getCodeType())) {
				Product product = appProduct.getByMCode(commonCodeLog.getMaterialCode());
				LabelGenerateService lgs = new LabelGenerateService(printJob, product);
				lgs.generateCommonCode(printJob, commonCodeLog, commonCode, SeedType.COMMON);
				commonCodeLog.setStatus(CommonCodeStatus.GENERATED
						.getDatabaseValue());
			} else {
				// 判断是否要分配10位的小包装码
				if (materialCodes.contains(commonCodeLog.getMaterialCode())) {
					codeLength = 10;
				}
				Integer avaCount = appPrimaryCode.getAvailablePrimaryCodeCount(codeLength);
				if (avaCount > commonCodeLog.getCount()) {
					// 生成通用码
					int result = appPrimaryCode.createCommonCodeByPrintJobId(commonCodeLog
							.getPrintJobId(), commonCode, commonCodeLog.getCount(),
							codeLength, commonCodeLog.getId());
					//判断预生成的码数量是否足够
					if(result > 0) {
						// 生成通用码下载文件
						createCommonCodeFile(commonCodeLog);
						commonCodeLog.setStatus(CommonCodeStatus.GENERATED
								.getDatabaseValue());
					} else {
						commonCodeLog.setStatus(CommonCodeStatus.GENERATE_ERROR
								.getDatabaseValue());
						commonCodeLog.setErrorMsg("目前没有足够的" + codeLength + "位小包装码，请等待");
					}
				} else {
					commonCodeLog.setStatus(CommonCodeStatus.GENERATE_ERROR
							.getDatabaseValue());
					commonCodeLog.setErrorMsg("目前没有足够的" + codeLength + "位小包装码，请等待");
				}
			}
			updCommonCodeLog(commonCodeLog);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("error occured when geenerating common code", e);
			HibernateUtil.rollbackTransaction();
			commonCodeLog.setStatus(CommonCodeStatus.GENERATE_ERROR
					.getDatabaseValue());
			commonCodeLog.setErrorMsg("生成通用码时系统出错");
			updCommonCodeLog(commonCodeLog);
		}
		logger.debug("end genernate common Code for print job : " + commonCodeLog.getPrintJobId());

	}

	/**
	 * 生成通用码下载文件
	 * @param commonCodeLog
	 * @throws Exception
	 */
	private void createCommonCodeFile(CommonCodeLog commonCodeLog)
			throws Exception {
		logger.debug("start create common Code file for print job : " + commonCodeLog.getPrintJobId());
		Product product = appProduct.getByMCode(commonCodeLog.getMaterialCode());
		int seq = appCommonCodeLog.getSeqForCommonCodeFile(commonCodeLog.getPrintJobId(), commonCodeLog.getId());
		PrintJob printJob = appPrintJob.getPrintJobByID(commonCodeLog
				.getPrintJobId());
		String filePath = FileUploadUtil.getCommonCodeFilePath()
				+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
				+ "/";
		String fileName = "T_" + StringUtil.removeNull(printJob.getProductionLine()) + "_"
				+ product.getProductnameen() +" "+ product.getPackSizeNameEn()
				+ "_" + printJob.getBatchNumber() + "."
				+ printJob.getPrintJobId() + "."+seq+".txt";
		// 删除文件名中特殊字符
		fileName = removeSpecCharInFileName(fileName);
		FileWriter fw = null;
		BufferedWriter bw = null;
		
//		ResultSet primaryCodes = appPrimaryCode.getForGenCommonCodeFile(commonCodeLog.getId(), commonCodeLog.getPrintJobId());
		String sql = "select PRIMARY_CODE, CARTON_CODE from PRIMARY_CODE where COMMON_CODE_LOG_ID = " + commonCodeLog.getId() +" and PRINT_JOB_ID = "+commonCodeLog.getPrintJobId()+" order by PALLET_CODE ,CARTON_CODE";
		Connection con = null;
		Statement statement = null;
		ResultSet primaryCodes = null;
		
		try {
			Session session = HibernateUtil.currentSession();
			con = session.connection();
			statement = con.createStatement();
			primaryCodes = statement.executeQuery(sql);
			
			FileUploadUtil.createEmptyFile(filePath, fileName);
			fw = new FileWriter(filePath+fileName);
			bw = new BufferedWriter(fw);
			while (primaryCodes.next()) {
				StringBuffer sb = new StringBuffer();
				// 2维码数据,包括GTIN码或者物料码，批次号，生产日期和小包装唯一码
				if (StringUtil.isEmpty(printJob.getGTINNumber())) {
					sb.append("01").append(printJob.getMaterialCode());
				} else {
					sb.append("01").append(printJob.getGTINNumber());
				}
				sb.append("10").append(printJob.getBatchNumber());
				sb.append("11").append(printJob.getProductionDate());
				sb.append("21").append(primaryCodes.getString(1)).append(",");
				// 生产日期
				sb.append(printJob.getProductionDate()).append(",");
				// 封装日期，如果生产日期=分装日期，则分装日期为空
				if (printJob.getProductionDate()
						.equals(printJob.getPackagingDate())) {
					sb.append(",");
				} else {
					sb.append(
							printJob.getPackagingDate() != null ? printJob
									.getPackagingDate() : "").append(",");
				}
				// 批次号
				sb.append(printJob.getBatchNumber()).append(",");
				// 箱码
				//10位唯一码， 用5位箱码  tommy 12-16
				if (primaryCodes.getString(1).length()==10 && primaryCodes.getString(2).length()>=6) {
					sb.append(primaryCodes.getString(2).substring(primaryCodes.getString(2).length()-6, primaryCodes.getString(2).length()-1)).append(",");
				} else {
					sb.append(primaryCodes.getString(2)).append(",");
				}
				// 小包装唯一码
				sb.append(primaryCodes.getString(1)).append("\r\n");
				bw.write(sb.toString());
			}
		} catch (Exception e) {
			logger.error("failed to create common Code File",e);
			throw new Exception("创建文件时出错：" + e.getMessage());
		} finally {
			if (primaryCodes != null) {
				primaryCodes.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
			}
		}
		commonCodeLog.setFilePath(filePath + fileName);
		logger.debug("end create common Code file for print job : " + commonCodeLog.getPrintJobId());
	}

	/**
	 * 更新日志状态
	 * @param commonCodeLog
	 */
	private void updCommonCodeLog(CommonCodeLog commonCodeLog) {
		logger.debug("update common Code process status to : " + commonCodeLog.getStatus());
		try { 
			appCommonCodeLog.updCommonCodeLog(commonCodeLog);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("hibernate rollback error: " + e.getMessage(), e);
			HibernateUtil.rollbackTransaction();
		}
	}

	/**
	 * 删除文件名中的特殊字符
	 * @param fileName
	 * @return
	 */
	private String removeSpecCharInFileName(String fileName) {
		return fileName.replaceAll("[*]", "X").replaceAll("\\\\", "")
				.replaceAll("/", "").replaceAll("[:]", "")
				.replaceAll("[?]", "").replaceAll("[<]", "").replaceAll("[>]",
						"").replaceAll("[|]", "").replaceAll("\"", "");
	}
}
