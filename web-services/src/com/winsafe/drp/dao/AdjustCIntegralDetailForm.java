package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 */
public class AdjustCIntegralDetailForm extends ActionForm {

	 /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String aciid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String cmobile;

    /** nullable persistent field */
    private Double adjustintegral;
    
    private Double integral;
    
    private String oid;
    
    private String oidname;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the aciid
	 */
	public String getAciid() {
		return aciid;
	}

	/**
	 * @param aciid the aciid to set
	 */
	public void setAciid(String aciid) {
		this.aciid = aciid;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the cname
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * @return the cmobile
	 */
	public String getCmobile() {
		return cmobile;
	}

	/**
	 * @param cmobile the cmobile to set
	 */
	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}

	/**
	 * @return the adjustintegral
	 */
	public Double getAdjustintegral() {
		return adjustintegral;
	}

	/**
	 * @param adjustintegral the adjustintegral to set
	 */
	public void setAdjustintegral(Double adjustintegral) {
		this.adjustintegral = adjustintegral;
	}

	/**
	 * @return the integral
	 */
	public Double getIntegral() {
		return integral;
	}

	/**
	 * @param integral the integral to set
	 */
	public void setIntegral(Double integral) {
		this.integral = integral;
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
	
	
    
    
}
