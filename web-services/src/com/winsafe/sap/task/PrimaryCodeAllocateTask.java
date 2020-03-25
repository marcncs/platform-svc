package com.winsafe.sap.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.winsafe.hbm.util.StringUtil;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil; 
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.metadata.CodeType;
import com.winsafe.sap.metadata.PrimaryCodeStatus;
import com.winsafe.sap.metadata.SeedType;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.sap.service.LabelGenerateService;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.SapConfig;
/*******************************************************************************************  
 * 定时任务类,为打印任务分配小包装码
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
public class PrimaryCodeAllocateTask {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger
			.getLogger(PrimaryCodeAllocateTask.class);

	//待分配小码的定时任务
	private static List<PrintJob> printJobs;

	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();

	private AppPrintJob appPrintJob = new AppPrintJob();

	private AppCartonCode appCartonCode = new AppCartonCode();
	private AppProduct appProduct = new AppProduct();
	private AppFUnit appFUnit = new AppFUnit();
	private AppBaseResource appBaseResource = new AppBaseResource();
	//需分配10为小包装码的物料号
	private String materialCodes;
	//通用码箱标
	private String commonCode;
	// 默认包装单位
	protected Integer defaultUnitId;
	// 默认包装名称
	protected String defaultUnitName;

	/**
	 * 初始化要处理的任务
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		commonCode = SapConfig.getSapConfig().getProperty("commonCode");
		materialCodes = SapConfig.getSapConfig().getProperty("materialCodes");
		String whereSql = " where isdelete = 0 and confirmFlag = 1"
				+ " and (primaryCodeStatus = "
				+ PrimaryCodeStatus.NOT_GENERATED.getDatabaseValue()
				+ " or primaryCodeStatus = "
				+ PrimaryCodeStatus.GENERATED_ERROR.getDatabaseValue() + ")";
		printJobs = appPrintJob.getPrintJobByWhere(whereSql);
		defaultUnitId = Integer.parseInt((String) SapConfig.getSapConfig().get(
				"unitId"));
		defaultUnitName = appBaseResource.getBaseResourceValue("CountUnit",
				defaultUnitId).getTagsubvalue();
	}
	/**
	 * 定时任务入口方法
	 */
	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start processing sap upload file.");
					this.init();
					isRunning = true;
					logger
							.info(DateUtil.getCurrentDate()
									+ " 小包装码分配任务---开始---");
					if (printJobs != null) {
						for (int i = 0; i < printJobs.size(); i++) {
							logger.debug("开始时间："
									+ printJobs.get(i).getPrintJobId()
									+ new Date());
							long startTime = System.currentTimeMillis();
							if (startProcess(printJobs.get(i))) {
								execute(printJobs.get(i));
							}
							long endTime = System.currentTimeMillis();
							logger.debug("编号为"
									+ printJobs.get(i).getPrintJobId()
									+ "的打印任务小包装码生成时间为" + (endTime - startTime)
									+ "ms " + (endTime - startTime) / 1000
									+ "s");
							printJobs.remove(i);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(DateUtil.getCurrentDate() + " 小包装码分配任务发生异常"
							+ e.getCause().getMessage());
					logger.error(e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger
							.info(DateUtil.getCurrentDate()
									+ " 小包装码分配任务---结束---");
				}
			}
		}

	}

	/**
	 * 更新打印任务的小包装码生成状态
	 * @param printJob
	 * @return
	 */
	private boolean startProcess(PrintJob printJob) {
		String sql = "update print_job set primary_code_status = "
				+ PrimaryCodeStatus.GENERATING.getDatabaseValue()
				+ " where print_job_id = " + printJob.getPrintJobId()
				+ " and (primary_code_status = "
				+ PrimaryCodeStatus.NOT_GENERATED.getDatabaseValue()
				+ " or primary_code_status = "
				+ PrimaryCodeStatus.GENERATED_ERROR.getDatabaseValue() + ")";
		try {
			int result = appPrintJob.updPrintJobBySql(sql);
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
	 * 分配小包装码
	 * @param fileName
	 * @throws Exception 
	 * @throws Exception
	 */
	public void execute(PrintJob printJob) {
		logger.debug("start allocate primary Code for print job : "
				+ printJob.getPrintJobId());
		// 算出小包装码数量
		Double boxQuantity = 0.00;
		FUnit fUnit = appFUnit.getFUnit(printJob.getProductId(), defaultUnitId);
		if (fUnit == null) {
			printJob.setPrimaryCodeStatus(PrimaryCodeStatus.GENERATED_ERROR
					.getDatabaseValue());
			printJob.setCodeErrorMsg("未找到产品的" + defaultUnitName + "到最小包装的比例关系");
			updPrintJob(printJob);
			return;
		} else {
			boxQuantity = fUnit.getXquantity();
		}
		
		if(CodeType.QR.getValue().equals(printJob.getCodeType()) ) {
			//QR码走新流程
			try { 
				Product product = appProduct.getByMCode(printJob.getMaterialCode());
				LabelGenerateService lgs = new LabelGenerateService(printJob, product);
				lgs.generatePrimaryCode(printJob, boxQuantity.intValue(), SeedType.GENERAL);
				printJob.setPrimaryCodeStatus(PrimaryCodeStatus.GENERATED
						.getDatabaseValue());
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				printJob.setPrimaryCodeStatus(PrimaryCodeStatus.GENERATED_ERROR
						.getDatabaseValue());
				printJob.setCodeErrorMsg("生成小包装码时系统出错：" + e.getMessage());
				logger.error("hibernate rollback error: " + e.getMessage(), e);
				HibernateUtil.rollbackTransaction();
			}
			updPrintJob(printJob);
		} else {
			//DM码按原流程走
			Integer codeLength = 13;
			// 判断是否要分配10位的小包装码
			if (materialCodes.contains(printJob.getMaterialCode())) {
				codeLength = 10;
			}
			Integer count = appPrimaryCode.getAvailablePrimaryCodeCount(codeLength);
			Integer noOfCase = (int) (printJob.getNumberOfCases() * boxQuantity);
			if (count > noOfCase) {
				try {
					List<CartonCode> cartonCodes = appCartonCode
							.getCartonCodeByPrintjobId(printJob.getPrintJobId());

					appPrimaryCode.allocatePrimaryCodeByPrintJobId(printJob
							, cartonCodes, boxQuantity.intValue(),
							codeLength);

					printJob.setPrimaryCodeStatus(PrimaryCodeStatus.GENERATED
							.getDatabaseValue());
					// 生成小包装码文件
					createPrimaryCodeFile(printJob);

					HibernateUtil.commitTransaction();
				} catch (Exception e) {
					printJob.setPrimaryCodeStatus(PrimaryCodeStatus.GENERATED_ERROR
							.getDatabaseValue());
					printJob.setCodeErrorMsg("生成小包装码时系统出错：" + e.getMessage());
					logger.error("hibernate rollback error: " + e.getMessage(), e);
					HibernateUtil.rollbackTransaction();
				} finally {
					HibernateUtil.closeSession();
				}
				updPrintJob(printJob);
			} else {
				printJob.setPrimaryCodeStatus(PrimaryCodeStatus.GENERATED_ERROR
						.getDatabaseValue());
				printJob.setCodeErrorMsg("目前没有足够的" + codeLength + "位小包装码，请等待(可用数量："+count.toString()+" 需求数量:"+noOfCase.toString());
				updPrintJob(printJob);
				return;
			}
		}
		logger.debug("end allocate primary Code for print job : "
				+ printJob.getPrintJobId());
	}

	/**
	 * 生成小包装码文件
	 * 
	 * @return
	 * @author ryan.xi
	 * @throws Exception
	 */

	private void createPrimaryCodeFile(PrintJob printJob) throws Exception {
		logger.debug("start create primary Code file for print job : "
				+ printJob.getPrintJobId());
		OutputStreamWriter fw = null;
		BufferedWriter bw = null;
		Product product = appProduct.getByMCode(printJob.getMaterialCode());
		String filePath = FileUploadUtil.getPrimayCodeFilePath()
				+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
				+ "/";
		String fileName = StringUtil.removeNull(printJob.getProductionLine())
				+ "_" + product.getProductnameen()+" "
				+ product.getPackSizeNameEn() + "_" + printJob.getBatchNumber()
				+ "." + printJob.getPrintJobId() + ".txt";
		// 删除文件名中特殊字符
		fileName = removeSpecCharInFileName(fileName);
		
		String sql = "select PRIMARY_CODE, CARTON_CODE from PRIMARY_CODE where PRINT_JOB_ID = " + printJob.getPrintJobId() +" and (CARTON_CODE != '" + commonCode +"' or CARTON_CODE is null) order by PALLET_CODE ,CARTON_CODE";
		
		Connection con = null;
		Statement statement = null;
		ResultSet primaryCodes = null;
//		primaryCodes = appPrimaryCode.getForGenPrimaryCodeFile(
//				printJob.getPrintJobId(), commonCode);
		try {
			Session session = HibernateUtil.currentSession();
			con = session.connection();
			statement = con.createStatement();
			primaryCodes = statement.executeQuery(sql);
			
			File file =FileUploadUtil.createEmptyFile(filePath, fileName);
			
			fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			bw = new BufferedWriter(fw);
			
			addFileHeader(printJob, product, bw);
			
			while (primaryCodes.next()) {
				StringBuffer sb = new StringBuffer();
				
				sb.append(primaryCodes.getString(1)).append(",");
				// 生产日期
				sb.append(printJob.getProductionDate()).append(",");
				// 封装日期，如果生产日期=分装日期，则分装日期为空
				if (printJob.getProductionDate().equals(
						printJob.getPackagingDate())) {
					sb.append(",");
				} else {
					sb.append(
							printJob.getPackagingDate() != null ? printJob
									.getPackagingDate() : "").append(",");
				}
				// 批次号
				sb.append(printJob.getBatchNumber()).append(",");
				// 箱码
				sb.append(StringUtil.removeNull(primaryCodes.getString(2))).append(",");
				// 小包装唯一码
				sb.append(primaryCodes.getString(1)).append("\r\n");
				
				/*// 2维码数据,包括GTIN码或者物料码，批次号，生产日期和小包装唯一码
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
				if (printJob.getProductionDate().equals(
						printJob.getPackagingDate())) {
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
				sb.append(primaryCodes.getString(1)).append("\r\n");*/
				bw.write(sb.toString());
			}
		} catch (Exception e) {
			logger.error("failed to create Primary Code File",e);
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

		printJob.setCodeFilePath(filePath + fileName);
		logger.debug("end allocate primary Code file for print job : "
				+ printJob.getPrintJobId());
	}

	private void addFileHeader(PrintJob printJob, Product product, BufferedWriter bw) throws IOException {
		//任务号, 任务数量, 产线号, 产品名称, 产品规格, 农药标准名称, 批号, 生产日期, 分装日期
		StringBuffer header = new StringBuffer();
		header.append(printJob.getPrintJobId()).append(",");
		header.append(printJob.getNumberOfCases()).append(",");
		header.append(printJob.getProductionLine()).append(",");
		header.append(product.getProductname()).append(",");
		header.append(product.getSpecmode()).append(",");
		header.append(product.getStandardName()).append(",");
		header.append(printJob.getBatchNumber()).append(",");
		// 生产日期
		header.append(printJob.getProductionDate()).append(",");
		// 封装日期
		header.append(StringUtil.removeNull(printJob.getPackagingDate())).append(",");
		//箱到小包装比例
		AppFUnit appFunit = new AppFUnit();
		FUnit funit = appFunit.getFUnit(product.getId(), Constants.DEFAULT_UNIT_ID);
		if(funit != null) {
			header.append(funit.getXquantity().toString()).append("\r\n");
		} else {
			header.append(0).append("\r\n");
		}
		//写入到文件中
		bw.write(header.toString());
	}
	/**
	 * 更新打印任务
	 * @param printJob
	 */
	private void updPrintJob(PrintJob printJob) {
		logger.debug("update printJob's primary code status to : "
				+ printJob.getPrimaryCodeStatus());
		try {
			appPrintJob.updPrintJob(printJob);
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
