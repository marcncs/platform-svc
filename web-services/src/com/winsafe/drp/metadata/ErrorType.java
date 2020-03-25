package com.winsafe.drp.metadata;

public enum ErrorType { 
	
	WARNING(1, "警告"), 
	SUCCESS(2, "成功"),
	FAIL(3, "失败");
	
	private Integer value;
	private String name;
	
	private ErrorType(Integer value, String name)
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
	public static ErrorType parseByName(String name) {
		for (ErrorType dealerType : ErrorType.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
}
