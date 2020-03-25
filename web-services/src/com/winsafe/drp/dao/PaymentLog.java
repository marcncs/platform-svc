package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PaymentLog implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private String payee;

    /** nullable persistent field */
    private String paypurpose;

    /** nullable persistent field */
    private Integer paymode;

    /** nullable persistent field */
    private Double paysum;
    
    private Double alreadyspend;
    private Date spenddate;
    
    private String voucher;

    /** nullable persistent field */
    private String billnum;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer ispay;

    /** nullable persistent field */
    private Integer payid;

    /** nullable persistent field */
    private Date paydate;
    
    private String makeorganid;
    
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;
    
    private Integer fundsrc;
    
    private String keyscontent;
    
    
    /**
	 * @return the keyscontent
	 */
	public String getKeyscontent() {
		return keyscontent;
	}

	/**
	 * @param keyscontent the keyscontent to set
	 */
	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}

	public Date getSpenddate() {
		return spenddate;
	}

	public void setSpenddate(Date spenddate) {
		this.spenddate = spenddate;
	}

    public Integer getFundsrc() {
		return fundsrc;
	}

	public void setFundsrc(Integer fundsrc) {
		this.fundsrc = fundsrc;
	}

	/** full constructor */
    public PaymentLog(String id, String poid, String payee, String paypurpose, Integer paymode, Double paysum, String billnum, String remark, Integer isaudit, Integer auditid, Date auditdate, Integer ispay, Integer payid, Date paydate, Integer makeid, Date makedate) {
        this.id = id;
        this.poid = poid;
        this.payee = payee;
        this.paypurpose = paypurpose;
        this.paymode = paymode;
        this.paysum = paysum;
        this.billnum = billnum;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.ispay = ispay;
        this.payid = payid;
        this.paydate = paydate;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public PaymentLog() {
    }

    /** minimal constructor */
    public PaymentLog(String id) {
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

    public String getPayee() {
        return this.payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPaypurpose() {
        return this.paypurpose;
    }

    public void setPaypurpose(String paypurpose) {
        this.paypurpose = paypurpose;
    }

    public Integer getPaymode() {
        return this.paymode;
    }

    public void setPaymode(Integer paymode) {
        this.paymode = paymode;
    }

    public Double getPaysum() {
        return this.paysum;
    }

    public void setPaysum(Double paysum) {
        this.paysum = paysum;
    }

    public String getBillnum() {
        return this.billnum;
    }

    public void setBillnum(String billnum) {
        this.billnum = billnum;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsaudit() {
        return this.isaudit;
    }

    public void setIsaudit(Integer isaudit) {
        this.isaudit = isaudit;
    }

    public Integer getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Integer auditid) {
        this.auditid = auditid;
    }

    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Integer getIspay() {
        return this.ispay;
    }

    public void setIspay(Integer ispay) {
        this.ispay = ispay;
    }

    public Integer getPayid() {
        return this.payid;
    }

    public void setPayid(Integer payid) {
        this.payid = payid;
    }

    public Date getPaydate() {
        return this.paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
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
        if ( !(other instanceof PaymentLog) ) return false;
        PaymentLog castOther = (PaymentLog) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Double getAlreadyspend() {
		return alreadyspend;
	}

	public void setAlreadyspend(Double alreadyspend) {
		this.alreadyspend = alreadyspend;
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

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

}
