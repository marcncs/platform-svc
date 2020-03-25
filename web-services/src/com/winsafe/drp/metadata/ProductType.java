package com.winsafe.drp.metadata;

public enum ProductType {
	HZ(1, "杭州工厂"), 
	TOLLING(2, "分装厂"),
	ES(3, "ES");
	private Integer value;
	private String name; 
	
	private ProductType(Integer value, String name)
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
	public static ProductType parseByName(String name) {
		for (ProductType dealerType : ProductType.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static ProductType parseByValue(Integer value) {
		for (ProductType dealerType : ProductType.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static String getAllValueString() {
		StringBuffer allValue = new StringBuffer();
		for (ProductType userType : ProductType.values()) {
			allValue.append(",").append(userType.getValue());
		}
		return allValue.substring(1); 
	}
}
