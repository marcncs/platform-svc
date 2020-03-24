package com.winsafe.drp.metadata;

public enum StockMoveConfirmStatus {
	
	NOT_AUDITED(0, "未审批"),
	OUT_ASM_APPROVED(4, "转出方ASM审批通过"),
	OUT_ASM_DISAPPROVE(-4, "转出方ASM审批未通过"),
	CHANNEL_MANAGER_APPROVED(2, "渠道经理审批通过"),
	CHANNEL_MANAGER_DISAPPROVED(-2, "渠道经理审批未通过"),
	IN_ASM_APPROVED(3, "转入方ASM审批通过"),
	IN_ASM_DISAPPROVED(-3, "转入方ASM审批未通过"),
	CHANNEL_APPROVED(1, "已审批"),
	CHANNEL_DISAPPROVED(-1, "审批不通过");
	
	private Integer value;
	private String name;
	
	private StockMoveConfirmStatus(Integer value, String name)
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
	public static StockMoveConfirmStatus parseByName(String name) {
		for (StockMoveConfirmStatus dealerType : StockMoveConfirmStatus.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static StockMoveConfirmStatus parseByValue(Integer value) {
		for (StockMoveConfirmStatus dealerType : StockMoveConfirmStatus.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
}
