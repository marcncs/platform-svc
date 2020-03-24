package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IncomeLog implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String roid;

    /** nullable persistent field */
    private String drawee;

    /** nullable persistent field */
    private Integer fundattach;

    /** nullable persistent field */
    private Double incomesum;
    
    private Double alreadyspend;
    
    private Date spenddate;

    /** nullable persistent field */
    private String billnum;
    
    private String voucher;

    /** nullable persistent field */
    private String remark;
    
    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isreceive;

    /** nullable persistent field */
    private Integer receiveid;

    /** nullable persistent field */
    private Date receivedate;
    
    private Integer paymentmode;
    
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

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	/** full constructor */
    public IncomeLog(String id, String roid, String drawee, Integer fundattach, Double incomesum, String billnum, String remark, Integer makeid, Date makedate, Integer isaudit, Integer auditid, Date auditdate, Integer isreceive, Integer receiveid, Date receivedate) {
        this.id = id;
        this.roid = roid;
        this.drawee = drawee;
        this.fundattach = fundattach;
        this.incomesum = incomesum;
        this.billnum = billnum;
        this.remark = remark;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isreceive = isreceive;
        this.receiveid = receiveid;
        this.receivedate = receivedate;
    }

    /** default constructor */
    public IncomeLog() {
    }

    /** minimal constructor */
    public IncomeLog(String id) {
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

    public String getDrawee() {
        return this.drawee;
    }

    public void setDrawee(String drawee) {
        this.drawee = drawee;
    }

    public Integer getFundattach() {
        return this.fundattach;
    }

    public void setFundattach(Integer fundattach) {
        this.fundattach = fundattach;
    }

    public Double getIncomesum() {
        return this.incomesum;
    }

    public void setIncomesum(Double incomesum) {
        this.incomesum = incomesum;
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

    public Integer getIsreceive() {
        return this.isreceive;
    }

    public void setIsreceive(Integer isreceive) {
        this.isreceive = isreceive;
    }

    public Integer getReceiveid() {
        return this.receiveid;
    }

    public void setReceiveid(Integer receiveid) {
        this.receiveid = receiveid;
    }

    public Date getReceivedate() {
        return this.receivedate;
    }

    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IncomeLog) ) return false;
        IncomeLog castOther = (IncomeLog) other;
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

	public Date getSpenddate() {
		return spenddate;
	}

	public void setSpenddate(Date spenddate) {
		this.spenddate = spenddate;
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
