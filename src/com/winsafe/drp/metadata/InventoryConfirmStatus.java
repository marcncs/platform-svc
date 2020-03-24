package com.winsafe.drp.metadata;

public enum InventoryConfirmStatus {
	
	NOT_AUDITED(0, "未审核"),
	APPROVED(1, "审核通过"),
	DISAPPROVE(2, "审核未通过");
	
	private Integer value;
	private String name;
	
	private InventoryConfirmStatus(Integer value, String name)
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
	public static InventoryConfirmStatus parseByName(String name) {
		for (InventoryConfirmStatus dealerType : InventoryConfirmStatus.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}  
		return null;
	}
	
	public static InventoryConfirmStatus parseByValue(Integer value) {
		for (InventoryConfirmStatus dealerType : InventoryConfirmStatus.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
}
