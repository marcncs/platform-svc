package com.winsafe.sap.metadata;

public enum PrimaryCodeStatus {
	//还未生成小包装码
	NOT_GENERATED(0, "未生成"),
	//已经生成小包装码
	GENERATED(1, "已生成"),
	GENERATED_ERROR(2,"生成出错"),
	GENERATING(3, "生成中"),
	//不需要生成小包装码
	NOT_REQUIRED(-1, "不需要生成");
	
	private Integer databaseValue;
	private String displayName;

	
	private PrimaryCodeStatus(Integer databaseValue, String displayName)
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
	public static PrimaryCodeStatus parse(Integer databaseValue) {
		for (PrimaryCodeStatus codeStatus : PrimaryCodeStatus.values()) {
			if (codeStatus.getDatabaseValue().equals(databaseValue)) {
				return codeStatus;
			}
		}
		return null;
	}
	
}
