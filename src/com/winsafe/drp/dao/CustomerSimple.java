package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class CustomerSimple extends ActionForm implements Serializable {

    /** identifier field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String tickettitle;
    
    private Double integral;

    /** nullable persistent field */
    private Integer rate;
    
    private String ratename;
    
    private Integer policyid;

    private String policyidname;
    
    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private Integer transportmode;

        /** nullable persistent field */
    private String commaddr;

    /** nullable persistent field */
    private String detailaddr;

        /** nullable persistent field */
    private String officetel;

    /** nullable persistent field */
    private String mobile;


	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCommaddr() {
		return commaddr;
	}

	public void setCommaddr(String commaddr) {
		this.commaddr = commaddr;
	}

	public String getDetailaddr() {
		return detailaddr;
	}

	public void setDetailaddr(String detailaddr) {
		this.detailaddr = detailaddr;
	}

	public Double getIntegral() {
		return integral;
	}

	public void setIntegral(Double integral) {
		this.integral = integral;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficetel() {
		return officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public Integer getPolicyid() {
		return policyid;
	}

	public void setPolicyid(Integer policyid) {
		this.policyid = policyid;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getRatename() {
		return ratename;
	}

	public void setRatename(String ratename) {
		this.ratename = ratename;
	}

	public String getTickettitle() {
		return tickettitle;
	}

	public void setTickettitle(String tickettitle) {
		this.tickettitle = tickettitle;
	}

	public Integer getTransportmode() {
		return transportmode;
	}

	public void setTransportmode(Integer transportmode) {
		this.transportmode = transportmode;
	}

	public String getPolicyidname() {
		return policyidname;
	}

	public void setPolicyidname(String policyidname) {
		this.policyidname = policyidname;
	}

    
}
