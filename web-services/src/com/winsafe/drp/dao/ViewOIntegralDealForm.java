package com.winsafe.drp.dao;


public class ViewOIntegralDealForm {
	/** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String oid;
    
    private String oidname;

    /** nullable persistent field */
    private Double dealintegral;

    /** nullable persistent field */
    private Double completeintegral;

    /** nullable persistent field */
    private String makedate;
    
    private Double tiaozheng;
    
    private Double leiji;

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}
	
	


	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * @return the oidname
	 */
	public String getOidname() {
		return oidname;
	}

	/**
	 * @param oidname the oidname to set
	 */
	public void setOidname(String oidname) {
		this.oidname = oidname;
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

	/**
	 * @return the tiaozheng
	 */
	public Double getTiaozheng() {
		return tiaozheng;
	}

	/**
	 * @param tiaozheng the tiaozheng to set
	 */
	public void setTiaozheng(Double tiaozheng) {
		this.tiaozheng = tiaozheng;
	}

	/**
	 * @return the leiji
	 */
	public Double getLeiji() {
		return leiji;
	}

	/**
	 * @param leiji the leiji to set
	 */
	public void setLeiji(Double leiji) {
		this.leiji = leiji;
	}
	
	

}
