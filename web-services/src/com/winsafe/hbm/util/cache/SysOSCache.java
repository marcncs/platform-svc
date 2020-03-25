package com.winsafe.hbm.util.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winsafe.hbm.util.cache.ICache;
import com.winsafe.hbm.util.cache.SysOSCache;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;


public class SysOSCache implements ICache {

	private static final Log logger = LogFactory.getLog(SysOSCache.class);

	private String keyPrefix;
	
	private GeneralCacheAdministrator admin;

	public SysOSCache(String keyPrefix) {
		this.keyPrefix = keyPrefix;
		this.admin = new GeneralCacheAdministrator();
	}

	public void add(Object key, Object value) {
		this.admin.putInCache(this.keyPrefix + "_" + key, value);
	}


	public Object get(Object key) {
		try {
			return null;
		} catch (Exception e) {
			this.admin.cancelUpdate(this.keyPrefix + "_" + key);
			return null;
		}
	}

	public void remove(Object key) {
		this.admin.flushEntry(this.keyPrefix + "_" + key);
	}

	public void removeAll() {
		this.admin.flushAll();
	}

}
