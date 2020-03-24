package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class SendTimeForm extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer irid;
    
    private String iridname;

    /** nullable persistent field */
    private Integer stime;

    /** nullable persistent field */
    private Integer etime;

    /** nullable persistent field */
    private Double integralrate;

    /** full constructor */
    public SendTimeForm(Integer id, Integer irid, Integer stime, Integer etime, Double integralrate) {
        this.id = id;
        this.irid = irid;
        this.stime = stime;
        this.etime = etime;
        this.integralrate = integralrate;
    }

    /** default constructor */
    public SendTimeForm() {
    }

    /** minimal constructor */
    public SendTimeForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIrid() {
        return this.irid;
    }

    public void setIrid(Integer irid) {
        this.irid = irid;
    }

    public Integer getStime() {
        return this.stime;
    }

    public void setStime(Integer stime) {
        this.stime = stime;
    }

    public Integer getEtime() {
        return this.etime;
    }

    public void setEtime(Integer etime) {
        this.etime = etime;
    }

    public Double getIntegralrate() {
        return this.integralrate;
    }

    public void setIntegralrate(Double integralrate) {
        this.integralrate = integralrate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SendTimeForm) ) return false;
        SendTimeForm castOther = (SendTimeForm) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getIridname() {
		return iridname;
	}

	public void setIridname(String iridname) {
		this.iridname = iridname;
	}

}
