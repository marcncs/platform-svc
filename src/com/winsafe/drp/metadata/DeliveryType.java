package com.winsafe.drp.metadata;

public enum DeliveryType {
	
	NOMAL(0, "正常发货"), 
	BONUS(1, "积分兑换");
	
	private Integer value; 
	private String name;
	
	private DeliveryType(Integer value, String name)
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
	public static DeliveryType parseByName(String name) {
		for (DeliveryType dealerType : DeliveryType.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static DeliveryType parseByValue(Integer value) {
		for (DeliveryType dealerType : DeliveryType.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
}
