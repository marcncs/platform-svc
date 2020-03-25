package com.winsafe.drp.dao;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class DeptForm extends ActionForm  {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String deptname;

    /** nullable persistent field */
    private String oid;
    
    private String oidname;

    public String getOidname() {
		return oidname;
	}

	public void setOidname(String oidname) {
		this.oidname = oidname;
	}

	/** full constructor */
    public DeptForm(Integer id, String deptname, String oid) {
        this.id = id;
        this.deptname = deptname;
        this.oid = oid;
    }

    /** default constructor */
    public DeptForm() {
    }

    /** minimal constructor */
    public DeptForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptname() {
        return this.deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getOid() {
        return this.oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Dept) ) return false;
        Dept castOther = (Dept) other;
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
