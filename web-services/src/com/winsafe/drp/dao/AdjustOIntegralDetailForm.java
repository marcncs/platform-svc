package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-4-3
 */
public class AdjustOIntegralDetailForm extends ActionForm {
	 /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String aoiid;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private String oname;

    /** nullable persistent field */
    private Double adjustintegral;
    
    private Double integral;

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
	 * @return the aoiid
	 */
	public String getAoiid() {
		return aoiid;
	}

	/**
	 * @param aoiid the aoiid to set
	 */
	public void setAoiid(String aoiid) {
		this.aoiid = aoiid;
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
	 * @return the oname
	 */
	public String getOname() {
		return oname;
	}

	/**
	 * @param oname the oname to set
	 */
	public void setOname(String oname) {
		this.oname = oname;
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
    
    
}
