package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class OrganSafetyIntercalate extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Double safetyh;
    
    private Double safetyl;

    /** full constructor */
    public OrganSafetyIntercalate(Integer id, String organid, String productid, Double safetyh,Double safetyl) {
        this.id = id;
        this.organid = organid;
        this.productid = productid;
        this.safetyh = safetyh;
        this.safetyl = safetyl;
    }

    /** default constructor */
    public OrganSafetyIntercalate() {
    }

    /** minimal constructor */
    public OrganSafetyIntercalate(Integer id) {
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


    public Double getSafetyh() {
		return safetyh;
	}

	public void setSafetyh(Double safetyh) {
		this.safetyh = safetyh;
	}

	public Double getSafetyl() {
		return safetyl;
	}

	public void setSafetyl(Double safetyl) {
		this.safetyl = safetyl;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganSafetyIntercalate) ) return false;
        OrganSafetyIntercalate castOther = (OrganSafetyIntercalate) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

}
