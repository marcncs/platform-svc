package com.winsafe.drp.dao;

public class PeddleBalanceDetailForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String pbid;

    /** nullable persistent field */
    private Integer paymentmode;
    
    private String paymentmodename;

    /** nullable persistent field */
    private Double subsum;

    /** nullable persistent field */
    private Integer fundattach;
    
    private String fundattachname;

	public Integer getFundattach() {
		return fundattach;
	}

	public void setFundattach(Integer fundattach) {
		this.fundattach = fundattach;
	}

	public String getFundattachname() {
		return fundattachname;
	}

	public void setFundattachname(String fundattachname) {
		this.fundattachname = fundattachname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getPaymentmodename() {
		return paymentmodename;
	}

	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
	}

	public String getPbid() {
		return pbid;
	}

	public void setPbid(String pbid) {
		this.pbid = pbid;
	}

	public Double getSubsum() {
		return subsum;
	}

	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}
}
