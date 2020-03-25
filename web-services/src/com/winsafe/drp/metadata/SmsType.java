package com.winsafe.drp.metadata;

import com.winsafe.sap.metadata.PrintStatus;

public enum SmsType {
	//物流信息
	GOODS_DELIVERT(0),
	//验证信息
	VALIDATE(1), 
	//验证码信息
	INDENTIFY_CODE(2),
	//新用户审批完成信息
	NEW_USER_APPROVE(3); 
	
	private Integer dbValue;

	
	private SmsType(Integer dbValue)
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
