package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Receivable implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String roid;

    /** nullable persistent field */
    private Double receivablesum;

    /** nullable persistent field */
    private Integer paymentmode;
    
    private Date awakedate;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String receivabledescribe;

    /** nullable persistent field */
    private Double alreadysum;

    /** nullable persistent field */
    private Integer isclose;
    
    private Date closedate;
    
    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public Receivable(String id, String roid, Double receivablesum, Integer paymentmode, String billno, String receivabledescribe, Double alreadysum, Integer isclose, Integer makeid, Date makedate) {
        this.id = id;
        this.roid = roid;
        this.receivablesum = receivablesum;
        this.paymentmode = paymentmode;
        this.billno = billno;
        this.receivabledescribe = receivabledescribe;
        this.alreadysum = alreadysum;
        this.isclose = isclose;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public Receivable() {
    }

    /** minimal constructor */
    public Receivable(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoid() {
        return this.roid;
    }

    public void setRoid(String roid) {
        this.roid = roid;
    }

    public Double getReceivablesum() {
        return this.receivablesum;
    }

    public void setReceivablesum(Double receivablesum) {
        this.receivablesum = receivablesum;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getReceivabledescribe() {
        return this.receivabledescribe;
    }

    public void setReceivabledescribe(String receivabledescribe) {
        this.receivabledescribe = receivabledescribe;
    }

    public Double getAlreadysum() {
        return this.alreadysum;
    }

    public void setAlreadysum(Double alreadysum) {
        this.alreadysum = alreadysum;
    }

    public Integer getIsclose() {
        return this.isclose;
    }

    public void setIsclose(Integer isclose) {
        this.isclose = isclose;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Receivable) ) return false;
        Receivable castOther = (Receivable) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Date getClosedate() {
		return closedate;
	}

	public void setClosedate(Date closedate) {
		this.closedate = closedate;
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
