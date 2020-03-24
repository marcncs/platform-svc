package com.winsafe.drp.action.newreport;

import java.util.HashMap;
import java.util.Map;

public class SalesConsumInventoryMonthlySummaryData {
	private Map<String,Double> salesVolume = new HashMap<String, Double>();
	private Map<String,Double> consumptionVolume = new HashMap<String, Double>();
	private Map<String,Double> endInventoryVolume = new HashMap<String, Double>();
	private Map<String,Double> otherConsumptionVolume = new HashMap<String, Double>();
	private Map<String,Double> salesValue = new HashMap<String, Double>();
	private Map<String,Double> consumptionValue = new HashMap<String, Double>();
	private Map<String,Double> endInventoryValue = new HashMap<String, Double>();
	private Map<String,Double> otherConsumptionValue = new HashMap<String, Double>();
	public Map<String, Double> getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(Map<String, Double> salesVolume) {
		this.salesVolume = salesVolume;
	}
	public Map<String, Double> getConsumptionVolume() {
		return consumptionVolume;
	}
	public void setConsumptionVolume(Map<String, Double> consumptionVolume) {
		this.consumptionVolume = consumptionVolume;
	}
	public Map<String, Double> getEndInventoryVolume() {
		return endInventoryVolume;
	}
	public void setEndInventoryVolume(Map<String, Double> endInventoryVolume) {
		this.endInventoryVolume = endInventoryVolume;
	}
	public Map<String, Double> getSalesValue() {
		return salesValue;
	}
	public void setSalesValue(Map<String, Double> salesValue) {
		this.salesValue = salesValue;
	}
	public Map<String, Double> getConsumptionValue() {
		return consumptionValue;
	}
	public void setConsumptionValue(Map<String, Double> consumptionValue) {
		this.consumptionValue = consumptionValue;
	}
	public Map<String, Double> getEndInventoryValue() {
		return endInventoryValue;
	}
	public void setEndInventoryValue(Map<String, Double> endInventoryValue) {
		this.endInventoryValue = endInventoryValue;
	}
	public Map<String, Double> getOtherConsumptionVolume() {
		return otherConsumptionVolume;
	}
	public void setOtherConsumptionVolume(Map<String, Double> otherConsumptionVolume) {
		this.otherConsumptionVolume = otherConsumptionVolume;
	}
	public Map<String, Double> getOtherConsumptionValue() {
		return otherConsumptionValue;
	}
	public void setOtherConsumptionValue(Map<String, Double> otherConsumptionValue) {
		this.otherConsumptionValue = otherConsumptionValue;
	}
	
}
