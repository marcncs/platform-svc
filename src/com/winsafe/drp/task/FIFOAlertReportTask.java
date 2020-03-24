package com.winsafe.drp.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jcifs.http.Handler;

import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppFIFOAlertReport;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.FIFOAlertReport;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.pojo.PrintJob;

public class FIFOAlertReportTask {

	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(FIFOAlertReportTask.class);

	private AppFIFOAlertReport appFIFOAlertReport = new AppFIFOAlertReport();
	private AppProductStockpileAll apsa = new AppProductStockpileAll();
	
	private AppFUnit appFUnit = new AppFUnit();
	
	private AppProduct appProduct = new AppProduct();
	private Map<String,Integer> proExpirydaysMap = new HashMap<String, Integer>();
	/**
	 * 初始化要处理的任务
	 */
	public void init() throws Exception {
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start processing sap upload file.");
					this.init();
					isRunning = true;
					logger.info(DateUtil.getCurrentDate()
							+ " 先进先出报表任务---开始---");
					execute();
				} catch (Exception e) {
					logger.error(e);
					logger.info(DateUtil.getCurrentDate() + " 先进先出报表任务发生异常"
							+ e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 先进先出报表任务---结束---");
				}
			}
		}

	}

	
	/**
	 * 
	 * @param fileName
	 * @throws Exception 
	 * @throws Exception
	 */
	/**
	 * @throws Exception
	 */
	public void execute() throws Exception {
		Date beginDate = appFIFOAlertReport.getMaxEndDate();
		Date endDate = DateUtil.getCurrentDate();
		List resultList = appFIFOAlertReport.getFIFOAlertReportByTime(beginDate, endDate);
		ArrayList<FIFOAlertReport> reports = new ArrayList<FIFOAlertReport>();
		Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
		for(int i=0;i<resultList.size();i++){
			Map map = (Map) resultList.get(i);
			FIFOAlertReport report = new FIFOAlertReport();
			MapUtil.mapToObject(map, report);
			report.setViewDate(DateUtil.getCurrentDate());
			report.setBeginDate(beginDate);
			report.setEndDate(endDate);
			report.setStockExpiryDate(getExpiryDate(report.getStockProductionDate(),report.getProductId()));
			report.setShipExpiryDate(getExpiryDate(report.getShipProductionDate(),report.getProductId()));
			report.setStockBatch(report.getStockBatch().split("_")[1]);
			report.setStockPile(apsa.getStockpileByWidBatchProductid(report.getWarehouseId(), report.getStockBatch(), report.getProductId(), Constants.WAREHOUSE_BIT_DEFAULT));
			reports.add(report);
			
			if(reports.size() >= Constants.DB_BULK_SIZE) {
				appFIFOAlertReport.addFIFOAlertReport(reports);
				reports.clear();
			}
		}
		if(reports.size() > 0) {
			appFIFOAlertReport.addFIFOAlertReport(reports);
		}
	}
	
	private String getExpiryDate(String productionDate, String productId) throws Exception {
		if(!StringUtil.isEmpty(productionDate)) {
			Date pDate =  DateUtil.formatStrDate(productionDate);
			Integer expiryDays = getProductExpirydays(productId);
			if(expiryDays != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(pDate); 
				calendar.add(Calendar.DATE, expiryDays);
				Date expiryDate = calendar.getTime();
				return DateUtil.formatDate(expiryDate, "yyyyMMdd");
			} 
		}
		return "";
	}
	
	private Integer getProductExpirydays(String productId) throws Exception {
		if(proExpirydaysMap.containsKey(productId)) {
			return proExpirydaysMap.get(productId);
		} else {
			Product product = appProduct.getProductByID(productId);
			if(product != null && product.getExpiryDays() != null) {
				proExpirydaysMap.put(productId, product.getExpiryDays());
				return product.getExpiryDays();
			}
		}
		return null;
	}

	private Boolean checkRate(String productId, Integer srcUnitId, Integer desUnitId, Map<String,FUnit>  rateMap){
		Boolean flag = true;
		try{
//			Double testQuantity = 1d;
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
//			changeUnit(productId ,srcUnitId,testQuantity,desUnitId,rateMap);
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
	private Double changeUnit(String productId,Integer srcUnit,Double srcQuantity,Integer desUnit,Map<String,FUnit> rateMap){
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
