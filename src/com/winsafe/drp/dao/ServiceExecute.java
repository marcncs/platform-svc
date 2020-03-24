package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ServiceExecute implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer said;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private Integer isaffirm;

    /** full constructor */
    public ServiceExecute(Integer id, Integer said, Integer userid, Integer isaffirm) {
        this.id = id;
        this.said = said;
        this.userid = userid;
        this.isaffirm = isaffirm;
    }

    /** default constructor */
    public ServiceExecute() {
    }

    /** minimal constructor */
    public ServiceExecute(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaid() {
        return this.said;
    }

    public void setSaid(Integer said) {
        this.said = said;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getIsaffirm() {
        return this.isaffirm;
    }

    public void setIsaffirm(Integer isaffirm) {
        this.isaffirm = isaffirm;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ServiceExecute) ) return false;
        ServiceExecute castOther = (ServiceExecute) other;
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
