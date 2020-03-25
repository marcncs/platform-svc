package com.winsafe.hbm.util.cache;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.hbm.util.cache.CacheManager;
import com.winsafe.drp.dao.Warehouse;


public class WarehouseCache {
	private CacheManager cm;
	private String CACHE_TAG = "warehouselist";
	
	public WarehouseCache(){
		cm = CacheManager.getInstance("Warehouse");
	}
	
	public Warehouse getWarehouse(String id){	
		List<Warehouse> list = getAllWarehouse();
		if ( list == null ){
			return null;
		}
		for ( Warehouse d : list ){
			if ( d.getId().equals(id) ){

				return d;
			}
		}
		return null;
	}
	
	public List getCanUseWarehouse(){
		List<Warehouse> list = getAllWarehouse();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( Warehouse d : list ){
			if ( d.getUseflag() == 1  ){	
				downOrgna.add(d);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	public List getCanUseWarehouseByOid(String oid){
		List<Warehouse> list = getAllWarehouse();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( Warehouse d : list ){
			if ( d.getUseflag() == 1 && d.getMakeorganid().equals(oid) ){	
				downOrgna.add(d);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	public List getWarehouseListByOID(String oid){
		List<Warehouse> list = getAllWarehouse();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( Warehouse d : list ){
			if ( d.getMakeorganid().equalsIgnoreCase(oid)  ){	
				downOrgna.add(d);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	public Warehouse getWarehouseByOID(String oid){
		List<Warehouse> list = getAllWarehouse();
		if ( list == null ){
			return null;
		}
		Warehouse nw = null;
		for ( Warehouse d : list ){
			if ( d.getMakeorganid().equalsIgnoreCase(oid)  ){	
				nw = d;
				break;
			}			
		}		
		return nw;
	}
	
	
	
	public void putWarehouseList(List list){
		cm.put(CACHE_TAG, list);
	}
	
	public void removeWarehouseList(){
		cm.remove(CACHE_TAG);
	}
	
	public List getAllWarehouse(){

		return (List)cm.get(CACHE_TAG);
	}
	
	
	public void modifyWarehouse(List list){
		removeWarehouseList();
		putWarehouseList(list);
	}
}
