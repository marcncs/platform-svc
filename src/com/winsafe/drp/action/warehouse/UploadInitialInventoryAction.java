package com.winsafe.drp.action.warehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.InventoryUploadLog;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.sap.util.FileUploadUtil;

import com.winsafe.drp.dao.AppProduct;

import com.winsafe.drp.dao.AppInventoryUploadLog;

public class UploadInitialInventoryAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(UploadInitialInventoryAction.class);
	
	private AppCartonCode appCartonCode = new AppCartonCode();
	private AppIdcode appIdcode = new AppIdcode();
	private AppPrintJob appPrintJob = new AppPrintJob();
	
	//tommy add for check 物料号
	private Map<String, Product> existMaterialCodes = new HashMap<String, Product>();
	private Set<String> notExistsMaterialCodes = new HashSet<String>();
	private Map<String, String> batchAndProductionDate = new HashMap<String, String>();
	private AppProduct appProduct = new AppProduct();
	
	private AppInventoryUploadLog appInventoryUploadLog = new AppInventoryUploadLog();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			super.initdata(request);
			boolean hasError = false;
			StringBuffer resultMsg = new StringBuffer();
			StringBuffer dealMsg = new StringBuffer();
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile file = (FormFile) mf.getIdcodefile();
			if (file != null && !file.equals("") && file.getContentType() != null) {
				if (!(file.getFileName().toLowerCase().indexOf("txt") >= 0)) {
					hasError = true;
					resultMsg.append("文件后缀名不正确");
				}
			} else {
				resultMsg.append("文件上传失败");
				hasError = true;
			}
			DBUserLog.addUserLog(request, "盘库文件");
			if(!hasError) {
				String fileName = file.getFileName().substring(0, file.getFileName().indexOf("txt")) + "_confirm.txt";
				
				//tommy add for 保存文件。记录信息
				//记录保存的文件名称
				String saveName = DateUtil.getCurrentDateTimeString() + "_" + file.getFileName();
				String savePath = FileUploadUtil.getInventoryUpdateFilePath() + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
				FileUploadUtil.saveUplodedFile(file.getInputStream(), savePath, saveName);

				// 生成SAP文件上传日志记录
				InventoryUploadLog inventoryUploadLog = new InventoryUploadLog();
				inventoryUploadLog.setFileName(file.getFileName());
				inventoryUploadLog.setFilePath(savePath + saveName);
				inventoryUploadLog.setMakeId(users.getUserid());
				inventoryUploadLog.setMakeOrganId(users.getMakeorganid());
				inventoryUploadLog.setMakeDeptId(users.getMakedeptid());
				inventoryUploadLog.setMakeDate(Dateutil.getCurrentDate());
				inventoryUploadLog.setIsDeal(SapUploadLogStatus.PROCESSING
						.getDatabaseValue());
				appInventoryUploadLog.addInventoryUploadLog(inventoryUploadLog);
				HibernateUtil.commitTransaction();
				
				resultMsg.append("文件导入成功, 请通过日志查看处理结果");
				request.setAttribute("result", resultMsg);

				//response.reset();
				//response.setHeader("content-disposition",
				//		"attachment; filename=" + fileName);
				//response.setContentType("text/plain;charset=UTF-8");
				//PrintWriter pw = response.getWriter();
				//readFile(file ,pw);
				dealFile(file,dealMsg);
				String outputFileName;
				outputFileName="output_"+saveName;
				FileUploadUtil.CreateLogFile(dealMsg.toString(), savePath,
						outputFileName);
				inventoryUploadLog.setLogFilePath(savePath + outputFileName);
				inventoryUploadLog.setIsDeal(SapUploadLogStatus.PROCESS_SUCCESS
						.getDatabaseValue());
				appInventoryUploadLog.updInventoryUploadLog(inventoryUploadLog);
				HibernateUtil.commitTransaction();
				return mapping.findForward("result");
			} else {
				request.setAttribute("result", resultMsg);
				return mapping.findForward("result");
			}
		} catch (Exception e) {
			logger.error("初始盘库文件导入", e);
			request.setAttribute("result", "文件");
			return mapping.findForward("result");
		} finally {
			existMaterialCodes.clear();
			notExistsMaterialCodes.clear();
			batchAndProductionDate.clear();
		}
		//return null;
	}

	private void readFile(FormFile file, PrintWriter pw) {
		Integer lineNumber = 0;
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			// 读取并验证文件中的内容
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				lineNumber++;
				// 判断是否为空行
				if ("".equals(line.trim())) {
					continue;
				}
				String[] inventoryData = line.split(",");
				if(inventoryData.length != 7) {
					pw.write(line + "  数据格式不正确" + "\r\n");
				} else {
					readData(inventoryData, pw, line);
				}
			}
		} catch (Exception e) {
			logger.error("error occurred when read file :");
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("failed to close BufferedReader", e);
				}
			}
		}
		
	}

	private void dealFile(FormFile file,StringBuffer resultMsg ) {
		Integer lineNumber = 0;
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			// 读取并验证文件中的内容
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				lineNumber++;
				// 判断是否为空行
				if ("".equals(line.trim())) {
					continue;
				}
				String[] inventoryData = line.split(",");
				if(inventoryData.length != 7) {
					resultMsg.append(line + "  数据格式不正确" + "\r\n");
				} else {
					//readData(inventoryData, pw, line);
					dealData(inventoryData, resultMsg, line);
				}
			}
		} catch (Exception e) {
			logger.error("error occurred when read file :");
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("failed to close BufferedReader", e);
				}
			}
		}
		
	}
	private void readData(String[] inventoryData, PrintWriter pw, String line) throws Exception {
		Integer statusFlag = 0;
		String cartonCode = inventoryData[4];
		String batch = "";
		String productionDate = "";
		String expiryDate = "";
		
		Product procuct=null;

		//tommy 需要检查物料号是否存在。
		if(!isMaterialCodeExists(inventoryData[2])) {
			statusFlag=-5;
			pw.write(line + "," + batch + "," + productionDate + "," + expiryDate + "," + statusFlag.toString() + "\r\n");
			return;
		} else {
			procuct=existMaterialCodes.get(inventoryData[2]);
		}

		CartonCode cc = appCartonCode.getByCartonCode(cartonCode);
		
		if(cc != null) {
			statusFlag = 1;
			if(!cc.getMaterialCode().equals(inventoryData[2])){
				statusFlag = -2;
			}
			Idcode idcode = appIdcode.getIdcodeById(cartonCode);
			if(idcode != null && !StringUtil.isEmpty(idcode.getWarehouseid())) {
				if (!"11954".equals(idcode.getWarehouseid())) {
				statusFlag = -3;
				}
			}
			//tommy modified 不管如何，都要检查批次信息
			//if(statusFlag == 1) {
				if(idcode != null) {
					batch = idcode.getBatch();
				} 
				PrintJob printJob = appPrintJob.getPrintJobByID(cc.getPrintJobId());
				if(printJob != null) {
					productionDate = printJob.getProductionDate();
					expiryDate = printJob.getExpiryDate();
					if (null==expiryDate) expiryDate="";
				}
			//}
			pw.write(line + "," + batch + "," + productionDate + "," + expiryDate + "," + statusFlag.toString() + "\r\n");
		} else {
			statusFlag = -1;
			if(!StringUtil.isEmpty(inventoryData[3])) {
				productionDate = inventoryData[3].substring(2, 8);
				if(isValidDate(productionDate, "yyMMdd")) {
					statusFlag = 3;
					batch=inventoryData[3].substring(10);
					productionDate="20"+inventoryData[3].substring(2, 8);
					
					SimpleDateFormat tempformat = new SimpleDateFormat("yyyyMMdd");
					 Date date = tempformat.parse(productionDate);
					 //expiryDate=tempformat.format(new Date(date.getTime() + procuct.getExpiryDays() * 24 * 60 * 60 * 1000));
					 
					 Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						calendar.add(Calendar.DATE, procuct.getExpiryDays());
						expiryDate = tempformat.format(calendar.getTime());
				} else {
					statusFlag = -5;
					productionDate = "";
				}
			}
			pw.write(line + "," + batch + "," + productionDate + "," + expiryDate + "," + statusFlag.toString() + "\r\n");
		}
	}
	
	private void dealData(String[] inventoryData, StringBuffer resultMsg, String line) throws Exception {
		Integer statusFlag = 0;
		String cartonCode = inventoryData[4];
		String batch = "";
		String productionDate = "";
		String expiryDate = "";
		
		Product procuct=null;

		//tommy 需要检查物料号是否存在。
		if(!isMaterialCodeExists(inventoryData[2])) {
			statusFlag=-5;
			resultMsg.append(line + "," + batch + "," + productionDate + "," + expiryDate + "," + statusFlag.toString() + "\r\n");
			return;
		} else {
			procuct=existMaterialCodes.get(inventoryData[2]);
		}

		CartonCode cc = appCartonCode.getByCartonCode(cartonCode);
		
		if(cc != null) {
			statusFlag = 1;
			if(!cc.getMaterialCode().equals(inventoryData[2])){
				statusFlag = -2;
			}
			Idcode idcode = appIdcode.getIdcodeById(cartonCode);
			if(idcode != null && !StringUtil.isEmpty(idcode.getWarehouseid())) {
				if (!"11954".equals(idcode.getWarehouseid())) {
				statusFlag = -3;
				}
			}
			//tommy modified 不管如何，都要检查批次信息
			//if(statusFlag == 1) {
				if(idcode != null) {
					batch = idcode.getBatch();
				} 
				PrintJob printJob = appPrintJob.getPrintJobByID(cc.getPrintJobId());
				if(printJob != null) {
					productionDate = printJob.getProductionDate();
					expiryDate = printJob.getExpiryDate();
					if (null==expiryDate) expiryDate="";
					if (null==productionDate) productionDate="";
				}
			//}
//				resultMsg.append(line + "," + batch + "," + productionDate + "," + expiryDate + "," + statusFlag.toString() + "\r\n");
		} else {
			statusFlag = -1;
			if(!StringUtil.isEmpty(inventoryData[3])) {
				productionDate = inventoryData[3].substring(2, 8);
				if(isValidDate(productionDate, "yyMMdd")) {
					statusFlag = 3;
					batch=inventoryData[3].substring(10);
					productionDate="20"+inventoryData[3].substring(2, 8);
					
					SimpleDateFormat tempformat = new SimpleDateFormat("yyyyMMdd");
					 Date date = tempformat.parse(productionDate);
					 //expiryDate=tempformat.format(new Date(date.getTime() + procuct.getExpiryDays() * 24 * 60 * 60 * 1000));
					 
					 Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						calendar.add(Calendar.DATE, procuct.getExpiryDays());
						expiryDate = tempformat.format(calendar.getTime());
				} else {
					statusFlag = -5;
					productionDate = "";
				}
			}
//			resultMsg.append(line + "," + batch + "," + productionDate + "," + expiryDate + "," + statusFlag.toString() + "\r\n");
		}
		//检查产品批次生产日期是否相同
		if(batchAndProductionDate.containsKey(inventoryData[2]+batch)) {
			if(!productionDate.equals(batchAndProductionDate.get(inventoryData[2]+batch))) {
				resultMsg.append(line + "," + batch + "," + productionDate + "," + expiryDate + "," + statusFlag.toString() + " , 相同批次的物料号存在不同的生产日期\r\n");
			} else {
				resultMsg.append(line + "," + batch + "," + productionDate + "," + expiryDate + "," + statusFlag.toString() + "\r\n");
			}
		} else {
			batchAndProductionDate.put(inventoryData[2]+batch, productionDate);
			resultMsg.append(line + "," + batch + "," + productionDate + "," + expiryDate + "," + statusFlag.toString() + "\r\n");
		}
	}
	/**
	 * 验证字符串是否为有效的日期格式
	 * Create Time 2014-10-20 上午11:23:26
	 * @param date
	 * @author Ryan.xi
	 */
	public boolean isValidDate(String date, String partten) { 
	      boolean convertSuccess=true; 
	      if(StringUtil.isEmpty(date)) {
	    	  return false;
	      }
	      SimpleDateFormat format = new SimpleDateFormat(partten); 
	       try { 
	          format.setLenient(false); 
	          format.parse(date); 
	       } catch (Exception e) { 
	           convertSuccess=false; 
	       } 
	       return convertSuccess; 
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
}
