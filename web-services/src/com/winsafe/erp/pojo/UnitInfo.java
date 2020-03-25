package com.winsafe.erp.pojo;

import java.util.Date;

public class UnitInfo {
	private long id;
	//工厂代码
	private String  organId;
	//产品编码
	private String productId;
	//包装单位编号（这里是托盘）
	private Integer unitId;
	//对应数量
	private Integer unitCount;
	//修改日期
    private Date modifiedDate;
    //修改人员
    private Integer modifiedUserID;
    //是否可用
    private Integer isactive;
    //标签类型 2016-3-16
    private String labelType;
    //是否需要分装
    private Integer needRepackage;
    //是否需要暗码
    private Integer needCovertCode;
    //当前序号
    private String codeSeq;
    private Integer version; 
    
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
	public String getLabelType() {
		return labelType;
	}
	public void setLabelType(String labelType) {
		this.labelType = labelType;
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
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
}
