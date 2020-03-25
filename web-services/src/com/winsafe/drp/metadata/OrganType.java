package com.winsafe.drp.metadata;

public enum OrganType {
	
	Plant(1, "工厂"),
	Dealer(2, "经销商");
	
	private Integer value;
	private String name;
	
	private OrganType(Integer value, String name)
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
	public static OrganType parseByName(String name) {
		for (OrganType organType : OrganType.values()) {
			if (organType.getName().equals(name)) {
				return organType;
			}
		}
		return null;
	}
}
