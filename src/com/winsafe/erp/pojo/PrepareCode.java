package com.winsafe.erp.pojo;

import java.io.Serializable;
import java.util.Date;

public class PrepareCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private Integer sapLogID;
	private Integer isuse;
	private String organid;
	private Date modifiedDate;
	private Integer modifiedUser;
	private Integer productPlanId;
	private String tcode;
	private Integer isRelease;
	
    public PrepareCode(String code, Integer sapLogID, Integer isuse, String organid, Date modifiedDate, Integer modifiedUser, 
    		Integer productPlanId,String tcode,Integer isRelease) {
        this.code = code;
        this.sapLogID = sapLogID;
        this.isuse = isuse;
        this.organid = organid;
        this.modifiedDate = modifiedDate;
        this.modifiedUser = modifiedUser;
        this.productPlanId = productPlanId;
        this.tcode = tcode;
        this.isRelease = isRelease;
    }
    /** default constructor */
    public PrepareCode() {
    }

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Integer getSapLogID() {
		return sapLogID;
	}


	public void setSapLogID(Integer sapLogID) {
		this.sapLogID = sapLogID;
	}


	public Integer getIsuse() {
		return isuse;
	}


	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}


	public String getOrganid() {
		return organid;
	}


	public void setOrganid(String organid) {
		this.organid = organid;
	}


	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public Integer getModifiedUser() {
		return modifiedUser;
	}


	public void setModifiedUser(Integer modifiedUser) {
		this.modifiedUser = modifiedUser;
	}


	public Integer getProductPlanId() {
		return productPlanId;
	}


	public void setProductPlanId(Integer productPlanId) {
		this.productPlanId = productPlanId;
	}
	
	public String getTcode() {
		return tcode;
	}
	public void setTcode(String tcode) {
		this.tcode = tcode;
	}
	public Integer getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}


}
