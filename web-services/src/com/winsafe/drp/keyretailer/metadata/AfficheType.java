package com.winsafe.drp.keyretailer.metadata;

public enum AfficheType {
	
	OTHERS(0, "其它"),
	VALIDATE(1, "拜验证"),
	TRACE(2, "拜追溯"),
	SYSTEM(3, "系统公告"),
	BONUS(4, "积分"),
	KEYBKDBKR(5, "客户信息系统"),
	AUDIT(6, "审核"), 
	BONUS_CONFIRM(7, "积分确认");
	
	private Integer value;
	private String name;
	
	private AfficheType(Integer value, String name)
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
	public static AfficheType parseByName(String name) {
		for (AfficheType bonusType : AfficheType.values()) {
			if (bonusType.getName().equals(name)) {
				return bonusType;
			}
		}
		return null;
	}
	public static AfficheType parseByValue(Integer name) {
		for (AfficheType bonusType : AfficheType.values()) {
			if (bonusType.getValue().equals(name)) {
				return bonusType;
			}
		}
		return null;
	}
}
