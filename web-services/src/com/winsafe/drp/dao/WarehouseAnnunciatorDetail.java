package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class WarehouseAnnunciatorDetail extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long waid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Double quantity;

    /** full constructor */
    public WarehouseAnnunciatorDetail(Long id, Long waid, String productid, Double quantity) {
        this.id = id;
        this.waid = waid;
        this.productid = productid;
        this.quantity = quantity;
    }

    /** default constructor */
    public WarehouseAnnunciatorDetail() {
    }

    /** minimal constructor */
    public WarehouseAnnunciatorDetail(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWaid() {
        return this.waid;
    }

    public void setWaid(Long waid) {
        this.waid = waid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
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
        if ( !(other instanceof WarehouseAnnunciatorDetail) ) return false;
        WarehouseAnnunciatorDetail castOther = (WarehouseAnnunciatorDetail) other;
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
