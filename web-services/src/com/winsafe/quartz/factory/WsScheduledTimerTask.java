package com.winsafe.quartz.factory;

import java.util.HashMap;
import java.util.Map;


public class WsScheduledTimerTask{
	
	/** 注册可使用的任务*/
	private Map<String,String> regTaskHandler = new HashMap<String, String>();

	public Map<String, String> getRegTaskHandler() {
		return regTaskHandler;
	}

	public void setRegTaskHandler(Map<String, String> regTaskHandler) {
		this.regTaskHandler = regTaskHandler;
	}
 

}
