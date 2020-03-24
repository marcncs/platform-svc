package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TaskExecute implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer tpid;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private Integer isaffirm;

    /** nullable persistent field */
    private Integer isover;

    /** nullable persistent field */
    private Date overdate;

    /** full constructor */
    public TaskExecute(Integer id, Integer tpid, Integer userid, Integer isaffirm, Integer isover, Date overdate) {
        this.id = id;
        this.tpid = tpid;
        this.userid = userid;
        this.isaffirm = isaffirm;
        this.isover = isover;
        this.overdate = overdate;
    }

    /** default constructor */
    public TaskExecute() {
    }

    /** minimal constructor */
    public TaskExecute(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTpid() {
        return this.tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
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

    public Integer getIsover() {
        return this.isover;
    }

    public void setIsover(Integer isover) {
        this.isover = isover;
    }

    public Date getOverdate() {
        return this.overdate;
    }

    public void setOverdate(Date overdate) {
        this.overdate = overdate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TaskExecute) ) return false;
        TaskExecute castOther = (TaskExecute) other;
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
