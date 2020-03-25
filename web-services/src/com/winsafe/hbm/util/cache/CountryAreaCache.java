package com.winsafe.hbm.util.cache;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.hbm.util.cache.CacheManager;
import com.winsafe.drp.dao.CountryArea;


public class CountryAreaCache {
	private CacheManager cm;
	private String CACHE_TAG = "countryarealist";
	
	public CountryAreaCache(){
		cm = CacheManager.getInstance("CountryArea");
	}
	
	public CountryArea getCountryAreaById(int id){	
		List<CountryArea> list = getAllCountryArea();
		if ( list == null ){
			return null;
		}
		for ( CountryArea d : list ){
			if ( d.getId().intValue() == id ){
				return d;
			}
		}
		return null;
	}
	
	public List getCountryAreaByParentid(int parentid){
		List<CountryArea> list = getAllCountryArea();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( CountryArea d : list ){
			if ( d.getParentid().intValue() == parentid ){	
				downOrgna.add(d);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	
	
	public void putCountryAreaList(List list){
		cm.put(CACHE_TAG, list);
	}
	
	public void removeCountryAreaList(){
		cm.remove(CACHE_TAG);
	}
	
	public List getAllCountryArea(){
		return (List)cm.get(CACHE_TAG);
	}
	
	
	public void modifyCountryArea(List list){
		removeCountryAreaList();
		putCountryAreaList(list);
	}
}
