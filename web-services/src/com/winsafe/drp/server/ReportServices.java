package com.winsafe.drp.server;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.SalesConsumeDetailReportForm;
import com.winsafe.drp.dao.SalesConsumeReportForm;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.JProperties;
import com.winsafe.sap.dao.AppPrintJob;

public class ReportServices {
	private static Logger logger = Logger.getLogger(ReportServices.class);
	protected AppFUnit appFUnit = new AppFUnit();
	protected AppBaseResource appBr = new AppBaseResource();
	protected AppCountryArea appCountryArea = new AppCountryArea();
	protected AppProduct appProduct = new AppProduct();
	protected AppProductStruct appProductStruct = new AppProductStruct();
	protected AppPrintJob appPrintJob = new AppPrintJob();
	protected AppStockAlterMove appStockAlterMove = new AppStockAlterMove();
	protected AppRegion appRegion = new AppRegion();
	protected DecimalFormat decimalFormat = new DecimalFormat("#,##0.000");
	
	protected Properties sysPro = null;
	
	public ReportServices() {
		super();
		init();
	}
	
	private void init() {
		try {
			sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		} catch (IOException e) {
			logger.error("加载配置文件system.properties出错", e);
		}
	}
	
	protected Boolean checkRate(SalesConsumeDetailReportForm scForm,Map<String,FUnit>  rateMap,Integer desUnit){
		Boolean flag = true;
		try{
			Double testQuantity = 1d;
			String srcKey = scForm.getProductId() + "_" + scForm.getUnitId();
			FUnit srcFunit = rateMap.get(srcKey);
			if(srcFunit == null){
				throw new Exception("funit [ productId(" + scForm.getProductId() + ") and unitId(" + scForm.getUnitId() + ") ] not exist");
			}
			String desKey = scForm.getProductId() + "_" + desUnit;
			FUnit desFunit = rateMap.get(desKey);
			if(desFunit == null){
				throw new Exception("funit [ productId(" + scForm.getProductId() + ") and unitId(" + desUnit + ") ] not exist");
			}
			Double desRate = desFunit.getXquantity();
			if(desRate == 0d){
				throw new Exception("funit [ productId(" + scForm.getProductId() + ") and unitId(" + desUnit + ") ] is zero");
			}
			changeUnit(scForm.getProductId(),scForm.getUnitId(),testQuantity,desUnit,rateMap);
		}catch (Exception e) {
			logger.error("", e);
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 检查单位是否可以正常转化
	 * Create Time 2015-1-30 下午02:27:02 
	 * @param scForm
	 * @param funitMap
	 * @return
	 * @author lipeng
	 */
	protected Boolean checkRate(SalesConsumeReportForm scForm,Map<String,FUnit>  rateMap,Integer desUnit){
		Boolean flag = true;
		try{
//			Double testQuantity = 1d;
			String srcKey = scForm.getProductId() + "_" + scForm.getUnitId();
			FUnit srcFunit = rateMap.get(srcKey);
			if(srcFunit == null){
				throw new Exception("funit [ productId(" + scForm.getProductId() + ") and unitId(" + scForm.getUnitId() + ") ] not exist");
			}
			String desKey = scForm.getProductId() + "_" + desUnit;
			FUnit desFunit = rateMap.get(desKey);
			if(desFunit == null){
				throw new Exception("funit [ productId(" + scForm.getProductId() + ") and unitId(" + desUnit + ") ] not exist");
			}
			Double desRate = desFunit.getXquantity();
			if(desRate == 0d){
				throw new Exception("funit [ productId(" + scForm.getProductId() + ") and unitId(" + desUnit + ") ] is zero");
			}
//			changeUnit(scForm.getProductId(),scForm.getUnitId(),testQuantity,desUnit,rateMap);
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
}
