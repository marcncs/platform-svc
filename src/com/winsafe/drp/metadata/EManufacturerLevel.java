package com.winsafe.drp.metadata;

public enum EManufacturerLevel {

	FREE(0 , "免费企业"),	//免费企业
	CHARGING(1 , "收费企业"),	//收费企业
	;
	private Integer value;
	private String name;
	
	private EManufacturerLevel(Integer value , String name) {
		this.value = value;
		this.name = name;
	}
	
	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public static EManufacturerLevel parseByName(String name) {
		for (EManufacturerLevel dealerType : EManufacturerLevel.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static EManufacturerLevel parseByValue(Integer value) {
		for (EManufacturerLevel dealerType : EManufacturerLevel.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static String getAllValueString() {
		StringBuffer allValue = new StringBuffer();
		for (EManufacturerLevel userType : EManufacturerLevel.values()) {
			allValue.append(",").append(userType.getValue());
		}
		return allValue.substring(1); 
	}
}
