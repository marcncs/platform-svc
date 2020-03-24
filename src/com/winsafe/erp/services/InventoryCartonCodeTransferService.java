package com.winsafe.erp.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List; 
import java.util.Map;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.erp.metadata.FileType;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

public class InventoryCartonCodeTransferService extends FileTransferService {
	
	private AppOrgan appOrgan = new AppOrgan();
	private String TAG_NAME = "InventoryCartonCodeTime";
	private List<String> organIdList = new ArrayList<String>();
	
	/**
	 * 初始化数据
	 */
	public void init() {
		
		organIdList = appOrgan.getOrganIdListByOrganType(OrganType.Dealer.getValue(), DealerType.PD.getValue());
		title="机构编号,仓库编号,产品编号,箱码,批次,生产日期,过期日期";
		columnNames = Arrays.asList("organid","warehouseid","productid","idcode","batch","production_date","packaging_date");
	}

	public void createTransferFile() throws Exception {
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime =DateUtil.getCurrentDateTime();
		if(startTime == null) {
			for(String organId : organIdList) {
				String fileName = organId+"_"+FileType.INVENTORY_CARTONCODE.getName()+"_"+DateUtil.getCurrentDateTimeString()+".txt";
				List<Map<String,String>> dataList = appTl.getInventoryCartonCodeToTransfer("", "", organId);
				createFileAndAddLog(dataList, FileType.INVENTORY_CARTONCODE, endTime, TAG_NAME, fileName);
			}
		}
	}
	

	public static void main(String[] args) throws Exception {
		new InventoryCartonCodeTransferService().createTransferFile();
		HibernateUtil.commitTransaction();
	}
	
}
