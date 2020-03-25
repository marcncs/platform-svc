package com.winsafe.drp.metadata;

public enum UserType {
	
	RM(1, "RM"), 
	ASM(2, "ASM"),
	SS(3, "SS"),
	SR(4, "SR"),
	CM(5, "CM"); 
	private Integer value;
	private String name;
	
	private UserType(Integer value, String name)
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
	public static UserType parseByName(String name) {
		for (UserType dealerType : UserType.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static UserType parseByValue(Integer value) {
		for (UserType dealerType : UserType.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static String getAllValueString() {
		StringBuffer allValue = new StringBuffer();
		for (UserType userType : UserType.values()) {
			allValue.append(",").append(userType.getValue());
		}
		return allValue.substring(1); 
	}
}
