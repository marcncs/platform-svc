package com.winsafe.sap.metadata;

public enum SapUploadLogStatus {
	//未处理
	NOT_PROCESS((int) 0, "未处理"),
	//正在处理
	PROCESSING((int) 1, "正在处理"),
	//正确处理
	PROCESS_SUCCESS((int) 2, "正确处理"),
	//处理错误
	PROCESS_FAIL((int) 3, "处理错误");
	
	private int databaseValue;
	private String displayName;
	
	private SapUploadLogStatus(int databaseValue, String displayName)
	{
		this.databaseValue = databaseValue;
		this.displayName = displayName;
	}

	public int getDatabaseValue() {
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
	public static SapUploadLogStatus parse(int databaseValue) {
		for (SapUploadLogStatus sapUploadLogStatus : SapUploadLogStatus.values()) {
			if (sapUploadLogStatus.getDatabaseValue() == databaseValue) {
				return sapUploadLogStatus;
			}
		}
		return null;
	}


}
