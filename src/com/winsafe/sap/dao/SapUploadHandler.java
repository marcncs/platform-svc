package com.winsafe.sap.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.server.OrganService;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.pojo.UploadSAPLog;
import com.winsafe.sap.util.SapConfig;
/*******************************************************************************************  
 * SAP文件处理类
 * @author: ryan.xi	  
 * @date：2014-12-17  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-12-17   ryan.xi  
 * 1.1      2016-01-15   lyn.xie            添加分装厂文件处理      
 *******************************************************************************************  
 */ 
public abstract class SapUploadHandler {
	
	private static Logger logger = Logger.getLogger(SapUploadHandler.class);
	
	protected Map<String, Organ> organs = new HashMap<String, Organ>();
	protected Map<String, Product> existMaterialCodes = new HashMap<String, Product>();
	protected Set<String> notExistsMaterialCodes = new HashSet<String>();
	protected Set<String> organWithoutWarehouse = new HashSet<String>();
	protected Map<String, Warehouse> organWithWarehouse = new HashMap<String, Warehouse>();
	protected Set<String> materialWithoutFunit = new HashSet<String>();
	protected Map<String, FUnit> materialWithFunit = new HashMap<String, FUnit>();
	protected Map<String, Warehouse> existWarehouses = new HashMap<String, Warehouse>();
	protected Set<String> notExistsWarehouses = new HashSet<String>();
	
	protected OrganService organService = new OrganService();
	protected AppWarehouse appWarehouse = new AppWarehouse();
	protected AppFUnit appFUnit = new AppFUnit();
	protected AppIdcode appIdcode = new AppIdcode();
	protected AppProduct appProduct = new AppProduct();
	protected AppBaseResource appBaseResource = new AppBaseResource();
	
	//保存报错信息
	protected StringBuffer errMsg = new StringBuffer();
	//错误记录数和总记录数
	protected Integer errCount = 0, totalCount = 0;
	//解析文件时是否出错
	protected boolean hasError = false;
	//文件日志
	protected UploadSAPLog sapUploadLog;
	//文件类型
	protected SapFileType fileType;
	//文件类型是否正确
	protected boolean isFileTypeCorrect = true;
	//默认包装单位
	protected Integer defaultUnitId;
	//默认包装名称
	protected String defaultUnitName;
	

	public SapUploadHandler(SapFileType fileType) throws Exception {
		this.fileType = fileType;
		List<Organ> organlist = organService.getOrganIdAndNames();
		for(Organ organ : organlist) {
			organs.put(organ.getOecode(), organ);
		}
		defaultUnitId = Integer.parseInt((String)SapConfig.getSapConfig().get("unitId"));
		defaultUnitName = appBaseResource.getBaseResourceValue("CountUnit", defaultUnitId).getTagsubvalue();
	}

	public StringBuffer getErrMsg() {
		return errMsg;
	}

	public Integer getErrCount() {
		return errCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}
	
	public void setUploadSAPLog(UploadSAPLog sapUploadLog) {
		this.sapUploadLog = sapUploadLog;
	}
	/**
	 * 验证SAP机构代码是否在系统中存在
	 * Create Time 2014-10-20 上午11:23:26
	 * @param materialNo
	 * @author Ryan.xi
	 */
	public boolean isOrganIdExists(String oeCode) {
		return organs.containsKey(oeCode);
	} 
	/**
	 * 验证SAP物料码是否在系统中存在
	 * Create Time 2014-10-20 上午11:23:26
	 * @param materialCode
	 * @author Ryan.xi
	 */
	public boolean isMaterialCodeExists(String materialCode) {
		if(existMaterialCodes.containsKey(materialCode)) {
			return true;
		} else if(notExistsMaterialCodes.contains(materialCode)){
			return false;
		} else {
			try {
				Product procuct = appProduct.getByMCode(materialCode);
				existMaterialCodes.put(materialCode, procuct);
				return true;
			} catch (NotExistException e) {
				notExistsMaterialCodes.add(materialCode);
				return false;
			}
		}
	}
	
