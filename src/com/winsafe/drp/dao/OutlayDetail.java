package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OutlayDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private Integer outlayprojectid;

    /** nullable persistent field */
    private Double outlaysum;
    
    private String voucher;

    /** nullable persistent field */
    private String remark;

    /** full constructor */
    public OutlayDetail(Integer id, String oid, Integer outlayprojectid, Double outlaysum, String remark) {
        this.id = id;
        this.oid = oid;
        this.outlayprojectid = outlayprojectid;
        this.outlaysum = outlaysum;
        this.remark = remark;
    }

    /** default constructor */
    public OutlayDetail() {
    }

    /** minimal constructor */
    public OutlayDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOid() {
        return this.oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getOutlayprojectid() {
        return this.outlayprojectid;
    }

    public void setOutlayprojectid(Integer outlayprojectid) {
        this.outlayprojectid = outlayprojectid;
    }

    public Double getOutlaysum() {
        return this.outlaysum;
    }

    public void setOutlaysum(Double outlaysum) {
        this.outlaysum = outlaysum;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OutlayDetail) ) return false;
        OutlayDetail castOther = (OutlayDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

}
