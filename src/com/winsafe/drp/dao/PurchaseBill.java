package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PurchaseBill implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String ppid;

    /** nullable persistent field */
    private Integer purchasesort;

    /** nullable persistent field */
    private String pid;
    
    private String pname;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer purchasedept;

    /** nullable persistent field */
    private Integer purchaseid;

    /** nullable persistent field */
    private Integer paymode;

    /** nullable persistent field */
    private Integer prompt;

    /** nullable persistent field */
    private Integer invmsg;

    /** nullable persistent field */
    private Integer ismaketicket;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Date receivedate;

    /** nullable persistent field */
    private String receiveaddr;

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
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer istransferadsum;

    /** nullable persistent field */
    private String keyscontent;

    /** full constructor */
    public PurchaseBill(String id, String ppid, Integer purchasesort, String pid, String plinkman, String tel, Integer purchasedept, Integer purchaseid, Integer paymode, Integer prompt, Integer invmsg, Integer ismaketicket, Double totalsum, Date receivedate, String receiveaddr, Integer isaudit, Integer auditid, Date auditdate, Integer isratify, Integer ratifyid, Date ratifydate, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, String remark, Integer istransferadsum, String keyscontent) {
        this.id = id;
        this.ppid = ppid;
        this.purchasesort = purchasesort;
        this.pid = pid;
        this.plinkman = plinkman;
        this.tel = tel;
        this.purchasedept = purchasedept;
        this.purchaseid = purchaseid;
        this.paymode = paymode;
        this.prompt = prompt;
        this.invmsg = invmsg;
        this.ismaketicket = ismaketicket;
        this.totalsum = totalsum;
        this.receivedate = receivedate;
        this.receiveaddr = receiveaddr;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isratify = isratify;
        this.ratifyid = ratifyid;
        this.ratifydate = ratifydate;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.remark = remark;
        this.istransferadsum = istransferadsum;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public PurchaseBill() {
    }

    /** minimal constructor */
    public PurchaseBill(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPpid() {
        return this.ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public Integer getPurchasesort() {
        return this.purchasesort;
    }

    public void setPurchasesort(Integer purchasesort) {
        this.purchasesort = purchasesort;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public Integer getPurchasedept() {
        return this.purchasedept;
    }

    public void setPurchasedept(Integer purchasedept) {
        this.purchasedept = purchasedept;
    }

    public Integer getPurchaseid() {
        return this.purchaseid;
    }

    public void setPurchaseid(Integer purchaseid) {
        this.purchaseid = purchaseid;
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

    public Integer getInvmsg() {
        return this.invmsg;
    }

    public void setInvmsg(Integer invmsg) {
        this.invmsg = invmsg;
    }

    public Integer getIsmaketicket() {
        return this.ismaketicket;
    }

    public void setIsmaketicket(Integer ismaketicket) {
        this.ismaketicket = ismaketicket;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
    }

    public Date getReceivedate() {
        return this.receivedate;
    }

    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }

    public String getReceiveaddr() {
        return this.receiveaddr;
    }

    public void setReceiveaddr(String receiveaddr) {
        this.receiveaddr = receiveaddr;
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

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIstransferadsum() {
        return this.istransferadsum;
    }

    public void setIstransferadsum(Integer istransferadsum) {
        this.istransferadsum = istransferadsum;
    }

    public String getKeyscontent() {
        return this.keyscontent;
    }

    public void setKeyscontent(String keyscontent) {
        this.keyscontent = keyscontent;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PurchaseBill) ) return false;
        PurchaseBill castOther = (PurchaseBill) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

}
