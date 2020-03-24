package com.winsafe.drp.metadata;

public enum DealerType {
	
	PD(1, "TD"),
	BKD(2, "BKD"),
	BKR(3, "BKR"),
	CO_OP(4, "合作社"),
	LARGE_FARMER(5, "大农户"),
	OTHER(6, "其他"),;
	
	private Integer value;
	private String name;
	
	private DealerType(Integer value, String name)
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
	public static DealerType parseByName(String name) {
		for (DealerType dealerType : DealerType.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static DealerType parseByValue(Integer value) {
		for (DealerType dealerType : DealerType.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
}
