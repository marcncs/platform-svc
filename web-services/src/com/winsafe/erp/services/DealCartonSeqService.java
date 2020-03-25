package com.winsafe.erp.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.PlantConfig;
import com.winsafe.erp.dao.AppCartonSeqDao;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.metadata.CartonSeqStatus;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.pojo.UploadCartonSeqLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.MD5BigFileUtil;

public class DealCartonSeqService {

	private static Logger logger = Logger.getLogger(DealCartonSeqService.class);
	
	// 同步锁
	private static Object lock = new Object();
		
	private AppCartonSeqDao csdao = new AppCartonSeqDao();
	
	private AppProductPlan appProductPlan = new AppProductPlan();
	
	private AppProduct appProduct = new AppProduct();

	/**
	 * 上传后关联码文件
	 * @param formFile
	 * @param fileSufix
	 * @throws Exception
	 */
	public String uploadCsFile(FormFile formFile, UsersBean users, String fileSufix, String plNo) throws Exception {
		synchronized (lock) {
			try {
				String resultMsg = checkFile(formFile, fileSufix);
				
				if(!StringUtil.isEmpty(resultMsg)) {
					return resultMsg;
				}
				
				this.addCartonSeqLog(formFile, users, plNo);
				return "";
			} catch (Exception e) {
				logger.error("",e);
				return "发生错误，请联系管理员查看";
			}
			
		}
	}

	private String checkFile(FormFile file, String fileSufix) {
		if (file != null && !file.equals("") && file.getContentType() != null) {
			if (!(file.getFileName().toLowerCase().indexOf(fileSufix) >= 0)) {
				return "文件后缀不正确";
			}
		} else {
			return "上传文件为空";
		}
		return null;
	}
	
	private String addCartonSeqLog(FormFile csFile, UsersBean users, String plId) throws Exception {
		//检查是否已上传过
		String md5 = MD5BigFileUtil.md5(csFile.getInputStream());
		
		UploadCartonSeqLog isNewFile = csdao.getCartonSeqLogByMD5(md5);
		if (null != isNewFile) {
			return "该文件已上传，请勿重复上传";
		}
		
		// 保存文件
		String fileName = csFile.getFileName();
		String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + fileName;
		String savePath = PlantConfig.getConfig().getProperty("cartonSeqFilePath") + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
		FileUploadUtil.saveUplodedFile(csFile.getInputStream(), savePath, saveFileName);
		
		/*添加文件上传日志--开始*/
		UploadCartonSeqLog uploadcsLog = new UploadCartonSeqLog();
		uploadcsLog.setFileName(saveFileName);
		uploadcsLog.setFilePath(savePath + saveFileName);
		if(users != null) {
			uploadcsLog.setMakeId(users.getUserid());
		} else {
			uploadcsLog.setMakeId(1);
		}
		uploadcsLog.setMakeDate(DateUtil.getCurrentDate());
		uploadcsLog.setIsDeal(SapUploadLogStatus.NOT_PROCESS
				.getDatabaseValue());
		uploadcsLog.setFileHaseCode(md5);
		uploadcsLog.setPlNo(plId);
		csdao.addUploadCartonSeqLog(uploadcsLog);
		/*处理文件*/
//		uploadcsLog = csdao.getCartonSeqLogByMD5(md5);
		//处理文件
		//this.dealCartonSeqFile(uploadcsLog, plId);
		
		return "";
	}
	
	public void dealCartonSeqFile(UploadCartonSeqLog uploadcsLog, String plId) throws Exception{
		// 获取要生成的日志文件路径
		String savePath = PlantConfig.getConfig().getProperty("cartonSeqLogFilePath")
				+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
		String saveName = DateUtil.getCurrentDateTimeString() + "_"
				+ UUID.randomUUID() + "_LOG.txt";
		try {
			uploadcsLog.setIsDeal(SapUploadLogStatus.PROCESSING.getDatabaseValue());
			csdao.updUploadCartonSeqLog(uploadcsLog);
			HibernateUtil.commitTransaction();
			ProductPlan productPlan = appProductPlan.getProductPlanByID(Integer.valueOf(plId));
			String filePath = uploadcsLog.getFilePath();
			File file = new File(filePath);
			
			//错误信息
			StringBuffer resultMsg = new StringBuffer();

			//验证数据返回message
			String ckMsg = this.checkData(file, uploadcsLog, plId, productPlan);
			resultMsg.append(ckMsg);
			createLogFile(resultMsg.toString(), savePath, saveName, uploadcsLog);
			uploadcsLog.setLogFilePath(savePath + saveName);
			uploadcsLog.setIsDeal(SapUploadLogStatus.PROCESS_SUCCESS.getDatabaseValue());
			csdao.updUploadCartonSeqLog(uploadcsLog);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("", e);
			createLogFile("系统异常，请联系管理员", savePath, saveName, uploadcsLog);
			uploadcsLog.setLogFilePath(savePath + saveName);
			uploadcsLog.setIsDeal(SapUploadLogStatus.PROCESS_FAIL.getDatabaseValue());
			csdao.updUploadCartonSeqLog(uploadcsLog);
			HibernateUtil.commitTransaction();
		}
		
	}
	
