package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class ReceivableObject extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private Integer objectsort;

    /** nullable persistent field */
    private String payer;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Date promisedate;
    
    private String keyscontent;

    public String getKeyscontent() {
		return keyscontent;
	}

	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}

	/** full constructor */
    public ReceivableObject(Integer id, String oid, Integer objectsort, String payer, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Date promisedate) {
        this.id = id;
        this.oid = oid;
        this.objectsort = objectsort;
        this.payer = payer;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.promisedate = promisedate;
    }

    /** default constructor */
    public ReceivableObject() {
    }

    /** minimal constructor */
    public ReceivableObject(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOid() {
        return this.oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getObjectsort() {
        return this.objectsort;
    }

    public void setObjectsort(Integer objectsort) {
        this.objectsort = objectsort;
    }

    public String getPayer() {
        return this.payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
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

    public Date getPromisedate() {
        return this.promisedate;
    }

    public void setPromisedate(Date promisedate) {
        this.promisedate = promisedate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ReceivableObject) ) return false;
        ReceivableObject castOther = (ReceivableObject) other;
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
