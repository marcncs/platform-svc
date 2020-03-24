package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PurchasePlan implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String billno;
    
    private Integer purchasesort;

    /** nullable persistent field */
    private Date plandate;

    /** nullable persistent field */
    private Integer plandept;

    /** nullable persistent field */
    private Integer planid;
    
    private Double totalsum;

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
    private Integer isratify;

    /** nullable persistent field */
    private Integer ratifyid;

    /** nullable persistent field */
    private Date ratifydate;

    /** nullable persistent field */
    private Integer iscomplete;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String keyscontent;

    /** full constructor */
    public PurchasePlan(String id, String billno,Integer purchasesort, Date plandate, Integer plandept, Integer planid, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isaudit, Integer auditid, Date auditdate, Integer isratify, Integer ratifyid, Date ratifydate, Integer iscomplete, String remark, String keyscontent) {
        this.id = id;
        this.billno = billno;
        this.purchasesort = purchasesort;
        this.plandate = plandate;
        this.plandept = plandept;
        this.planid = planid;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isratify = isratify;
        this.ratifyid = ratifyid;
        this.ratifydate = ratifydate;
        this.iscomplete = iscomplete;
        this.remark = remark;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public PurchasePlan() {
    }

    /** minimal constructor */
    public PurchasePlan(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }
    
    
    public Integer getPurchasesort() {
		return purchasesort;
	}

	public void setPurchasesort(Integer purchasesort) {
		this.purchasesort = purchasesort;
	}

	public Date getPlandate() {
        return this.plandate;
    }

    public void setPlandate(Date plandate) {
        this.plandate = plandate;
    }

    public Integer getPlandept() {
        return this.plandept;
    }

    public void setPlandept(Integer plandept) {
        this.plandept = plandept;
    }

    public Integer getPlanid() {
        return this.planid;
    }

    public void setPlanid(Integer planid) {
        this.planid = planid;
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

    public Integer getIsratify() {
        return this.isratify;
    }

    public void setIsratify(Integer isratify) {
        this.isratify = isratify;
    }

    public Integer getRatifyid() {
        return this.ratifyid;
    }

    public void setRatifyid(Integer ratifyid) {
        this.ratifyid = ratifyid;
    }

    public Date getRatifydate() {
        return this.ratifydate;
    }

    public void setRatifydate(Date ratifydate) {
        this.ratifydate = ratifydate;
    }

    public Integer getIscomplete() {
        return this.iscomplete;
    }

    public void setIscomplete(Integer iscomplete) {
        this.iscomplete = iscomplete;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKeyscontent() {
        return this.keyscontent;
    }

    public void setKeyscontent(String keyscontent) {
        this.keyscontent = keyscontent;
    }
    
    

    public Double getTotalsum() {
		return totalsum;
	}

	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PurchasePlan) ) return false;
        PurchasePlan castOther = (PurchasePlan) other;
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
