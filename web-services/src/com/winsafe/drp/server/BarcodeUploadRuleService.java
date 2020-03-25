package com.winsafe.drp.server;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.UploadBarcodeBean;
import com.winsafe.drp.dao.UploadIdcode;
import com.winsafe.drp.dao.UploadIdcodeBean;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.util.DateUtil;

public class BarcodeUploadRuleService {
	private Logger logger = Logger.getLogger(BarcodeUploadRuleService.class);
	//  单据号
	private final static String BILL_NO = "billNo";
	//	发货仓库编号
	private final static String FROM_WH_ID = "fromWHID";
	//	生产日期
	private final static String PRODUCT_DATE = "productDate";
	//	批次
	private final static String BATCH_NUMBER = "batchNumber";
	//	产品编号
	private final static String LCODE = "lCode";
	//	扫描标志位
	private final static String SCANNER_FLAG = "scannerFlag";
	//	条码
	private final static String BARCODE = "barCode";
	//	扫描类型
	private final static String SCANNER_TYPE = "scannerType";
	//	扫描日期
	private final static String SCANNER_DATE = "scannerDate";
	//	数量
	private final static String COUNT = "count";
	//	采集器编号
	private final static String SCANNER_NAME = "ScannerName";
	//	入库仓库编号
	private final static String TO_WH_ID = "toWHID";
	//	用户名
	private final static String USER_ID = "userID";
	
	private final static String DATA_FORMAT = "yyyyMMddhhmmss";
	
	// 获取有单上传对像
	public UploadIdcode getOMUploadIdcode(JSONObject jsonObject) throws Exception{
		
		UploadIdcode uploadIdcode = new UploadIdcode();
		
		uploadIdcode.setBillNo(getBillNo(jsonObject));
		uploadIdcode.setInWarehouseId(getInWarehouseId(jsonObject));
		//uploadIdcode.setProDate(getProductDate(jsonObject));
		//uploadIdcode.setBatch(getBatch(jsonObject));
		uploadIdcode.setProductId(getStringProperty(jsonObject,LCODE,false));
		uploadIdcode.setIdcode(getIdcode(jsonObject));
		uploadIdcode.setScanFlag(getScanSign(jsonObject));
		uploadIdcode.setScanType(getScanType(jsonObject));
		uploadIdcode.setScanDate(getScanDate(jsonObject));
		uploadIdcode.setQuantity(getProductCount(jsonObject));
		uploadIdcode.setScannerNo(getScannerNo(jsonObject));
		uploadIdcode.setOutWarehouseId(getOutWarehouseId(jsonObject));
		uploadIdcode.setUsername(getUserName(jsonObject));
		
		return uploadIdcode;
	}
	
	// 获取条码上传对象
	public UploadBarcodeBean getNoBillSamUploadIdcode(JSONObject jsonObject) throws Exception{
		
		UploadBarcodeBean bean = new UploadBarcodeBean();
		// 取得扫描类型
		bean.setScanType(getScanType(jsonObject));
		// 取得条形码编号
		bean.setIdcode(getIdcode(jsonObject));
		// 调入仓库编号
		bean.setInwarehouseid(getInWarehouseId(jsonObject));
		 //产品物流码前缀
		bean.setLcode(getProductId(jsonObject));
		//获取扫描标识位
		bean.setScanFlag(getScanSign(jsonObject));
		// 获取用户名
		bean.setUsername(getUserName(jsonObject));
		//得到用户名
		bean.setQuantity(getProductCount(jsonObject).intValue());
		// 调出仓库编号
		bean.setOutwarehouseid(getOutWarehouseId(jsonObject));
		//获取单据号
		bean.setBillNo(getBillNo(jsonObject));
		//获取批次
		bean.setBatch(getBatch(jsonObject));
		
		return bean;
	}
	
	//获取单号
	public String getBillNo(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,BILL_NO,true);
	}
	
	//获取仓库编号
	public String getOutWarehouseId(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,FROM_WH_ID,true);
	}
	
	//获取生产日期
	public String getProductDate(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,PRODUCT_DATE,true);
	}
	
	//获取批次
	public String getBatch(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,BATCH_NUMBER,true);
	}
	
	//获取产品编号
	public String getProductId(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,LCODE,true);
	}
	
	//获取扫描标识位
	public String getScanSign(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,SCANNER_FLAG,true);
	}
	
	//从采集条码中解析出条形码
	public String getIdcode(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,BARCODE,true);
	}
	
	//获取扫描类型
	public String getScanType(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,SCANNER_TYPE,true);
	}

	//获取扫描日期
	public Date getScanDate(JSONObject jsonObject) throws IdcodeException
	{
		return getDateProperty(jsonObject,SCANNER_DATE,true);
	}

	//获取产品数量
	public Double getProductCount(JSONObject jsonObject) throws IdcodeException
	{
		return getDoublDateProperty(jsonObject,COUNT,true);
	}

	//获取采集器编号
	public String getScannerNo(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,SCANNER_NAME,true);
	}
	
	//获取入库仓库编号
	public String getInWarehouseId(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,TO_WH_ID,true);
	}
	
	//获取用户名
	public String getUserName(JSONObject jsonObject) throws IdcodeException
	{
		return getStringProperty(jsonObject,USER_ID,true);
	}
	
	
	public String getStringProperty(JSONObject jsonObject,String property,boolean validate) throws IdcodeException
	{
		String result = "";
		try {
			result = jsonObject.getString(property);
		} catch (Exception e) {
			if(validate){
				throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00011, property));
			}
		}
		return result;
	}
	
	public Date getDateProperty(JSONObject jsonObject,String property,boolean validate) throws IdcodeException
	{
		Date date = null;
		try {
			String result = jsonObject.getString(property);
			date = DateUtil.StringToDate(result, DATA_FORMAT);
		} catch (Exception e) {
			if(validate){
				throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00011, property));
			}
		}
		return date;
	}
	
	public Double getDoublDateProperty(JSONObject jsonObject,String property,boolean validate) throws IdcodeException
	{
		Double value = null;
		try {
			String result = jsonObject.getString(property);
			value = Double.valueOf(result);
		} catch (Exception e) {
			if(validate){
				throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00011, property));
			}
		}
		return value;
	}
}