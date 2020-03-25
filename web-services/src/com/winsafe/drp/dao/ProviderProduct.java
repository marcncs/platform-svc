package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class ProviderProduct extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String providerid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String pvproductname;
    
    private String pvpycode;

    /** nullable persistent field */
    private String pvspecmode;

    /** nullable persistent field */
    private Integer countunit;

    /** nullable persistent field */
    private String barcode;

    /** nullable persistent field */
    private Double price;

    /** nullable persistent field */
    private Date pricedate;

    private String nccode;
    /** full constructor */
    public ProviderProduct(Integer id, String providerid, String productid, String pvproductname, String pvspecmode, Integer countunit, String barcode, Double price, Date pricedate,String nccode) {
        this.id = id;
        this.providerid = providerid;
        this.productid = productid;
        this.pvproductname = pvproductname;
        this.pvspecmode = pvspecmode;
        this.countunit = countunit;
        this.barcode = barcode;
        this.price = price;
        this.pricedate = pricedate;
        this.nccode=nccode;
    }

    public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	/** default constructor */
    public ProviderProduct() {
    }

    /** minimal constructor */
    public ProviderProduct(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProviderid() {
        return this.providerid;
    }

    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Integer getCountunit() {
        return this.countunit;
    }

    public void setCountunit(Integer countunit) {
        this.countunit = countunit;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getPricedate() {
        return this.pricedate;
    }

    public void setPricedate(Date pricedate) {
        this.pricedate = pricedate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ProviderProduct) ) return false;
        ProviderProduct castOther = (ProviderProduct) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getPvproductname() {
		return pvproductname;
	}

	public void setPvproductname(String pvproductname) {
		this.pvproductname = pvproductname;
	}

	public String getPvspecmode() {
		return pvspecmode;
	}

	public void setPvspecmode(String pvspecmode) {
		this.pvspecmode = pvspecmode;
	}

	public String getPvpycode() {
		return pvpycode;
	}

	public void setPvpycode(String pvpycode) {
		this.pvpycode = pvpycode;
	}

}
