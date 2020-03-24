package com.winsafe.sap.metadata;

public enum SapFileErrorType {
	RETURN_DATA(0, "退货数据"),
	PRODUCT_NOT_EXIST(1, "产品不存在"),
	ORGAN_WAREHOUSE_NOT_EXISTS(2, "机构或仓库不存在"),
	FILE_FORMAT_ERROR(3, "文件格式错误"),
	BILL_TYPE_EMPTY(4, "单据类型为空"),
	FILE_PROCESS_ERROR(5, "文件处理错误"),
	WAREHOUSE_NOT_UESABLE(6, "机构或仓库不可用"),
	BILL_ALREADY_EXISTS(7,"发货单单号已存在"),
	FUNIT_NOT_CONFIGED(8,"包装比例关系未设置"),
	CODE_DUPLICATE_SEND(9,"条码重复发送"),
	DELIVERY_NOT_EXISTS(10,"发货单不存在");
	
	private Integer dbValue;
	private String name;
	
	private SapFileErrorType(Integer dbValue, String name)
	{
		this.dbValue = dbValue;
		this.name = name;
	}
	
	public Integer getDbValue() {
		return dbValue;
	}

	public String getName() {
		return name;
	}


	/**
	 * Parse database value to enum Instance
	 * 
	 * @param databaseValue
	 * @return 'null' when invalid databaseValue is provided
	 */
	public static SapFileErrorType parse(Integer databaseValue) {
		for (SapFileErrorType errorType : SapFileErrorType.values()) {
			if (errorType.getDbValue().equals(databaseValue)) {
				return errorType;
			}
		}
		return null;
	}
}
