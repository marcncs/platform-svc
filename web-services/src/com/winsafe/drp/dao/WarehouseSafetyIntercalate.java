package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class WarehouseSafetyIntercalate extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long warehouseid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Double safetyh;
    
    private Double safetyl;

    /** full constructor */
    public WarehouseSafetyIntercalate(Long id, Long warehouseid, String productid, Double safetyh,Double safetyl) {
        this.id = id;
        this.warehouseid = warehouseid;
        this.productid = productid;
        this.safetyh = safetyh;
        this.safetyl = safetyl;
    }

    /** default constructor */
    public WarehouseSafetyIntercalate() {
    }

    /** minimal constructor */
    public WarehouseSafetyIntercalate(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(Long warehouseid) {
        this.warehouseid = warehouseid;
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
        if ( !(other instanceof WarehouseSafetyIntercalate) ) return false;
        WarehouseSafetyIntercalate castOther = (WarehouseSafetyIntercalate) other;
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
