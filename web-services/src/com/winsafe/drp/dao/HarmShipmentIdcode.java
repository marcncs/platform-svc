package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class HarmShipmentIdcode extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String hsid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String idcode;

    /** full constructor */
    public HarmShipmentIdcode(Long id, String hsid, String productid, String batch, String idcode) {
        this.id = id;
        this.hsid = hsid;
        this.productid = productid;
        this.batch = batch;
        this.idcode = idcode;
    }

    /** default constructor */
    public HarmShipmentIdcode() {
    }

    /** minimal constructor */
    public HarmShipmentIdcode(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHsid() {
        return this.hsid;
    }

    public void setHsid(String hsid) {
        this.hsid = hsid;
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
        if ( !(other instanceof HarmShipmentIdcode) ) return false;
        HarmShipmentIdcode castOther = (HarmShipmentIdcode) other;
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
