package com.winsafe.drp.task.psi;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.winsafe.drp.dao.AppInventoryReport;
import com.winsafe.drp.dao.AppPurchaseSalesReport;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.InventoryReport;
import com.winsafe.drp.dao.PurchaseSalesReport;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

/**
 * PSI报表数据生成类
 * Create Time 2014年5月7日16:20:26
 * @author Andy.liu
 * 
 */
public class PSIReportService {
	/** 台账Dao */
	private AppStockWasteBook aswb = new AppStockWasteBook();
	/** I Dao */
	private AppInventoryReport air = new AppInventoryReport();
	/** PS Dao */
	private AppPurchaseSalesReport apsr = new AppPurchaseSalesReport();

	/** 查询PS表中的最后时间*/
	private final String psSql = "select max(RECORDDATE) from Purchase_Sales_Report ";
	/** 查询I表中的最后时间*/
	private final String iSql = "select max(RECORDDATE) from Inventory_Report ";
	/** 查询台账表中的最小时间*/
	private final String swbSql = "select min(RECORDDATE) from STOCK_WASTE_BOOK ";

	/** 初始化数据 */
	public PSIReportService() {
	}

	/**
	 * 生成入口
	 */
	public void producePSIReportData() {
		try {
			HibernateUtil.currentSession(true);
			// 生成PS数据
			psReportData();
			// 生成I数据
			iReportData();
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			HibernateUtil.rollbackTransaction(); 
		} finally{
			HibernateUtil.closeSession();
		}
	}

	/**
	 * PS数据生成 
	 * Create Time 2014年5月7日16:20:45
	 * @author Andy.liu
	 * @throws Exception
	 */
	private void psReportData() throws Exception {
		/*
		 * 1，获取最后一次生成的时间，数据来源为PurchaseSalesReport表中最大的记录时间
		 * 2，如果时间为空，则表示数据库中无记录，是第一次生成
		 * 3 ，第一次生成时，获取台账表的最小时间，作为起点时间
		 * 4，如果时间不为空，则获取该时间，作为起点
		 * 5，获取起点时间到起点时间的下一天作为时间段，查询台账表的记录
		 * 6，台账表中分组条件是warehouseId、productId、 batch 
		 * 7，sum(cycleInQuantity)为P的数量，sum(cycleOutQuantity)为S的数量
		 * 8，循环将数据记录到PurchaseSalesReport表中
		 */
		String startDate = getStartDate(psSql);
		String endDate;
		Date startDate0;
		Date endDate0;
		if (StringUtil.isEmpty(startDate)) {
			startDate = getStartDate(swbSql);
			// 如果台账表中无数据，不处理
			if (StringUtil.isEmpty(startDate)) {
				return;
			}
		}
		startDate = startDate.replace("/", "-");
		startDate0 = DateUtil.StringToDate(startDate, "yyyy-MM-dd ");
		startDate0 = DateUtil.addDay2Date(1, startDate0);  //每次生成的开始时间都是在数据库中的时间+1天。
		startDate0 = DateUtil.setHHmmssSSS(startDate0, 0, 0, 0, 0);  //精确到毫秒级
		Date currentDate0 = DateUtil.setHHmmssSSS(DateUtil.getCurrentDate(), 0, 0, 0, 0); //精确到毫秒级
		// 按天循环开始计算,一直计算到当前系统时间
		while (startDate0.before(currentDate0)) {
			endDate0 = DateUtil.addDay2Date(1, startDate0);
			endDate = DateUtil.formatDate(endDate0)+" 00:00:00";
			insertPSReport(endDate, startDate0);
			//下一天
			startDate0 = DateUtil.addDay2Date(1, startDate0);;
			
		}

	}
	
