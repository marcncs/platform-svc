package com.winsafe.erp.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map; 

import com.winsafe.erp.metadata.FileType;
import com.winsafe.hbm.entity.HibernateUtil;

public class BillPrimaryCodeTransferService extends FileTransferService {
	
	private String TAG_NAME = "billPrimaryCodeTime";
	
	/**
	 * 初始化数据
	 */
	public void init() {
		title="箱码,小包装码";
		columnNames = Arrays.asList("idcode","primary_code");
	}

	public void createTransferFile() throws Exception {
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime = getEndTime();
		List<Map<String,String>> dataList = appTl.getBillPrimaryCodeToTransfer(startTime, endTime);
		createFileAndAddLog(dataList, FileType.BILL_PRIMARYCODE, endTime, TAG_NAME);
	}
	
	public static void main(String[] args) throws Exception {
		new BillPrimaryCodeTransferService().createTransferFile();
		HibernateUtil.commitTransaction();
	}
	
}
