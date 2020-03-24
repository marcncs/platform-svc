package com.winsafe.drp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.erp.dao.AppBillImportConfig;
import com.winsafe.erp.dao.AppProductConfig;
import com.winsafe.erp.pojo.BillImportConfig;
import com.winsafe.erp.pojo.ProductConfig;

public abstract class BillImportService {
	
	private static final Logger logger = Logger.getLogger(BillImportService.class);
	
	protected AppBillImportConfig abic = new AppBillImportConfig();
	protected AppProductConfig apic = new AppProductConfig();
	protected List<BillImportConfig> billConfigs = null;
	//key-文件中的列名, value-配置信息
	protected Map<String, BillImportConfig> colAndConfig = new HashMap<String, BillImportConfig>();
	//key-文件中产品编码, value-系统中物料号
	protected Map<String, String> productCodes = new HashMap<String, String>();
	//文件中单据数据的起始行号
	protected Integer dataRowNo = 1;
	//文件中表头所在行号
	protected Integer titleRowNo = 0;
	protected Organ organ;
	protected Warehouse warehouse;
	
	//模板编号
	protected String templateNo;
	protected UsersBean users;
	
	public String dealFile(String filePath, Integer userId) throws Exception{
		initDate();
		if(colAndConfig.size() == 0) {
			return "未找到该机构的ERP导入配置信息";
		}
//		Workbook wb = ReadExcelUtil.getWorkbook(filePath);
//		Sheet sheet = wb.getSheetAt(0);
		List<Map<String, String>> dataList = ReadExcelUtil.getDataRowsWithRequiredColumns(filePath, colAndConfig, titleRowNo, dataRowNo);
		String result = dealData(dataList, userId);
		dataList.clear();
		return result;
	}
	
	protected void initDate() throws Exception{

		//获取单据导入配置信息
		billConfigs = abic.getAllBillImportConfig(organ.getId(), templateNo);
		for(BillImportConfig billConfig : billConfigs) {
//			从数据库中获得Excel对应的列
			if(billConfig.getFieldName().equals("dataRowNo")) {
				dataRowNo = Integer.parseInt(billConfig.getDefaultValue()) - 1;
			} else if(billConfig.getFieldName().equals("titleRowNo")) {
				titleRowNo = Integer.parseInt(billConfig.getDefaultValue()) - 1;
			} else {
				colAndConfig.put(billConfig.getColumnName(), billConfig);
			}
		}
		//获取产品代码配置信息
		List<ProductConfig> productConfigs = apic.getProductConfigByOrganId(organ.getId());
		for(ProductConfig productConfig : productConfigs) {
			productCodes.put(productConfig.getErpProductId(), productConfig.getmCode());
		}
	}
	
	
	protected abstract List<BillImportConfig> getReuiredColumns() throws Exception;
	
	protected abstract String dealData(List<Map<String, String>> dataList, Integer userId) throws Exception;
	
	/**
	 * 检查单位是否可以正常转化
	 * Create Time 2015-1-30 下午02:27:02 
	 * @param scForm
	 * @param funitMap
	 * @return
	 * @author lipeng
	 */
	protected Boolean checkRate(String productId, Integer srcUnitId, Integer desUnitId, Map<String,FUnit>  rateMap){
		Boolean flag = true;
		try{
			String srcKey = productId + "_" + srcUnitId;
			FUnit srcFunit = rateMap.get(srcKey);
			if(srcFunit == null){
				throw new Exception("funit [ productId(" + productId + ") and unitId(" + srcUnitId + ") ] not exist");
			}
			String desKey = productId + "_" + desUnitId;
			FUnit desFunit = rateMap.get(desKey);
			if(desFunit == null){
				throw new Exception("funit [ productId(" + productId + ") and unitId(" + desUnitId + ") ] not exist");
			}
			Double desRate = desFunit.getXquantity();
			if(desRate == 0d){
				throw new Exception("funit [ productId(" + productId + ") and unitId(" + desUnitId + ") ] is zero");
			}
		}catch (Exception e) {
			logger.error("", e);
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据单位转化数量
	 * Create Time 2015-1-30 下午02:07:55 
	 * @param productId
	 * @param srcUnit
	 * @param srcQuantity
	 * @param desUnit
	 * @param rateMap
	 * @return
	 * @author lipeng
	 */
	protected Double changeUnit(String productId,Integer srcUnit,Double srcQuantity,Integer desUnit,Map<String,FUnit> rateMap){
		// 先换算出最小单位数量
		String srcKey = productId + "_" + srcUnit;
		Double srcRate = rateMap.get(srcKey).getXquantity();
		Double minUnitQuantity = ArithDouble.mul(srcQuantity, srcRate);
		// 再换算成目标单位数量
		String desKey = productId + "_" + desUnit;
		Double desRate = rateMap.get(desKey).getXquantity();
		Double desQuantity = ArithDouble.div(minUnitQuantity, desRate);
		return desQuantity;
	}
}
