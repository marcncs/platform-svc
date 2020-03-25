package com.winsafe.erp.services;

import java.util.Arrays;
import java.util.List; 
import java.util.Map;

import com.winsafe.erp.metadata.FileType;
import com.winsafe.hbm.entity.HibernateUtil;

public class BillDetailTransferService extends FileTransferService {
	
	private String TAG_NAME = "billDetailTime";
	
	/**
	 * 初始化数据
	 */
	public void init() {
		title="交易单据编号,产品编号,单据数量（单位：件）,签收数量（单位：件）";
		columnNames = Arrays.asList("id","productid","quantity","receivequantity");
	}

	public void createTransferFile() throws Exception {
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime = getEndTime();
		List<Map<String,String>> dataList = appTl.getBillDetailsToTransfer(startTime, endTime);
		createFileAndAddLog(dataList, FileType.BILL_DETAIL, endTime, TAG_NAME);
	}
	
	public static void main(String[] args) throws Exception {
		new BillDetailTransferService().createTransferFile();
		HibernateUtil.commitTransaction();
	}
	
}
