package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SuggestInspect implements Serializable {

    /** identifier field */
    private Long id;
    
    private String typeId;
    private String typeName;
    private String makeName;
    private Date makeDate;
    private String siid;
    private String customerCode;
    private String disWareHouseName;
    private String souWareHouseName;
    private String isPost;
    private Integer isMerge;
    private Integer isRemove;
    private Long mergeId;
    private Integer isOut;
    private Date outDate;
    private Date createDate;
    private Long outUserId;

    /** default constructor */
    public SuggestInspect() {
    	
    }

    /** minimal constructor */
    public SuggestInspect(Long id) {
        this.id = id;
    }

	public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

	public Long getOutUserId() {
		return outUserId;
	}

	public void setOutUserId(Long outUserId) {
		this.outUserId = outUserId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getIsOut() {
		return isOut;
	}

	public void setIsOut(Integer isOut) {
		this.isOut = isOut;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	public String getSiid() {
		return siid;
	}

	public void setSiid(String siid) {
		this.siid = siid;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getDisWareHouseName() {
		return disWareHouseName;
	}

	public void setDisWareHouseName(String disWareHouseName) {
		this.disWareHouseName = disWareHouseName;
	}

	public String getSouWareHouseName() {
		return souWareHouseName;
	}

	public void setSouWareHouseName(String souWareHouseName) {
		this.souWareHouseName = souWareHouseName;
	}

	public String getIsPost() {
		return isPost;
	}

	public void setIsPost(String isPost) {
		this.isPost = isPost;
	}
	

	public Integer getIsMerge() {
		return isMerge;
	}

	public void setIsMerge(Integer isMerge) {
		this.isMerge = isMerge;
	}

	public Integer getIsRemove() {
		return isRemove;
	}

	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}

	public Long getMergeId() {
		return mergeId;
	}

	public void setMergeId(Long mergeId) {
		this.mergeId = mergeId;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SuggestInspect) ) return false;
        SuggestInspect castOther = (SuggestInspect) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	

	 
	


}
