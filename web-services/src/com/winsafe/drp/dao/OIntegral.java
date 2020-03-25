package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class OIntegral extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String powerorganid;

    /** nullable persistent field */
    private String equiporganid;

    /** nullable persistent field */
    private Integer isort;

    /** nullable persistent field */
    private Double ointegral;
    
    private String iyear;

    public String getIyear() {
		return iyear;
	}

	public void setIyear(String iyear) {
		this.iyear = iyear;
	}

	/** full constructor */
    public OIntegral(Long id, String powerorganid, String equiporganid, Integer isort, Double ointegral) {
        this.id = id;
        this.powerorganid = powerorganid;
        this.equiporganid = equiporganid;
        this.isort = isort;
        this.ointegral = ointegral;
    }

    /** default constructor */
    public OIntegral() {
    }

    /** minimal constructor */
    public OIntegral(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPowerorganid() {
        return this.powerorganid;
    }

    public void setPowerorganid(String powerorganid) {
        this.powerorganid = powerorganid;
    }

    public String getEquiporganid() {
        return this.equiporganid;
    }

    public void setEquiporganid(String equiporganid) {
        this.equiporganid = equiporganid;
    }

    public Integer getIsort() {
        return this.isort;
    }

    public void setIsort(Integer isort) {
        this.isort = isort;
    }

    public Double getOintegral() {
        return this.ointegral;
    }

    public void setOintegral(Double ointegral) {
        this.ointegral = ointegral;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OIntegral) ) return false;
        OIntegral castOther = (OIntegral) other;
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
