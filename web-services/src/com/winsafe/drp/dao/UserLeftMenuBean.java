package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class UserLeftMenuBean extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long userid;

    /** nullable persistent field */
    private Long lmid;

    /** nullable persistent field */
    private String lmenuname;

    /** nullable persistent field */
    private String lmenuurl;
    
    private List datelist;

    /** full constructor */
    public UserLeftMenuBean(Long id, Long userid, Long lmid, String lmenuname, String lmenuurl) {
        this.id = id;
        this.userid = userid;
        this.lmid = lmid;
        this.lmenuname = lmenuname;
        this.lmenuurl = lmenuurl;
    }

    /** default constructor */
    public UserLeftMenuBean() {
    }

    /** minimal constructor */
    public UserLeftMenuBean(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getLmid() {
        return this.lmid;
    }

    public void setLmid(Long lmid) {
        this.lmid = lmid;
    }

    public String getLmenuname() {
        return this.lmenuname;
    }

    public void setLmenuname(String lmenuname) {
        this.lmenuname = lmenuname;
    }

    public String getLmenuurl() {
        return this.lmenuurl;
    }

    public void setLmenuurl(String lmenuurl) {
        this.lmenuurl = lmenuurl;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserLeftMenuBean) ) return false;
        UserLeftMenuBean castOther = (UserLeftMenuBean) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public List getDatelist() {
		return datelist;
	}

	public void setDatelist(List datelist) {
		this.datelist = datelist;
	}

}
