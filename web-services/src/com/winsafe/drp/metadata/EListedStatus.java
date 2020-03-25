package com.winsafe.drp.metadata;

public enum EListedStatus {

	UNLISTED(0 , "未上架"),	//未上架
	LISTED(1 , "上架"),	//上架
	DELIST(2 , "下架")	//下架
	;
	private Integer value;
	private String name;
	
	private EListedStatus(Integer value , String name) {
		this.value = value;
		this.name = name;
	}
	
	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public static EListedStatus parseByName(String name) {
		for (EListedStatus dealerType : EListedStatus.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static EListedStatus parseByValue(Integer value) {
		for (EListedStatus dealerType : EListedStatus.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static String getAllValueString() {
		StringBuffer allValue = new StringBuffer();
		for (EListedStatus userType : EListedStatus.values()) {
			allValue.append(",").append(userType.getValue());
		}
		return allValue.substring(1); 
	}
}
