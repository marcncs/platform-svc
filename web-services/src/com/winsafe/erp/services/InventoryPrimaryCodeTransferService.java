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

public class InventoryPrimaryCodeTransferService extends FileTransferService {
	
	private AppOrgan appOrgan = new AppOrgan();
	private String TAG_NAME = "inventoryPrimaryCodeTime";
	private List<String> organIdList = new ArrayList<String>();
	/**
	 * 初始化数据
	 */
	public void init() {
		organIdList = appOrgan.getOrganIdListByOrganType(OrganType.Dealer.getValue(), DealerType.PD.getValue());
		title="箱码,小包装码";
		columnNames = Arrays.asList("idcode","primary_code");
	}

	public void createTransferFile() throws Exception {
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime = DateUtil.getCurrentDateTime();
		if(startTime == null) {
			for(String organId : organIdList) {
				String fileName = organId+"_"+FileType.INVENTORY_PRIMARYCODE.getName()+"_"+DateUtil.getCurrentDateTimeString()+".txt";
				List<Map<String,String>> dataList = appTl.getInventoryPrimaryCodeToTransfer(organId);
				createFileAndAddLog(dataList, FileType.INVENTORY_PRIMARYCODE, endTime, TAG_NAME, fileName);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		new InventoryPrimaryCodeTransferService().createTransferFile();
		HibernateUtil.commitTransaction();
	}
	
}