	/**
	 * 对文件中数据进行检验
	 * @param file
	 * @param dataList
	 * @param productPlan 
	 * @return
	 * @throws Exception
	 */
	private String checkData(File file, UploadCartonSeqLog uploadcsLog, String plId, ProductPlan productPlan) throws Exception{
		Product product = appProduct.getProductByID(productPlan.getProductId());
		String prefix = product.getRegCertType()+product.getRegCertCodeFixed()+product.getProduceType()+product.getSpecCode()+product.getInnerProduceType();
		//Constants.DB_BULK_SIZE;
		List<String> batchSqls = new ArrayList<>();
		//错误信息
		StringBuffer resultMsg = new StringBuffer();
		
		/*处理每一行*/
		int lineNo= 0;
		int errorCount= 0;
		Set<String> primarCodeSet = new HashSet<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				//CSData csData = new CSData();
				// 判断是否为空行
				if ("".equals(line.trim())) {
					continue;
				}
				lineNo++;
				//小码，箱码，PO编号
				String[] data = line.split(",");
				if(data.length != 3) {
					errorCount++;
					resultMsg.append("第"+lineNo+"行数据格式不正确\r\n");
					continue;
				}
				//小码
				String primaryCode = data[0];
				//箱码
				String cartonCode = data[1];
				//productplan编号
				String plNo = data[2];
				//查重
				if (primarCodeSet.contains(primaryCode)) {
					errorCount++;
					resultMsg.append("第"+lineNo+"行小码["+primaryCode+"]重复\r\n");
					continue;
				}
				if(!primaryCode.startsWith(prefix)) {
					errorCount++;
					resultMsg.append("第"+lineNo+"行小包装码["+primaryCode+"]不属于产品["+product.getmCode()+product.getProductname()+"]\r\n");
					continue;
				}
				String seq = primaryCode.substring(12, 21);
				
				if (!plId.equals(plNo)) {
					errorCount++;
					resultMsg.append("第"+lineNo+"行生产计划编号["+plNo+"]与上传时所选的计划编号不一致\r\n");
					continue;
				}
				
				primarCodeSet.add(primaryCode);
				/*csData.setPrimaryCode(primaryCode);
				csData.setCartonCode(cartonCode);
				csData.setPlNo(plNo);
				dataList.add(csData);*/
				batchSqls.add(getInsertTempSql(primaryCode, cartonCode, plNo, seq, lineNo));
				if(batchSqls.size()%Constants.DB_BULK_SIZE == 0) {
					EntityManager.executeBatch(batchSqls);
					batchSqls.clear();
				}
			}
			if(batchSqls.size()>0) {
				EntityManager.executeBatch(batchSqls);
				batchSqls.clear();
			}
		} finally {
			br.close();
		}
		uploadcsLog.setTotalCount(lineNo);
		uploadcsLog.setErrorCount(errorCount);
		//检查小包装码是否存在
		checkIsPrimaryCodeExists();
		//检查箱码是否都在该计划任务中
		checkIsCartonCodeExists(plId);
		//检查小包装是否已使用
		checkIsPrimaryCodeAlreadyUsed();
		//更新小码对应箱码
		updatePriumaryCode(productPlan.getPrintJobId());
		//更细箱序号状态
		updateCartonSeqStatus(product.getId());
		//获取错误信息
		createErrorMsg(resultMsg, uploadcsLog);
		
		return resultMsg.toString();
	}
	
	private void createErrorMsg(StringBuffer resultMsg, UploadCartonSeqLog uploadcsLog) throws Exception {
		String sql ="select ERRORCODE,LINENO from transaction_temp_carton_seq where ERRORCODE!=0";
		List<Map<String,String>> errorList = EntityManager.jdbcquery(sql);
		uploadcsLog.setErrorCount(uploadcsLog.getErrorCount()+errorList.size());
		for(Map<String,String> map : errorList) {
			if("1".equals(map.get("errorcode"))) {
				resultMsg.append("第"+map.get("lineno")+"行小包装码在系统中不存在\r\n");
			} else if("2".equals(map.get("errorcode"))) {
				resultMsg.append("第"+map.get("lineno")+"行箱码不在该生产计划中\r\n");
			} else if("3".equals(map.get("errorcode"))) {
				resultMsg.append("第"+map.get("lineno")+"行小包装码已被使用过\r\n");
			}
		}
	}

	private void updateCartonSeqStatus(String productId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update CARTON_SEQ set status=").append(CartonSeqStatus.USED.getValue()).append(" where productid='").append(productId).append("' and seq in (select seq from transaction_temp_carton_seq where errorcode=0 GROUP BY seq)");
		EntityManager.executeUpdate(sql.toString());
	}

	private void updatePriumaryCode(Integer printJobId) throws Exception { 
		StringBuffer sql = new StringBuffer();
		sql.append("merge into PRIMARY_CODE pcode ");
		sql.append("USING (select PRIMARYCODE,CARTONCODE FROM transaction_temp_carton_seq where errorcode=0) tmp ON (tmp.PRIMARYCODE=pcode.PRIMARY_CODE) ");
		sql.append("WHEN MATCHED THEN ");
		sql.append("UPDATE SET pcode.CARTON_CODE=tmp.CARTONCODE, pcode.IS_USED=1, pcode.PRINT_JOB_ID=").append(printJobId);
		EntityManager.executeUpdate(sql.toString());
	}

	private void checkIsPrimaryCodeAlreadyUsed() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("merge into transaction_temp_carton_seq tmp ");
		sql.append("USING (select PRIMARY_CODE,IS_USED FROM PRIMARY_CODE) pcode ON (tmp.PRIMARYCODE=pcode.PRIMARY_CODE) ");
		sql.append("WHEN MATCHED THEN UPDATE SET tmp.errorcode=3 where pcode.IS_USED=1 ");
		EntityManager.executeUpdate(sql.toString());
		
	}

	private void checkIsCartonCodeExists(String plId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update transaction_temp_carton_seq tmp set errorcode = 2 where not EXISTS (select * from PREPARE_CODE where PRODUCTPLAN_ID=").append(plId).append(" and CODE = tmp.cartoncode) ");
		EntityManager.executeUpdate(sql.toString());
	}

	private void checkIsPrimaryCodeExists() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update transaction_temp_carton_seq tmp set errorcode = 1 where not EXISTS (select PRIMARY_CODE from PRIMARY_CODE where PRIMARY_CODE = tmp.primarycode) ");
		EntityManager.executeUpdate(sql.toString());
	}

	private String getInsertTempSql(String primaryCode, String cartonCode, String plNo, String seq, int lineNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into transaction_temp_carton_seq(primarycode,cartoncode,planid,errorcode,seq,LINENO) ");
		sql.append("values('").append(primaryCode).append("','").append(cartonCode).append("',").append(plNo).append(",0,'").append(seq).append("',").append(lineNo).append(")");
		return sql.toString();
	}
	
	public static void main(String[] args) {
		String primaryCode = "10502003801300000011100193748177";
		String cartonCode = "123";
		String plNo = "123";
		StringBuffer sql = new StringBuffer();
		sql.append("insert into transaction_temp_carton_seq(primarycode,cartoncode,planid,errorcode) ");
		sql.append("values('").append(primaryCode).append("','").append(cartonCode).append("',").append(plNo).append(",0)");
		System.out.println(primaryCode.substring(12, 21));
	}

	/**
	 * 输出日志文件
	 * @param string
	 * @param savePath
	 * @param saveName
	 * @param uploadcsLog
	 */
	private void createLogFile(String string, String savePath, String saveName, UploadCartonSeqLog uploadcsLog) {
		try {
			FileUploadUtil.CreateFileWithMessage(string,
					savePath, saveName);
		} catch (Exception e) {
			logger.error("error occurred when create log file :" +savePath+saveName, e);
		}
	}
}
