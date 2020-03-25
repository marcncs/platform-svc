package com.winsafe.erp.metadata;

public enum QrStatus { 
	
	NOT_GENERATED(0, "未生成"),
	GENERATING(1, "生成中"),
	GENERATED(2, "已生成"), 
	GENERATE_ERROR(3, "生成失败");
	
	private Integer value;
	private String name;

	
	private QrStatus(Integer databaseValue, String displayName)
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
	public static QrStatus parse(Integer databaseValue) {
		for (QrStatus codeStatus : QrStatus.values()) {
			if (codeStatus.getValue().equals(databaseValue)) {
				return codeStatus;
			}
		}
		return null;
	}
	
}
