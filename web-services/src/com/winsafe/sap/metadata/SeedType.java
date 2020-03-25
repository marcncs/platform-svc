package com.winsafe.sap.metadata;

public enum SeedType {
	
	GENERAL(1, "普通码"),
	COMMON(2, "通用码"), 
	TOLLER(3, "分装厂");
	
	private Integer value;
	private String name;
	
	private SeedType(Integer value, String name)
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
	public static SeedType parseByName(String name) {
		for (SeedType organType : SeedType.values()) {
			if (organType.getName().equals(name)) {
				return organType;
			}
		}
		return null;
	}
	
	public static SeedType parseByValue(Integer value) {
		for (SeedType organType : SeedType.values()) {
			if (organType.getValue().equals(value)) {
				return organType;
			}
		}
		return null;
	}
}
