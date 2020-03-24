package com.winsafe.erp.metadata;

public enum ProduceFileType {
	
	HZ_PLANT(1, "杭州工厂码文件"),
	TOLLING(2, "分装厂文件"),
	TOLLING_REPLACE(3, "分装厂码替换文件");
	private Integer value; 
	private String name;
	
	private ProduceFileType(Integer value, String name)
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
	public static ProduceFileType parseByName(String name) {
		for (ProduceFileType organType : ProduceFileType.values()) {
			if (organType.getName().equals(name)) {
				return organType;
			}
		}
		return null;
	}
	
	public static ProduceFileType parseByValue(Integer value) {
		for (ProduceFileType organType : ProduceFileType.values()) {
			if (organType.getValue().equals(value)) {
				return organType;
			}
		}
		return null;
	}
}
