package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LoanOutIdcode implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String loid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String idcode;

    /** full constructor */
    public LoanOutIdcode(Long id, String loid, String productid, String batch, String idcode) {
        this.id = id;
        this.loid = loid;
        this.productid = productid;
        this.batch = batch;
        this.idcode = idcode;
    }

    /** default constructor */
    public LoanOutIdcode() {
    }

    /** minimal constructor */
    public LoanOutIdcode(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoid() {
        return this.loid;
    }

    public void setLoid(String loid) {
        this.loid = loid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getIdcode() {
        return this.idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof LoanOutIdcode) ) return false;
        LoanOutIdcode castOther = (LoanOutIdcode) other;
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
