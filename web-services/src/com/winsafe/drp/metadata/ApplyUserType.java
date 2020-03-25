package com.winsafe.drp.metadata;

public enum ApplyUserType {
	TD(1, "TD用户"),
	WH(2, "仓库用户"),
	BKD(3, "BKD"),
	BKR(4, "BKR");
	private Integer value;
	private String name; 
	
	private ApplyUserType(Integer value, String name)
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
	public static ApplyUserType parseByName(String name) {
		for (ApplyUserType dealerType : ApplyUserType.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static ApplyUserType parseByValue(Integer value) {
		for (ApplyUserType dealerType : ApplyUserType.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static String getAllValueString() {
		StringBuffer allValue = new StringBuffer();
		for (ApplyUserType userType : ApplyUserType.values()) {
			allValue.append(",").append(userType.getValue());
		}
		return allValue.substring(1); 
	}
}
