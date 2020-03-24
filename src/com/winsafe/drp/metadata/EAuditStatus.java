package com.winsafe.drp.metadata;

public enum EAuditStatus {
	PENDING(0 , "待审核"), //待审核
	PASSED(1 , "审核通过"), //审核通过
	REJECT(2 , "审核拒绝") //审核拒绝/失败
	;
	
	private Integer value;
	private String name;
	
	private EAuditStatus(Integer value , String name) {
		this.value = value;
		this.name = name;
	}
	
	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public static EAuditStatus parseByName(String name) {
		for (EAuditStatus dealerType : EAuditStatus.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static EAuditStatus parseByValue(Integer value) {
		for (EAuditStatus dealerType : EAuditStatus.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static String getAllValueString() {
		StringBuffer allValue = new StringBuffer();
		for (EAuditStatus userType : EAuditStatus.values()) {
			allValue.append(",").append(userType.getValue());
		}
		return allValue.substring(1); 
	}
}
