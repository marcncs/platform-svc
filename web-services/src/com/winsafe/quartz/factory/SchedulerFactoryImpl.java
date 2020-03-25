 
package com.winsafe.quartz.factory;

import org.quartz.impl.StdSchedulerFactory;

/**
 * Project:aladdinMMS->Class:SchedulerFactoryImpl.java
 * <p style="font-size:16px;">Description :任务工厂</p>
 * Create Time 2012-3-21 下午04:20:37 
 * @author <a href='fuming.zhang@winsafe.cn'>zhangfuming</a>
 * @version 0.8
 */
public class SchedulerFactoryImpl extends StdSchedulerFactory {
	
	public Integer id;

	public SchedulerFactoryImpl(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
