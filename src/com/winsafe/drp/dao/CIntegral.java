package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class CIntegral extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;
    
    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private Integer isort;

    /** nullable persistent field */
    private Double cintegral;
    
    private String iyear;

    /** full constructor */
    public CIntegral(Long id, String organid, String cid, Integer isort, Double cintegral) {
        this.id = id;
        this.organid = organid;
        this.cid = cid;
        this.isort = isort;
        this.cintegral = cintegral;
    }

    /** default constructor */
    public CIntegral() {
    }

    /** minimal constructor */
    public CIntegral(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getIsort() {
        return this.isort;
    }

    public void setIsort(Integer isort) {
        this.isort = isort;
    }

    public Double getCintegral() {
        return this.cintegral;
    }

    public void setCintegral(Double cintegral) {
        this.cintegral = cintegral;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CIntegral) ) return false;
        CIntegral castOther = (CIntegral) other;
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

	public String getIyear() {
		return iyear;
	}

	public void setIyear(String iyear) {
		this.iyear = iyear;
	}

}