	/**
	 * I数据生成 
	 * Create Time 2014年5月7日16:20:45
	 * @author Andy.liu
	 * @throws Exception
	 */
	private void iReportData() throws Exception {
		/*
		 * 1，获取最后一次生成的时间，数据来源为InventoryReport表中最大的记录时间
		 * 2，如果时间为空，则表示数据库中无记录，是第一次生成
		 * 3，第一次生成时，获取台账表的最小时间，作为起点时间
		 * 4，直接计算sum(cycleInQuantity-cycleOutInQuantity)作为I的数量
		 * 5，循环将数据记录到InventoryReport表中 
		 * 6，如果时间不为空，则获取该时间，作为起点
		 * 7，获取起点时间到起点时间的下一天作为时间段，查询台账表的记录
		 * 8，获取起点时间的前一天I表中的CYCLEBALANCEQUANTITY数据作为基准数据
		 * 9，匹配warehouseId、product、batch相同的记录
		 * 10，CYCLEBALANCEQUANTITY+cycleInQuantity-cycleOutQuantity为最终I的数量
		 * 11，循环将数据记录到InventoryReport表中
		 */
		String startDate = getStartDate(iSql);
		String endDate;
		Date startDate0 = null;
		Date endDate0;
		// 如果为空，则是第一次生成
		if (StringUtil.isEmpty(startDate)) { 
			startDate = insertFirstIreport();  //第一次生成的方法返回生成后的时间
			if(startDate == null){
				return;
			}
		}
		startDate = startDate.replace("/", "-");
		startDate0 = DateUtil.StringToDate(startDate, "yyyy-MM-dd ");
		startDate0 = DateUtil.addDay2Date(1, startDate0);  //每次生成的开始时间都是在数据库中的时间+1天。
		startDate0 = DateUtil.setHHmmssSSS(startDate0, 0, 0, 0, 0); //开始时间精确到毫秒级
		Date currentDate0 = DateUtil.setHHmmssSSS(DateUtil.getCurrentDate(),0,0,0,0); //当前时间精确到毫秒级
		// 按天循环开始计算,一直计算到当前系统时间
		while (startDate0.before(currentDate0)) {
			endDate0 = DateUtil.addDay2Date(1, startDate0);
			endDate = DateUtil.formatDate(endDate0)+" 00:00:00";
			insertIreport(endDate, startDate0);
			//下一天
			startDate0 = DateUtil.addDay2Date(1, startDate0);
		}
	}

