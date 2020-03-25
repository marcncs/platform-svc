package com.winsafe.drp.action.newreport;

import java.util.Map;

public class MonthlyData {
	private String year;
	private String month;
	private String organId;
	private String productId;
	private String warehouseId;
	private SalesConsumInventoryMonthlySummaryData summaryData;
	
	public MonthlyData(String year, String month, String organId,
			String productId, String warehouseId,
			SalesConsumInventoryMonthlySummaryData summaryData) {
		super();
		this.year = year;
		this.month = month;
		this.organId = organId;
		this.productId = productId;
		this.warehouseId = warehouseId;
		this.summaryData = summaryData;
	}
	public MonthlyData() {
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public SalesConsumInventoryMonthlySummaryData getSummaryData() {
		return summaryData;
	}
	public void setSummaryData(SalesConsumInventoryMonthlySummaryData summaryData) {
		this.summaryData = summaryData;
	}
	
	public String getKey() {
		return productId + "_" + organId + "_" + warehouseId + "_" + year + "_" + month;
	}
	
	public Double getConsumptionVolume() {
		return checkNull(summaryData.getConsumptionVolume().get(getKey()));
	}

	public Double getEndInventoryVolume() {
		return checkNull(summaryData.getEndInventoryVolume().get(getKey()));
	}
	public void setEndInventoryVolume(Double endInventoryVolume) {
		summaryData.getEndInventoryVolume().put(getKey(), endInventoryVolume);
	}
	public Double getSalesValue() {
		return checkNull(summaryData.getSalesValue().get(getKey()));
	}
	public Double getConsumptionValue() {
		return checkNull(summaryData.getConsumptionValue().get(getKey()));
	}
	public Double getEndInventoryValue() {
		return checkNull(summaryData.getEndInventoryValue().get(getKey()));
	}
	public void setEndInventoryValue(Double endInventoryValue) {
		summaryData.getEndInventoryValue().put(getKey(), endInventoryValue);
	}
	public Double getOtherConsumptionVolume() {
		return checkNull(summaryData.getOtherConsumptionVolume().get(getKey()));
	}
	public Double getOtherConsumptionValue() {
		return checkNull(summaryData.getOtherConsumptionValue().get(getKey()));
	}
	public Double getSalesVolume() {
		return checkNull(summaryData.getSalesVolume().get(getKey()));
	}
	
	public double checkNull(Double value) {
		return value != null ? value : 0d;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

}
