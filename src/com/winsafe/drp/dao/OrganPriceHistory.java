package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrganPriceHistory implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Long policyid;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Long userid;

    /** nullable persistent field */
    private Date modifydate;

    /** full constructor */
    public OrganPriceHistory(Long id, String organid, String productid, Integer unitid, Long policyid, Double unitprice, Long userid, Date modifydate) {
        this.id = id;
        this.organid = organid;
        this.productid = productid;
        this.unitid = unitid;
        this.policyid = policyid;
        this.unitprice = unitprice;
        this.userid = userid;
        this.modifydate = modifydate;
    }

    /** default constructor */
    public OrganPriceHistory() {
    }

    /** minimal constructor */
    public OrganPriceHistory(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Integer getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public Long getPolicyid() {
        return this.policyid;
    }

    public void setPolicyid(Long policyid) {
        this.policyid = policyid;
    }

    public Double getUnitprice() {
        return this.unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getModifydate() {
        return this.modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganPriceHistory) ) return false;
        OrganPriceHistory castOther = (OrganPriceHistory) other;
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
