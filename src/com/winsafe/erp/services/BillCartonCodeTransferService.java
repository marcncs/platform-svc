package com.winsafe.erp.services;

import java.util.Arrays;
import java.util.List; 
import java.util.Map;

import com.winsafe.erp.metadata.FileType;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.StringUtil;

public class BillCartonCodeTransferService extends FileTransferService {
	
	private String TAG_NAME = "billCartonCodeTime";
	
	/**
	 * 初始化数据
	 */
	public void init() {
		title="交易单据编号,产品编号,箱码,批次,生产日期,过期日期";
		columnNames = Arrays.asList("billno","productid","idcode","batch","production_date","packaging_date");
	}

	public void createTransferFile() throws Exception {
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime = getEndTime();
		List<Map<String,String>> dataList = appTl.getBillCartonCodeToTransfer(startTime, endTime);
		for(Map<String,String> data : dataList) {
			if(StringUtil.isEmpty(data.get("packaging_date"))) {
				data.put("packaging_date", data.get("production_date"));
			}
		}
		//查询没有经过生产流程，直接出库的条码
		List<Map<String,String>> dataWithoutCartonCode = appTl.getBillCartonCodeToTransferWithoutCartonCode(startTime, endTime);
		if(dataWithoutCartonCode!=null&&dataWithoutCartonCode.size()>0) {
			for(Map<String,String> data : dataWithoutCartonCode) {
				if(StringUtil.isEmpty(data.get("packaging_date"))) {
					data.put("packaging_date", data.get("production_date"));
				}
			}
			dataList.addAll(dataWithoutCartonCode);
		}
		createFileAndAddLog(dataList, FileType.BILL_CARTONCODE, endTime, TAG_NAME);
	}
	
	public static void main(String[] args) throws Exception {
		new BillCartonCodeTransferService().createTransferFile();
		HibernateUtil.commitTransaction();
	}
	
}
