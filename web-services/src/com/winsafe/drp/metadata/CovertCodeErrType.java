package com.winsafe.drp.metadata;

import com.winsafe.sap.metadata.PrintStatus;

public enum CovertCodeErrType {
	DUPLICATE_COVERT_CODE(-5),
	PRIMARY_CODE_NOT_EXISTS(-6),
	PRIMARY_CODE_ALREADY_HAS_COVERT_CODE(-7),
	ALREADY_UPDATED_SAME(-8),
	CORRECT(0);
	
	private Integer dbValue;

	
	private CovertCodeErrType(Integer dbValue)
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
