package com.winsafe.drp.util.cache;

public interface ICache { 
	public abstract Object getNew(Object id);
	public abstract boolean equals(Object id,CacheBean cacheBean);
}
