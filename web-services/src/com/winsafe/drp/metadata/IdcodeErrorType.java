package com.winsafe.drp.metadata;

public enum IdcodeErrorType {
	E00001(0,"E00001","单据不存在"),     
	E00002(1,"E00002","单据已复核"),     
	E00003(2,"E00003","条码不在系统中"), 
	E00004(3,"E00004","条码不可用"),     
	E00005(4,"E00005","条码不在当前仓库"),
	E00006(5,"E00006","条码已存在当前单据"),
	E00007(6,"E00007","单据已作废"),     
	E00008(7,"E00008","条码不符合规则"), 
	E00009(8,"E00009","产品不存在"),     
	E00010(9,"E00010","扫描类型不正确"), 
	E00011(10,"E00011","条码与产品不对应"),
	E00012(11,"E00012","获取属性失败"),  
	E00013(12,"E00013","不符合规则"),    
	E00014(13,"E00014","扫描标识位不正确"),
	E00101(14,"E00101","出库仓库不存在"),
	E00102(15,"E00102","用户对出库仓库无"),
	E00103(16,"E00103","入库仓库不存在"),
	E00104(17,"E00104","用户对入库仓库没"),
	E00106(18,"E00106","当前文件中条码重"),
	E00107(19,"E00107","条码格式不正确"),
	E00108(20,"E00108","条码与产品不符"),
	E00016(21,"E00016","条码数量超过产品数量");

	
	private Integer intValue;
	private String dbValue;
	private String displayValue;

	
	private IdcodeErrorType(Integer intValue, String dbValue, String displayValue)
	{
		this.dbValue = dbValue;
		this.intValue = intValue;
		this.displayValue = displayValue;
	}

	public String getDbValue() {
		return dbValue;
	}
	
	public Integer getIntValue() {
		return intValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	/**
	 * Parse database value to enum Instance
	 * 
	 * @param dbValue
	 * @return 'null' when invalid dbValue is provided
	 */
	public static IdcodeErrorType parseByIntValue(Integer intValue) {
		for (IdcodeErrorType idcodeErrorType : IdcodeErrorType.values()) {
			if (idcodeErrorType.getIntValue().equals(intValue)) {
				return idcodeErrorType;
			}
		}
		return null;
	}
	
	public static IdcodeErrorType parseByDBValue(String dbValue) {
		for (IdcodeErrorType idcodeErrorType : IdcodeErrorType.values()) {
			if (idcodeErrorType.getDbValue().equals(dbValue)) {
				return idcodeErrorType;
			}
		}
		return null;
	}
}
