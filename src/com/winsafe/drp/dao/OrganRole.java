package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrganRole implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer roleid;
    
    private String rname;

    /** nullable persistent field */
    private String organid;
    
    private String oname;
    


    public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	/** full constructor */
    public OrganRole(Integer id, Integer roleid, String organid) {
        this.id = id;
        this.roleid = roleid;
        this.organid = organid;
    }

    /** default constructor */
    public OrganRole() {
    }

    /** minimal constructor */
    public OrganRole(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleid() {
        return this.roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    

    public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganRole) ) return false;
        OrganRole castOther = (OrganRole) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

}
