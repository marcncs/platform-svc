package com.winsafe.sap.metadata;

public enum CommonCodeStatus {
	
	NOT_GENERATED(0, "未生成"),
	GENERATING(1, "生成中"),
	GENERATED(2, "已生成"),
	GENERATE_ERROR(3, "生成出错");
	
	private Integer databaseValue;
	private String displayName;

	
	private CommonCodeStatus(Integer databaseValue, String displayName)
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
	public static CommonCodeStatus parse(Integer databaseValue) {
		for (CommonCodeStatus codeStatus : CommonCodeStatus.values()) {
			if (codeStatus.getDatabaseValue().equals(databaseValue)) {
				return codeStatus;
			}
		}
		return null;
	}
	
}
