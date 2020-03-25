package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class ViewCIntegralDeal extends ActionForm implements Serializable {

	private VCIDPK vcidPK; 
	
    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private Double dealintegral;

    /** nullable persistent field */
    private Double completeintegral;

    /** nullable persistent field */
    private Date makedate;
    
    private String mobile;

    /** full constructor */
    public ViewCIntegralDeal(String billno, String organid, String cid, Double dealintegral, Double completeintegral, Date makedate) {
        this.billno = billno;
        this.organid = organid;
        this.cid = cid;
        this.dealintegral = dealintegral;
        this.completeintegral = completeintegral;
        this.makedate = makedate;
    }

    /** default constructor */
    public ViewCIntegralDeal() {
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Double getDealintegral() {
        return this.dealintegral;
    }

    public void setDealintegral(Double dealintegral) {
        this.dealintegral = dealintegral;
    }

    public Double getCompleteintegral() {
        return this.completeintegral;
    }

    public void setCompleteintegral(Double completeintegral) {
        this.completeintegral = completeintegral;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

	public VCIDPK getVcidPK() {
		return vcidPK;
	}

	public void setVcidPK(VCIDPK vcidPK) {
		this.vcidPK = vcidPK;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	

}
