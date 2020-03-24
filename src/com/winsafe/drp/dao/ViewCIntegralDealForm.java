package com.winsafe.drp.dao;


public class ViewCIntegralDealForm {
	/** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String organid;
    
    private String organidname;

    /** nullable persistent field */
    private String cid;
    
    private String cidname;

    /** nullable persistent field */
    private Double dealintegral;

    /** nullable persistent field */
    private Double completeintegral;

    /** nullable persistent field */
    private String makedate;
    
    private String mobile;

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCidname() {
		return cidname;
	}

	public void setCidname(String cidname) {
		this.cidname = cidname;
	}

	public Double getCompleteintegral() {
		return completeintegral;
	}

	public void setCompleteintegral(Double completeintegral) {
		this.completeintegral = completeintegral;
	}

	public Double getDealintegral() {
		return dealintegral;
	}

	public void setDealintegral(Double dealintegral) {
		this.dealintegral = dealintegral;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

	public String getOrganidname() {
		return organidname;
	}

	public void setOrganidname(String organidname) {
		this.organidname = organidname;
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
