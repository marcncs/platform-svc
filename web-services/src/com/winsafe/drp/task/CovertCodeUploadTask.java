package com.winsafe.drp.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.UploadPrLog;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.metadata.CovertCodeErrType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.mail.dao.AppBatchCompleteMail;
import com.winsafe.mail.metadata.MailType;
import com.winsafe.mail.pojo.BatchCompleteMail;
import com.winsafe.mail.smtp.base.SMTPMailFactory;
import com.winsafe.sap.dao.AppCovertErrorLog;
import com.winsafe.sap.dao.AppCovertUploadReport;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.CovertUploadReport;
import com.winsafe.sap.pojo.CovertUploadReportBean;
import com.winsafe.sap.util.FileUploadUtil;

public class CovertCodeUploadTask {

	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(CovertCodeUploadTask.class);

	private static List<UploadPrLog> uploadPrLogs;
	AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
	AppCovertUploadReport appCovertUploadReport = new AppCovertUploadReport();
	AppCovertErrorLog appCovertErrorLog = new AppCovertErrorLog();
	private Map<String, Product> existMaterialCodes = new HashMap<String, Product>();
	private Set<String> notExistsMaterialCodes = new HashSet<String>();
	protected AppProduct appProduct = new AppProduct();
	private AppBatchCompleteMail abcm = new AppBatchCompleteMail();
	//产线对应批次号
	private Map<String, String> batchNumberMap = new HashMap<String, String>();
	
	private RecordDao recordDao = new RecordDao();
	
	Integer duplicateCount = 0;

