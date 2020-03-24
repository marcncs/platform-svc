package com.winsafe.hbm.util.cache;

/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ICache {
	public void add(Object key, Object value);

	public Object get(Object key);

	public void remove(Object key);

	public void removeAll();
}
