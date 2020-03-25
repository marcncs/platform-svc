package com.winsafe.drp.keyretailer.metadata;


public enum BonusStatus {
	
	NOT_COUNTTED(0, "未统计"),
	DELIVER_FAILED(-1, "出库统计失败"),
	DELIVER(1, "出货统计完成"),
	RECEIVE_FAILED(-2, "收货统计失败"),
	COMPLETED(2, "统计完成");
	
	private Integer value;
	private String name;
	
	private BonusStatus(Integer value, String name)
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
	public static BonusStatus parseByName(String name) {
		for (BonusStatus bonusType : BonusStatus.values()) {
			if (bonusType.getName().equals(name)) {
				return bonusType;
			}
		}
		return null;
	}
	public static BonusStatus parseByValue(Integer name) {
		for (BonusStatus bonusType : BonusStatus.values()) {
			if (bonusType.getValue().equals(name)) {
				return bonusType;
			}
		}
		return null;
	}
}
