package com.winsafe.erp.services;

import java.util.Arrays;
import java.util.List; 
import java.util.Map;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.DeliveryType;
import com.winsafe.erp.metadata.FileType;
import com.winsafe.hbm.util.StringUtil;

public class CSSITransferService extends FileTransferService {
	
	private String TAG_NAME = "CSSITime";
	private AppCountryArea appCountryArea = new AppCountryArea();
	
	/**
	 * 初始化数据
	 */
	public void init() {
		title="saleInfoId|year|month|tdCode|tdName|tdProvinceCode|tdProvince|tdCityCode|tdCity|tdAreaCode|tdArea|saleDate|tdInsertDate|saleTypeName|productBarCode|tdStoreroomCode|tdStoreroomName|rgTypeName|rgCode|rgName|rgProvinceCode|rgProvince|rgCityCode|rgCity|rgAreaCode|rgArea|rgInsertDate|productCode|productName|productNameAll|productFormat|saleNumKL|insertDate";
		columnNames = Arrays.asList("saleinfoid","year","month","tdcode","tdname","tdprovincecode","tdprovince","tdcitycode","tdcity","tdareacode","tdarea","saledate","tdinsertdate","saletypename","productbarcode","tdstoreroomcode","tdstoreroomname","rgtypename","rgcode","rgname","rgprovincecode","rgprovince","rgcitycode","rgcity","rgareacode","rgarea","rginsertdate","productcode","productname","productnameall","productformat","salenumkl","insertdate");
	}

	public void createTransferFile() throws Exception {
		setSplit("|");
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime = getEndTime();
		
		List<Map<String,String>> dataList = appTl.getCSSIToTransfer(startTime, endTime);
		if(dataList!=null && dataList.size()>0) {
			Map<String, String> areaNameMap =  appCountryArea.getAreaNameMap();
			for(Map<String,String> map : dataList) {
				map.put("tdprovince", StringUtil.removeNull(areaNameMap.get(map.get("tdprovincecode"))));
				map.put("tdcity", StringUtil.removeNull(areaNameMap.get(map.get("tdcitycode"))));
				map.put("tdarea", StringUtil.removeNull(areaNameMap.get(map.get("tdareacode"))));
				
				map.put("rgprovince", StringUtil.removeNull(areaNameMap.get(map.get("rgprovincecode"))));
				map.put("rgcity", StringUtil.removeNull(areaNameMap.get(map.get("rgcitycode"))));
				map.put("rgarea", StringUtil.removeNull(areaNameMap.get(map.get("rgareacode"))));
				
				map.put("rgtypename", DealerType.parseByValue(Integer.valueOf(map.get("rgtypename"))).getName());
				if(!StringUtil.isEmpty(map.get("saletypename"))) {
					map.put("saletypename", DeliveryType.parseByValue(Integer.valueOf(map.get("saletypename"))).getName());
				}
			}
		}
		createFileAndAddLog(dataList, FileType.CSSI_FILE, endTime, TAG_NAME);
	}
}
