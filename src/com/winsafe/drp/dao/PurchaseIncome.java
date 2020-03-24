package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PurchaseIncome implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private String provideid;

    /** nullable persistent field */
    private String providename;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer paymode;

    /** nullable persistent field */
    private Integer prompt;

    /** nullable persistent field */
    private Date incomedate;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer istally;

    /** nullable persistent field */
    private Integer tallyid;

    /** nullable persistent field */
    private Date tallydate;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer printtimes;

    /** nullable persistent field */
    private String keyscontent;
    
    private String nccode;

    /** full constructor */
    public PurchaseIncome(String id, String warehouseid, String poid, String provideid, String providename, String plinkman, String tel, Integer paymode, Integer prompt, Date incomedate, Double totalsum, String remark, Integer isaudit, Integer auditid, Date auditdate, Integer istally, Integer tallyid, Date tallydate, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer printtimes, String keyscontent,String nccode) {
        this.id = id;
        this.warehouseid = warehouseid;
        this.poid = poid;
        this.provideid = provideid;
        this.providename = providename;
        this.plinkman = plinkman;
        this.tel = tel;
        this.paymode = paymode;
        this.prompt = prompt;
        this.incomedate = incomedate;
        this.totalsum = totalsum;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.istally = istally;
        this.tallyid = tallyid;
        this.tallydate = tallydate;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.printtimes = printtimes;
        this.keyscontent = keyscontent;
        this.nccode=nccode;
    }

    /** default constructor */
    public PurchaseIncome() {
    }

    /** minimal constructor */
    public PurchaseIncome(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getPoid() {
        return this.poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public String getProvideid() {
        return this.provideid;
    }

    public void setProvideid(String provideid) {
        this.provideid = provideid;
    }

    public String getProvidename() {
        return this.providename;
    }

    public void setProvidename(String providename) {
        this.providename = providename;
    }

    public String getPlinkman() {
        return this.plinkman;
    }

    public void setPlinkman(String plinkman) {
        this.plinkman = plinkman;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getPaymode() {
        return this.paymode;
    }

    public void setPaymode(Integer paymode) {
        this.paymode = paymode;
    }

    public Integer getPrompt() {
        return this.prompt;
    }

    public void setPrompt(Integer prompt) {
        this.prompt = prompt;
    }

    public Date getIncomedate() {
        return this.incomedate;
    }

    public void setIncomedate(Date incomedate) {
        this.incomedate = incomedate;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
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

    public Integer getIstally() {
        return this.istally;
    }

    public void setIstally(Integer istally) {
        this.istally = istally;
    }

    public Integer getTallyid() {
        return this.tallyid;
    }

    public void setTallyid(Integer tallyid) {
        this.tallyid = tallyid;
    }

    public Date getTallydate() {
        return this.tallydate;
    }

    public void setTallydate(Date tallydate) {
        this.tallydate = tallydate;
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

    public Integer getPrinttimes() {
        return this.printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    public String getKeyscontent() {
        return this.keyscontent;
    }

    public void setKeyscontent(String keyscontent) {
        this.keyscontent = keyscontent;
    }

    
    public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PurchaseIncome) ) return false;
        PurchaseIncome castOther = (PurchaseIncome) other;
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