	/**
	 * 验证物料是否设置了包装比例关系
	 * Create Time 2014-10-20 上午11:23:26
	 * @param materialCode
	 * @author Ryan.xi
	 */
	public boolean isMaterialFUnitExists(String productId) {
		if(materialWithFunit.containsKey(productId)) {
			return true;
		} else if(materialWithoutFunit.contains(productId)){
			return false;
		} else {
			FUnit fUnit = appFUnit.getFUnit(productId, defaultUnitId);
			if(fUnit == null) {
				materialWithoutFunit.add(productId);
				return false;
			} else {
				materialWithFunit.put(productId, fUnit);
				return true;
			}
		}
	}
	/**
	 * 验证机构是否存在仓库
	 * Create Time 2014-10-20 上午11:23:26
	 * @param organId
	 * @author Ryan.xi
	 */
	public boolean isOrganWarehousExists(String organId) {
		if(organWithWarehouse.containsKey(organId)) {
			return true;
		} else if(organWithoutWarehouse.contains(organId)){
			return false;
		} else {
			try {
				Warehouse warehouse = appWarehouse.getAvaiableWarehouseByOID(organId);
				organWithWarehouse.put(organId, warehouse);
				return true;
			} catch (NotExistException e) {
				logger.error(e);
				organWithoutWarehouse.add(organId);
				return false;
			}
		}
	}
	
	/**
	 * 验证机构是否存在仓库
	 * Create Time 2014-10-20 上午11:23:26
	 * @param organId
	 * @author Ryan.xi
	 */
	public boolean isWarehousExists(String ncode) {
		if(existWarehouses.containsKey(ncode)) {
			return true;
		} else if(notExistsWarehouses.contains(ncode)){
			return false;
		} else {
			try {
				Warehouse warehouse = appWarehouse.getWarehouseByNCode(ncode);
				existWarehouses.put(ncode, warehouse);
				return true;
			} catch (NotExistException e) {
				logger.error(e);
				notExistsWarehouses.add(ncode);
				return false;
			}
		}
	}
	/**
	 * 验证字符串是否为有效的日期格式
	 * Create Time 2014-10-20 上午11:23:26
	 * @param date
	 * @author Ryan.xi
	 */
	public boolean isValidDate(String date, String partten) { 
	      boolean convertSuccess=true; 
	      if(StringUtil.isEmpty(date)) {
	    	  return false;
	      }
	      SimpleDateFormat format = new SimpleDateFormat(partten); 
	       try { 
	          format.setLenient(false); 
	          format.parse(date); 
	       } catch (Exception e) { 
	           convertSuccess=false; 
	       } 
	       return convertSuccess; 
	}
	
	/**
	 * 初始化Digester框架类并开始解析文件
	 * Create Time 2014-10-20 上午11:23:26
	 * @param file 要解析的文件
	 * @throws IOException, SAXException
	 * @author Ryan.xi
	 */
	public boolean parse(File file) throws Exception {
		Digester digester = new Digester();
		digester.push(this);
		addRule(digester);
		addConvertor();
		if(fileType == SapFileType.DELIVERY) {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), SapConfig.getSapConfig().getProperty("sapFileCharset"));
			digester.parse(isr);
		} else if(fileType == SapFileType.SAPCODE) {
			return false;
		}else {
			digester.parse(file);
		}
		checkResult();
		return hasError;
	}
	/**
	 * 检查解析的结果
	 * Create Time 2014-10-20 上午11:23:26
	 * @param file 要解析的文件
	 * @throws IOException, SAXException
	 * @author Ryan.xi
	 */
	public abstract void checkResult();
	
	/**
	 * 添加文件解析规则
	 * Create Time 2014-10-20 上午11:23:26 
	 * @throws Exception
	 */
	public abstract void addRule(Digester digester);
	
	/**
	 * 添加转换器
	 * Create Time 2014-10-20 上午11:23:26 
	 * @throws Exception
	 */
	public void addConvertor() {
		String pattern = "yyyyMMdd";
        Locale locale = Locale.getDefault();
        DateLocaleConverter converter = new DateLocaleConverter(locale, pattern);
        converter.setLenient(true);
        ConvertUtils.register(converter, java.util.Date.class);
	}
	
	/**
	 * 将解析后的数据添加到系统中
	 * Create Time 2014-10-20 上午11:23:26 
	 * @throws Exception
	 */
	public abstract void handle() throws Exception;

	public static SapUploadHandler getHandler(SapFileType fileType) throws Exception {
		switch (fileType) {
		case ORDER_SAP:
			return new AutoOrderUploadHandler(fileType);
		case ORDER_PlANT:
			return new ManualPlantUploadHandler(fileType);
		case ORDER_TOLLER:
			return new ManualTollerUploadHandler(fileType);
		case DELIVERY:
			return new SapDeliveryUploadHandler(fileType);	
		case INVOICE:
			return new SapInvoiceUploadHandler(fileType);

		default:
			break;
		}
		return null;
	}
}
