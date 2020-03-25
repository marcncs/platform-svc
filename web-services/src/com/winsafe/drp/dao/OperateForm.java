package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-3-1
 */
public class OperateForm extends ActionForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String operatename;

    /** nullable persistent field */
    private Integer moduleid;
    
    private Integer ispd;

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
	 * @return the operatename
	 */
	public String getOperatename() {
		return operatename;
	}

	/**
	 * @param operatename the operatename to set
	 */
	public void setOperatename(String operatename) {
		this.operatename = operatename;
	}

	/**
	 * @return the moduleid
	 */
	public Integer getModuleid() {
		return moduleid;
	}

	/**
	 * @param moduleid the moduleid to set
	 */
	public void setModuleid(Integer moduleid) {
		this.moduleid = moduleid;
	}

	/**
	 * @return the ispd
	 */
	public Integer getIspd() {
		return ispd;
	}

	/**
	 * @param ispd the ispd to set
	 */
	public void setIspd(Integer ispd) {
		this.ispd = ispd;
	}
    
    
}
