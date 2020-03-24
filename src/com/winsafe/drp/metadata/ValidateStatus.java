package com.winsafe.drp.metadata;

public enum ValidateStatus {
	
	NOT_AUDITED(0, "未审核"),
	PASSED(1, "已通过审核"),
	NOT_PASSED(2, "审核未通过");
	
	private Integer value; 
	private String name;
	
	private ValidateStatus(Integer value, String name)
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
	public static ValidateStatus parseByName(String name) {
		for (ValidateStatus dealerType : ValidateStatus.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static ValidateStatus parseByValue(Integer value) {
		for (ValidateStatus dealerType : ValidateStatus.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
}
