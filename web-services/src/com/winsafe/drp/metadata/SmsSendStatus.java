package com.winsafe.drp.metadata;

import com.winsafe.sap.metadata.PrintStatus;

public enum SmsSendStatus {
	//未发送
	NOT_SEND(0),
	//发送成功
	SUCCESS(1),
	//发送失败
	FAILED(2);
	
	private Integer dbValue;

	
	private SmsSendStatus(Integer dbValue)
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
