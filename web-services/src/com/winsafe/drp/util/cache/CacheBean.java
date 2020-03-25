package com.winsafe.drp.util.cache;

public class CacheBean { 
	private Object key;
	private Object object;
	private long lastTime;
	
	public CacheBean(Object object,long lastTime){
		this.object=object; 
		this.lastTime=lastTime;
	}
	public CacheBean(Object object){ 
		this.object=object;
		this.lastTime=System.currentTimeMillis();
	}
	public CacheBean(Object key,Object object){
		this.key=key;
		this.object=object;
		this.lastTime=System.currentTimeMillis();
	}
	public CacheBean(){
	}
	
	public Object getObject() {
		return object;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
}
