package com.winsafe.sap.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;


import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import org.hibernate.Session;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.FileUtil;
import com.winsafe.drp.util.HttpUtils; 
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.PlantConfig;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCommonCodeLog;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.metadata.FileType;
import com.winsafe.sap.metadata.SyncStatus;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.CommonCodeLog;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.sap.util.FileUploadUtil;

/*******************************************************************************************  
 * 定时将打印任务,箱码和小码文件同步到杭州工厂
 * @author: ryan.xi	  
 * @date：2017-10-23  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2017-10-23   ryan.xi                 
 *******************************************************************************************  
 */  
public class SyncPrintJobTask {

	private static Logger logger = Logger
			.getLogger(SyncPrintJobTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private AppPrintJob appPrintJob = new AppPrintJob();
	private AppCommonCodeLog appCommonCodeLog = new AppCommonCodeLog();
	private AppProduct appProduct = new AppProduct();
	//杭州工厂文件上传接口地址
	private String url = null;
	//上传文件保存路径
	private String filePath = null;
	
	private String[] cartonCodeExcludes = new String[]{}; 
	private String[] printJobExcludes = new String[]{"cartonSeqStatus"};
	private String[] primaryCodeExcludes = new String[]{"seq"};
	
	/**
	 * 初始化要处理的任务
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		url = PlantConfig.getConfig().getProperty("HZPlantUrl");
		filePath = PlantConfig.getConfig().getProperty("printJobFilePath");
	}
	/**
	 * 定时任务入口方法
	 */
	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start genernate primary code.");
					logger.info(DateUtil.getCurrentDate() + " 打印任务同步任务---开始---");
					this.init();
					isRunning = true;
					startSync();
				} catch (Exception e) {
					logger.info(DateUtil.getCurrentDate() + " 打印任务同步任务发生异常:"
							+ e.getMessage());
					logger.error(e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate() + " 打印任务同步任务---结束---");
				}
			}
		}

	}
	
	/**
	 * 开始上传文件
	 * @throws Exception
	 */
	private void startSync() throws Exception {
//		String dateString = DateUtil.getCurrentDateTimeString();
		//同步打印任务
		syncPrintJob();
		//同步通用码
		syncCommonCode();
	}
	
	/**
	 * 生成通用码文件,打包上传
	 * @param dateString
	 */
	private void syncCommonCode() {
		List<CommonCodeLog> commonCodeList = appCommonCodeLog.getNotUploadedCommonCodeLog();
		for(CommonCodeLog log : commonCodeList) {
			try {
				File zipfile = null;
				//查看文件是否已生成过
				if(!StringUtil.isEmpty(log.getSyncFilePath())) {
					zipfile = new File(log.getSyncFilePath());  
				} else {
					//生成文件并打包
					File pcFile = genPrimaryCodeFile(log.getPrintJobId(), log.getId().toString(), filePath, log.getId().toString());
					String zipFileName = "commonCode_"+log.getId().toString()+".zip";
					zipfile = new File(filePath+zipFileName);  
					FileUtil.zipFiles(new File[]{pcFile}, zipfile);
					//更新文件路径
					log.setSyncFilePath(filePath+zipFileName);
					appCommonCodeLog.updCommonCodeLog(log);
					HibernateUtil.commitTransaction();
				}
				//上传文件
				if(!HttpUtils.upload(url+"?fileType="+FileType.COMMON_CODE.getValue(), zipfile)) {
					log.setSyncStatus(SyncStatus.UPLOAD_ERROR.getValue());
					HibernateUtil.commitTransaction();
					continue;
				}
				//更新 同步状态
				log.setSyncStatus(SyncStatus.UPLOADED.getValue());
				appCommonCodeLog.updCommonCodeLog(log);
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				logger.error("",e);
				HibernateUtil.rollbackTransaction();
			}
		}
		
	}

	/**
	 * 生成打印任务,箱码和小包装文件,打包上传
	 * @param dateString
	 */
	private void syncPrintJob() {
		List<PrintJob> printJobList = appPrintJob.getNotUploadedPrintJob();
		
		for(PrintJob pj : printJobList) {
			try {
				Product product = appProduct.getProductByID(pj.getProductId());
				File zipfile = null;
				//检查文件是否已生成过
				if(!StringUtil.isEmpty(pj.getSyncFilePath())) {
					zipfile = new File(pj.getSyncFilePath());  
				} else {
					String zipFileName = "printJob_"+pj.getPrintJobId().toString()+".zip";
					zipfile = new File(filePath+zipFileName);  
					//生成要同步的文件
					File pjFile = genPrintJobFile(pj, pj.getPrintJobId().toString(), filePath);
					File ccFile = genCartonCodeFile(pj, pj.getPrintJobId().toString(), filePath);
					File pcFile = null;
					if(product.getIsidcode()!=null && product.getIsidcode() == YesOrNo.YES.getValue()) {
						//印刷产品生成空小包装文件
						pcFile = genEmptyPrimaryCodeFile(pj.getPrintJobId().toString(), filePath);
					} else {
						//非印刷产品需生成小包装文件
						pcFile = genPrimaryCodeFile(pj.getPrintJobId(), pj.getPrintJobId().toString(), filePath, null);
					}
					FileUtil.zipFiles(new File[]{pjFile, ccFile, pcFile}, zipfile);
					//更新文件路径
					pj.setSyncFilePath(filePath+zipFileName);
					appPrintJob.updPrintJob(pj);
					HibernateUtil.commitTransaction();
				}
				//上传文件
				if(!HttpUtils.upload(url+"?fileType="+FileType.PRINT_JOB.getValue(), zipfile)) {
					pj.setSyncStatus(SyncStatus.UPLOAD_ERROR.getValue());
					HibernateUtil.commitTransaction();
					continue;
				}
				//更新 同步状态
				pj.setSyncStatus(SyncStatus.UPLOADED.getValue());
				appPrintJob.updPrintJob(pj);
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				logger.error("",e);
				HibernateUtil.rollbackTransaction();
			}
		}
	}

	/**
	 * 生成小码文件
	 * @param printJobId
	 * @param dateString
	 * @param filePath
	 * @param commonCodeLogId
	 * @return
	 * @throws Exception
	 */
	private File genPrimaryCodeFile(int printJobId, String dateString,
			String filePath, String commonCodeLogId) throws Exception {
		String fileName = "primaryCode_"+dateString+".txt";
		File file = FileUploadUtil.createEmptyFile(filePath, fileName);
		String sql ="select ID id,PRIMARY_CODE primaryCode,CARTON_CODE cartonCode,PALLET_CODE palletCode,IS_USED isUsed,CREATE_DATE createDate, " +
				"PRINT_JOB_ID printJobId,COVERT_CODE covertCode,UPLOAD_PR_ID uploadPrId,CODE_LENGTH codeLength,NUMBEROFQUERY numberOfQuery, " +
				"FIRSTTIME firstTime,COMMON_CODE_LOG_ID commonCodeLogId,COMMON_CODE_LOG_ID " +
				"from PRIMARY_CODE where PRINT_JOB_ID ="+printJobId;
		if(!StringUtil.isEmpty(commonCodeLogId)) {
			sql+=" and COMMON_CODE_LOG_ID = " + commonCodeLogId;	
		}
		genFile(sql, file, PrimaryCode.class);
		return file;
	}
	
	/**
	 * 生成小码文件
	 * @param printJobId
	 * @param dateString
	 * @param filePath
	 * @param commonCodeLogId
	 * @return
	 * @throws Exception
	 */
	private File genEmptyPrimaryCodeFile(String dateString,
			String filePath) throws Exception {
		String fileName = "primaryCode_"+dateString+".txt";
		File file = FileUploadUtil.createEmptyFile(filePath, fileName);
		return file;
	}

	/**
	 * 生成箱码文件
	 * @param pj
	 * @param dateString
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	private File genCartonCodeFile(PrintJob pj, String dateString, String filePath) throws Exception {
		String fileName = "cartonCode_"+dateString+".txt";
		File file = FileUploadUtil.createEmptyFile(filePath, fileName);
		String sql ="select CARTON_CODE cartonCode,PALLET_CODE palletCode,OUT_PIN_CODE outPinCode,INNER_PIN_CODE innerPinCode,CREATE_DATE createDate, " +
			"MATERIAL_CODE materialCode,PRODUCT_ID productID,PRINT_JOB_ID printJobId,PRIMARY_CODE_STATUS PrimaryCodeStatus,PRINT_SEQ printSeq, " +
			"COUNT_IN_PALLET countInPallet,CARTON_SEQ cartonSeq from CARTON_CODE where PRINT_JOB_ID = "+pj.getPrintJobId();
		genFile(sql, file, CartonCode.class);
		return file;

	}

	/**
	 * 生成条码文件
	 * @param sql
	 * @param file
	 * @param type
	 * @throws Exception
	 */
	private void genFile(String sql, File file, Class type) throws Exception {
		OutputStreamWriter fw = null;
		BufferedWriter bw = null;
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			Session session = HibernateUtil.currentSession();
			con = session.connection();
			statement = con.createStatement();
			result = statement.executeQuery(sql);
			fw = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
			bw = new BufferedWriter(fw);
			ResultSetMetaData meteData = result.getMetaData();
			result.setFetchSize(50);
			int columnCount = meteData.getColumnCount();
			//将数据转成
			while (result.next()) {
				Map<String,String> map = new HashMap<String,String>();
				for (int i = 1; i <= columnCount; i++)
				{
					map.put(meteData.getColumnName(i).toLowerCase(), result.getString(i));
				}
				Object obj = type.newInstance();
				MapUtil.mapToObjectIgnoreCase(map, obj);
				String[] excludes = new String[]{};
				if(type == CartonCode.class) {
					excludes = cartonCodeExcludes;
				} else if(type == PrimaryCode.class) {
					excludes = primaryCodeExcludes;
				}
				JSONObject object = JSONObject.fromBean(obj, excludes);
				bw.write(object.toString());
				bw.write("\r\n");
				bw.flush();
			}
		} finally {
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
		
	}

	/**
	 * 生成打印任务文件
	 * @param pj
	 * @param dateString
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	private File genPrintJobFile(PrintJob pj, String dateString, String filePath) throws Exception {
		String fileName = "printJob_"+dateString+".txt";
		OutputStreamWriter fw = null;
		BufferedWriter bw = null;
		File file = null;
		try {
			file = FileUploadUtil.createEmptyFile(filePath, fileName);
			fw = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
			bw = new BufferedWriter(fw);
			JSONObject object = JSONObject.fromObject(pj,printJobExcludes);
			bw.write(object.toString());
			bw.write("\r\n");
			bw.flush();
		} finally {
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
			}
		}
		return file;
	}
	
	public static void main(String[] args) throws Exception {
		new SyncPrintJobTask().run();
	}
}
