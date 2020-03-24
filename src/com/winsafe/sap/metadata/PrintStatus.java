package com.winsafe.sap.metadata;

public enum PrintStatus {
	
	NOT_PRINTED(0, "未打印"),
	PRINTING(1, "打印中"),
	PRINTED(2, "已打印"),
	CONFIRMED(3, "已确认");
	
	private Integer databaseValue;
	private String displayName;

	
	private PrintStatus(Integer databaseValue, String displayName)
	{
		this.databaseValue = databaseValue;
		this.displayName = displayName;
	}

	public Integer getDatabaseValue() {
		return databaseValue;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Parse database value to enum Instance
	 * 
	 * @param databaseValue
	 * @return 'null' when invalid databaseValue is provided
	 */
	public static PrintStatus parse(Integer databaseValue) {
		for (PrintStatus codeStatus : PrintStatus.values()) {
			if (codeStatus.getDatabaseValue().equals(databaseValue)) {
				return codeStatus;
			}
		}
		return null;
	}
	
}
