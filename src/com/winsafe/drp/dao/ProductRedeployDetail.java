package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ProductRedeployDetail implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long prid;
    
    private String productid;

    /** nullable persistent field */
    private Double standardpurchase;

    /** nullable persistent field */
    private Double standardsale;

    /** nullable persistent field */
    private Double pricei;

    /** nullable persistent field */
    private Double priceii;

    /** nullable persistent field */
    private Double priceiii;

    /** nullable persistent field */
    private Double pricewholesale;

    /** nullable persistent field */
    private Double priceivs;

    /** nullable persistent field */
    private Double priceuni;

    /** nullable persistent field */
    private Double leastsale;

    /** nullable persistent field */
    private Double cost;

    /** full constructor */
    public ProductRedeployDetail(Long id, String productid, Double standardpurchase, Double standardsale, Double pricei, Double priceii, Double priceiii, Double pricewholesale, Double priceivs, Double priceuni, Double leastsale, Double cost) {
        this.id = id;
        this.productid = productid;
        this.standardpurchase = standardpurchase;
        this.standardsale = standardsale;
        this.pricei = pricei;
        this.priceii = priceii;
        this.priceiii = priceiii;
        this.pricewholesale = pricewholesale;
        this.priceivs = priceivs;
        this.priceuni = priceuni;
        this.leastsale = leastsale;
        this.cost = cost;
    }

    /** default constructor */
    public ProductRedeployDetail() {
    }

    /** minimal constructor */
    public ProductRedeployDetail(Long id) {
        this.id = id;
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

    public Double getStandardpurchase() {
        return this.standardpurchase;
    }

    public void setStandardpurchase(Double standardpurchase) {
        this.standardpurchase = standardpurchase;
    }

    public Double getStandardsale() {
        return this.standardsale;
    }

    public void setStandardsale(Double standardsale) {
        this.standardsale = standardsale;
    }

    public Double getPricei() {
        return this.pricei;
    }

    public void setPricei(Double pricei) {
        this.pricei = pricei;
    }

    public Double getPriceii() {
        return this.priceii;
    }

    public void setPriceii(Double priceii) {
        this.priceii = priceii;
    }

    public Double getPriceiii() {
        return this.priceiii;
    }

    public void setPriceiii(Double priceiii) {
        this.priceiii = priceiii;
    }

    public Double getPricewholesale() {
        return this.pricewholesale;
    }

    public void setPricewholesale(Double pricewholesale) {
        this.pricewholesale = pricewholesale;
    }

    public Double getPriceivs() {
        return this.priceivs;
    }

    public void setPriceivs(Double priceivs) {
        this.priceivs = priceivs;
    }

    public Double getPriceuni() {
        return this.priceuni;
    }

    public void setPriceuni(Double priceuni) {
        this.priceuni = priceuni;
    }

    public Double getLeastsale() {
        return this.leastsale;
    }

    public void setLeastsale(Double leastsale) {
        this.leastsale = leastsale;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ProductRedeployDetail) ) return false;
        ProductRedeployDetail castOther = (ProductRedeployDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Long getPrid() {
		return prid;
	}

	public void setPrid(Long prid) {
		this.prid = prid;
	}

}
