package com.winsafe.drp.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppInventoryUpload;
import com.winsafe.drp.dao.AppInventoryUploadLog;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.InventoryUploadLog;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.sap.task.SapUploadTask;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.SapConfig;

/**
 * 自动处理生产数据
 * @author Andy.liu
 *
 */
public class InventoryUploadTask extends Thread {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(SapUploadTask.class);

	private static List<InventoryUploadLog> inventoryUploadLogs;

	private AppPrintJob appPrintJob = new AppPrintJob();
	
	private AppInventoryUploadLog appInventoryUploadLog = new AppInventoryUploadLog();
	
	private AppInventoryUpload appInventoryUpload = new AppInventoryUpload();
	
	private AppProductStockpile apsp = new AppProductStockpile();
	
	private AppProduct appProduct = new AppProduct();
	
	private Integer defaultUnitId;

	private AppFUnit appFUnit = new AppFUnit();
	
	// tommy add
	// 增加批处理的数据大小， 默认100条记录
	private Integer inventoryBatchSzie=100;
	
	protected Map<String, Product> existMaterialCodes = new HashMap<String, Product>();
	protected Set<String> notExistsMaterialCodes = new HashSet<String>();
	protected Set<String> materialWithoutFunit = new HashSet<String>();
	protected Map<String, FUnit> materialWithFunit = new HashMap<String, FUnit>();
	/**
	 * 初始化要处理的任务
	 */
	@SuppressWarnings("unchecked")
	public void init() throws Exception {
		String whereSql = " where isDeal = "
				+ SapUploadLogStatus.NOT_PROCESS.getDatabaseValue();
		inventoryUploadLogs = appInventoryUploadLog.getInventoryUploadLogByWhere(whereSql);
		defaultUnitId = Integer.parseInt((String)SapConfig.getSapConfig().get("unitId"));
		if (null!=SapConfig.getSapConfig().get("inventoryBatchSzie")) {
			try {
			inventoryBatchSzie = Integer.parseInt((String)SapConfig.getSapConfig().get("inventoryBatchSzie"));
			} catch (Exception e) {
				inventoryBatchSzie=100;
			}
		}
		
		
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start processing sap upload file.");
					this.init();
					isRunning = true;
//					logger.info(DateUtil.getCurrentDate()
//							+ " 自动处理SAP上传文件任务---开始---");
					if (inventoryUploadLogs != null) {
						for (int i = 0; i < inventoryUploadLogs.size(); i++) {
							long startTime = System.currentTimeMillis();
							if(startProcess(inventoryUploadLogs.get(i))) {
								execute(inventoryUploadLogs.get(i));
							}
							long endTime = System.currentTimeMillis();
							logger.debug("文件"
									+ inventoryUploadLogs.get(i).getFilePath()
									+ "处理时间为" + (endTime - startTime) + "ms "
									+ (endTime - startTime) / 1000 + "s");
							inventoryUploadLogs.remove(i);
						}
					}
				} catch (Exception e) {
					logger.error(e);
//					logger.info(DateUtil.getCurrentDate()
//							+ " 自动处理SAP上传文件任务发生异常" + e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
//					logger.info(DateUtil.getCurrentDate()
//							+ " 自动处理SAP上传文件任务---结束---");
				}
			}
		}
	}

	// 更新文件上传日志状态
	private void updInventoryUploadLog(InventoryUploadLog inventoryUploadLog) {
		logger.debug("update sapUploadLog isdeal status to " + SapUploadLogStatus.parse(inventoryUploadLog.getIsDeal()).getDisplayName());
		try {
			appInventoryUploadLog.updInventoryUploadLog(inventoryUploadLog);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("error occurred when updating sapUploadLog processing status: " + e.getMessage(),
					e);
			HibernateUtil.rollbackTransaction();
		}
		
	}

	private boolean startProcess(InventoryUploadLog inventoryUploadLog) {
		String sql = "update INVENTORY_UPLOAD_LOG set isdeal = "
				+ SapUploadLogStatus.PROCESSING.getDatabaseValue()
				+ " where id = " + inventoryUploadLog.getId() + " and isdeal="
				+ SapUploadLogStatus.NOT_PROCESS.getDatabaseValue();
		try {
			int result = appInventoryUploadLog.updInventoryUploadLogBySql(sql);
			HibernateUtil.commitTransaction();
			if(result > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(
					"hibernate rollback error when update InventoryUploadLog's isdeal status: "
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
	public void execute(InventoryUploadLog inventoryUploadLog) {
		logger.debug("start process sap file " + inventoryUploadLog.getFileName());
		boolean hasError = false;
		StringBuffer resultMsg = new StringBuffer();
		String filePath = inventoryUploadLog.getFilePath();
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				hasError = true;
				resultMsg = resultMsg.append("文件不存在：").append(filePath);
			} else {
				hasError = addInventoryUpload(file, resultMsg, inventoryUploadLog);
				if(!hasError) {
				//	addPrintJob(inventoryUploadLog);
					hasError=updateDataWithUpload(resultMsg,inventoryUploadLog);
					if (!hasError)  HibernateUtil.commitTransaction();
				}
			}
		} catch (Exception e) {
			hasError = true;
			logger.error("processing sap upload file error: " + e.getMessage(),
					e);
			resultMsg = resultMsg.append("导入数据时发生系统错误" + e.getMessage());
			HibernateUtil.rollbackTransaction();
		}
		
		if(!hasError) {
			inventoryUploadLog.setIsDeal(SapUploadLogStatus.PROCESS_SUCCESS
					.getDatabaseValue());
			updInventoryUploadLog(inventoryUploadLog);
		} else {
			inventoryUploadLog.setIsDeal(SapUploadLogStatus.PROCESS_FAIL
					.getDatabaseValue());
			createLogFile(inventoryUploadLog, resultMsg);
			updInventoryUploadLog(inventoryUploadLog);
		}
		logger.debug("process inventory file " + inventoryUploadLog.getFileName() + " complete");
	}
	
	private boolean addInventoryUpload(File file, StringBuffer resultMsg, InventoryUploadLog inventoryUploadLog) throws Exception {
		String[] ids = file.getName().split("_");
		Integer lineNumber = 0;
		boolean hasError = false;
		List<String> inventoryUploadBatchSqls = new ArrayList<String>();
		List<String> updateIdcodeBatchSqls = new ArrayList<String>();
		BufferedReader br = null;
		Set<String> set = new HashSet<String>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			// 读取并验证文件中的内容
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				lineNumber++;
				// 判断是否为空行
				if ("".equals(line.trim())) {
					continue;
				}
				String[] inventoryData = line.split(",");
				if(inventoryData.length != 11) {
					resultMsg.append("第"+lineNumber+"行数据格式不正确" + "\r\n");
					hasError = true;
				} else {
					if(set.contains(inventoryData[4])) {
						resultMsg.append("第"+lineNumber+"行存在重复箱码：" + inventoryData[4] +"\r\n");
						hasError = true;
						continue;
					} else {
						set.add(inventoryData[4]);
					}
					if(!hasError) {
						hasError = doValidate(inventoryData, resultMsg, lineNumber);
					}
					if(!hasError) {
						String batch = inventoryData[7];
						if(StringUtil.isEmpty(batch)) {
							batch = Constants.NO_BATCH;
						}
						
						Integer statusFlag = Integer.parseInt(inventoryData[10]);
						//if(statusFlag == 3 || statusFlag == 1) {
							//循环内只做倒入数据插入 tommy
							String sql = "insert into INVENTORY_UPLOAD (ID,BILLNO,MATERIALCODE,CARTONCODE,BATCH,PRODUCEDATE,EXPIRYDATE,FLAG,ORGANID,WAREHOUSEID,ORGANOECODE,WAREHOUSENCCODE,INVENTORYUPLOADID) values (seq_inventory_upload.nextval,'"
								+ inventoryData[1] + "','" + inventoryData[2] + "','" + inventoryData[4] + "','"
								+ batch + "','" + inventoryData[8] + "','" + inventoryData[9] + "','"
								+ inventoryData[10] + "','"	+ ids[ids.length - 5] + "','" + ids[ids.length - 4] + "','" + ids[ids.length - 3] + "','"
								+ ids[ids.length - 2] + "'," + inventoryUploadLog.getId() + ")";
							
							//String sql2 = "update idcode set WAREHOUSEID = '" + ids[ids.length - 4] + "' where idcode = '" + inventoryData[4] +"'"; 
							//Product product = existMaterialCodes.get(inventoryData[2]);
							//apsp.inProductStockpile(product.getId(), batch, 1.0, ids[ids.length - 4], "000", "","",defaultUnitId,false);
							inventoryUploadBatchSqls.add(sql);
							//updateIdcodeBatchSqls.add(sql2);
							
							if(inventoryUploadBatchSqls.size() > inventoryBatchSzie) {
								EntityManager.executeBatch(inventoryUploadBatchSqls);
								//EntityManager.executeBatch(updateIdcodeBatchSqls);
								inventoryUploadBatchSqls.clear();
								//updateIdcodeBatchSqls.clear();
							} 
						//}
					}
				}
			}
			if(!hasError) {
				EntityManager.executeBatch(inventoryUploadBatchSqls);
				EntityManager.executeBatch(updateIdcodeBatchSqls);
			}
		} catch (Exception e) {
			logger.error("error occurred when read file :");
			hasError = true;
			throw e;
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("failed to close BufferedReader", e);
				}
			}
		}
		return hasError;
	}


	private boolean updateDataWithUpload(StringBuffer resultMsg, InventoryUploadLog inventoryUploadLog) throws Exception {
		//对存在于idcode表中的数据更新当前仓库
		boolean hasError = false;
		String uploadId = inventoryUploadLog.getId()+"";
		String transOrder = "pk999999";
		List<String> updateIdcodeBatchSqls = new ArrayList<String>();
		try {
			//对存在于idcode表中的数据更新当前仓库
			StringBuffer updIdcodeWid = new StringBuffer();
			updIdcodeWid.append(" \r\n update idcode a set a.WAREHOUSEid=(select b.warehouseid from INVENTORY_UPLOAD b ");
			updIdcodeWid.append(" \r\n  where b.inventoryuploadid='"+uploadId+"' and a.IDCODE=b.cartoncode) ");
			updIdcodeWid.append(" \r\n  , a.makeorganid=(select b.organid from INVENTORY_UPLOAD b ");
			updIdcodeWid.append(" \r\n  where b.inventoryuploadid='"+uploadId+"' and a.IDCODE=b.cartoncode) ");
			updIdcodeWid.append(" \r\n  where exists (select 1 from INVENTORY_UPLOAD b ");
			updIdcodeWid.append(" \r\n  where b.inventoryuploadid='"+uploadId+"' and a.IDCODE=b.cartoncode )");
			updIdcodeWid.append(" \r\n ");
			updateIdcodeBatchSqls.add(updIdcodeWid.toString());
			
			EntityManager.executeBatch(updateIdcodeBatchSqls);
			updateIdcodeBatchSqls.clear();
			
			// 对有数据的进行库存更新 （生产系统应该无更新）
			StringBuffer updPs = new StringBuffer();
			
			updPs.append(" \r\n update product_stockpile a set stockpile=stockpile  + (");
			updPs.append(" \r\n select myquantity from  (");
			updPs.append(" \r\n select materialcode, batch,warehouseid,unitquantity*xquantity as myquantity,x.id from ( ");
			updPs.append(" \r\n select materialcode, batch,warehouseid,count(cartoncode) as unitquantity,b.id from INVENTORY_UPLOAD a, PRODUCT b");
			updPs.append(" \r\n where inventoryuploadid='"+uploadId+"' and b.mcode =a.materialcode");
			updPs.append(" \r\n group by materialcode, batch,warehouseid ,b.id");
			updPs.append(" \r\n ) x, F_UNIT c  where  x.id=c.productid  and c.funitid=2 ");
			updPs.append(" \r\n ) b  where a.productid=b.id and a.batch=b.batch and a.warehouseid=b.warehouseid )");
			updPs.append(" \r\n where exists (select 1 from (");
			updPs.append(" \r\n select materialcode, batch,warehouseid,unitquantity*xquantity as myquantity,x.id from ( ");
			updPs.append(" \r\n select materialcode, batch,warehouseid,count(cartoncode) as unitquantity,b.id from INVENTORY_UPLOAD a, PRODUCT b");
			updPs.append(" \r\n where inventoryuploadid='"+uploadId+"' and b.mcode =a.materialcode");
			updPs.append(" \r\n group by materialcode, batch,warehouseid ,b.id");
			updPs.append(" \r\n ) x, F_UNIT c  where  x.id=c.productid  and c.funitid=2 ");
			updPs.append(" \r\n ) b  where a.productid=b.id and a.batch=b.batch and a.warehouseid=b.warehouseid) ");
			updateIdcodeBatchSqls.add(updPs.toString());	
			
			EntityManager.executeBatch(updateIdcodeBatchSqls);
			updateIdcodeBatchSqls.clear();
			
			// 对有数据的进行库存更新 （生产系统应该无更新）
			StringBuffer updPsAll = new StringBuffer();

			updPsAll.append(" \r\n update product_stockpile_all a set stockpile=stockpile  + (");
			updPsAll.append(" \r\n select myquantity from (");
			updPsAll.append(" \r\n select materialcode, batch,warehouseid,unitquantity*xquantity as myquantity,x.id from ( ");
			updPsAll.append(" \r\n select materialcode, batch,warehouseid,count(cartoncode) as unitquantity,b.id from INVENTORY_UPLOAD a, PRODUCT b");
			updPsAll.append(" \r\n where inventoryuploadid='"+uploadId+"' and b.mcode =a.materialcode");
			updPsAll.append(" \r\n group by materialcode, batch,warehouseid ,b.id");
			updPsAll.append(" \r\n ) x, F_UNIT c  where  x.id=c.productid  and c.funitid=2 ");
			updPsAll.append(" \r\n ) b  where a.productid=b.id and a.batch=b.batch and a.warehouseid=b.warehouseid)");
			updPsAll.append(" \r\n where exists (select 1 from (");
			updPsAll.append(" \r\n select materialcode, batch,warehouseid,unitquantity*xquantity as myquantity,x.id from ( ");
			updPsAll.append(" \r\n select materialcode, batch,warehouseid,count(cartoncode) as unitquantity,b.id from INVENTORY_UPLOAD a, PRODUCT b");
			updPsAll.append(" \r\n where inventoryuploadid='"+uploadId+"' and b.mcode =a.materialcode");
			updPsAll.append(" \r\n group by materialcode, batch,warehouseid ,b.id");
			updPsAll.append(" \r\n ) x, F_UNIT c  where  x.id=c.productid  and c.funitid=2 ");
			updPsAll.append(" \r\n ) b  where a.productid=b.id and a.batch=b.batch and a.warehouseid=b.warehouseid) ");
			updateIdcodeBatchSqls.add(updPsAll.toString());	
			
			EntityManager.executeBatch(updateIdcodeBatchSqls);
			updateIdcodeBatchSqls.clear();
			
			// 对SAP的数据， 插入到箱码表中。(SAP的数据）
			// 箱码表中，inner_pin_code 为批次信息。PALLET_CODE保存盘点单的上传ID
			StringBuffer insertCarcontCode = new StringBuffer();
			insertCarcontCode.append(" \r\n insert into CARTON_CODE (PRODUCT_ID,CARTON_CODE,PALLET_CODE,MATERIAL_CODE,CREATE_DATE,PRIMARY_CODE_STATUS,inner_pin_code)");
			insertCarcontCode.append(" \r\n select b.id,cartoncode ,inventoryuploadid,materialcode, SYSDATE,1,batch from INVENTORY_UPLOAD a, PRODUCT b");
			insertCarcontCode.append(" \r\n where inventoryuploadid='"+uploadId+"' and b.mcode =a.materialcode");
			insertCarcontCode.append(" \r\n and  CARTONcode not  in (select CARTON_CODE from CARTON_CODE)");
			updateIdcodeBatchSqls.add(insertCarcontCode.toString());
			
			// 对SAP数据， 插入到流通码表中，（SAp数据）
			StringBuffer insertIdcode = new StringBuffer();
			
			insertIdcode.append(" \r\n insert into idcode (idcode,PRODUCTID,PRODUCTNAME,batch,PRODUCEDATE,unitid,quantity,isuse,isout,CARTONCODE,PALLETCODE,NCLOTNO,MAKEORGANID,WAREHOUSEID,WAREHOUSEBIT,packquantity)");
			insertIdcode.append(" \r\n select cartoncode ,b.id,b.PRODUCTNAME,BATCH,producedate,2,1,1,0,cartoncode,inventoryuploadid,materialcode,ORGANID,warehouseid,'000',xquantity");
			insertIdcode.append(" \r\n from INVENTORY_UPLOAD a, PRODUCT b , f_unit c");
			insertIdcode.append(" \r\n where inventoryuploadid='"+uploadId+"' and b.mcode =a.materialcode AND b.id=c.productid and c.funitid=2");
			insertIdcode.append(" \r\n and  cartoncode not  in (select idcode from idcode)");
			updateIdcodeBatchSqls.add(insertIdcode.toString());	
			
			EntityManager.executeBatch(updateIdcodeBatchSqls);
			updateIdcodeBatchSqls.clear();
			
			// 对无库存的产品插入到库存表中（SAp和iconcept 数据都要）
			StringBuffer insertPs = new StringBuffer();

			insertPs.append(" \r\n insert into product_stockpile (id,productid,countunit,batch,warehouseid,warehousebit,stockpile,prepareout,islock,makedate)");
			insertPs.append(" \r\n select print_job_id_OLD_SEQ.nextval,id,2,batch,warehouseid,'000',myquantity ,0,0,SYSDATE from (");
			insertPs.append(" \r\n select x.id,x.batch,x.materialcode,x.warehouseid,x.myquantity ,y.id as yid from (");
			insertPs.append(" \r\n select z.id,batch,materialcode,warehouseid, unitquantity*c.xquantity as myquantity from ( ");
			insertPs.append(" \r\n select b.id,batch,materialcode,warehouseid,count(cartoncode) as unitquantity from INVENTORY_UPLOAD a, PRODUCT b");
			insertPs.append(" \r\n where inventoryuploadid='"+uploadId+"' and b.mcode =a.materialcode");
			insertPs.append(" \r\n group by materialcode, batch,warehouseid ,b.id");
			insertPs.append(" \r\n ) z, F_UNIT c where  z.id=c.productid  and c.funitid=2 ");
			insertPs.append(" \r\n ) x LEFT JOIN product_stockpile y on   x.id=y.productid and y.batch=x.batch and x.warehouseid=y.warehouseid");
			insertPs.append(" \r\n )");
			insertPs.append(" \r\n where  yid is NULL");
			insertPs.append(" \r\n ");
			updateIdcodeBatchSqls.add(insertPs.toString());
			
			EntityManager.executeBatch(updateIdcodeBatchSqls);
			updateIdcodeBatchSqls.clear();
			
			// 对无库存的产品插入到库存表中（SAp和iconcept 数据都要）
			StringBuffer insertPsAll = new StringBuffer();
			insertPsAll.append(" \r\n insert into product_stockpile_all (id,productid,countunit,batch,warehouseid,stockpile,prepareout,islock,makedate)");
			insertPsAll.append(" \r\n select print_job_id_OLD_SEQ.nextval,id,2,batch,warehouseid,myquantity ,0,0,SYSDATE from (");
			insertPsAll.append(" \r\n select x.id,x.batch,x.materialcode,x.warehouseid,x.myquantity ,y.id as yid from (");
			insertPsAll.append(" \r\n select z.id,batch,materialcode,warehouseid, unitquantity*c.xquantity as myquantity from ( ");
			insertPsAll.append(" \r\n select b.id,batch,materialcode,warehouseid,count(cartoncode) as unitquantity from INVENTORY_UPLOAD a, PRODUCT b");
			insertPsAll.append(" \r\n where inventoryuploadid='"+uploadId+"' and b.mcode =a.materialcode");
			insertPsAll.append(" \r\n group by materialcode, batch,warehouseid ,b.id");
			insertPsAll.append(" \r\n ) z, F_UNIT c where  z.id=c.productid  and c.funitid=2 ");
			insertPsAll.append(" \r\n ) x LEFT JOIN product_stockpile_all y on   x.id=y.productid and y.batch=x.batch and x.warehouseid=y.warehouseid");
			insertPsAll.append(" \r\n )");
			insertPsAll.append(" \r\n where  yid is NULL");
			insertPsAll.append(" \r\n ");
			updateIdcodeBatchSqls.add(insertPsAll.toString());
			
			//EntityManager.executeBatch(updateIdcodeBatchSqls);
			//updateIdcodeBatchSqls.clear();
			
			// 生成打印任务 PRINT_JOB_ID为负数， TRANS_ORDER 为‘pk999999'表示盘库导入的任务号
			// 所有的机构盘库导入用相同的batch, (实际上 materialcode,batch,producedate,expirydate,b.id 决定一个打印任务）
			StringBuffer insertPrintJob = new StringBuffer();
			
			insertPrintJob.append(" \r\n  insert into PRINT_JOB (PRINT_JOB_ID,TRANS_ORDER,MATERIAL_CODE,BATCH_NUMBER,PRODUCTION_DATE,EXPIRY_DATE,PRODUCT_ID,ISDELETE,CONFIRM_FLAG)");
			insertPrintJob.append(" \r\n  select  print_job_id_OLD_SEQ.nextval,'" + transOrder + "', materialcode,batch,producedate,expirydate, id,1,1 from ( ");
			insertPrintJob.append(" \r\n  select  materialcode,batch,producedate,expirydate, b.id from ");
			insertPrintJob.append(" \r\n  (");
			insertPrintJob.append(" \r\n  select  materialcode,batch,producedate,expirydate,inventoryuploadid from INVENTORY_UPLOAD");
			insertPrintJob.append(" \r\n  LEFT JOIN CARTON_CODE  on CARTONCODE=CARTON_CODE where PALLET_CODE='"+ uploadId + "'");
			insertPrintJob.append(" \r\n  ) a, PRODUCT b");
			insertPrintJob.append(" \r\n  where inventoryuploadid='"+uploadId+"' and b.mcode =a.materialcode ");
			insertPrintJob.append(" \r\n  group by materialcode,batch,producedate,expirydate,b.id");
			insertPrintJob.append(" \r\n  ) x LEFT JOIN PRINT_JOB c on");
			insertPrintJob.append(" \r\n  materialcode=c.MATERIAL_CODE and batch=c.BATCH_NUMBER and producedate=c.PRODUCTION_DATE and expirydate=c.EXPIRY_DATE and id=PRODUCT_ID and c.TRANS_ORDER='" + transOrder + "'");
			insertPrintJob.append(" \r\n  where PRINT_JOB_id is NULL ");
			insertPrintJob.append(" \r\n  ");
			updateIdcodeBatchSqls.add(insertPrintJob.toString());
			
			EntityManager.executeBatch(updateIdcodeBatchSqls);
			updateIdcodeBatchSqls.clear();
			
			
			// 更新刚才插入的箱码中，对应的print_job_id
			// 通过TRANS_ORDER，pk999999，PALLET_CODE= inventoryuploadid
			StringBuffer updCartonCodePrintJobId = new StringBuffer();
			updCartonCodePrintJobId.append(" \r\n  update CARTON_CODE c set print_job_id= (");
			updCartonCodePrintJobId.append(" \r\n  select print_job_id from PRINT_JOB p ");
			updCartonCodePrintJobId.append(" \r\n  where p.TRANS_ORDER='" + transOrder + "' and p.MATERIAL_CODE=c.MATERIAL_CODE and p.BATCH_NUMBER=c.inner_pin_code and c.print_job_id is null and c.PALLET_CODE='" + uploadId + "' and rownum<2 " );
			updCartonCodePrintJobId.append(" \r\n  ) ");
			updCartonCodePrintJobId.append(" \r\n  where exists (");
			updCartonCodePrintJobId.append(" \r\n  select 1 from PRINT_JOB p");
			updCartonCodePrintJobId.append(" \r\n  where p.TRANS_ORDER='" + transOrder + "' and p.MATERIAL_CODE=c.MATERIAL_CODE and p.BATCH_NUMBER=c.inner_pin_code and c.print_job_id is null and c.PALLET_CODE='" + uploadId + "' and rownum<2 ");
			updCartonCodePrintJobId.append(" \r\n  )");
			updCartonCodePrintJobId.append(" \r\n  ");
			updateIdcodeBatchSqls.add(updCartonCodePrintJobId.toString());
			
			EntityManager.executeBatch(updateIdcodeBatchSqls);
			updateIdcodeBatchSqls.clear();
			
			//更新库存中的小包装单位
				
			StringBuffer updstockpileallCountUnit = new StringBuffer();
			updstockpileallCountUnit.append(" \r\n  update product_stockpile_all a set countunit= (");
			updstockpileallCountUnit.append(" \r\n  select funitid from f_unit c where c.ismain=1 and a.productid=c.productid and a.countunit=2");
			updstockpileallCountUnit.append(" \r\n  ) where exists (select 1 from f_unit c where c.ismain=1 and a.productid=c.productid and a.countunit=2 )");
			updateIdcodeBatchSqls.add(updstockpileallCountUnit.toString());
			
			EntityManager.executeBatch(updateIdcodeBatchSqls);
			updateIdcodeBatchSqls.clear();
			
			//更新库存中的小包装单位
			StringBuffer updstockpileCountUnit = new StringBuffer();
			updstockpileCountUnit.append(" \r\n  update product_stockpile a set countunit= (");
			updstockpileCountUnit.append(" \r\n  select funitid from f_unit c where c.ismain=1 and a.productid=c.productid and a.countunit=2");
			updstockpileCountUnit.append(" \r\n  ) where exists (select 1 from f_unit c where c.ismain=1 and a.productid=c.productid and a.countunit=2 )");
			updateIdcodeBatchSqls.add(updstockpileCountUnit.toString());
			
			EntityManager.executeBatch(updateIdcodeBatchSqls);
			updateIdcodeBatchSqls.clear();
						
		} catch (Exception e) {
			logger.error("error occurred when deal with database  :");
			logger.error(e);
			hasError = true;
			throw e;
		} finally {
		}
		return hasError;
	}


	private void addPrintJob(InventoryUploadLog inventoryUploadLog) throws Exception {
		List ls = appInventoryUpload.getInventoryUpload(inventoryUploadLog.getId());
		for (int i = 0; i < ls.size(); i++) {
			Object[] ob = (Object[]) ls.get(i);
			Product product = existMaterialCodes.get((String)ob[4]);
			FUnit funit = materialWithFunit.get(product.getId());
			PrintJob pj = new PrintJob();
			pj.setTransOrder(StringUtil.removeNull((String)ob[0]));
			pj.setProductionDate(StringUtil.removeNull((String)ob[1]));
			pj.setBatchNumber(StringUtil.removeNull((String)ob[2]));
			pj.setExpiryDate(StringUtil.removeNull((String)ob[3]));
			pj.setMaterialCode(StringUtil.removeNull((String)ob[4]));
			if(product != null) {
				pj.setMaterialName(product.getProductname());
				pj.setProductId(product.getId());
			}
			pj.setIsDelete(YesOrNo.YES.getValue());
			pj.setPrintJobId(appPrintJob.getPrintJobIdForInventoryUpload());
			pj.setCreateUser(inventoryUploadLog.getMakeId());
			appPrintJob.addPrintJob(pj);
			String whereSql = createWhereSql(pj.getTransOrder(), pj.getProductionDate(), pj.getBatchNumber(), pj.getExpiryDate(), inventoryUploadLog);
			appInventoryUploadLog.updCartonCode(whereSql, pj);
			appInventoryUploadLog.addCartonCode(whereSql, pj);
			appInventoryUploadLog.addIdcode(whereSql, pj, funit);
		}
		
	}

	


	private String createWhereSql(String transOrder, String productionDate,
			String batchNumber, String expiryDate, InventoryUploadLog inventoryUploadLog) {
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		if(StringUtil.isEmpty(transOrder)) {
			sb.append(" iu.BILLNO is null and ");
		} else {
			sb.append(" iu.BILLNO ='"+transOrder+"' and ");
		}
		if(StringUtil.isEmpty(productionDate)) {
			sb.append(" iu.PRODUCEDATE is null and ");
		} else {
			sb.append(" iu.PRODUCEDATE ='"+productionDate+"' and ");
		}
		if(StringUtil.isEmpty(batchNumber)) {
			sb.append(" iu.BATCH is null and ");
		} else {
			sb.append(" iu.BATCH ='"+batchNumber+"' and ");
		}
		if(StringUtil.isEmpty(expiryDate)) {
			sb.append(" iu.EXPIRYDATE is null and ");
		} else {
			sb.append(" iu.EXPIRYDATE ='"+expiryDate+"' and ");
		}
		sb.append(" iu.INVENTORYUPLOADID = ").append(inventoryUploadLog.getId());
		
		return sb.toString();
	}

	private boolean doValidate(String[] inventoryData, StringBuffer resultMsg, Integer lineNumber) {
		boolean hasError = false;
		if(!isMaterialCodeExists(inventoryData[2])) {
			hasError = true;
			resultMsg.append("第"+lineNumber+"行物料号"+inventoryData[2]+"不存在\r\n" );
		} else {
			if(!isMaterialFUnitExists(existMaterialCodes.get(inventoryData[2]).getId())) {
				hasError = true;
				resultMsg.append("第"+lineNumber+"行物料号为"+inventoryData[2]+"的产品默认包装比例[件]未设置\r\n");
			}
		}
		return hasError;
	}

	private void createLogFile(InventoryUploadLog inventoryUploadLog, StringBuffer resultMsg) {
		try {
			// 创建日志文件
			String savePath = FileUploadUtil.getInventoryUpdateLogFilePath()
					+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
					+ "/";
			String fileName = inventoryUploadLog.getFileName();
			String saveName = DateUtil.getCurrentDateTimeString() + "_"
					+ fileName.substring(fileName.indexOf("."), fileName.length())
					+ "_LOG.txt";
			logger.debug("start creating sap log file :" + savePath + saveName);
			FileUploadUtil.CreateLogFile(resultMsg.toString(), savePath,
					saveName);
			inventoryUploadLog.setLogFilePath(savePath + saveName);
		} catch (Exception e) {
			logger.error(
					"error occurred when creating sapUploadLog log file: " + e.getMessage(), e);
		} 
	}
	
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
	 * 验证物料是否设置了包装比例关系
	 * Create Time 2014-10-20 上午11:23:26
	 * @param materialCode
	 * @author Ryan.xi
	 */
	public boolean isMaterialFUnitExists(String productId) {
		if(materialWithFunit.containsKey(productId)) {
			return true;
		} else if(materialWithoutFunit.contains(productId)){
			return false;
		} else {
			FUnit fUnit = appFUnit.getFUnit(productId, defaultUnitId);
			if(fUnit == null) {
				materialWithoutFunit.add(productId);
				return false;
			} else {
				materialWithFunit.put(productId, fUnit);
				return true;
			}
		}
	}
	
}
