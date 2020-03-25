package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-6-24
 */
public class IdcodeDetailForm extends ActionForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String billid;

    /** nullable persistent field */
    private String idcode;

    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private String makeuser;

    /** nullable persistent field */
    private Integer idbilltype;
    
    private String idbilltypename;
    
    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private String equiporganid;
    
    private String equiporganidname;

    /** nullable persistent field */
    private Long warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String cmobile;
    
    private String provideid;
    
    private String providename;
    
    private Integer isuse;
    
    private String isusename;

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
	 * @return the productid
	 */
	public String getProductid() {
		return productid;
	}

	/**
	 * @param productid the productid to set
	 */
	public void setProductid(String productid) {
		this.productid = productid;
	}

	/**
	 * @return the productname
	 */
	public String getProductname() {
		return productname;
	}

	/**
	 * @param productname the productname to set
	 */
	public void setProductname(String productname) {
		this.productname = productname;
	}

	/**
	 * @return the specmode
	 */
	public String getSpecmode() {
		return specmode;
	}

	/**
	 * @param specmode the specmode to set
	 */
	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	/**
	 * @return the billid
	 */
	public String getBillid() {
		return billid;
	}

	/**
	 * @param billid the billid to set
	 */
	public void setBillid(String billid) {
		this.billid = billid;
	}

	/**
	 * @return the idcode
	 */
	public String getIdcode() {
		return idcode;
	}

	/**
	 * @param idcode the idcode to set
	 */
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	/**
	 * @return the makedate
	 */
	public String getMakedate() {
		return makedate;
	}

	/**
	 * @param makedate the makedate to set
	 */
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	/**
	 * @return the makeid
	 */
	public Long getMakeid() {
		return makeid;
	}

	/**
	 * @param makeid the makeid to set
	 */
	public void setMakeid(Long makeid) {
		this.makeid = makeid;
	}

	/**
	 * @return the makeuser
	 */
	public String getMakeuser() {
		return makeuser;
	}

	/**
	 * @param makeuser the makeuser to set
	 */
	public void setMakeuser(String makeuser) {
		this.makeuser = makeuser;
	}

	/**
	 * @return the idbilltype
	 */
	public Integer getIdbilltype() {
		return idbilltype;
	}

	/**
	 * @param idbilltype the idbilltype to set
	 */
	public void setIdbilltype(Integer idbilltype) {
		this.idbilltype = idbilltype;
	}

	/**
	 * @return the idbilltypename
	 */
	public String getIdbilltypename() {
		return idbilltypename;
	}

	/**
	 * @param idbilltypename the idbilltypename to set
	 */
	public void setIdbilltypename(String idbilltypename) {
		this.idbilltypename = idbilltypename;
	}

	/**
	 * @return the makeorganid
	 */
	public String getMakeorganid() {
		return makeorganid;
	}

	/**
	 * @param makeorganid the makeorganid to set
	 */
	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	/**
	 * @return the makeorganidname
	 */
	public String getMakeorganidname() {
		return makeorganidname;
	}

	/**
	 * @param makeorganidname the makeorganidname to set
	 */
	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

	/**
	 * @return the equiporganid
	 */
	public String getEquiporganid() {
		return equiporganid;
	}

	/**
	 * @param equiporganid the equiporganid to set
	 */
	public void setEquiporganid(String equiporganid) {
		this.equiporganid = equiporganid;
	}

	/**
	 * @return the equiporganidname
	 */
	public String getEquiporganidname() {
		return equiporganidname;
	}

	/**
	 * @param equiporganidname the equiporganidname to set
	 */
	public void setEquiporganidname(String equiporganidname) {
		this.equiporganidname = equiporganidname;
	}

	/**
	 * @return the warehouseid
	 */
	public Long getWarehouseid() {
		return warehouseid;
	}

	/**
	 * @param warehouseid the warehouseid to set
	 */
	public void setWarehouseid(Long warehouseid) {
		this.warehouseid = warehouseid;
	}

	/**
	 * @return the warehouseidname
	 */
	public String getWarehouseidname() {
		return warehouseidname;
	}

	/**
	 * @param warehouseidname the warehouseidname to set
	 */
	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
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
	 * @return the provideid
	 */
	public String getProvideid() {
		return provideid;
	}

	/**
	 * @param provideid the provideid to set
	 */
	public void setProvideid(String provideid) {
		this.provideid = provideid;
	}

	/**
	 * @return the providename
	 */
	public String getProvidename() {
		return providename;
	}

	/**
	 * @param providename the providename to set
	 */
	public void setProvidename(String providename) {
		this.providename = providename;
	}

	/**
	 * @return the isuse
	 */
	public Integer getIsuse() {
		return isuse;
	}

	/**
	 * @param isuse the isuse to set
	 */
	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}

	/**
	 * @return the isusename
	 */
	public String getIsusename() {
		return isusename;
	}

	/**
	 * @param isusename the isusename to set
	 */
	public void setIsusename(String isusename) {
		this.isusename = isusename;
	}
    
	
    
}
