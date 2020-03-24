package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Payable implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private Double payablesum;

    /** nullable persistent field */
    private Integer paymode;
    
    private Date awakedate;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String payabledescribe;

    /** nullable persistent field */
    private Double alreadysum;
    
    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isclose;
    
    private Date closedate;

    public Date getClosedate() {
		return closedate;
	}

	public void setClosedate(Date closedate) {
		this.closedate = closedate;
	}

	/** full constructor */
    public Payable(String id, String poid, Double payablesum, Integer paymode, String billno, String payabledescribe, Double alreadysum, Integer makeid, Date makedate, Integer isclose) {
        this.id = id;
        this.poid = poid;
        this.payablesum = payablesum;
        this.paymode = paymode;
        this.billno = billno;
        this.payabledescribe = payabledescribe;
        this.alreadysum = alreadysum;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isclose = isclose;
    }

    /** default constructor */
    public Payable() {
    }

    /** minimal constructor */
    public Payable(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoid() {
        return this.poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public Double getPayablesum() {
        return this.payablesum;
    }

    public void setPayablesum(Double payablesum) {
        this.payablesum = payablesum;
    }

    public Integer getPaymode() {
        return this.paymode;
    }

    public void setPaymode(Integer paymode) {
        this.paymode = paymode;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getPayabledescribe() {
        return this.payabledescribe;
    }

    public void setPayabledescribe(String payabledescribe) {
        this.payabledescribe = payabledescribe;
    }

    public Double getAlreadysum() {
        return this.alreadysum;
    }

    public void setAlreadysum(Double alreadysum) {
        this.alreadysum = alreadysum;
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

    public Integer getIsclose() {
        return this.isclose;
    }

    public void setIsclose(Integer isclose) {
        this.isclose = isclose;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Payable) ) return false;
        Payable castOther = (Payable) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public Date getAwakedate() {
		return awakedate;
	}

	public void setAwakedate(Date awakedate) {
		this.awakedate = awakedate;
	}

}
