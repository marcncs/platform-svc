package com.winsafe.drp.metadata;

public enum ECertifyStatus {
	UNCERTIFIED(0 , "未认证"),	//未认证
	PENDING(1 , "待认证"), //待认证
	PASSED(2 , "认证通过"), //认证通过
	REJECT(3 , "认证失败") //认证被拒/失败
	;
	private Integer value;
	private String name;
	
	private ECertifyStatus(Integer value , String name) {
		this.value = value;
		this.name = name;
	}
	
	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public static ECertifyStatus parseByName(String name) {
		for (ECertifyStatus dealerType : ECertifyStatus.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static ECertifyStatus parseByValue(Integer value) {
		for (ECertifyStatus dealerType : ECertifyStatus.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static String getAllValueString() {
		StringBuffer allValue = new StringBuffer();
		for (ECertifyStatus userType : ECertifyStatus.values()) {
			allValue.append(",").append(userType.getValue());
		}
		return allValue.substring(1); 
	}
}
