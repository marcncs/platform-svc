package com.winsafe.drp.metadata;

public enum LinkMode {
	
	Before(1, "前关联"),
	After(2, "后关联");
	
	private Integer value; 
	private String name;
	
	private LinkMode(Integer value, String name)
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
	public static LinkMode parseByName(String name) {
		for (LinkMode plantType : LinkMode.values()) {
			if (plantType.getName().equals(name)) {
				return plantType;
			}
		}
		return null;
	}
	
	public static LinkMode parseByValue(Integer value) {
		for (LinkMode plantType : LinkMode.values()) {
			if (plantType.getValue().equals(value)) {
				return plantType;
			}
		}
		return null;
	}
}
