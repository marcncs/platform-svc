package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CorrelationDocument implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer objsort;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String documentname;

    /** nullable persistent field */
    private String realpathname;
    
    private Date updatedate;
    
    private String description;

    public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	/** nullable persistent field */
    private String makeorganid;
    
    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public CorrelationDocument(Integer id, Integer objsort, String cid, String cname, String documentname, String realpathname, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.objsort = objsort;
        this.cid = cid;
        this.cname = cname;  
        this.documentname = documentname;
        this.realpathname = realpathname;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public CorrelationDocument() {
    }

    /** minimal constructor */
    public CorrelationDocument(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getObjsort() {
        return this.objsort;
    }

    public void setObjsort(Integer objsort) {
        this.objsort = objsort;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDocumentname() {
        return this.documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public String getRealpathname() {
        return this.realpathname;
    }

    public void setRealpathname(String realpathname) {
        this.realpathname = realpathname;
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
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CorrelationDocument) ) return false;
        CorrelationDocument castOther = (CorrelationDocument) other;
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
