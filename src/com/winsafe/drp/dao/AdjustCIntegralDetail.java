package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AdjustCIntegralDetail implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String aciid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String cmobile;

    /** nullable persistent field */
    private Double adjustintegral;
    
    private String oid;

    /** full constructor */
    public AdjustCIntegralDetail(Long id, String aciid, String cid, String cname, String cmobile, Double adjustintegral) {
        this.id = id;
        this.aciid = aciid;
        this.cid = cid;
        this.cname = cname;
        this.cmobile = cmobile;
        this.adjustintegral = adjustintegral;
    }

    /** default constructor */
    public AdjustCIntegralDetail() {
    }

    /** minimal constructor */
    public AdjustCIntegralDetail(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAciid() {
        return this.aciid;
    }

    public void setAciid(String aciid) {
        this.aciid = aciid;
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

    public String getCmobile() {
        return this.cmobile;
    }

    public void setCmobile(String cmobile) {
        this.cmobile = cmobile;
    }

    public Double getAdjustintegral() {
        return this.adjustintegral;
    }

    public void setAdjustintegral(Double adjustintegral) {
        this.adjustintegral = adjustintegral;
    }
    
    

    /**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AdjustCIntegralDetail) ) return false;
        AdjustCIntegralDetail castOther = (AdjustCIntegralDetail) other;
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
