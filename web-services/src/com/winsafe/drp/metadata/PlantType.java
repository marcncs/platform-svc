package com.winsafe.drp.metadata;

public enum PlantType {
	
	Hangzhou(1, "杭州工厂"),
	Toller(2, "分装厂"),
	Basement(3, "地库");
	
	private Integer value;
	private String name;
	
	private PlantType(Integer value, String name)
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
	public static PlantType parseByName(String name) {
		for (PlantType plantType : PlantType.values()) {
			if (plantType.getName().equals(name)) {
				return plantType;
			}
		}
		return null;
	}
	
	public static PlantType parseByValue(Integer value) {
		for (PlantType plantType : PlantType.values()) {
			if (plantType.getValue().equals(value)) {
				return plantType;
			}
		}
		return null;
	}
}
