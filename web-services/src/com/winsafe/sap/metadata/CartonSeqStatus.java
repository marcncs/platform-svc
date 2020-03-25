package com.winsafe.sap.metadata;

public enum CartonSeqStatus { 
	
	NOT_RELATED(0, "未关联"),
	RELATED(1, "已关联"), 
	NO_DEED(-1, "无需关联");
	
	private Integer value;
	private String name;

	
	private CartonSeqStatus(Integer databaseValue, String displayName)
	{
		this.value = databaseValue;
		this.name = displayName;
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
	 * @param databaseValue
	 * @return 'null' when invalid databaseValue is provided
	 */
	public static CartonSeqStatus parse(Integer databaseValue) {
		for (CartonSeqStatus codeStatus : CartonSeqStatus.values()) {
			if (codeStatus.getValue().equals(databaseValue)) {
				return codeStatus;
			}
		}
		return null;
	}
	
}
