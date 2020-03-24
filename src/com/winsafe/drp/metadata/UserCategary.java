package com.winsafe.drp.metadata;

public enum UserCategary {
	RTCI(1, "RTCI用户"), 
	TRACE(2, "拜追踪用户"), 
	RI(3, "拜耳客户信息系统用户");
	private Integer value;
	private String name;
	
	private UserCategary(Integer value, String name)
	{
		this.value = value;
		this.name = name;
	}
	
	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	/**
	 * Parse database value to enum Instance
	 * 
	 * @param dbValue
	 * @return 'null' when invalid dbValue is provided
	 */
	public static UserCategary parseByName(String name) {
		for (UserCategary dealerType : UserCategary.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static UserCategary parseByValue(Integer value) {
		for (UserCategary dealerType : UserCategary.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static String getAllValueString() {
		StringBuffer allValue = new StringBuffer();
		for (UserCategary userType : UserCategary.values()) {
			allValue.append(",").append(userType.getValue());
		}
		return allValue.substring(1); 
	}
}
