package com.winsafe.drp.metadata;

import com.winsafe.sap.metadata.PrintStatus;

public enum AppType {
	
	ANDROID(0),
	IOS(1);
	
	private Integer dbValue;

	
	private AppType(Integer dbValue)
	{
		this.dbValue = dbValue;
	}

	public Integer getDbValue() {
		return dbValue;
	}
	
	/**
	 * Parse database value to enum Instance
	 * 
	 * @param dbValue
	 * @return 'null' when invalid dbValue is provided
	 */
	public static PrintStatus parse(Integer dbValue) {
		for (PrintStatus codeStatus : PrintStatus.values()) {
			if (codeStatus.getDatabaseValue().equals(dbValue)) {
				return codeStatus;
			}
		}
		return null;
	}
}
