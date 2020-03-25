package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LeftMenu implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String lmenuname;

    /** nullable persistent field */
    private String lmenuurl;

    /** nullable persistent field */
    private Integer lmenuparentid;

    /** nullable persistent field */
    private Integer lmenulevel;

    /** nullable persistent field */
    private Integer lmenuorder;
    
    private Integer isVisible;
    
    private Double sort;
    
    private String lmenuurlen; 

    /** full constructor */
    public LeftMenu(Integer id, String lmenuname, String lmenuurl, Integer lmenuparentid, Integer lmenulevel, Integer lmenuorder) {
        this.id = id;
        this.lmenuname = lmenuname;
        this.lmenuurl = lmenuurl;
        this.lmenuparentid = lmenuparentid;
        this.lmenulevel = lmenulevel;
        this.lmenuorder = lmenuorder;
    }

    /** default constructor */
    public LeftMenu() {
    }

    /** minimal constructor */
    public LeftMenu(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getLmenuparentid() {
        return this.lmenuparentid;
    }

    public void setLmenuparentid(Integer lmenuparentid) {
        this.lmenuparentid = lmenuparentid;
    }

    public Integer getLmenulevel() {
        return this.lmenulevel;
    }

    public void setLmenulevel(Integer lmenulevel) {
        this.lmenulevel = lmenulevel;
    }

    public Integer getLmenuorder() {
        return this.lmenuorder;
    }

    public void setLmenuorder(Integer lmenuorder) {
        this.lmenuorder = lmenuorder;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof LeftMenu) ) return false;
        LeftMenu castOther = (LeftMenu) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public Double getSort() {
		return sort;
	}

	public void setSort(Double sort) {
		this.sort = sort;
	}

	public String getLmenuurlen() {
		return lmenuurlen;
	}

	public void setLmenuurlen(String lmenuurlen) {
		this.lmenuurlen = lmenuurlen;
	}

}
