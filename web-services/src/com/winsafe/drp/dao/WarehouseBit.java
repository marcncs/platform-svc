package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class WarehouseBit implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String wid;

    /** nullable persistent field */
    private String wbid;
    private String bitName;

    /** full constructor */
    public WarehouseBit(Integer id, String wid, String wbid) {
        this.id = id;
        this.wid = wid;
        this.wbid = wbid;
    }

    /** default constructor */
    public WarehouseBit() {
    }

    /** minimal constructor */
    public WarehouseBit(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getBitName() {
		return bitName;
	}

	public void setBitName(String bitName) {
		this.bitName = bitName;
	}

	public String getWid() {
        return this.wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getWbid() {
        return this.wbid;
    }

    public void setWbid(String wbid) {
        this.wbid = wbid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof WarehouseBit) ) return false;
        WarehouseBit castOther = (WarehouseBit) other;
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
