package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class MsgTemplate implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String templatename;

    /** nullable persistent field */
    private String templatecontent;

    /** nullable persistent field */
    private Integer templatetype;
    
    private Integer isuse;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer updid;

    /** nullable persistent field */
    private Date upddate;

    /** full constructor */
    public MsgTemplate(Integer id, String templatename, String templatecontent, Integer templatetype, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer updid, Date upddate) {
        this.id = id;
        this.templatename = templatename;
        this.templatecontent = templatecontent;
        this.templatetype = templatetype;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.updid = updid;
        this.upddate = upddate;
    }

    /** default constructor */
    public MsgTemplate() {
    }

    /** minimal constructor */
    public MsgTemplate(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplatename() {
        return this.templatename;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }

    public String getTemplatecontent() {
        return this.templatecontent;
    }

    public void setTemplatecontent(String templatecontent) {
        this.templatecontent = templatecontent;
    }

    public Integer getTemplatetype() {
        return this.templatetype;
    }

    public void setTemplatetype(Integer templatetype) {
        this.templatetype = templatetype;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Integer getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Integer makedeptid) {
        this.makedeptid = makedeptid;
    }

    public Integer getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Integer makeid) {
        this.makeid = makeid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public Integer getUpdid() {
        return this.updid;
    }

    public void setUpdid(Integer updid) {
        this.updid = updid;
    }

    public Date getUpddate() {
        return this.upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }
    
    

    public Integer getIsuse() {
		return isuse;
	}

	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MsgTemplate) ) return false;
        MsgTemplate castOther = (MsgTemplate) other;
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
