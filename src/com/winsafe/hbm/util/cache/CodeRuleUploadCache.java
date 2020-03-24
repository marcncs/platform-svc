package com.winsafe.hbm.util.cache;

import java.util.List;

import org.apache.log4j.Logger;

import com.winsafe.hbm.util.cache.CacheManager;
import com.winsafe.drp.dao.AppCacheDao;
import com.winsafe.drp.dao.AppCodeRule;
import com.winsafe.drp.dao.CodeRuleUpload;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.cache.CacheBean;
import com.winsafe.drp.util.cache.CacheController;
import com.winsafe.drp.util.cache.ICache;

public class CodeRuleUploadCache {
	private Logger logger = Logger.getLogger(CodeRuleUploadCache.class);
	
	private AppCodeRule appcr = new AppCodeRule();
	private CacheController aCacheController=new CacheController();
	private CacheManager cm;
	private String CACHE_TAG = "coderuleuploadlist";
	
	public CodeRuleUploadCache(){
		cm = CacheManager.getInstance("CodeRuleUpload");
	}
	
	public List<CodeRuleUpload> getAllCodeRuleUpload(){
		
		return (List<CodeRuleUpload>)aCacheController.getBean(CodeRuleUploadCache.class.getName(), "", new ICache() {
			public List<CodeRuleUpload> getNew(Object id) {
				try {
					return appcr.getAllCodeRuleUploadList();
				} catch (Exception e) {
					logger.error("", e);
				}
				return null;
			}

			public boolean equals(Object id,CacheBean cacheBean){
				if(cacheBean==null ){
					return false;
				}else{
					List<CodeRuleUpload> object = (List<CodeRuleUpload>)cacheBean.getObject();
					if(object==null 
							|| (System.currentTimeMillis()-cacheBean.getLastTime()>Constants.CACHE_TIME)){
						return false;
					}
				}
				return true;
			}
		});
	}
	
	public CodeRuleUpload getCodeRuleUploadByCrp(int crproperty){	
		List<CodeRuleUpload> list = getAllCodeRuleUpload();
		if ( list == null ){
			return null;
		}
		for ( CodeRuleUpload d : list ){
			if ( d.getCruproperty().intValue() == crproperty){
				
				return d;
			}
		}
		return null;
	}
	
	
	public void putCodeRuleUploadList(List list){
		cm.put(CACHE_TAG, list);
	}
	
	public void removeCodeRuleUploadList(){
		cm.remove(CACHE_TAG);
	}
	
//	public List getAllCodeRuleUpload(){
//		
//		return (List)cm.get(CACHE_TAG);
//	}
	
	
	public void modifyCodeRuleUpload(List list){
		removeCodeRuleUploadList();
		putCodeRuleUploadList(list);
	}
}
