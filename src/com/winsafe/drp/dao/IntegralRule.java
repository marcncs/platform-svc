package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class IntegralRule extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;
    
    private String rmode;

    /** nullable persistent field */
    private Integer rkey;

    /** nullable persistent field */
    private Double irate;

    /** full constructor */
    public IntegralRule(Integer id, Integer rkey, Double irate) {
        this.id = id;
        this.rkey = rkey;
        this.irate = irate;
    }

    /** default constructor */
    public IntegralRule() {
    }

    /** minimal constructor */
    public IntegralRule(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getRkey() {
        return this.rkey;
    }

    public void setRkey(Integer rkey) {
        this.rkey = rkey;
    }

    public Double getIrate() {
        return this.irate;
    }

    public void setIrate(Double irate) {
        this.irate = irate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IntegralRule) ) return false;
        IntegralRule castOther = (IntegralRule) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getRmode() {
		return rmode;
	}

	public void setRmode(String rmode) {
		this.rmode = rmode;
	}

}
