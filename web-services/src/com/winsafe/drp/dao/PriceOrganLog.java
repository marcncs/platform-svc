package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PriceOrganLog implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String organid;
    
    private String organidname;

    /** full constructor */
    public PriceOrganLog(Long id, String productid, String organid) {
        this.id = id;
        this.productid = productid;
        this.organid = organid;
    }

    /** default constructor */
    public PriceOrganLog() {
    }

    /** minimal constructor */
    public PriceOrganLog(Long id) {
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

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }
    
    

    /**
	 * @return the organidname
	 */
	public String getOrganidname() {
		return organidname;
	}

	/**
	 * @param organidname the organidname to set
	 */
	public void setOrganidname(String organidname) {
		this.organidname = organidname;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PriceOrganLog) ) return false;
        PriceOrganLog castOther = (PriceOrganLog) other;
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
