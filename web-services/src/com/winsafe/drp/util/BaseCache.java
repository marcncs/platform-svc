package com.winsafe.drp.util;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.dao.BaseResource;
import com.winsafe.hbm.util.cache.CacheManager;

/**
 * 资源属性缓存类
 * @author jelli
 * 2006-5-8
 */
public class BaseCache {
	private CacheManager cm;
	private String CACHE_TAG = "baseresourcelist";
	
	public BaseCache(){
		cm = CacheManager.getInstance("BaseResource");
	}
	
	public BaseResource getBaseResource(String tagName, int key){		
		List<BaseResource> list = getAllBaseResource();
		if ( list == null ){
			return null;
		}
		for ( BaseResource br : list ){
			if ( br.getTagname().equalsIgnoreCase(tagName) && 
					br.getTagsubkey().intValue() == key ){
				
				return br;
			}
		}
		return null;
	}
	
	public List BaseResourceCache (String tagName){
		List<BaseResource> list = getAllBaseResource();
		if ( list == null ){
			return null;
		}
		List taglist = new ArrayList();
		for ( BaseResource br : list ){
			if ( br.getTagname().equalsIgnoreCase(tagName) ){
				
				taglist.add(br);
			}
		}
		if ( taglist.size() == 0 ){
			return null;
		}
	    return taglist;
	}
	
	public List getBaseResource(String tagName){
		List<BaseResource> list = getAllBaseResource();
		if ( list == null ){
			return null;
		}
		List taglist = new ArrayList();
		for ( BaseResource br : list ){
			if ( br.getTagname().equalsIgnoreCase(tagName) ){
				
				taglist.add(br);
			}
		}
		if ( taglist.size() == 0 ){
			return null;
		}
	    return taglist;
	}
	
	public List DeptCache(String tagName){
		List<BaseResource> list = getAllBaseResource();
		if ( list == null ){
			return null;
		}
		List taglist = new ArrayList();
		for ( BaseResource br : list ){
			if ( br.getTagname().equalsIgnoreCase(tagName) ){
				
				taglist.add(br);
			}
		}
		if ( taglist.size() == 0 ){
			return null;
		}
	    return taglist;
	}
	
	public List CurrencyRateCache (String tagName){
		List<BaseResource> list = getAllBaseResource();
		if ( list == null ){
			return null;
		}
		List taglist = new ArrayList();
		for ( BaseResource br : list ){
			if ( br.getTagname().equalsIgnoreCase(tagName) ){
				
				taglist.add(br);
			}
		}
		if ( taglist.size() == 0 ){
			return null;
		}
	    return taglist;
	}
	
	public List WarehouseCache (String tagName){
		List<BaseResource> list = getAllBaseResource();
		if ( list == null ){
			return null;
		}
		List taglist = new ArrayList();
		for ( BaseResource br : list ){
			if ( br.getTagname().equalsIgnoreCase(tagName) ){
				
				taglist.add(br);
			}
		}
		if ( taglist.size() == 0 ){
			return null;
		}
	    return taglist;
	}
	
	public List SysOSCache  (String tagName){
		List<BaseResource> list = getAllBaseResource();
		if ( list == null ){
			return null;
		}
		List taglist = new ArrayList();
		for ( BaseResource br : list ){
			if ( br.getTagname().equalsIgnoreCase(tagName) ){
				
				taglist.add(br);
			}
		}
		if ( taglist.size() == 0 ){
			return null;
		}
	    return taglist;
	}
	
	
	public List getIsuseBaseResource(String tagName){
		List<BaseResource> list = getAllBaseResource();
		if ( list == null ){
			return null;
		}
		List taglist = new ArrayList();
		for ( BaseResource br : list ){
			if ( br.getTagname().equalsIgnoreCase(tagName) && br.getIsuse().intValue() == 1 ){
				
				taglist.add(br);
			}
		}
		if ( taglist.size() == 0 ){
			return null;
		}
	    return taglist;
	}
	
	public void putBaseResourcetList(List list){
		cm.put(CACHE_TAG, list);
	}
	
	public void removeBaseResourceList(){
		cm.remove(CACHE_TAG);
	}
	
	public List getAllBaseResource(){
		return (List)cm.get(CACHE_TAG);
	}
	
	public void modifyBaseResource(List list){

		removeBaseResourceList();

		putBaseResourcetList(list);
	}
}
