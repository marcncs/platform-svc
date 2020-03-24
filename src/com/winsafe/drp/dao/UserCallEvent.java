package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UserCallEvent implements Serializable {

    /** identifier field */
    private Integer id;
    
    private Integer monitoruserid;

    /** nullable persistent field */
    private Integer userid;

    /** full constructor */
    public UserCallEvent(Integer id, Integer userid) {
        this.id = id;
        this.userid = userid;
    }

    /** default constructor */
    public UserCallEvent() {
    }

    /** minimal constructor */
    public UserCallEvent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    
    

    /**
	 * @return the monitoruserid
	 */
	public Integer getMonitoruserid() {
		return monitoruserid;
	}

	/**
	 * @param monitoruserid the monitoruserid to set
	 */
	public void setMonitoruserid(Integer monitoruserid) {
		this.monitoruserid = monitoruserid;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserCallEvent) ) return false;
        UserCallEvent castOther = (UserCallEvent) other;
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
