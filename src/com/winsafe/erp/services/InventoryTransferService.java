package com.winsafe.erp.services;

import java.util.Arrays;
import java.util.List; 
import java.util.Map;


import com.winsafe.erp.metadata.FileType;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

public class InventoryTransferService extends FileTransferService {
	
	private String TAG_NAME = "InventoryTime";
	
	/**
	 * 初始化数据
	 */
	public void init() {
		title="机构编号,仓库编号,产品编号,产品件数";
		columnNames = Arrays.asList("organid","warehouseid","productid","stockpile");
	}

	public void createTransferFile() throws Exception {
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime =DateUtil.getCurrentDateTime();
		if(startTime == null) {
			List<Map<String,String>> dataList = appTl.getInventoryToTransfer(startTime, endTime);
			createFileAndAddLog(dataList, FileType.INVENTORY, endTime, TAG_NAME);
			//初始化单据的结束时间
			initEndTime();
		}
	}
	
	private void initEndTime() throws Exception {
		String endDate = DateUtil.getCurrentDateTime();
		updateEndTime("billCartonCodeTime",endDate);
		updateEndTime("billDetailTime",endDate);
		updateEndTime("billPrimaryCodeTime",endDate);
		updateEndTime("billTime",endDate);
	}
	
	public static void main(String[] args) throws Exception {
		new InventoryTransferService().createTransferFile();
		HibernateUtil.commitTransaction();
	}
	
}
