package com.winsafe.erp.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream; 
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.owasp.esapi.ESAPI;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.erp.dao.AppFileTransfer;
import com.winsafe.erp.metadata.FileType;
import com.winsafe.erp.metadata.TransferFileStatus;
import com.winsafe.erp.pojo.TransferLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.util.FileUploadUtil;

public class FileTransferService {
	
//	private static Logger logger = Logger.getLogger(FileTransferService.class);
	//标题
	protected String title = null;
	//列名
	protected List<String> columnNames = null;
	
	private AppBaseResource appBr = new AppBaseResource();
	protected AppFileTransfer appTl = new AppFileTransfer();
	protected String split = ",";
	
	/**
	 * 获取结束时间,取当前时间前30分钟
	 * @return
	 */
	protected String getEndTime() {
		int delay = getDelayTime(); 
		Calendar curTime = Calendar.getInstance();
		curTime.add(Calendar.MINUTE, -delay);
		return DateUtil.formatDateTime(curTime.getTime());
	}

	private int getDelayTime() {
		try {
			return Integer.valueOf(SysConfig.getSysConfig().getProperty("transferDelayInMinutes"));
		} catch (Exception e) {
			return 30;
		}
	}

	/**
	 * 获取起始时间
	 * @param tagName
	 * @return
	 * @throws Exception
	 */
	protected String getStartTime(String tagName) throws Exception {
		BaseResource br = appBr.getBaseResourceValue(tagName, 1);
		if(br != null) {
			return br.getTagsubvalue();
		}
		return null;
	}
	
	/**
	 * 生成文件
	 * @param title
	 * @param columnNames
	 * @param dataList
	 * @param product
	 * @return
	 * @throws Exception 
	 */
	private File createTransferFile(String title, List<String> columnNames, List<Map<String, String>> dataList,
			FileType fileType, String fileName) throws Exception {
		String filePath = SysConfig.getSysConfig().getProperty("transferFilePath");
		if(StringUtil.isEmpty(fileName)) {
			fileName = fileType.getName()+"_"+DateUtil.getCurrentDateTimeString()+".txt";
		}
		File file = FileUploadUtil.createEmptyFile(filePath, fileName);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Constants.DEFAULT_FILE_ENCODE));
			bw.write(title);
			bw.newLine();
			for(Map<String, String> data : dataList) {
				StringBuffer line = new StringBuffer();
				for(String columnName : columnNames) {
					line.append(split).append(StringUtil.removeNull(data.get(columnName)));
				}
				bw.write(line.substring(1));
				bw.newLine();
			}
			bw.flush();
		} finally {
			if(bw != null) {
				bw.close();
			}
		}
		return file;
	}
	
	protected void updateEndTime(String tagName, String endTime) throws Exception {
		BaseResource br = appBr.getBaseResourceValue(tagName, 1);
		if(br != null) {
			br.setTagsubvalue(endTime);
			appBr.updBaseResource(br);
		} else {
			addEndTime(tagName, endTime);
		}
		
	}

	private void addEndTime(String tagName, String endTime) throws Exception {
		BaseResource br = new BaseResource();
		Long brid = Long.valueOf(MakeCode.getExcIDByRandomTableName("base_resource",0,""));
//		Long brid = -1l;
		br.setId(brid);
		br.setTagname(tagName);
		br.setTagsubkey(1);
		br.setTagsubvalue(endTime);
		appBr.addBaseResource(br);
	}

	private TransferLog addTransferLog(File file, FileType fileType) throws Exception {
		TransferLog tlog = new TransferLog();
		tlog.setFileName(file.getName());
		tlog.setFilePath(file.getPath());
		tlog.setFileType(fileType.getValue());
		tlog.setMakeDate(DateUtil.getCurrentDate());
		tlog.setStatus(TransferFileStatus.NOT_TRANSFERED.getValue());
		if(FileType.INVENTORY_DETAIL != fileType) {
			appTl.addTransferLog(tlog);
		} 
		return tlog;
	}

	protected void createFileAndAddLog(List<Map<String, String>> dataList, FileType fileType, String endTime, String tagName) throws Exception {
		if(dataList.size() > 0) {
			File file = createTransferFile(title, columnNames, dataList, fileType, null);
			addTransferLog(file, fileType);
		}
		updateEndTime(tagName, endTime);
	}
	
	protected void createFileAndAddLog(List<Map<String, String>> dataList, FileType fileType, String endTime, String tagName, String fileName) throws Exception {
		if(dataList.size() > 0) {
			File file = createTransferFile(title, columnNames, dataList, fileType, fileName);
			addTransferLog(file, fileType);
		}
		updateEndTime(tagName, endTime);
	}
	
	protected TransferLog createFileAndAddLog(List<Map<String, String>> dataList, FileType fileType) throws Exception {
		if(dataList.size() > 0) {
			File file = createTransferFile(title, columnNames, dataList, fileType, null);
			return addTransferLog(file, fileType);
		} else {
			return null;
		}
	}
	
	public static FileTransferService getTransferService(FileType fileType) {
		switch (fileType) {
		case PRODUCT:
			return new ProductTransferService();
		case BILL:
			return new BillTransferService();
		case BILL_DETAIL:
			return new BillDetailTransferService();
		case BILL_CARTONCODE:
			return new BillCartonCodeTransferService();
		case BILL_PRIMARYCODE:
			return new BillPrimaryCodeTransferService();
		case DEALER:
			return new DealerTransferService();
		case INVENTORY:
			return new InventoryTransferService();
		case INVENTORY_CARTONCODE:
			return new InventoryCartonCodeTransferService();
		case INVENTORY_PRIMARYCODE:
			return new InventoryPrimaryCodeTransferService();
		case INVENTORY_DETAIL:
			return new InventoryDetailTransferService();
		case WAREHOUSE:
			return new WarehouseTransferService();
		case CSSI_FILE: 
			return new CSSITransferService();
		default:
			break;
		}
		return new FileTransferService();
	}
	
	public void setSplit(String split) {
		this.split = split;
	}

	public void createTransferFile() throws Exception {};
}
