package com.winsafe.erp.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map; 

import com.winsafe.drp.metadata.DeliveryType;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.erp.metadata.FileType;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.StringUtil;

public class BillTransferService extends FileTransferService {
	
	private String TAG_NAME = "billTime";
	
	/**
	 * 初始化数据
	 */
	public void init() {
		title="交易单据编号,发货单类型,出货方机构编号,出货方机构名称,出货方仓库编号,出货方仓库名称,收货方机构编号,收货方机构名称,"
				+ "收货方仓库编号,收货方仓库名称,单据创建时间,单据复核时间,单据确认时间（指出货方确认）,单据签收时间（指收货方确认）"; 
		columnNames = Arrays.asList("id","bsort","outoid","outoname","outwid","outwname","inoid","inoname","inwid","inwname","makedate","auditdate","shipmentdate","receivedate");
	}

	public void createTransferFile() throws Exception {
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime = getEndTime();
		List<Map<String,String>> dataList = appTl.getBillsToTransfer(startTime, endTime);
		for(Map<String,String> data : dataList) {
			if(!StringUtil.isEmpty(data.get("bsort"))) {
				DeliveryType type = DeliveryType.parseByValue(Integer.valueOf(data.get("bsort")));
				data.put("bsort", type.getName());
			}
			data.put("outoname", ESAPIUtil.decodeForHTML(data.get("outoname")));
			data.put("inoname", ESAPIUtil.decodeForHTML(data.get("inoname")));
		}
		createFileAndAddLog(dataList, FileType.BILL, endTime, TAG_NAME);
	}
	
	public static void main(String[] args) throws Exception {
		new BillTransferService().createTransferFile();
		HibernateUtil.commitTransaction();
	}
	
}
