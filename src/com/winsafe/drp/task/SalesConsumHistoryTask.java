package com.winsafe.drp.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppSalesConsumHistory;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.ProductRollingPrice;
import com.winsafe.drp.report.pojo.SalesConsumHistory;
import com.winsafe.drp.server.SalesConsumeInventoryReportService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

public class SalesConsumHistoryTask {

	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(SalesConsumHistoryTask.class);

	private AppSalesConsumHistory appSalesConsumHistory = new AppSalesConsumHistory();
	private AppBaseResource appBaseResource = new AppBaseResource();
	private SalesConsumeInventoryReportService scirs = new SalesConsumeInventoryReportService();
	private Properties sysPro = null;
	private Integer delayMinutes = -30;
	/**
	 * 初始化要处理的任务
	 */
	public void init() throws Exception {
		try {
			sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			delayMinutes = -Integer.parseInt(sysPro.getProperty("delayMinutes"));
		} catch (Exception e) {
			logger.error("加载配置文件system.properties出错", e);
		}
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					this.init();
					isRunning = true;
					logger.info(DateUtil.getCurrentDate()
							+ " 销售消耗历史数据任务---开始---");
					execute();
					HibernateUtil.commitTransaction();
				} catch (Exception e) {
					HibernateUtil.rollbackTransaction();
					logger.error("",e);
					logger.info(DateUtil.getCurrentDate() + " 销售消耗历史数据任务发生异常"
							+ e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 销售消耗历史数据任务---结束---");
				}
			}
		}
	}
	
	/**
	 * @throws Exception
	 */
	public void execute() throws Exception {
		BaseResource salesConsumDate = appBaseResource.getBaseResourceValueWithLock("SalesConsumDate", 1);
		if(salesConsumDate == null) {
			initHistoryData();
		} else {
			addOrUpdHistoryData(salesConsumDate);
			addNotExistsHistoryData(salesConsumDate);
		}
	}
	
	private void addNotExistsHistoryData(BaseResource salesConsumDate) throws Exception {
		String year = salesConsumDate.getTagsubvalue().split("-")[0];
		String month = salesConsumDate.getTagsubvalue().split("-")[1];
		List<Map<String,String>> savh = appSalesConsumHistory.getNotExistsHistoryDataByDate(Integer.parseInt(year), Integer.parseInt(month));
		for(Map<String,String> map : savh) {
			SalesConsumHistory sch = new SalesConsumHistory();
			MapUtil.mapToObject(map, sch);
			sch.setMonth(Integer.parseInt(month));
			sch.setYear(Integer.parseInt(year));
			sch.setConsumValue(0d);
			sch.setConsumVolume(0d);
			sch.setHasInvoice(0);
			sch.setMakeDate(DateUtil.getCurrentDate());
			sch.setMonthBeginInventory(0d);
			sch.setMonthEndInventory(0d);
			sch.setOtherConsumVolume(0d);
			sch.setSalesValue(0d);
			sch.setSalesVolume(0d);
			//设置年月
			String monthAndYear = year+month;
			//设置价格
			sch.setPrice(getPrice(monthAndYear, sch));
			appSalesConsumHistory.addSalesConsumHistory(sch);
		}
		
	}

	private void addOrUpdHistoryData(BaseResource salesConsumDate) throws Exception {
		Calendar current = Calendar.getInstance();
		current.add(Calendar.MINUTE, delayMinutes);
		String endDate = DateUtil.formatDateTime(current.getTime());
		List<Map<String, String>> salesDatas = scirs.getAllSalesConsumHistoryDataByDate(salesConsumDate.getTagsubvalue(), endDate);
		
		List<Map<String, String>> newSalesDatas = new ArrayList<Map<String,String>>();
		
		for(Map<String, String> salesData : salesDatas) {
			String monthAndYear = salesData.get("mydate");
			String month = monthAndYear.substring(4, monthAndYear.length());
			String year = monthAndYear.substring(0, 4);
			String organId = salesData.get("organid");
			String productId = salesData.get("productid");
			String warehouseId = salesData.get("warehouseid");
//			String hasInvoice = salesData.get("hasinvoice");
			SalesConsumHistory sch = appSalesConsumHistory.getSalesConsumHistory(year,month,organId,warehouseId,productId);
			if(sch != null) {
				sch.setSalesVolume(ArithDouble.add(sch.getSalesVolume(), Double.parseDouble(salesData.get("salesvolume"))));
				sch.setConsumVolume(ArithDouble.add(sch.getConsumVolume(), Double.parseDouble(salesData.get("consumvolume"))));
				sch.setOtherConsumVolume(ArithDouble.add(sch.getOtherConsumVolume(), Double.parseDouble(salesData.get("otherconsumvolume"))));
				if(!StringUtil.isEmpty(salesData.get("salesvalue")) && !"0".equals(salesData.get("salesvalue"))) {
					sch.setSalesValue(ArithDouble.add(sch.getSalesValue(), Double.parseDouble(salesData.get("salesvalue"))));
					sch.setPrice(getPrice(monthAndYear, sch));
				}
				appSalesConsumHistory.updSalesConsumHistory(sch);
			} else {
//				newSalesDatas.add(salesData);
				addSalesConsumHistory(salesData);
			}	
			//查看记录之后有没有统计好的价格
			List<SalesConsumHistory> schs = appSalesConsumHistory.getNewerSalesConsumHistory(year,month,organId,warehouseId,productId);
			if(schs != null && schs.size() > 0) {
				for(SalesConsumHistory newerSch : schs) {
					String yearMonth = newerSch.getYear() + (newerSch.getMonth() < 10 ? "0" : "") + newerSch.getMonth();
					newerSch.setPrice(getPrice(yearMonth, newerSch));
					appSalesConsumHistory.updSalesConsumHistory(newerSch);
				}
			}
		}
//		if(newSalesDatas.size() > 0) {
//			addSalesConsumHistory(newSalesDatas);
//		}
		salesConsumDate.setTagsubvalue(endDate);
		appBaseResource.updBaseResource(salesConsumDate);
	}
	
	private void addSalesConsumHistory(Map<String, String> salesData) throws Exception {
		SalesConsumHistory sch = new SalesConsumHistory();
		MapUtil.mapToObject(salesData, sch);
		sch.setMakeDate(DateUtil.getCurrentDate());
		//设置年月
		String monthAndYear = salesData.get("mydate");
		sch.setMonth(Integer.parseInt(monthAndYear.substring(4, monthAndYear.length())));
		sch.setYear(Integer.parseInt(monthAndYear.substring(0, 4)));
		//设置价格
		sch.setPrice(getPrice(monthAndYear, sch));
		appSalesConsumHistory.addSalesConsumHistory(sch);
		
	}

	private Double getPrice(String yearMonth, SalesConsumHistory sch) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.StringToDate(yearMonth, "yyyyMM"));
		return scirs.getPrice(calendar, sch.getProductId(), sch.getOrganId());
	}

	private void initHistoryData() throws Exception {
		Calendar current = Calendar.getInstance();
		current.add(Calendar.MINUTE, delayMinutes);
		String dateTime = DateUtil.formatDateTime(current.getTime());
		List<Map<String, String>> salesDatas = scirs.getInitalSalesConsumHistoryData(dateTime);
		addSalesConsumHistory(salesDatas);
		addBaseResource(dateTime);
	}

	
	private void addSalesConsumHistory(List<Map<String, String>> salesDatas) throws Exception {
		ProductRollingPrice prp = null;
		for(Map<String, String> salesData : salesDatas) {
			if(prp == null) {
				prp = scirs.getPrductRollingPrices(salesData.get("mydate"), salesData.get("productid"), salesData.get("organid"), salesData.get("warehouseid"));
				addSalesConsumHistory(salesData, prp);
			} else {
				if(salesData.get("productid").equals(prp.getProductId()) && salesData.get("organid").equals(prp.getOrganId())) {
					addSalesConsumHistory(salesData, prp);
				} else {
					addSalesConsumHistory(prp);
					prp = null;
					prp = scirs.getPrductRollingPrices(salesData.get("mydate"), salesData.get("productid"), salesData.get("organid"), salesData.get("warehouseid"));
					addSalesConsumHistory(salesData, prp);
				}
			}
		}
		if(prp != null && prp.getPriceMap().size() > 0) {
			addSalesConsumHistory(prp);
		}
	}

	private void addSalesConsumHistory(ProductRollingPrice prp) {
		for(String yearMonth : prp.getPriceMap().keySet()) {
			SalesConsumHistory sch = new SalesConsumHistory();
			sch.setMonth(Integer.parseInt(yearMonth.substring(4, yearMonth.length())));
			sch.setYear(Integer.parseInt(yearMonth.substring(0, 4)));
			sch.setPrice(prp.getPriceMap().get(yearMonth));
			sch.setConsumValue(0d);
			sch.setConsumVolume(0d);
			sch.setHasInvoice(0);
			sch.setMakeDate(DateUtil.getCurrentDate());
			sch.setMonthBeginInventory(0d);
			sch.setMonthEndInventory(0d);
			sch.setOtherConsumVolume(0d);
			sch.setProductId(prp.getProductId());
			sch.setOrganId(prp.getOrganId());
			sch.setWarehouseId(prp.getWarehouseId());
			sch.setSalesValue(0d);
			sch.setSalesVolume(0d);
			appSalesConsumHistory.addSalesConsumHistory(sch);
		}
		
	}

	private void addSalesConsumHistory(Map<String, String> salesData, ProductRollingPrice prp) throws Exception {
		SalesConsumHistory sch = new SalesConsumHistory();
		MapUtil.mapToObject(salesData, sch);
		sch.setMakeDate(DateUtil.getCurrentDate());
		//设置年月
		String monthAndYear = salesData.get("mydate");
		sch.setMonth(Integer.parseInt(monthAndYear.substring(4, monthAndYear.length())));
		sch.setYear(Integer.parseInt(monthAndYear.substring(0, 4)));
		//设置价格
		if(prp != null) {
			sch.setPrice(prp.getPriceMap().remove(monthAndYear));
		} else {
			sch.setPrice(0d);
		}
		
		appSalesConsumHistory.addSalesConsumHistory(sch);
	}
	
	private void addBaseResource(String dateTime) throws Exception {
		BaseResource br = new BaseResource();
	    Long brid = Long.valueOf(MakeCode.getExcIDByRandomTableName("base_resource",0,""));
	    br.setId(brid);
	    br.setTagname("SalesConsumDate");
	    br.setTagsubkey(1);
	    br.setTagsubvalue(dateTime);
	    br.setIsuse(1);
	    appBaseResource.addBaseResource(br);
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
	
	
	public static void main(String[] args) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(2015, 3-1, 1);
//		calendar.add(calendar.DAY_OF_MONTH, -1);
//		System.out.println(getInventoryBeginDate(2015,3));
//		System.out.println(getInventoryEndDate(2015,3));
	}
	
}
