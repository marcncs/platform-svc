package com.winsafe.drp.metadata;

public enum EIsDeleted {

	isDeleted(1 , "已删除" , true),//已删除
	nromal(0 , "未删除" , false) //未删除
	;
	
	private Integer value;
	private String name;
	private Boolean is;
	
	private EIsDeleted (Integer value , String name , Boolean is){
		this.value = value;
		this.name = name;
		this.is = is;
	}
	
	public Integer getValue(){
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean getIs(){
		return is;
	}
	
	public static EIsDeleted parseByName(String name) {
		for (EIsDeleted dealerType : EIsDeleted.values()) {
			if (dealerType.getName().equals(name)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static EIsDeleted parseByValue(Integer value) {
		for (EIsDeleted dealerType : EIsDeleted.values()) {
			if (dealerType.getValue().equals(value)) {
				return dealerType;
			}
		}
		return null;
	}
	
	public static String getAllValueString() {
		StringBuffer allValue = new StringBuffer();
		for (EIsDeleted userType : EIsDeleted.values()) {
			allValue.append(",").append(userType.getValue());
		}
		return allValue.substring(1); 
	}
	
}
