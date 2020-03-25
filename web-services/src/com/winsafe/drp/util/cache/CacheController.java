package com.winsafe.drp.util.cache;

import java.util.Hashtable;

public class CacheController {
	/**缓存时间（毫秒） */
	public static final long CACHE_TIME=60000;
	private Hashtable<Object,CacheBean> CacheTable=new Hashtable<Object,CacheBean>(); 
	
	/**
	 * 获取对象
	 * 如果缓存对象可以就使用缓存对象，否则获取新对象并对它做缓存
	 * Create Time Oct 12, 2012 12:02:24 PM 
	 * @param clazz 用于获取缓存对象
	 * @param id 对象主键，用于比较两个对象是否相等
	 * @param cache 获取新对象和比较对象是否相等的方法
	 * @return
	 * @author huangxy 
	 */
	public Object getBean(String key,Object id,ICache cache){
		CacheBean cacheBean=null;
		cacheBean=CacheTable.get(key);

		if(!cache.equals(id,cacheBean)){
			return setCache(key,cache,id);
		}

		return cacheBean.getObject();
	}
	
	/**
	 * 设置缓存
	 * 清除就对象，获取新对象做缓存
	 * Create Time Oct 12, 2012 12:05:40 PM 
	 * @param clazz
	 * @param cache
	 * @param id
	 * @return
	 * @author huangxy
	 */
	public Object setCache(String key,ICache cache,Object id){
		CacheTable.remove(key);

		Object object=cache.getNew(id);
		if(object!=null){
			CacheBean cacheBean = new CacheBean(key,object);
			CacheTable.put(key, cacheBean);
		}
		return object;
	}
	
	public void clearCache(String key){
		CacheTable.remove(key);
	}
}
