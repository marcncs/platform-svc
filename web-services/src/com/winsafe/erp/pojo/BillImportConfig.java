package com.winsafe.erp.pojo;

import com.winsafe.erp.util.Field;

public class BillImportConfig {
	private long id;
	//机构编码
	private String organId;
	//模板编号
	private String templateNo;
	//IS系统字段名
	private String fieldName;
	//文件中对应列名
	private String columnName;
	//默认值
	private String defaultValue;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getTemplateNo() {
		return templateNo;
	}
	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getFieldDisplayName() {
		Field field = Field.parse(fieldName);
		if(field != null) {
			return field.getDisplayName();
		}
		return fieldName;
	}
}
