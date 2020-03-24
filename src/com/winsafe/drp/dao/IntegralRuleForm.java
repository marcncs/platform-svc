package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class IntegralRuleForm extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;
    
    private String rmode;
    
    private String rmodename;

    /** nullable persistent field */
    private Integer rkey;
    
    private String rkeyname;

    /** nullable persistent field */
    private Double irate;

    /** full constructor */
    public IntegralRuleForm(Integer id, Integer rkey, Double irate) {
        this.id = id;
        this.rkey = rkey;
        this.irate = irate;
    }

    /** default constructor */
    public IntegralRuleForm() {
    }

    /** minimal constructor */
    public IntegralRuleForm(Integer id) {
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
        if ( !(other instanceof IntegralRuleForm) ) return false;
        IntegralRuleForm castOther = (IntegralRuleForm) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getRkeyname() {
		return rkeyname;
	}

	public void setRkeyname(String rkeyname) {
		this.rkeyname = rkeyname;
	}

	public String getRmode() {
		return rmode;
	}

	public void setRmode(String rmode) {
		this.rmode = rmode;
	}

	public String getRmodename() {
		return rmodename;
	}

	public void setRmodename(String rmodename) {
		this.rmodename = rmodename;
	}

}
