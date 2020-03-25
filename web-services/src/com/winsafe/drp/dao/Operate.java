package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class Operate extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String operatename;

    /** nullable persistent field */
    private Integer moduleid;
    
    private String modulename;

    /** full constructor */
    public Operate(Long id, String operatename, Integer moduleid) {
        this.id = id;
        this.operatename = operatename;
        this.moduleid = moduleid;
    }

    /** default constructor */
    public Operate() {
    }

    /** minimal constructor */
    public Operate(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperatename() {
        return this.operatename;
    }

    public void setOperatename(String operatename) {
        this.operatename = operatename;
    }

    public Integer getModuleid() {
        return this.moduleid;
    }

    public void setModuleid(Integer moduleid) {
        this.moduleid = moduleid;
    }
    
    

    /**
	 * @return the modulename
	 */
	public String getModulename() {
		return modulename;
	}

	/**
	 * @param modulename the modulename to set
	 */
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Operate) ) return false;
        Operate castOther = (Operate) other;
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