	/**
	 * 初始化要处理的任务
	 */
	public void init() throws Exception {
		uploadPrLogs = recordDao.getAllUnProcessedLog();
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start processing sap upload file.");
					this.init();
					isRunning = true;
					logger.info(DateUtil.getCurrentDate()
							+ " 自动处理暗码上传文件任务---开始---");
					if (uploadPrLogs != null && uploadPrLogs.size() > 0) {
						for (int i = 0; i < uploadPrLogs.size(); i++) {
							if(uploadPrLogs.get(i) == null) {
								continue;
							}
							logger.debug("开始时间：" + uploadPrLogs.get(i).getId()
									+ new Date());
							long startTime = System.currentTimeMillis();
							if (startProcess(uploadPrLogs.get(i))) {
								execute(uploadPrLogs.get(i));
							}
							long endTime = System.currentTimeMillis();
							logger.debug("编号为" + uploadPrLogs.get(i).getId()
									+ "的暗码上传任务处理时间为" + (endTime - startTime)
									+ "ms " + (endTime - startTime) / 1000
									+ "s");
							
							if(duplicateCount > 0) {
								addNotificationMail(uploadPrLogs.get(i));
							}
						}
					}
				} catch (Exception e) {
					logger.error(e);
					logger.info(DateUtil.getCurrentDate() + " 自动处理暗码上传文件任务发生异常"
							+ e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 自动处理暗码上传文件任务---结束---");
				}
			}
		}
	}

	// 更新文件上传日志状态
	private void updUploadPrlog(UploadPrLog uploadPrLog) {
		try {
			recordDao.updUploadPrlog(uploadPrLog);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("error occurred when update uploadPrLog status to : " + uploadPrLog.getIsdeal(), e);
			HibernateUtil.rollbackTransaction();
		}
		
	}

	private boolean startProcess(UploadPrLog uploadPrLog) {
		String sql = "update Upload_Pr_Log set isdeal = 1 where isdeal = 0 and id = "
				+ uploadPrLog.getId();
		try {
			int result = recordDao.updUploadPrlogBySql(sql);
			HibernateUtil.commitTransaction();
			if (result > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(
					"hibernate rollback error when update printJob's primary_code_status: "
							+ e.getMessage(), e);
			HibernateUtil.rollbackTransaction();
			return false;
		}
	}

	/**
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void execute(UploadPrLog uploadPrLog) {
		boolean hasError = false;
		Map<CovertUploadReport, CovertUploadReportBean> reports = new HashMap<CovertUploadReport, CovertUploadReportBean>();
		Integer errorCount = 0;
		// 错误信息
		StringBuffer resultMsg = new StringBuffer();
		String filePath = uploadPrLog.getFilepath();
		File file = new File(filePath);
		String savePath = FileUploadUtil.getCovertCodeLogFilePath()
				+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
				+ "/";
		String fileName = uploadPrLog.getFilename();
		String saveName = DateUtil.getCurrentDateTimeString() + "_"
				+ fileName.substring(fileName.indexOf("."), fileName.length())
				+ "_LOG.txt";

		try {
			if(recordDao.isDuplicateFileExists(uploadPrLog.getFilename())) {
				hasError = true;
				resultMsg.append("已处理过相同的文件");
			} else {
				if (!file.exists()) {
					hasError = true;
					resultMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00214, filePath));
				} else {
					//读取并验证文件中的数据
					readAndCheckFileContent(file, uploadPrLog, resultMsg, reports);
					checkDuplicateCovertCode(uploadPrLog, resultMsg, reports);
					checkDuplicateCovertCodeInLog(uploadPrLog, resultMsg, reports);
					checkAlreadyExistsCovertCode(uploadPrLog, resultMsg, reports);
					updateCovertCodeAndErrorLog(reports);
					createUpdateCovertCodeSql(reports, uploadPrLog);
					updateCovertCode(uploadPrLog, resultMsg, reports);
					addCovertUploadLog(reports);
					HibernateUtil.commitTransaction();
				}
			}

		} catch (Exception e) {
			logger.error("hibernate rollback error: " + e.getMessage(), e);
			hasError = true;
			resultMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00218, e.getMessage()));
			HibernateUtil.rollbackTransaction();
		}

		createLogFile(resultMsg.toString(), savePath, saveName);
		// 更新日志记录
		if (hasError) {
			uploadPrLog.setIsdeal(-1);
		} else {
			uploadPrLog.setIsdeal(2);
		}
		for(CovertUploadReportBean reportBean : reports.values()) {
			errorCount = errorCount + reportBean.getErrorCount();
		}
		uploadPrLog.setErrorCount(errorCount);
		uploadPrLog.setLogFilePath(savePath + saveName);
		updUploadPrlog(uploadPrLog);
	}
	
	private void checkDuplicateCovertCodeInLog(UploadPrLog uploadPrLog,
			StringBuffer resultMsg,
			Map<CovertUploadReport, CovertUploadReportBean> reports) throws Exception {
		logger.debug("start checking duplicate covert code in covert_error_log table");
		for(CovertUploadReport report : reports.keySet()) {
			CovertUploadReportBean reportBean = reports.get(report);
			for (String sql : reportBean.getCheckDuplicateInLogSqls()) {
				
				Connection con = null;
				Statement statement = null;
				ResultSet result = null;
//				ResultSet result = appCovertErrorLog.getForCovertCodeReport(sql);
				try {
					Session session = HibernateUtil.currentSession();
					con = session.connection();
					statement = con.createStatement();
					result = statement.executeQuery(sql);
					while (result.next()) {
						if(!reportBean.getCovertCodeMap().containsKey(result.getString(1))) {
							continue;
						}
						
						String errMsg = "已上传过重复暗码:" + result.getString(1);
						duplicateCount++;
						createErrorLogSql(reportBean.getCovertCodeMap().get(result.getString(1))[1],reportBean.getCovertCodeMap().get(result.getString(1))[0], result.getString(1), CovertCodeErrType.DUPLICATE_COVERT_CODE.getDbValue(), uploadPrLog, errMsg,report.getPrintDate(),report.getRecodeType(),reportBean.getErrorLogSqls());
						reportBean.setErrorCount(reportBean.getErrorCount() + 1);
						
//						createUpdateCoverCodeToEmptySql(null, result.getString(1), reportBean.getUpdateCovertToEmptySqls());
//						createUpdateErrorLogSql(null, result.getString(1), reportBean.getUpdateErrorLogSqls());
						
						reportBean.getCovertCodeMap().remove(result.getString(1));
						resultMsg.append(errMsg).append("\r\n");
					}
				} finally {
					if (result != null) {
						result.close();
					}
					if (statement != null) {
						statement.close();
					}
					if (con != null) {
						con.close();
					}
				}
				
			}
		}
		
	}

	private void updateCovertCodeAndErrorLog(
			Map<CovertUploadReport, CovertUploadReportBean> reports) throws Exception {
		for(CovertUploadReport report : reports.keySet()) {
			CovertUploadReportBean reportBean = reports.get(report);
			appCovertErrorLog.updCovertErrorLogs(reportBean.getUpdateErrorLogSqls());
			appPrimaryCode.updPrimaryCodes(reportBean.getUpdateCovertToEmptySqls());
		}
		
		
	}

	private void checkAlreadyExistsCovertCode(UploadPrLog uploadPrLog,
			StringBuffer resultMsg,
			Map<CovertUploadReport, CovertUploadReportBean> reports) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("start checking already exists covert code in primaryCode table");
		for(CovertUploadReport report : reports.keySet()) {
			CovertUploadReportBean reportBean = reports.get(report);
			for (String sql : reportBean.getCheckAlreadyExistsSqls()) {
				Connection con = null;
				Statement statement = null;
				ResultSet result = null;
//				ResultSet result = appPrimaryCode.getForCovertCodeReport(sql);
				try {
					Session session = HibernateUtil.currentSession();
					con = session.connection();
					statement = con.createStatement();
					result = statement.executeQuery(sql);
					while (result.next()) {
						String[] primaryCodes = reportBean.getCovertCodeMap().get(reportBean.getPrimaryCodeMap().get(result.getString(2)));
						if(primaryCodes == null) {
							continue;
						}
						String errMsg = "";
						String covertCode = "";
						if(reportBean.getPrimaryCodeMap().get(result.getString(2)).equals(result.getString(1))) {
							createErrorLogSql(primaryCodes[1],primaryCodes[0], result.getString(1), CovertCodeErrType.ALREADY_UPDATED_SAME.getDbValue(), uploadPrLog, errMsg,report.getPrintDate(),report.getRecodeType(),reportBean.getErrorLogSqls());
							reportBean.setErrorCount(reportBean.getErrorCount() + 1);
						} else {
							
							if(result.getString(1) != null) {
								covertCode = result.getString(1);
								errMsg = "小包装码:" + result.getString(2) +",已存在暗码：" +result.getString(1);
							} else {
								covertCode = reportBean.getPrimaryCodeMap().get(result.getString(2));
								errMsg = "小包装码:" + result.getString(2) +",已更新过暗码";
							}
							
							createErrorLogSql(primaryCodes[1],primaryCodes[0], reportBean.getPrimaryCodeMap().get(result.getString(2)), CovertCodeErrType.PRIMARY_CODE_ALREADY_HAS_COVERT_CODE.getDbValue(), uploadPrLog, errMsg,report.getPrintDate(),report.getRecodeType(),reportBean.getErrorLogSqls());
							reportBean.setErrorCount(reportBean.getErrorCount() + 1);
							
							if(result.getString(1) != null) {
								createUpdateCoverCodeToEmptySql(result.getString(2), null, reportBean.getUpdateCovertToEmptySqls());
							}
							createUpdateErrorLogSql(primaryCodes[1], null, reportBean.getUpdateErrorLogSqls());
						}
						
						
						
						reportBean.getCovertCodeMap().remove(reportBean.getPrimaryCodeMap().get(result.getString(2)));
						resultMsg.append(errMsg).append("\r\n");
					}
				} finally {
					if (result != null) {
						result.close();
					}
					if (statement != null) {
						statement.close();
					}
					if (con != null) {
						con.close();
					}
				}
			}
		}
	}

	private void addCovertUploadLog(
			Map<CovertUploadReport, CovertUploadReportBean> reports) throws Exception {
		logger.debug("start adding covert code upload log/report to db");
		for(CovertUploadReport report : reports.keySet()) {
			report.setErrorCount(reports.get(report).getErrorCount());
			report.setTotalCount(reports.get(report).getTotalCount());
			appCovertUploadReport.addCovertUploadReport(report);
			appCovertErrorLog.addCovertErrorLogs(reports.get(report).getErrorLogSqls(), report.getId());
		}
	}

	private void createLogFile(String string, String savePath, String saveName) {
		try {
			FileUploadUtil.CreateFileWithMessage(string,
					savePath, saveName);
			
		} catch (Exception e) {
			logger.error("error occurred when create log file :" +savePath+saveName, e);
		}
		
	}

	private void updateCovertCode(UploadPrLog uploadPrLog, StringBuffer resultMsg, Map<CovertUploadReport, CovertUploadReportBean> reports) throws Exception {
		logger.debug("start updating covert code to primaryCode table");
		for(CovertUploadReport report : reports.keySet()) {
			CovertUploadReportBean reportBean = reports.get(report);
			// 用来统计处理结果信息
			Integer lineNumber = 0;
			Integer loopCount = 0;
			if (reportBean.getUpdateCovertSqls().size() % Constants.DB_BULK_SIZE > 0) {
				loopCount = reportBean.getUpdateCovertSqls().size() / Constants.DB_BULK_SIZE
						+ 1;
			} else {
				loopCount = reportBean.getUpdateCovertSqls().size() / Constants.DB_BULK_SIZE;
			}

			for (int i = 1; i <= loopCount; i++) {
				int[] result = null;
				// 更新暗码数据到系统中
				if (i != loopCount) {
					result = appPrimaryCode
							.updateConvertCodeWithResult(reportBean.getUpdateCovertSqls()
									.subList((i - 1)
											* Constants.DB_BULK_SIZE, i
											* Constants.DB_BULK_SIZE));
				} else {
					result = appPrimaryCode
							.updateConvertCodeWithResult(reportBean.getUpdateCovertSqls()
									.subList((i - 1)
											* Constants.DB_BULK_SIZE,
											reportBean.getUpdateCovertSqls().size()));
				}

				for (int resultIndex = 0; resultIndex < result.length; resultIndex++) {
					lineNumber++;
					if (result[resultIndex] == 0) {
						String errMsg = "小包装码 " + reportBean.getCovertCodeMap().get(reportBean.getCovertCodeList().get(lineNumber -1))[0] + "不存在";
						reportBean.setErrorCount(reportBean.getErrorCount() + 1);
						createErrorLogSql(reportBean.getCovertCodeMap().get(reportBean.getCovertCodeList().get(lineNumber -1))[1],reportBean.getCovertCodeMap().get(reportBean.getCovertCodeList().get(lineNumber -1))[0], reportBean.getCovertCodeList().get(lineNumber -1), CovertCodeErrType.PRIMARY_CODE_NOT_EXISTS.getDbValue(), uploadPrLog,errMsg,report.getPrintDate(),report.getRecodeType(), reportBean.getErrorLogSqls());
						resultMsg.append("小包装码 ").append(reportBean.getCovertCodeMap().get(reportBean.getCovertCodeList().get(lineNumber -1))[0]).append("不存在").append("\r\n");
					} else {
						createErrorLogSql(reportBean.getCovertCodeMap().get(reportBean.getCovertCodeList().get(lineNumber -1))[1],reportBean.getCovertCodeMap().get(reportBean.getCovertCodeList().get(lineNumber -1))[0], reportBean.getCovertCodeList().get(lineNumber -1), CovertCodeErrType.CORRECT.getDbValue(), uploadPrLog,"",report.getPrintDate(),report.getRecodeType(), reportBean.getErrorLogSqls());
					}
				}
			}
		}
		
	}

	private void createUpdateCovertCodeSql(Map<CovertUploadReport, CovertUploadReportBean> reports, UploadPrLog uploadPrLog) {
		for(CovertUploadReportBean reportBean : reports.values()) {
			for(String covertCode : reportBean.getCovertCodeMap().keySet()) {
				// 生成更新语句
				StringBuffer updateCovertSql = new StringBuffer();
				updateCovertSql
						.append("update primary_code set covert_code = '")
						.append(covertCode)
						.append("', upload_pr_id = '")
						.append(uploadPrLog.getId())
						.append("' where primary_code = '")
						.append(reportBean.getCovertCodeMap().get(covertCode)[0])
						.append("' and covert_code is null");
				reportBean.getUpdateCovertSqls().add(updateCovertSql.toString());
				reportBean.getCovertCodeList().add(covertCode);
			}
		}
	}

	private boolean readAndCheckFileContent(File file, UploadPrLog uploadPrLog, StringBuffer resultMsg, Map<CovertUploadReport, CovertUploadReportBean> reports) {
		Integer lineNumber = 0;
		boolean hasError = false;
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			// 读取并验证文件中的内容
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				// 判断是否为空行
				if ("".equals(line.trim())) {
					continue;
				}
				lineNumber++;
				String[] productData = line.split(",");
				hasError = doValidate(productData, lineNumber, resultMsg); 
				
				if(!hasError) {
					//小于50且不等于32位时
					if(productData[0].trim().length() < 50 
							&& productData[0].trim().length() != Constants.PRIMARY_CODE_III_II) {
						String covertCode = productData[1].trim();
						String lineNo = productData[2].trim();
						Integer recodetype = getRecodeType(productData);
						String printdate = getPrintDate(productData);
						CovertUploadReportBean reportBean = null;
						CovertUploadReport report = new CovertUploadReport(lineNo, recodetype, printdate, uploadPrLog.getId(), uploadPrLog.getMakedate(), uploadPrLog.getMakeid(), null, null, null, null);
						if(reports.containsKey(report)) {
							reportBean = reports.get(report);
							reportBean.setTotalCount(reportBean.getTotalCount() + 1);
						} else {
							reportBean = new CovertUploadReportBean();
							reportBean.setTotalCount(1);
							reports.put(report, reportBean);
						}
						createErrorLogSql(productData[0],null, covertCode, recodetype, uploadPrLog,"",printdate,recodetype, reportBean.getErrorLogSqls());
						reportBean.setErrorCount(reportBean.getErrorCount() + 1);
						continue;
					}
					
					String primaryCode = "";
					String batch = "";
					productData[0] = productData[0].trim();
					String lineNo = productData[2].trim();
					// 从二维码中截取小包装码
					if (productData[0].length() == 53) {
						primaryCode = productData[0].substring(40,
								53);
						batch = productData[0].substring(18, 28);
					} else if(productData[0].length() == Constants.PRIMARY_CODE_III_II) {
						primaryCode = productData[0];
						//获取新规码的批次号
						batch = getNewCodeBatch(primaryCode, lineNo, productData);
					} else {
						primaryCode = productData[0].substring(40,
								50);
						batch = productData[0].substring(18, 28);
					}
//					String materialCode = productData[0].substring(8, 16);
					Product product = getProduct(productData[0]);
					String productName = product.getProductname();
					String productId = product.getId();
					String covertCode = productData[1].trim();
					Integer recodetype = getRecodeType(productData);
					String printdate = getPrintDate(productData);
					
					CovertUploadReportBean reportBean = null;
					CovertUploadReport report = new CovertUploadReport(lineNo, recodetype, printdate, uploadPrLog.getId(), uploadPrLog.getMakedate(), uploadPrLog.getMakeid(), product.getmCode(), productName, batch, productId);
//					CovertUploadReport report = new CovertUploadReport();
					if(reports.containsKey(report)) {
						reportBean = reports.get(report);
						reportBean.setTotalCount(reportBean.getTotalCount() + 1);
						if(report.getRecodeType() != -3 && report.getRecodeType() != -4) {
							reports.remove(report);
							reports.put(report, reportBean);
						}
					} else {
						reportBean = new CovertUploadReportBean();
						reportBean.setTotalCount(1);
						reports.put(report, reportBean);
					}
					
					boolean isDuplicate = false;
					for(CovertUploadReportBean covertReportBean : reports.values()) {
						if(covertReportBean.getCovertCodeMap().containsKey(covertCode)) {
							isDuplicate =true;
							break;
						}
					}
					
					//判断文件中暗码是否有重复
					if (isDuplicate) {
						duplicateCount++;
						createErrorLogSql(productData[0],primaryCode, covertCode, CovertCodeErrType.DUPLICATE_COVERT_CODE.getDbValue(), uploadPrLog,"文件中存在重复暗码:"+covertCode,printdate,recodetype, reportBean.getErrorLogSqls());
						reportBean.setErrorCount(reportBean.getErrorCount() + 1);
						
						createErrorLogSql(reportBean.getCovertCodeMap().get(covertCode)[1],reportBean.getCovertCodeMap().get(covertCode)[0], covertCode, CovertCodeErrType.DUPLICATE_COVERT_CODE.getDbValue(), uploadPrLog,"文件中存在重复暗码:"+covertCode,printdate,recodetype, reportBean.getErrorLogSqls());
						reportBean.setErrorCount(reportBean.getErrorCount() + 1);
						
						reportBean.getCovertCodeMap().remove(covertCode);
						
						resultMsg.append("文件中存在重复暗码:").append(covertCode).append("\r\n");
					} else if(recodetype < 0) {
						createErrorLogSql(productData[0],primaryCode, covertCode, recodetype, uploadPrLog,"",printdate,recodetype, reportBean.getErrorLogSqls());
						reportBean.setErrorCount(reportBean.getErrorCount() + 1);
					} else {
						reportBean.getCovertCodeMap().put(covertCode, new String[]{primaryCode,productData[0].trim()});
						reportBean.getPrimaryCodeMap().put(primaryCode, covertCode);
						reportBean.getCheckDuplicateSql().append("'").append(covertCode).append(
						"'").append(",");
						reportBean.setDuplicateSqlInClauseCount(reportBean.getDuplicateSqlInClauseCount() + 1);
						
						reportBean.getCheckDuplicateInLogSql().append("'").append(covertCode).append(
						"'").append(",");
						reportBean.setDuplicateInLogSqlInClauseCount(reportBean.getDuplicateSqlInClauseCount() + 1);
						
						reportBean.getCheckAlreadyExistsSql().append("'").append(primaryCode).append(
						"'").append(",");
						reportBean.setAlreadyExistsSqlInClauseCount(reportBean.getAlreadyExistsSqlInClauseCount() + 1);
					}

					// 生成查询语句
					if (reportBean.getDuplicateSqlInClauseCount() > 0 && reportBean.getDuplicateSqlInClauseCount() % 1000 == 0) {
						String sql = reportBean.getCheckDuplicateSql().toString().substring(0,
								reportBean.getCheckDuplicateSql().toString().length() - 1);
						reportBean.getCheckDuplicateSqls().add(sql + ")");
						reportBean.setCheckDuplicateSql(new StringBuffer("select COVERT_CODE,PRIMARY_CODE FROM PRIMARY_CODE where COVERT_CODE in ("));
						reportBean.setDuplicateSqlInClauseCount(0);
					}
					
					if (reportBean.getDuplicateInLogSqlInClauseCount() > 0 && reportBean.getDuplicateInLogSqlInClauseCount() % 1000 == 0) {
						String sql = reportBean.getCheckDuplicateInLogSql().toString().substring(0,
								reportBean.getCheckDuplicateInLogSql().toString().length() - 1);
						reportBean.getCheckDuplicateInLogSqls().add(sql + ")");
						reportBean.setCheckDuplicateInLogSql(new StringBuffer("select DISTINCT COVERT_CODE from COVERT_ERROR_LOG where COVERT_CODE IN ("));
						reportBean.setDuplicateInLogSqlInClauseCount(0);
					}
					
					if (reportBean.getAlreadyExistsSqlInClauseCount() > 0 && reportBean.getAlreadyExistsSqlInClauseCount() % 1000 == 0) {
						String sql = reportBean.getCheckAlreadyExistsSql().toString().substring(0,
								reportBean.getCheckAlreadyExistsSql().toString().length() - 1);
						reportBean.getCheckAlreadyExistsSqls().add(sql + ")");
						reportBean.setCheckAlreadyExistsSql(new StringBuffer("select COVERT_CODE,PRIMARY_CODE FROM PRIMARY_CODE where (COVERT_CODE is not null or UPLOAD_PR_ID = -1) and PRIMARY_CODE in ("));
						reportBean.setAlreadyExistsSqlInClauseCount(0);
					}
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("file not found", e);
			resultMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00214, file.getPath())).append("\r\n");
		} catch (IOException e) {
			logger.error("error occurred when read file :" + file.getName(), e);
			resultMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00215, e.getMessage())).append("\r\n");
		} catch (Exception e) {
			logger.error("error occurred when read file :" + file.getName(), e);
			resultMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00218, e.getMessage())).append("\r\n");
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("failed to close BufferedReader", e);
				}
			}
		}
		
		if(!hasError) {
			for(CovertUploadReportBean reportBean : reports.values()) {
				if (reportBean.getDuplicateSqlInClauseCount() % 1000 > 0) {
					String sql = reportBean.getCheckDuplicateSql().toString().substring(0,
							reportBean.getCheckDuplicateSql().toString().length() - 1);
					reportBean.getCheckDuplicateSqls().add(sql + ")");
					reportBean.setCheckDuplicateSql(null);
					reportBean.setDuplicateSqlInClauseCount(0);
				}
				
				if (reportBean.getDuplicateInLogSqlInClauseCount() % 1000 > 0) {
					String sql = reportBean.getCheckDuplicateInLogSql().toString().substring(0,
							reportBean.getCheckDuplicateInLogSql().toString().length() - 1);
					reportBean.getCheckDuplicateInLogSqls().add(sql + ")");
					reportBean.setCheckDuplicateInLogSql(null);
					reportBean.setDuplicateInLogSqlInClauseCount(0);
				}
				
				if (reportBean.getAlreadyExistsSqlInClauseCount() % 1000 > 0) {
					String sql = reportBean.getCheckAlreadyExistsSql().toString().substring(0,
							reportBean.getCheckAlreadyExistsSql().toString().length() - 1);
					reportBean.getCheckAlreadyExistsSqls().add(sql + ")");
					reportBean.setCheckAlreadyExistsSql(null);
					reportBean.setAlreadyExistsSqlInClauseCount(0);
				}
			}
		}
		//添加文件记录总数
		uploadPrLog.setTotalCount(lineNumber);
		return hasError;
	}

	private String getPrintDate(String[] productData) {
		if(productData.length == 6) {
			return productData[5].trim();
		} else {
			return productData[4].trim();
		}
	}

	private Integer getRecodeType(String[] productData) {
		if(productData.length == 6) {
			return Integer.parseInt(productData[4].trim());
		} else {
			return Integer.parseInt(productData[3].trim());
		}
	}

	private Product getProduct(String tdCode) {
		if(tdCode.length() == Constants.PRIMARY_CODE_III_II) {
			String regCode = tdCode.substring(1, 7);
			String specCode = tdCode.substring(8, 11);
			return existMaterialCodes.get(regCode+specCode);
		} else {
			return existMaterialCodes.get(tdCode.substring(8, 16));
		}
	}

	private String getNewCodeBatch(String primaryCode, String lineNo, String[] productData) {
		if(productData.length == 6) {
			return productData[3].trim();
		}
		if(batchNumberMap.containsKey(lineNo)) {
			return batchNumberMap.get(lineNo);
		} else {
			AppPrintJob appPrintJob = new AppPrintJob();
			String batch = appPrintJob.getBatchNumberByPrimaryCode(primaryCode);
			if(!StringUtil.isEmpty(batch)) {
				batchNumberMap.put(lineNo, batch);
				return batch;
			}
		}
		
		return "";
	}

	private boolean doValidate(String[] productData, Integer lineNumber, StringBuffer resultMsg) {
		boolean hasError = false;
		String lineNo = "第" + lineNumber + "行: ";
		if (productData.length < 5
				|| StringUtil.isEmpty(productData[0])
				|| StringUtil.isEmpty(productData[2])) {
			resultMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00206, lineNo)).append("\r\n");
			hasError = true;
		} else {
			if(productData[0].trim().length() >= 50) {
//				resultMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00217, lineNo + "二维码")).append("\r\n");
//				hasError = true;
				String materialCode = productData[0].trim().substring(8, 16);
				if(!isMaterialCodeExists(materialCode)) {
					resultMsg.append(lineNo).append(UploadErrorMsg.getError(UploadErrorMsg.E00204, materialCode)).append("\r\n");
					hasError = true;
				}
			} 
			if(productData[0].trim().length() == Constants.PRIMARY_CODE_III_II) {
				String regCode = productData[0].substring(1, 7);
				String specCode = productData[0].substring(8, 11);
				if(!isMaterialCodeExists(regCode, specCode)) {
					resultMsg.append(lineNo).append(UploadErrorMsg.getError(UploadErrorMsg.E00204, regCode+"_"+specCode)).append("\r\n");
					hasError = true;
				}
			}
			//检查生产类型
			String recodeType = "";
			if(productData.length == 6) {
				recodeType = productData[4];
			} else {
				recodeType = productData[3];
			}
			if(!recodeType.trim().matches("-?[0-9]+")) {
				resultMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00216, lineNo + "生产类型")).append("\r\n");
				hasError = true;
			} else {
				if("-2".equals(recodeType.trim())) {
					resultMsg.append(lineNo).append("有二维码没序列号 \r\n");
				} else if("-3".equals(recodeType.trim())) {
					resultMsg.append(lineNo).append("有序列号没二维码  \r\n");
				} else if("-4".equals(recodeType.trim())) {
					resultMsg.append(lineNo).append("无二维码无序列号  \r\n");
				}
			}
		}
		return hasError;
	}

	private void checkDuplicateCovertCode(UploadPrLog uploadPrLog,
			StringBuffer resultMsg, Map<CovertUploadReport, CovertUploadReportBean> reports) throws Exception {
		logger.debug("start checking duplicate covert code in primaryCode table");
		for(CovertUploadReport report : reports.keySet()) {
			CovertUploadReportBean reportBean = reports.get(report);
			for (String sql : reportBean.getCheckDuplicateSqls()) {
				Connection con = null;
				Statement statement = null;
				ResultSet result = null;
//				ResultSet result = appPrimaryCode.getForCovertCodeReport(sql);
				try {
					Session session = HibernateUtil.currentSession();
					con = session.connection();
					statement = con.createStatement();
					result = statement.executeQuery(sql);
					while (result.next()) {
						if(!reportBean.getCovertCodeMap().containsKey(result.getString(1))) {
							continue;
						}
						String errMsg = "系统中存在重复暗码:" + result.getString(1) +",对应小包装码：" +result.getString(2);
						if(result.getString(2).equals(reportBean.getCovertCodeMap().get(result.getString(1))[0])) {
							createErrorLogSql(reportBean.getCovertCodeMap().get(result.getString(1))[1],reportBean.getCovertCodeMap().get(result.getString(1))[0], result.getString(1), CovertCodeErrType.ALREADY_UPDATED_SAME.getDbValue(), uploadPrLog, errMsg,report.getPrintDate(),report.getRecodeType(),reportBean.getErrorLogSqls());
							reportBean.setErrorCount(reportBean.getErrorCount() + 1);
						} else {
							duplicateCount++;
							createErrorLogSql(reportBean.getCovertCodeMap().get(result.getString(1))[1],reportBean.getCovertCodeMap().get(result.getString(1))[0], result.getString(1), CovertCodeErrType.DUPLICATE_COVERT_CODE.getDbValue(), uploadPrLog, errMsg,report.getPrintDate(),report.getRecodeType(),reportBean.getErrorLogSqls());
							reportBean.setErrorCount(reportBean.getErrorCount() + 1);
							createUpdateCoverCodeToEmptySql(null, result.getString(1), reportBean.getUpdateCovertToEmptySqls());
							createUpdateErrorLogSql(null, result.getString(1), reportBean.getUpdateErrorLogSqls());
						}
						
						reportBean.getCovertCodeMap().remove(result.getString(1));
						resultMsg.append(errMsg).append("\r\n");
					}
				} finally {
					if (result != null) {
						result.close();
					}
					if (statement != null) {
						statement.close();
					}
					if (con != null) {
						con.close();
					}
				}
			}
		}
	}
	
	private void createErrorLogSql(String tdCode,String primaryCode, String covertCode,
			Integer errorType, UploadPrLog uploadPrLog, String errMsg, String printDate, Integer recodeType, List<String> errorLogSqls) {
		String errorLogSql = "INSERT INTO COVERT_ERROR_LOG (ID,TDCODE,COVERT_CODE,ERROR_TYPE,UPLOAD_PR_ID,UPLOAD_DATE,UPLOAD_USER,ERROR_MSG,PRINT_DATE,RECODE_TYPE,PRIMARY_CODE) VALUES (seq_covert_error_log.nextval, '"
			+ tdCode
			+ "', '"
			+ covertCode
			+ "', "
			+ errorType
			+ ", :curId"
			+ ", SYSDATE, "
			+ uploadPrLog.getMakeid()
			+ ", '"
			+ errMsg
			+ "', '"
			+ printDate
			+ "',"
			+ recodeType
			+ ", '"
			+ primaryCode +
			"')";
		errorLogSqls.add(errorLogSql);
	}
	
	private void createUpdateCoverCodeToEmptySql(String primaryCode, String covertCode, List<String> updateCovertToEmptySqls) {
		String sql = null;
		if(primaryCode != null) {
			sql = "update primary_code set covert_code = null, UPLOAD_PR_ID = -1 where primary_code = '"+primaryCode+"'";
		} else {
			sql = "update primary_code set covert_code = null, UPLOAD_PR_ID = -1  where covert_code = '"+covertCode+"'";
		}
		updateCovertToEmptySqls.add(sql);
	}
	
	private void createUpdateErrorLogSql(String tdCode, String covertCode,
			List<String> updateErrorLogSqls) {
		String sql = null;
		if(tdCode != null) {
			sql = "update COVERT_ERROR_LOG set ERROR_TYPE = "+CovertCodeErrType.PRIMARY_CODE_ALREADY_HAS_COVERT_CODE.getDbValue()+" where TDCODE = '"+tdCode+"'";
		} else {
			sql = "update COVERT_ERROR_LOG set ERROR_TYPE = "+CovertCodeErrType.DUPLICATE_COVERT_CODE.getDbValue()+" where COVERT_CODE = '"+covertCode+"'";
		}
		updateErrorLogSqls.add(sql);
	}
	/**
	 * 验证SAP物料码是否在系统中存在
	 * Create Time 2014-10-20 上午11:23:26
	 * @param materialCode
	 * @author Ryan.xi
	 */
	public boolean isMaterialCodeExists(String materialCode) {
		if(existMaterialCodes.containsKey(materialCode)) {
			return true;
		} else if(notExistsMaterialCodes.contains(materialCode)){
			return false;
		} else {
			try {
				Product procuct = appProduct.getByMCode(materialCode);
				existMaterialCodes.put(materialCode, procuct);
				return true;
			} catch (NotExistException e) {
				notExistsMaterialCodes.add(materialCode);
				return false;
			}
		}
	}
	
	/**
	 * 验证SAP物料码是否在系统中存在
	 * Create Time 2014-10-20 上午11:23:26
	 * @param materialCode
	 * @author Ryan.xi
	 * @throws Exception 
	 */
	public boolean isMaterialCodeExists(String regCcode, String specCode) {
		String key = regCcode+specCode;
		if(existMaterialCodes.containsKey(key)) {
			return true;
		} else if(notExistsMaterialCodes.contains(key)){
			return false;
		} else {
			try {
				Product procuct = appProduct.getProductByRegCertAndSpecCode(regCcode, specCode);
				if(procuct != null) {
					existMaterialCodes.put(key, procuct);
					return true;
				} else {
					notExistsMaterialCodes.add(key);
					return false;
				}
			} catch (Exception e) {
				notExistsMaterialCodes.add(key);
				return false;
			}
		}
	}
	
	
	private void addNotificationMail(UploadPrLog uploadPrLog) {
		String dateType = DateUtil.formatDate(new Date()) + "_" +MailType.COVERT_CODE_DUPLICATE.getDbValue();
		try {
			BatchCompleteMail mail = abcm.getMailByDateType(dateType);
			if(mail != null) {
				mail.setMailBody(mail.getMailBody() + "文件编号："+uploadPrLog.getId()+" 重复记录数："+duplicateCount+"\r\n");
				abcm.update(mail);
			} else {
				mail = new BatchCompleteMail();
				Properties mailPro = SMTPMailFactory.getSMTPMailFactory();
				mail.setMailSender(mailPro.getProperty("covertcode_mailSender"));
				mail.setMailFrom(mailPro.getProperty("covertcode_mailFrom"));
				mail.setMailTo(mailPro.getProperty("covertcode_mailTo"));
				mail.setMailCc(mailPro.getProperty("covertcode_mailCc"));
				mail.setMailSubject(mailPro.getProperty("covertcode_mailSubject"));
				mail.setMailBody("文件编号："+uploadPrLog.getId()+" 重复记录数："+duplicateCount+"\r\n");
				mail.setCreateDate(DateUtil.getCurrentDate());
				mail.setMailType(MailType.COVERT_CODE_DUPLICATE.getDbValue());
				mail.setMailDateType(dateType);
				abcm.add(mail);
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("添加更新邮件时异常：",e);
		}
		/*
		Mail mail= new Mail();;
		try {
			String mailTo = SapConfig.getSapConfig().getProperty("mailTo");
			String mailCc = SapConfig.getSapConfig().getProperty("mailCc");
			String mailBcc = SapConfig.getSapConfig().getProperty("mailBcc");
			String mailSubject = SapConfig.getSapConfig().getProperty("mailSubject");
			String mailSenderStr =  SapConfig.getSapConfig().getProperty("mailSender");
			String mailBody = "SAP上传文件处理失败，上传日志编号：" + sapUploadLog.getId();
			if(!StringUtils.isRealEmpty(mailTo))
				mail.setTo(mailTo.split(","));
			if(!StringUtils.isRealEmpty(mailCc))
				mail.setCc(mailCc.split(","));
			if(!StringUtils.isRealEmpty(mailBcc))
				mail.setBc(mailBcc.split(","));
			mail.setSubject(mailSubject);
			mail.setBody(mailBody);
			mail.setMailSender(mailSenderStr);
			MailSender mailSender = new MailSender(mail, SMTPMailFactory.getSMTPMailFactory()); 
			mailSender.sendMail();
		} catch (Exception e) {
			logger.error("Error occurs while sending SAP error notification mails", e);
		}*/
	}
}
