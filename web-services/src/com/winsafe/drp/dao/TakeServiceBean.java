package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TakeServiceBean {

	private TakeBill takebill;
	private List<String> warehouseids = new ArrayList<String>();
	private Map<String, TakeTicket> ttmap = new HashMap<String, TakeTicket>();
	
	public Map<String, TakeTicket> getTtmap() {
		return ttmap;
	}
	public void setTtmap(Map<String, TakeTicket> ttmap) {
		this.ttmap = ttmap;
	}
	public List<String> getWarehouseids() {
		return warehouseids;
	}
	public void setWarehouseids(List<String> warehouseids) {
		this.warehouseids = warehouseids;
	}
	public TakeBill getTakebill() {
		return takebill;
	}
	public void setTakebill(TakeBill takebill) {
		this.takebill = takebill;
	}
	
	
	
}
