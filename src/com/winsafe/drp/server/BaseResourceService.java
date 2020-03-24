package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.hbm.util.cache.BaseResourceCache;

public class BaseResourceService {
	private AppBaseResource appbr = new AppBaseResource();
	private BaseResourceCache cache = new BaseResourceCache();
	
	public List getBaseResource(String tagName)throws Exception{
	    List list = cache.getBaseResource(tagName);
	    if ( list == null ){
	    	list = appbr.getBaseResource(tagName);
	    	modifyCache();
	    }
		return list;
	  }
	
	public List getBaseResourceIsUse(String tagName)throws Exception{
		List list = cache.getIsuseBaseResource(tagName);
	    if ( list == null ){
	    	list = appbr.getIsUseBaseResource(tagName);
	    	modifyCache();
	    }
		return list;
	}
	
	public List getAllBaseResource()throws Exception{
		List list = cache.getAllBaseResource();
	    if ( list == null ){
	    	list = appbr.getAllBaseResource();
	    	
	    }
		return list;
	}
	
	public BaseResource getBaseResourceValue(String tagName, int key)throws Exception{
		BaseResource br = cache.getBaseResource(tagName, key);
		if ( br == null ){
			br = appbr.getBaseResourceValue(tagName, key);
			modifyCache();
		}
		return br;
	}
	
	public String getMaxBaseResourceByTagName(String tagname) throws Exception {
		return appbr.getMaxBaseResourceByTagName(tagname);
	}
	
	public void addBaseResource(BaseResource br)throws Exception{
		appbr.addBaseResource(br);
		modifyCache();
	}
	
//	public BaseResource getBaseResourceByTagNameTagSubKey(String tagname,Integer tagsubkey) throws Exception {
//		return appbr.getBaseResourceByTagNameTagSubKey(tagname, tagsubkey);
//	}
	
	public void updBaseResource(BaseResource br) throws Exception {
		appbr.updBaseResource(br);
		modifyCache();
	}
	

	private void modifyCache() throws Exception {
		// cache.modifyBaseResource(appbr.getAllBaseResource());
	}
	
	public String getBaseResourceName(String tagName, int key)throws Exception{
		BaseResource br = getBaseResourceValue(tagName, key);
		if ( br != null ){
			return br.getTagsubvalue();
		}
		return "";
	}
}
