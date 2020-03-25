package com.winsafe.erp.pojo;

import java.util.Date;

public class UnitInfoForm {
	private long id;
	// 工厂代码
	private String organId;
	// 产品编码
	private String productId;
	// 包装单位编号（这里是托盘）
	private Integer unitId;
	// 对应数量
	private Integer unitCount;
	// 修改日期
	private Date modifiedDate;
	// 修改人员
	private Integer modifiedUserID;
	// 是否可用
	private Integer isactive;
	// 产品名称
	private String productName;
	// 工厂名称
	private String organName;
	// 日期
	private String formatDate;
	// 物料号
	private String mcode;

	// 标签类型
	private String labelType;

	// 产品规格
	private String specmode;
	//是否需要分装
    private Integer needRepackage;
    //是否需要生成暗码
    private Integer needCovertCode;
    
    private String codeSeq; 

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getUnitCount() {
		return unitCount;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public void setUnitCount(Integer unitCount) {
		this.unitCount = unitCount;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getModifiedUserID() {
		return modifiedUserID;
	}

	public void setModifiedUserID(Integer modifiedUserID) {
		this.modifiedUserID = modifiedUserID;
	}

	public Integer getIsactive() {
		return isactive;
	}

	public void setIsactive(Integer isactive) {
		this.isactive = isactive;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}

	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public Integer getNeedRepackage() {
		return needRepackage;
	}

	public void setNeedRepackage(Integer needRepackage) {
		this.needRepackage = needRepackage;
	}

	public Integer getNeedCovertCode() {
		return needCovertCode;
	}

	public void setNeedCovertCode(Integer needCovertCode) {
		this.needCovertCode = needCovertCode;
	}

	public String getCodeSeq() {
		return codeSeq;
	}

	public void setCodeSeq(String codeSeq) {
		this.codeSeq = codeSeq;
	}

}
