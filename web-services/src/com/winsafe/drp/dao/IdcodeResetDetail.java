package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IdcodeResetDetail implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String irid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Double quantity;

    /** full constructor */
    public IdcodeResetDetail(Long id, String irid, String productid, String productname, String specmode, Double quantity) {
        this.id = id;
        this.irid = irid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.quantity = quantity;
    }

    /** default constructor */
    public IdcodeResetDetail() {
    }

    /** minimal constructor */
    public IdcodeResetDetail(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIrid() {
        return this.irid;
    }

    public void setIrid(String irid) {
        this.irid = irid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSpecmode() {
        return this.specmode;
    }

    public void setSpecmode(String specmode) {
        this.specmode = specmode;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IdcodeResetDetail) ) return false;
        IdcodeResetDetail castOther = (IdcodeResetDetail) other;
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
