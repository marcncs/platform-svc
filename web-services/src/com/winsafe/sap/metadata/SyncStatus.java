package com.winsafe.sap.metadata;

public enum SyncStatus { 
	
	NOT_UPLOADED(0, "未同步"),
	UPLOADED(1, "已上传"),
	POST_BACK(2, "已回传"), 
	UPLOAD_ERROR(3, "上传失败"),
	NODEED(-1, "无需同步");
	
	private Integer value;
	private String name;

	
	private SyncStatus(Integer databaseValue, String displayName)
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
	public static SyncStatus parse(Integer databaseValue) {
		for (SyncStatus codeStatus : SyncStatus.values()) {
			if (codeStatus.getValue().equals(databaseValue)) {
				return codeStatus;
			}
		}
		return null;
	}
	
}