	/**
	 * 记录PS数据
	 * @param endDate
	 * @param startDate0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void insertPSReport(String endDate, Date startDate0)
			throws Exception {
		PurchaseSalesReport psr;
		List<Map> swbList = aswb.getStockWasteBookByStartDateAndEndDate(
				DateUtil.formatDateTime(startDate0), endDate);
		for (Map map : swbList) {
			String productid = (String) map.get("productid");
			String warehouseid = (String) map.get("warehouseid");
			String batch = (String) map.get("batch");
			double cycleinquantity = Double.valueOf((String) map.get("cycleinquantity"));
			double cycleoutquantity = Double.valueOf((String) map.get("cycleoutquantity"));
			psr = new PurchaseSalesReport();
			psr.setBatch(batch);
			psr.setProductid(productid);
			psr.setWarehouseid(warehouseid);
			psr.setWarehousebit("000");
			psr.setRecorddate(startDate0);
			psr.setIsDelete(0);
			psr.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("purchase_sales_report", 0, "")));
			psr.setCycleinquantity(cycleinquantity);
			psr.setCycleoutquantity(cycleoutquantity);
			apsr.savePurchaseSalesReport(psr);
		}
		if (swbList == null || swbList.isEmpty()) {
			psr = new PurchaseSalesReport();
			Long id = Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"purchase_sales_report", 0, ""));
			psr.setId(id);
			psr.setIsDelete(1);
			psr.setRecorddate(startDate0);
			apsr.savePurchaseSalesReport(psr);
		}
	}



	/**
	 * 记录I数据
	 * @param startDate
	 * @param endDate
	 * @param startDate0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void insertIreport(String endDate, Date startDate0)
			throws Exception {
		List<Map> swbList = aswb.getStockWasteBookByStartDateAndEndDate(DateUtil.formatDateTime(startDate0), endDate);

		// 复制前一天的数据
		//如果startDate0的时间和当前日期一样，则不复制
		List<InventoryReport> list = this.copyIreport(DateUtil.formatDateTime(DateUtil.addDay2Date(-1, startDate0)), startDate0);
		InventoryReport ir;
		//获取台账表记录
		for (Map map : swbList) {
			String productid = (String) map.get("productid");
			String warehouseid = (String) map.get("warehouseid");
			String batch = (String) map.get("batch");
			double cycleinquantity = Double.valueOf((String) map.get("cycleinquantity"));
			double cycleoutquantity = Double.valueOf((String) map.get("cycleoutquantity"));
			//匹配历史记录判断新增和更新
			InventoryReport report = air.getInventoryReportSwb(warehouseid, productid, batch);
			if (report == null) {
				// 新增
				report = new InventoryReport();
				report.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("inventory_report", 0,"")));
				report.setBatch(batch);
				report.setWarehouseid(warehouseid);
				report.setWarehousebit("000");
				report.setProductid(productid);
				report.setCyclebalancequantity(cycleinquantity - cycleoutquantity);
				report.setIsDelete(0);
				report.setRecorddate(startDate0);
				air.saveInventoryReport(report);
			} else {
				// 更新
				report.setCyclebalancequantity(report.getCyclebalancequantity()+ cycleinquantity - cycleoutquantity);
				air.updInventoryReport(report);
			}
		}
		if((list==null||list.size()<0)&&(swbList==null||swbList.size()<0)){
			if (swbList == null || swbList.isEmpty()) {
				ir = new InventoryReport();
				Long id = Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"inventory_report", 0, ""));
				ir.setId(id);
				ir.setIsDelete(1);
				ir.setRecorddate(startDate0);
				air.saveInventoryReport(ir);
			}
		}
	}

	/**
	 * 首次记录I数据
	 * 若时间为空，则不做处理，且返回NULL
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String insertFirstIreport() throws Exception {
		String startDate;
		String endDate;
		InventoryReport ir;
		Date startDate0;
		Date endDate0;
		startDate = getStartDate(swbSql);
		// 如果台账表中无数据，不处理
		if (StringUtil.isEmpty(startDate)) {
			return null;
		}
		startDate = startDate.replace("/", "-");
		startDate0 = DateUtil.StringToDate(startDate, "yyyy-MM-dd ");
		startDate0 = DateUtil.setHHmmss(startDate0, 0, 0, 0);
		endDate0 = DateUtil.addDay2Date(1, startDate0);
		endDate = DateUtil.formatDate(endDate0)+" 00:00:00";
		List<Map> list = aswb.getStockWasteBookByStartDateAndEndDate(
				DateUtil.formatDateTime(startDate0), endDate);
		for (Map map : list) {
			String productid = (String) map.get("productid");
			String warehouseid = (String) map.get("warehouseid");
			String batch = (String) map.get("batch");
			double cycleinquantity = Double.valueOf((String) map.get("cycleinquantity"));
			double cycleoutquantity = Double.valueOf((String) map.get("cycleoutquantity"));
			ir = new InventoryReport();
			ir.setBatch(batch);
			ir.setProductid(productid);
			ir.setWarehousebit("000");
			ir.setWarehouseid(warehouseid);
			ir.setCyclebalancequantity(cycleinquantity - cycleoutquantity);
			ir.setIsDelete(0);
			ir.setRecorddate(startDate0);
			ir.setId( Long.valueOf(MakeCode.getExcIDByRandomTableName("inventory_report", 0, "")));
			air.saveInventoryReport(ir);
		}
		if (list == null || list.isEmpty()) {
			ir = new InventoryReport();
			Long id = Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"inventory_report", 0, ""));
			ir.setId(id);
			ir.setIsDelete(1);
			ir.setRecorddate(startDate0);
			air.saveInventoryReport(ir);
		}
		// 重置时间，作为后面开始时间
		startDate = getStartDate(iSql); 
		return startDate;
	}

	/**
	 * 复制前一天的数据
	 * 做为当天的基础数据
	 * @param date
	 * @param recorddate
	 * @throws Exception
	 */
	public List<InventoryReport> copyIreport(String date, Date recorddate) throws Exception {
		List<InventoryReport> list = air.getInventoryByDate(date);
		InventoryReport ir = null;
		for (InventoryReport inventoryReport : list) {
			ir = new InventoryReport();
			BeanUtils.copyProperties(ir, inventoryReport);
			ir.setRecorddate(recorddate);
			ir.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"inventory_report", 0, "")));
			air.saveInventoryReport(ir);
		}
		return list;
		
	}

	/**
	 * 获取时间
	 * @param sql 
	 * @throws Exception
	 */
	private String getStartDate(String sql) throws Exception {
		String resultDate = "";
		try {
			ResultSet rs = EntityManager.query2(sql);
			if (rs.next()) {
				resultDate = rs.getString(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultDate;
	}
	
	
}
