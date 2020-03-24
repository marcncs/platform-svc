package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class OrganGrade extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Double integralrate;

    /** nullable persistent field */
    private String gradename;
    
    private Integer policyid;

    /** full constructor */
    public OrganGrade(Integer id, Double integralrate, String gradename) {
        this.id = id;
        this.integralrate = integralrate;
        this.gradename = gradename;
    }

    /** default constructor */
    public OrganGrade() {
    }

    /** minimal constructor */
    public OrganGrade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganGrade) ) return false;
        OrganGrade castOther = (OrganGrade) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Double getIntegralrate() {
		return integralrate;
	}

	public void setIntegralrate(Double integralrate) {
		this.integralrate = integralrate;
	}

	public String getGradename() {
		return gradename;
	}

	public void setGradename(String gradename) {
		this.gradename = gradename;
	}

	public Integer getPolicyid() {
		return policyid;
	}

	public void setPolicyid(Integer policyid) {
		this.policyid = policyid;
	}

}
