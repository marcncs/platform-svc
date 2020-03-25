package com.winsafe.erp.util;

public enum Field {
	MOVE_DATE("moveDate", "单据日期", false),
	NCCODE("nccode", "单据编号", true),
	RECEIVE_ORGAN_NAME("receiveorganidname", "收货机构名称", true),
	RECEIVE_ORGAN_ID("receiveorganid", "收货机构编号", false),
	QUANTITY("countQuantity", "计量单位数", true),
	NUMBER_OF_PACKAGES("quantity", "件数", true),
	PRODUCT_CODE("productCode", "产品编码", true),
	IN_WAREHOUSE_ID("inWarehouseName", "收货仓库编号", false),
//	IN_WAREHOUSE_NAME("inWarehouseName", "收货仓库名称", false),
//	IN_RETURN("isReturn", "是否退货"),
	DATA_ROW_NO("dataRowNo", "数据起始行号",true),
	TITLE_ROW_NUM("titleRowNo", "表头行号",true),
	REMARK("remark", "备注", false),
	OUT_ORGAN_NAME("outOrganName", "出货机构名称", false),
	OUT_WAREHOUSE_NAME("outWarehouseName", "出货仓库名称", false);
	
	
	private String fieldName;
	private String displayName;
	private boolean required;
	
	private Field(String fieldName, String displayName, boolean required)
	{
		this.fieldName = fieldName;
		this.displayName = displayName;
		this.required = required;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Parse database value to enum Instance
	 * 
	 * @param databaseValue
	 * @return 'null' when invalid databaseValue is provided
	 */
	public static Field parse(String fieldName) {
		for (Field field : Field.values()) {
			if (field.getFieldName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}
}
