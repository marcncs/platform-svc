package com.winsafe.quartz.task.type;


public interface TaskEnum {
	
    /** 启动*/
    Integer START = 0;
    
    /** 挂起*/
    Integer STOP = 1;
 
	
	//未使用
	enum TaskType{
		
		SMSReceiveTask("SMSReceiveTask", "com.winsafe.framework.quartz.task.SMSReceiveTask");
		
		private String name;
		private String value;
		
		private TaskType(String name,String value){
			this.name = name;
			this.value = value;
		}
		
		public static String getDisplayName(String value) {
			for (TaskType v : TaskType.values()) {
				if (v.value.equals(value)) {
					return v.name;
				}
			}
			return "";
		}
		
		public static String getValue(String name){
			for (TaskType v : TaskType.values()) {
				if (v.name.equals(name)) {
					return v.value;
				}
			}
			return "";
		}
		
	}

}
