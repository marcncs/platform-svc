package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CodeUnit implements Serializable {

    /** identifier field */
    private String ucode;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private String uname;

    /** nullable persistent field */
    private String remark;

    /** full constructor */
    public CodeUnit(Integer unitid, String uname, String remark) {
        this.unitid = unitid;
        this.uname = uname;
        this.remark = remark;
    }

    /** default constructor */
    public CodeUnit() {
    }

   

    public String getUcode() {
		return ucode;
	}

	public void setUcode(String ucode) {
		this.ucode = ucode;
	}

	public Integer getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public String getUname() {
        return this.uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getUcode())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CodeUnit) ) return false;
        CodeUnit castOther = (CodeUnit) other;
        return new EqualsBuilder()
            .append(this.getUcode(), castOther.getUcode())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUcode())
            .toHashCode();
    }

}
