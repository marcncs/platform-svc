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
import com.winsafe.erp.dao.AppApplyQrCode;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.metadata.FileType;
import com.winsafe.sap.metadata.SyncStatus;
import com.winsafe.sap.pojo.ApplyQrCodeHz;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.util.FileUploadUtil;
/*******************************************************************************************  
 * 定时将码申请文件文件同步到杭州工厂
 * @author: ryan.xi	  
 * @date：2019-08-05  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2019-08-05   ryan.xi                 
 *******************************************************************************************  
 */  
public class SyncApplyQrCodeHzTask {

	private static Logger logger = Logger
			.getLogger(SyncApplyQrCodeHzTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private AppApplyQrCode appAqc = new AppApplyQrCode();
	private AppProduct appProduct = new AppProduct();
	//杭州工厂文件上传接口地址
	private String url = null;
	//上传文件保存路径
	private String filePath = null;
	
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
					logger.info(DateUtil.getCurrentDate() + "码申请同步任务---开始---");
					this.init();
					isRunning = true;
					startSync();
				} catch (Exception e) {
					logger.info(DateUtil.getCurrentDate() + "码申请同步任务发生异常:"
							+ e.getMessage());
					logger.error(e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate() + "码申请同步任务---结束---");
				}
			}
		}
	}
	
	/**
	 * 开始上传文件
	 * @throws Exception
	 */
	private void startSync() throws Exception {
		//同步打印任务
		syncApplyQrCodeHz();

	}
	
	

	/**
	 * 生成打印任务,箱码和小包装文件,打包上传
	 * @param dateString
	 */
	private void syncApplyQrCodeHz() {
		List<ApplyQrCodeHz> applyQrCodeHzList = appAqc.getNotUploadedLog();
		
		for(ApplyQrCodeHz aqc : applyQrCodeHzList) {
			try {
				File zipfile = null;
				//检查文件是否已生成过
				if(!StringUtil.isEmpty(aqc.getSyncFilePath())) {
					zipfile = new File(aqc.getSyncFilePath());  
				} else {
					//生成要同步的文件
					File pcFile = genPrimaryCodeFile(aqc, filePath);
					String zipFileName = "applyQrCodeHz_"+aqc.getId().toString()+".zip";
					zipfile = new File(filePath+zipFileName);  
					FileUtil.zipFiles(new File[]{pcFile}, zipfile);
					//更新文件路径
					aqc.setSyncFilePath(filePath+zipFileName);
					//appAqc.updApplyQrCodeHz(aqc);
					HibernateUtil.commitTransaction();
				}
				//上传文件
				if(!HttpUtils.upload(url+"?fileType="+FileType.COMMON_CODE.getValue(), zipfile)) {
					aqc.setSyncStatus(SyncStatus.UPLOAD_ERROR.getValue());
					HibernateUtil.commitTransaction();
					continue;
				}
				//更新 同步状态
				aqc.setSyncStatus(SyncStatus.UPLOADED.getValue());
				//appAqc.updApplyQrCodeHz(aqc);
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction();
				logger.error("",e);
			}
		}
	}

	private File genPrimaryCodeFile(ApplyQrCodeHz aqc, String filePath) throws Exception {
		Product product = appProduct.getProductByID(aqc.getProductId());
		String codePrefix=product.getRegCertType()+product.getRegCertCodeFixed()+product.getProduceType()+product.getSpecCode()+product.getInnerProduceType();
		String fileName = "ApplyQrCodeHz_"+aqc.getId()+".txt";
		File file = FileUploadUtil.createEmptyFile(filePath, fileName);
		String sql ="select ID id,PRIMARY_CODE primaryCode,CARTON_CODE cartonCode,PALLET_CODE palletCode,IS_USED isUsed,CREATE_DATE createDate, " +
				"PRINT_JOB_ID printJobId,COVERT_CODE covertCode,UPLOAD_PR_ID uploadPrId,CODE_LENGTH codeLength,NUMBEROFQUERY numberOfQuery, " +
				"FIRSTTIME firstTime,COMMON_CODE_LOG_ID commonCodeLogId,COMMON_CODE_LOG_ID " +
				"from PRIMARY_CODE where PRIMARY_CODE like '"+codePrefix+"%' and UPLOAD_PR_ID = "+aqc.getId();
		genFile(sql, file);
		return file;
	}
	

	/**
	 * 生成条码文件
	 * @param sql
	 * @param file
	 * @param type
	 * @throws Exception
	 */
	private void genFile(String sql, File file) throws Exception {
		Session session = HibernateUtil.currentSession();
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
				Connection con = session.connection();
				Statement statement = con.createStatement();
				ResultSet result = statement.executeQuery(sql);){
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
				PrimaryCode obj = new PrimaryCode();
				MapUtil.mapToObjectIgnoreCase(map, obj);
				JSONObject object = JSONObject.fromBean(obj, primaryCodeExcludes);
				bw.write(object.toString());
				bw.write("\r\n");
				bw.flush();
			}
		} 
	}
}
