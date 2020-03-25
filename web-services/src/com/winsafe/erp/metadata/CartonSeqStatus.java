package com.winsafe.erp.metadata;

public enum CartonSeqStatus { 
	
	NOT_USED(0, "未使用"),
	LOCKED(1, "已锁定"),
	USED(2, "已使用"), 
	UNPACKED(3, "已拆包"), 
	ACTIVATING(4, "激活中");
	
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
