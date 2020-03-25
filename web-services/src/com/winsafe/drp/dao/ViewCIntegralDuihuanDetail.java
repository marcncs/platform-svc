package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewCIntegralDuihuanDetail implements Serializable {

    /** nullable persistent field */
    private Long id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private Date makedate;
    
    private String strmakedate;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Double dealintegral;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String makeorganid;

    /** full constructor */
    public ViewCIntegralDuihuanDetail(Long id, String cid, String organid, Date makedate, String billno, Double dealintegral, String cname, String mobile, String makeorganid) {
        this.id = id;
        this.cid = cid;
        this.organid = organid;
        this.makedate = makedate;
        this.billno = billno;
        this.dealintegral = dealintegral;
        this.cname = cname;
        this.mobile = mobile;
        this.makeorganid = makeorganid;
    }

    /** default constructor */
    public ViewCIntegralDuihuanDetail() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public Double getDealintegral() {
        return this.dealintegral;
    }

    public void setDealintegral(Double dealintegral) {
        this.dealintegral = dealintegral;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

	/**
	 * @return the strmakedate
	 */
	public String getStrmakedate() {
		return strmakedate;
	}

	/**
	 * @param strmakedate the strmakedate to set
	 */
	public void setStrmakedate(String strmakedate) {
		this.strmakedate = strmakedate;
	}
    
    

}
