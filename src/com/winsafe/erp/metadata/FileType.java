package com.winsafe.erp.metadata;

public enum FileType {
	
	PRODUCT(1, "product"),
	DEALER(2, "dealer"),
	WAREHOUSE(3, "warehouse"), 
	BILL(4, "bill"),
	BILL_DETAIL(5, "bill_detail"),
	BILL_CARTONCODE(6, "bill_cartoncode"),
	BILL_PRIMARYCODE(7, "bill_primarycode"),
	INVENTORY(8, "inventory"),
	INVENTORY_CARTONCODE(9, "inventory_cartoncode"),
	INVENTORY_PRIMARYCODE(10, "inventory_primarycode"),
	INVENTORY_DETAIL(11, "inventory_detail"),
	CSSI_FILE(12, "CSSI_data");
	private Integer value;
	private String name;
	
	private FileType(Integer value, String name)
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
	public static FileType parseByName(String name) {
		for (FileType organType : FileType.values()) {
			if (organType.getName().equals(name)) {
				return organType;
			}
		}
		return null;
	}
	
	public static FileType parseByValue(Integer value) {
		for (FileType organType : FileType.values()) {
			if (organType.getValue().equals(value)) {
				return organType;
			}
		}
		return null;
	}
}
