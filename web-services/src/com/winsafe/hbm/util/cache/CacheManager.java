package com.winsafe.hbm.util.cache;

import com.winsafe.hbm.util.cache.CacheManager;
import com.winsafe.hbm.util.cache.ICache;
import com.winsafe.hbm.util.cache.SysOSCache;

/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CacheManager {
	private ICache cache;
	private static CacheManager instance;
	private static Object lock = new Object();

	public CacheManager(String prefix) {
		cache = new SysOSCache(prefix);
	}

	public static CacheManager getInstance(String prefix) {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new CacheManager(prefix);
				}
			}
		}
		return instance;
	}

	public void put(Object key, Object value) {
		cache.add(key, value);
	}

	public void  remove(Object key) {
		cache.remove(key);
	}

	public Object get(Object key) {
		return  cache.get(key);
	}

	public void removeAll() {
		cache.removeAll();
	}
}
