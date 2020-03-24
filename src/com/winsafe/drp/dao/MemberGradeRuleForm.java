package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-3-3
 */
public class MemberGradeRuleForm extends ActionForm {

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Double startprice;

    /** nullable persistent field */
    private Double endprice;

    /** nullable persistent field */
    private Double startintegral;

    /** nullable persistent field */
    private Double endintegral;

    /** nullable persistent field */
    private Integer mgid;
    
    private String gradename;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the startprice
	 */
	public Double getStartprice() {
		return startprice;
	}

	/**
	 * @param startprice the startprice to set
	 */
	public void setStartprice(Double startprice) {
		this.startprice = startprice;
	}

	/**
	 * @return the endprice
	 */
	public Double getEndprice() {
		return endprice;
	}

	/**
	 * @param endprice the endprice to set
	 */
	public void setEndprice(Double endprice) {
		this.endprice = endprice;
	}

	/**
	 * @return the startintegral
	 */
	public Double getStartintegral() {
		return startintegral;
	}

	/**
	 * @param startintegral the startintegral to set
	 */
	public void setStartintegral(Double startintegral) {
		this.startintegral = startintegral;
	}

	/**
	 * @return the endintegral
	 */
	public Double getEndintegral() {
		return endintegral;
	}

	/**
	 * @param endintegral the endintegral to set
	 */
	public void setEndintegral(Double endintegral) {
		this.endintegral = endintegral;
	}

	/**
	 * @return the mgid
	 */
	public Integer getMgid() {
		return mgid;
	}

	/**
	 * @param mgid the mgid to set
	 */
	public void setMgid(Integer mgid) {
		this.mgid = mgid;
	}

	/**
	 * @return the gradename
	 */
	public String getGradename() {
		return gradename;
	}

	/**
	 * @param gradename the gradename to set
	 */
	public void setGradename(String gradename) {
		this.gradename = gradename;
	}
	
	
    
    

}
