package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UserLog implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private Date logtime;

    /** nullable persistent field */
    private String detail;

    /** nullable persistent field */
    private Integer modeltype;

    /** nullable persistent field */
    private String modifycontent;
    
    private String modifycontentAbbr;

    /** full constructor */
    public UserLog(Long id, Integer userid, Date logtime, String detail, Integer modeltype, String modifycontent) {
        this.id = id;
        this.userid = userid;
        this.logtime = logtime;
        this.detail = detail;
        this.modeltype = modeltype;
        this.modifycontent = modifycontent;
    }

    /** default constructor */
    public UserLog() {
    }

    /** minimal constructor */
    public UserLog(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getLogtime() {
        return this.logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getModeltype() {
        return this.modeltype;
    }

    public void setModeltype(Integer modeltype) {
        this.modeltype = modeltype;
    }

    public String getModifycontent() {
        return this.modifycontent;
    }

    public void setModifycontent(String modifycontent) {
        this.modifycontent = modifycontent;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserLog) ) return false;
        UserLog castOther = (UserLog) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getModifycontentAbbr() {
		return modifycontentAbbr;
	}

	public void setModifycontentAbbr(String modifycontentAbbr) {
		this.modifycontentAbbr = modifycontentAbbr;
	}

}
