package com.winsafe.mail.metadata;

public enum MailType {
	COVERT_CODE_DUPLICATE(0, "暗码重复错误提醒"),
	DELIVERY_ERROR(1, "发货单处理错误提醒"),
	PRODUCT_MSG(2, "分装厂生产计划审批提醒"),
	STOCK_MOVE_AUDIT(3, "转仓单审批提醒"),
	AMOUNT_INVENTORY_AUDIT(4, "数量盘点审批提醒"),
	BARCODE_INVENTORY_AUDIT(5, "条码盘点审批提醒"),
	IAM_NEW_USER(6, "IAM新增用户提醒"),
	DUPLICATE_DELIVERY_IDCODE(7, "发货单重复箱码"); 
	
	private Integer dbValue;
	private String name;
	
	
	
	private MailType(Integer dbValue, String name) {
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
	public static MailType parse(Integer dbValue) {
		for (MailType mailType : MailType.values()) {
			if (mailType.getDbValue().equals(dbValue)) {
				return mailType;
			}
		}
		return null;
	}
	
}
