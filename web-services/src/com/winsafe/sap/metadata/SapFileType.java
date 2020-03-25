package com.winsafe.sap.metadata;

public enum SapFileType {
	//订单数据文件
	ORDER_SAP("0", "order", "SAP上传订单数据文件"),
	//杭州工厂订单数据文件
	ORDER_PlANT("1", "plantorder", "杭州工厂订单数据文件"),
	//分装厂订单数据文件
	ORDER_TOLLER("2", "tollerorder", "分装厂订单数据文件"),
	//发货数据文件
	DELIVERY("3", "delivery", "发货数据文件"),
	//发票数据文件
	INVOICE("4", "invoice", "发票数据文件"),
	//SAP码数据文件
	SAPCODE("90", "sapcode", "SAP码数据文件"),
	//SAP码数据文件
	TollerChange("91", "tollerchange", "箱托转换数据文件");
	
	private String databaseValue;
	private String sapName;
	private String displayName;
	
	private SapFileType(String databaseValue, String sapName,String displayName)
	{
		this.databaseValue = databaseValue;
		this.sapName = sapName;
		this.displayName = displayName;
	}

	public String getDatabaseValue() {
		return databaseValue;
	}

	public String getSapName() {
		return sapName;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Parse database value to enum Instance
	 * 
	 * @param databaseValue
	 * @return 'null' when invalid databaseValue is provided
	 */
	public static SapFileType parse(String databaseValue) {
		for (SapFileType sapFileType : SapFileType.values()) {
			if (sapFileType.getDatabaseValue().equals(databaseValue)) {
				return sapFileType;
			}
		}
		return null;
	}
	
	public static SapFileType parseBySapName(String sapName) {
		for (SapFileType sapFileType : SapFileType.values()) {
			if (sapFileType.getSapName().equals(sapName)) {
				return sapFileType;
			}
		}
		return null;
	}
	
}
