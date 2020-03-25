package com.winsafe.drp.service.report;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.sun.org.apache.commons.beanutils.PropertyUtils;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.action.newreport.MonthlyData;
import com.winsafe.drp.action.newreport.SalesConsumInventoryMonthlySummaryData;
import com.winsafe.drp.dao.AppSalesConsumptionInventoryMonthlyVolumeReport;
import com.winsafe.drp.dao.SalesConsumMonthReportForm;
import com.winsafe.drp.dao.SalesConsumptionInventory;
import com.winsafe.drp.dao.SalesConsumptionInventoryMonthlyVolumeReportForm;
import com.winsafe.drp.dao.SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.SalesConsumeInventoryReportService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

public class SalesConsumptionInventoryMonthlyVolumeReportService extends BaseAction{
	
	private AppSalesConsumptionInventoryMonthlyVolumeReport app = new AppSalesConsumptionInventoryMonthlyVolumeReport();
	private SalesConsumeInventoryReportService scirs = new SalesConsumeInventoryReportService();
	
	public void getTitleList(HttpServletRequest request, List<SalesConsumptionInventory> titleBeginList, List<SalesConsumptionInventory> titleList) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");//格式化为年月
		String beginDateStr = String.valueOf(request.getAttribute("beginDate"));
		String endDateStr = String.valueOf(request.getAttribute("endDate"));

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(beginDateStr));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(endDateStr));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		int i = 0;
		String dateStr = null;
		while (curr.before(max)) {
			if(i == 0){
				i++;
				dateStr = sdf2.format(curr.getTime());
				SalesConsumptionInventory sci = new SalesConsumptionInventory();
				sci.setYear(Integer.valueOf(dateStr.split("/")[0]));
				sci.setMonth(Integer.valueOf(dateStr.split("/")[1]));
				sci.setDisplayDate(sci.getYear() + "/" + sci.getMonth());
				sci.setSalesVolume(0d);
				sci.setComsumptionVolume(0d);
				sci.setMonthEndInventory(0d);
				sci.setLastYearDisplayDate(sci.getYear()-1 + "/" + sci.getMonth());
				sci.setLastYearSalesVolume(0d);
				sci.setLastYearComsumptionVolume(0d);
				sci.setLastYearMonthEndInventory(0d);
				titleBeginList.add(sci);
			}
			else{
				dateStr = sdf2.format(curr.getTime());
				SalesConsumptionInventory sci = new SalesConsumptionInventory();
				sci.setYear(Integer.valueOf(dateStr.split("/")[0]));
				sci.setMonth(Integer.valueOf(dateStr.split("/")[1]));
				sci.setDisplayDate(sci.getYear() + "/" + sci.getMonth());
				sci.setSalesVolume(0d);
				sci.setComsumptionVolume(0d);
				sci.setMonthEndInventory(0d);
				sci.setLastYearDisplayDate(sci.getYear()-1 + "/" + sci.getMonth());
				sci.setLastYearSalesVolume(0d);
				sci.setLastYearComsumptionVolume(0d);
				sci.setLastYearMonthEndInventory(0d);
				titleList.add(sci);
			}
			curr.add(Calendar.MONTH, 1);
		}
		if(titleList == null || titleList.size() == 0){
			titleList.addAll(titleBeginList);
		}
	}
	
	public List<SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm> getSalesConsumptionInventoryMonthlyVolumeReportPageDisplayFormList(HttpServletRequest request, int pageSize, UsersBean users) throws Exception{
		List<SalesConsumptionInventoryMonthlyVolumeReportForm> reports = getSalesConsumptionInventoryMonthlyVolumeReportFormList(request, pageSize, users);
		List<SalesConsumptionInventory> titleList = new ArrayList<SalesConsumptionInventory>();
		List<SalesConsumptionInventory> titleBeginList = new ArrayList<SalesConsumptionInventory>();
		getTitleList(request, titleBeginList, titleList);
		List<SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm> list = dealReports(request, reports, titleBeginList, titleList, users);
		reports = null;
		titleList = null;
		titleBeginList = null;
		return list;
	}
	
	
	/**
	 * 将从数据库获取的数据（按机构ID，仓库ID，产品ID升序排序）转为页面显示的数据
	 * @param request 
	 * @param reports
	 * @param titleBeginList
	 * @param titleList
	 * @param users 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws HibernateException
	 * @throws SQLException
	 */
	private List<SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm> dealReports(HttpServletRequest request, List<SalesConsumptionInventoryMonthlyVolumeReportForm> reports, List<SalesConsumptionInventory> titleBeginList, List<SalesConsumptionInventory> titleList, UsersBean users) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, HibernateException, SQLException{
		List<SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm> list = new ArrayList<SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm>();
		Map<String, SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm> keyFormMap = new HashMap<String, SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm>();
		String key = null;
		//获取库存
		Calendar thisYear = Calendar.getInstance();
		thisYear.setTime(DateUtil.StringToDate(request.getParameter("beginDate"))); 
		String fromDate = request.getParameter("beginDate").substring(0, 7).replace("-", "");
		String toDate = request.getParameter("endDate").substring(0, 7).replace("-", "");
		
		Calendar lastYear = Calendar.getInstance();
		lastYear.setTime(DateUtil.StringToDate(request.getParameter("beginDate"))); 
		lastYear.add(Calendar.YEAR, -1);
		
		Map<String, Double> thisYearInventoryMap = scirs.getMonthBeginInventoryMap(request.getParameter("ProductName"), request.getParameter("packSizeName"), request.getParameter("region"), request.getParameter("organId"), request.getParameter("warehouseId"), thisYear, fromDate, toDate, users);
		Map<String, Double> lastYearInventoryMap = scirs.getMonthBeginInventoryMap(request.getParameter("ProductName"), request.getParameter("packSizeName"), request.getParameter("region"), request.getParameter("organId"), request.getParameter("warehouseId"), lastYear, fromDate, toDate, users);
		//form这个变量存储了页面每一行的数据
		SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm form = null;
		//遍历从数据库获得的数据，将其按机构ID，仓库ID和产品ID分组
		for(SalesConsumptionInventoryMonthlyVolumeReportForm report: reports){
			key = report.getOrganId() + "_" + report.getWarehouseId() + "_" + report.getProductId();
			if(!keyFormMap.containsKey(key)){
				form = new SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm();
				PropertyUtils.copyProperties(form, report);
				form.setKey(key);
				keyFormMap.put(key, form);
				list.add(form);
			}
			form = keyFormMap.get(key);
			
			//初始化列表
			if(form.getSalesConsumptionInventoryBeginList() == null){
				List<SalesConsumptionInventory> sciList = new ArrayList<SalesConsumptionInventory>();
				SalesConsumptionInventory nSci = null;
				for(SalesConsumptionInventory sci: titleBeginList){
					nSci = new SalesConsumptionInventory();
					nSci.setYear(sci.getYear());
					nSci.setMonth(sci.getMonth());
					nSci.setSalesVolume(0d);
					nSci.setComsumptionVolume(0d);
					nSci.setMonthEndInventory(0d);
					nSci.setLastYearSalesVolume(0d);
					nSci.setLastYearComsumptionVolume(0d);
					nSci.setLastYearMonthEndInventory(0d);
					sciList.add(nSci);
					nSci = null;
				}
				form.setSalesConsumptionInventoryBeginList(sciList);
			}
			//将数据库获取的数据进行赋值
			Double thisYearInitalInventory = thisYearInventoryMap.get(report.getProductId()+"_"+report.getOrganId()+"_"+report.getWarehouseId());
			if(thisYearInitalInventory == null) {
				thisYearInitalInventory = 0d;
			}
			Double lastYearInitalInventory = lastYearInventoryMap.get(report.getProductId()+"_"+report.getOrganId()+"_"+report.getWarehouseId());
			if(lastYearInitalInventory == null) {
				lastYearInitalInventory = 0d;
			}
			for(SalesConsumptionInventory sci: form.getSalesConsumptionInventoryBeginList()){
				if(sci.getYear() != null && report.getYear() != null && sci.getYear().intValue() == report.getYear().intValue()
						&& sci.getMonth() != null && report.getMonth() != null && sci.getMonth().intValue() == report.getMonth().intValue()){
					sci.setDisplayDate(report.getDisplayDate());
					sci.setSalesVolume(report.getSalesVolume());
					sci.setComsumptionVolume(report.getComsumptionVolume());
//					sci.setMonthEndInventory(ArithDouble.sub(ArithDouble.add(thisYearInitalInventory, report.getSalesVolume()), ArithDouble.add(report.getComsumptionVolume(), report.getOtherComsumptionVolume())));
					sci.setMonthEndInventory(ArithDouble.sub(ArithDouble.add(thisYearInitalInventory, report.getSalesVolume()), report.getComsumptionVolume()));
					//beginMonthInventoryMap.put(report.getWarehouseId()+"_"+report.getProductId()+"_"+report.getYear()+"_"+report.getMonth(), sci.getMonthEndInventory());
					
					sci.setLastYearDisplayDate(sci.getYear()-1 + "/" + sci.getMonth());
					sci.setLastYearSalesVolume(report.getLastYearSalesVolume());
					sci.setLastYearComsumptionVolume(report.getLastYearComsumptionVolume());
//					sci.setLastYearMonthEndInventory(ArithDouble.sub(ArithDouble.add(lastYearInitalInventory, report.getLastYearSalesVolume()), ArithDouble.add(report.getLastYearComsumptionVolume(), report.getLastYearOtherComsumptionVolume())));
					sci.setLastYearMonthEndInventory(ArithDouble.sub(ArithDouble.add(lastYearInitalInventory, report.getLastYearSalesVolume()), report.getLastYearComsumptionVolume())); 
					//beginMonthInventoryMap.put(report.getWarehouseId()+"_"+report.getProductId()+"_"+(report.getYear()-1)+"_"+report.getMonth(), sci.getLastYearMonthEndInventory());
					break;
				}
			}
			//初始化列表
			if(form.getSalesConsumptionInventoryList() == null){
				List<SalesConsumptionInventory> sciList = new ArrayList<SalesConsumptionInventory>();
				SalesConsumptionInventory nSci = null;
				for(SalesConsumptionInventory sci: titleList){
					nSci = new SalesConsumptionInventory();
					nSci.setYear(sci.getYear());
					nSci.setMonth(sci.getMonth());
					nSci.setSalesVolume(0d);
					nSci.setComsumptionVolume(0d);
					nSci.setMonthEndInventory(0d);
					nSci.setLastYearSalesVolume(0d);
					nSci.setLastYearComsumptionVolume(0d);
					nSci.setLastYearMonthEndInventory(0d);
					sciList.add(nSci);
					nSci = null;
				}
				form.setSalesConsumptionInventoryList(sciList);
			}
			//将数据库获取的数据进行赋值。
			for(SalesConsumptionInventory sci: form.getSalesConsumptionInventoryList()){
				if(sci.getYear() != null && report.getYear() != null && sci.getYear().intValue() == report.getYear().intValue()
						&& sci.getMonth() != null && report.getMonth() != null && sci.getMonth().intValue() == report.getMonth().intValue()){
					sci.setDisplayDate(report.getDisplayDate());
					sci.setSalesVolume(report.getSalesVolume());
					sci.setComsumptionVolume(report.getComsumptionVolume());
					//sci.setMonthEndInventory(calculateMonthEndInventory(report.getWarehouseId(), report.getProductId(), report.getYear(), report.getMonth(), beginMonthInventoryMap, sci.getSalesVolume(), sci.getComsumptionVolume()));
					
					sci.setLastYearDisplayDate(sci.getYear()-1 + "/" + sci.getMonth());
					sci.setLastYearSalesVolume(report.getLastYearSalesVolume());
					sci.setLastYearComsumptionVolume(report.getLastYearComsumptionVolume());
					//sci.setLastYearMonthEndInventory(calculateMonthEndInventory(report.getWarehouseId(), report.getProductId(), report.getYear()-1, report.getMonth(), beginMonthInventoryMap, sci.getLastYearSalesVolume(), sci.getLastYearComsumptionVolume()));
					break;
				}
			}
			key = null;
		}
		//计算期末库存数量
		SalesConsumptionInventory beginMonthInventory = null;
		Map<String, Double> beginMonthInventoryMap = new HashMap<String, Double>();
		for(SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm display: list){
			//获得起始月份的期末库存
			beginMonthInventory = display.getSalesConsumptionInventoryBeginList().get(0);
			if(!beginMonthInventoryMap.containsKey(display.getWarehouseId() + "_" + display.getProductId() + "_" + beginMonthInventory.getYear() + "_" + beginMonthInventory.getMonth())){
				beginMonthInventoryMap.put(display.getWarehouseId() + "_" + display.getProductId() + "_" + beginMonthInventory.getYear() + "_" + beginMonthInventory.getMonth(), beginMonthInventory.getMonthEndInventory());
			}
			if(!beginMonthInventoryMap.containsKey(display.getWarehouseId() + "_" + display.getProductId() + "_" + (beginMonthInventory.getYear()-1) + "_" + beginMonthInventory.getMonth())){
				beginMonthInventoryMap.put(display.getWarehouseId() + "_" + display.getProductId() + "_" + (beginMonthInventory.getYear()-1) + "_" + beginMonthInventory.getMonth(), beginMonthInventory.getLastYearMonthEndInventory());
			}
			
			//遍历每个月，计算期末库存
			for(SalesConsumptionInventory sci: display.getSalesConsumptionInventoryList()){
				sci.setMonthEndInventory(calculateMonthEndInventory(display.getWarehouseId(), display.getProductId(), sci.getYear(), sci.getMonth(), beginMonthInventoryMap, sci.getSalesVolume(), sci.getComsumptionVolume(), fromDate.equals(toDate)));
				sci.setLastYearMonthEndInventory(calculateMonthEndInventory(display.getWarehouseId(), display.getProductId(), (sci.getYear()-1), sci.getMonth(), beginMonthInventoryMap, sci.getLastYearSalesVolume(), sci.getLastYearComsumptionVolume(), fromDate.equals(toDate)));
			}
			beginMonthInventory = null;
		}
		beginMonthInventoryMap = null;
		
		keyFormMap = null;
		beginMonthInventoryMap = null;
		return list;
	}
	
	private Double calculateMonthEndInventory(String warehouseId, String productId, Integer year, Integer month, Map<String, Double> beginMonthInventoryMap, Double salesVolume, Double comsumptionVolume, boolean isSameMonth){
		//一月份的上一个月是去年12月
		int lastMonthYear = year;
		int lastMonthMonth = month;
		if(!isSameMonth) {
			if(month == 1){
				lastMonthMonth = 12;
				lastMonthYear = year - 1;
			}
			else{
				lastMonthMonth = month - 1;
				lastMonthYear = year;
			}
		}
		String lastMonthKey = warehouseId + "_" + productId + "_" + lastMonthYear + "_" + lastMonthMonth;
		String thisMonthKey = warehouseId + "_" + productId + "_" + year + "_" + month;
		if(!beginMonthInventoryMap.containsKey(lastMonthKey)){
			beginMonthInventoryMap.put(lastMonthKey, 0d);
		}
		Double lastMonthInventory = beginMonthInventoryMap.get(lastMonthKey);
		if(isSameMonth) {
			beginMonthInventoryMap.put(thisMonthKey, (lastMonthInventory == null? 0d: lastMonthInventory));
		} else {
			beginMonthInventoryMap.put(thisMonthKey, (lastMonthInventory == null? 0d: lastMonthInventory) + (salesVolume == null? 0d: salesVolume) - (comsumptionVolume == null? 0d: comsumptionVolume));
		}
		return beginMonthInventoryMap.get(thisMonthKey);
	}
	
	@SuppressWarnings("unchecked")
	private List<SalesConsumptionInventoryMonthlyVolumeReportForm> getSalesConsumptionInventoryMonthlyVolumeReportFormList(HttpServletRequest request, int pageSize, UsersBean users) throws Exception{
		List resultList = null;
		if(pageSize < 0){
			resultList = app.getSalesConsumptionInventoryMonthlyVolumeReport(request, users);
		}
		else{
			resultList = app.getSalesConsumptionInventoryMonthlyVolumeReport(request, pageSize, users);
		}
		List<SalesConsumptionInventoryMonthlyVolumeReportForm> reports = new ArrayList<SalesConsumptionInventoryMonthlyVolumeReportForm>();
		SalesConsumptionInventoryMonthlyVolumeReportForm report = null;
		for(int i=0; i<resultList.size(); i++){
			Map map = (Map) resultList.get(i);
			report = new SalesConsumptionInventoryMonthlyVolumeReportForm();
			MapUtil.mapToObject(map, report);
			reports.add(report);
			report = null;
		}
		resultList = null;
		return reports;
	}

	public List<String> getTitleList(HttpServletRequest request) {
		List<String> titleList = new ArrayList<String>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");//格式化为年月
		String beginDateStr = String.valueOf(request.getAttribute("beginDate"));
		String endDateStr = String.valueOf(request.getAttribute("endDate"));

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(DateUtil.formatStrDate(beginDateStr));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(DateUtil.formatStrDate(endDateStr));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		int i = 0;
		String dateStr = null;
		while (curr.before(max)) {
			i++;
			dateStr = sdf2.format(curr.getTime());
			titleList.add(dateStr);
			curr.add(Calendar.MONTH, 1);
		}
		return titleList;
		
	}
	
	public static void main(String[] args) {
		List<String> titleList = new ArrayList<String>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");//格式化为年月
		String beginDateStr = "2015-09-08";
		String endDateStr = "2015-10-08";

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(DateUtil.formatStrDate(beginDateStr));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(DateUtil.formatStrDate(endDateStr));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		int i = 0;
		String dateStr = null;
		while (curr.before(max)) {
			i++;
			dateStr = sdf2.format(curr.getTime());
			titleList.add(dateStr);
			curr.add(Calendar.MONTH, 1);
		}
	}

	public void setDataList(SalesConsumMonthReportForm scForm,
			SalesConsumInventoryMonthlySummaryData scimsData, SalesConsumMonthReportForm queryForm) {
		List<MonthlyData> dataList = new ArrayList<MonthlyData>();
		String beginDateStr = String.valueOf(queryForm.getBeginDate());
		String endDateStr = String.valueOf(queryForm.getEndDate());

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(DateUtil.formatStrDate(beginDateStr));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(DateUtil.formatStrDate(endDateStr));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		int i = 0;
		while (curr.before(max)) {
			i++;
			MonthlyData md = new MonthlyData(String.valueOf(curr.get(Calendar.YEAR)),String.valueOf(curr.get(Calendar.MONTH) + 1), scForm.getOrganId(), scForm.getProductId(),scForm.getWarehouseId(), scimsData);
			dataList.add(md);
			curr.add(Calendar.MONTH, 1);
		}
		scForm.setMonthlyDataList(dataList);
	}

	public void setInventory(SalesConsumMonthReportForm scForm, Double initalEndInventory) {
		for(MonthlyData monthlyData : scForm.getMonthlyDataList()) {
//			initalEndInventory = ArithDouble.sub(ArithDouble.add(initalEndInventory, monthlyData.getSalesVolume()), ArithDouble.add(monthlyData.getConsumptionVolume(), monthlyData.getOtherConsumptionVolume()));
			initalEndInventory = ArithDouble.sub(ArithDouble.add(initalEndInventory, monthlyData.getSalesVolume()), monthlyData.getConsumptionVolume());
			monthlyData.setEndInventoryVolume(initalEndInventory);
			monthlyData.setEndInventoryValue(ArithDouble.mul(initalEndInventory, scForm.getValue()));
		}
	}
}
