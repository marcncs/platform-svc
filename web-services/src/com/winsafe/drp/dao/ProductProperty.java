package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class ProductProperty extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String productid;

    /** nullable persistent field */
    private Integer propertycode;

    /** full constructor */
    public ProductProperty(Long id, String productid, Integer propertycode) {
        this.id = id;
        this.productid = productid;
        this.propertycode = propertycode;
    }

    /** default constructor */
    public ProductProperty() {
    }

    /** minimal constructor */
    public ProductProperty(Long id, String productid) {
        this.id = id;
        this.productid = productid;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Integer getPropertycode() {
        return this.propertycode;
    }

    public void setPropertycode(Integer propertycode) {
        this.propertycode = propertycode;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ProductProperty) ) return false;
        ProductProperty castOther = (ProductProperty) other;
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
