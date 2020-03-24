package com.winsafe.drp.keyretailer.metadata;


public enum BonusType {
	
	DELIVER(1, "发货"),
	DELIVER_RECEIVE(2, "收货"),
	RETURN(3, "退货"),
	RETURN_RECEIVE(4, "退回"),
	ADJUST(5, "调整"),
	FINAL_BONUS(6, "完成率"),
	REBATE(7, "返利");
	
	private Integer value;
	private String name;
	
	private BonusType(Integer value, String name)
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
	public static BonusType parseByName(String name) {
		for (BonusType bonusType : BonusType.values()) {
			if (bonusType.getName().equals(name)) {
				return bonusType;
			}
		}
		return null;
	}
	public static BonusType parseByValue(Integer name) {
		for (BonusType bonusType : BonusType.values()) {
			if (bonusType.getValue().equals(name)) {
				return bonusType;
			}
		}
		return null;
	}
}
