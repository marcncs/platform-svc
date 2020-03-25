package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import net.sf.json.JSONObject;

import com.winsafe.drp.action.scanner.DownloadOrganAction;
import com.winsafe.drp.action.scanner.DownloadProductAction;
import com.winsafe.drp.action.scanner.DownloadUnitAction;
import com.winsafe.drp.action.scanner.DownloadWarehouseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.sap.util.MD5BigFileUtil;

public class AppScannerDownload {

	public void save(Object scannerDownload) {
		EntityManager.save(scannerDownload);
	}
	
	public void update(Object scannerDownload) throws Exception {
		EntityManager.update(scannerDownload);
	}
	
	public ScannerDownload getByIMEI(String IMEI){
		String sql = " from ScannerDownload as sd where sd.imei='" + IMEI +"'";
		return (ScannerDownload) EntityManager.find(sql);
	}
	
	/**
	 * 获取下载信息的md5
	 */
	public String getBaseDataMd5(Users users,String scannerNo) throws Exception{
		List list = null;
		List<String> md5List = new ArrayList<String>();
		/**
		 * 下载机构信息
		 */
		DownloadOrganAction organUtil = new DownloadOrganAction();
		//管辖
		list = organUtil.getUserVisit(users, scannerNo);
		md5List.add(getMs5String(list));
//		//业务往来
		list = organUtil.getOrganVisitByUser(users);
		md5List.add(getMs5String(list));
//		//可转仓
		list = organUtil.getUserVisit(users, scannerNo);
		md5List.add(getMs5String(list));
//		//工厂
		list = organUtil.getPlant();
		md5List.add(getMs5String(list));
//		/**
//		 * 下载仓库信息
//		 */
		DownloadWarehouseAction warehouseUtil = new DownloadWarehouseAction();
//		//管辖
		list = warehouseUtil.getRuleUserWH(users, scannerNo);
		md5List.add(getMs5String(list));
//		//业务往来
		list = warehouseUtil.getWarehouseVisit(users);
		md5List.add(getMs5String(list));
		//可转仓
		list = warehouseUtil.getJsonList(warehouseUtil.getRuleUserWH(users));
		md5List.add(getMs5String(list));
//		//工厂
		list = warehouseUtil.getPlantWarehouse();
		md5List.add(getMs5String(list));
		// 下载产品信息
		DownloadProductAction productUtil = new DownloadProductAction();
		list = productUtil.getProductJsonList(productUtil.getProducts());
		md5List.add(getMs5String(list));
		// 下载单位信息
		DownloadUnitAction unitUtil = new DownloadUnitAction();
		list = unitUtil.getCountUnit();
		md5List.add(getMs5String(list));
		
		//对md5集合再次进行md5检验
		String resultMd5 = "";
		for(String s : md5List){
			resultMd5 += s;
		}
		return MD5BigFileUtil.md5(resultMd5);
	}
	
	private String getMs5String(List list){
		JSONObject json = ResponseUtil.getJsonMsg( Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list);
		return MD5BigFileUtil.md5(json.toString());
	}
}
