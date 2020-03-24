package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ProductPicture implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String pictureurl;

    /** full constructor */
    public ProductPicture(Integer id, String productid, String pictureurl) {
        this.id = id;
        this.productid = productid;
        this.pictureurl = pictureurl;
    }

    /** default constructor */
    public ProductPicture() {
    }

    /** minimal constructor */
    public ProductPicture(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPictureurl() {
        return this.pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ProductPicture) ) return false;
        ProductPicture castOther = (ProductPicture) other;
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
