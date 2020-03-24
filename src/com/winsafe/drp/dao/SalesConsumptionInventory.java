package com.winsafe.drp.dao;


public class SalesConsumptionInventory {
	private Integer year;
	private Integer month;
	private String displayDate;
	private Double salesVolume;
	private Double comsumptionVolume;
	private Double monthEndInventory;
	private String lastYearDisplayDate;
	private Double lastYearSalesVolume;
	private Double lastYearComsumptionVolume;
	private Double lastYearMonthEndInventory;
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getDisplayDate() {
		return displayDate;
	}
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}
	public Double getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(Double salesVolume) {
		this.salesVolume = salesVolume;
	}
	public Double getComsumptionVolume() {
		return comsumptionVolume;
	}
	public void setComsumptionVolume(Double comsumptionVolume) {
		this.comsumptionVolume = comsumptionVolume;
	}
	public Double getMonthEndInventory() {
		return monthEndInventory;
	}
	public void setMonthEndInventory(Double monthEndInventory) {
		this.monthEndInventory = monthEndInventory;
	}
	public Double getLastYearSalesVolume() {
		return lastYearSalesVolume;
	}
	public void setLastYearSalesVolume(Double lastYearSalesVolume) {
		this.lastYearSalesVolume = lastYearSalesVolume;
	}
	public Double getLastYearComsumptionVolume() {
		return lastYearComsumptionVolume;
	}
	public void setLastYearComsumptionVolume(Double lastYearComsumptionVolume) {
		this.lastYearComsumptionVolume = lastYearComsumptionVolume;
	}
	public Double getLastYearMonthEndInventory() {
		return lastYearMonthEndInventory;
	}
	public void setLastYearMonthEndInventory(Double lastYearMonthEndInventory) {
		this.lastYearMonthEndInventory = lastYearMonthEndInventory;
	}
	public String getLastYearDisplayDate() {
		return lastYearDisplayDate;
	}
	public void setLastYearDisplayDate(String lastYearDisplayDate) {
		this.lastYearDisplayDate = lastYearDisplayDate;
	}
}
