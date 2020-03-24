package com.winsafe.erp.metadata;

public enum TransferFileStatus {
	
	NOT_TRANSFERED(1, "未传输"),
	TRANSFERED(2, "已传输"), 
	TRANSFER_ERROR(3, "传输失败");
	
	private Integer value;
	private String name;
	
	private TransferFileStatus(Integer value, String name)
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
	public static TransferFileStatus parseByName(String name) {
		for (TransferFileStatus organType : TransferFileStatus.values()) {
			if (organType.getName().equals(name)) {
				return organType;
			}
		}
		return null;
	}
	
	public static TransferFileStatus parseByValue(Integer value) {
		for (TransferFileStatus organType : TransferFileStatus.values()) {
			if (organType.getValue().equals(value)) {
				return organType;
			}
		}
		return null;
	}
}
