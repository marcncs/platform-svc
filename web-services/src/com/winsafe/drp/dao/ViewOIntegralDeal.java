package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewOIntegralDeal implements Serializable {

	private VOIDPK voidPK;
	
	/** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private Double dealintegral;

    /** nullable persistent field */
    private Double completeintegral;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public ViewOIntegralDeal(String billno, String oid, Double dealintegral, Double completeintegral, Date makedate) {
        this.billno = billno;
        this.oid = oid;
        this.dealintegral = dealintegral;
        this.completeintegral = completeintegral;
        this.makedate = makedate;
    }

    /** default constructor */
    public ViewOIntegralDeal() {
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getOid() {
        return this.oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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

	/**
	 * @return the voidPK
	 */
	public VOIDPK getVoidPK() {
		return voidPK;
	}

	/**
	 * @param voidPK the voidPK to set
	 */
	public void setVoidPK(VOIDPK voidPK) {
		this.voidPK = voidPK;
	}
    
    

}
