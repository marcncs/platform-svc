package com.winsafe.erp.pojo;

import java.io.Serializable;
import java.util.Date;

public class PrepareCodeForm implements Serializable {

	/**
	 * 
	 */
	private String code;
	private String sapLogID;
	private Integer isuse;
	private String organid;
	private String modifiedDate;
	private Integer modifiedUser;
	private Integer productPlanId;
	private String tcode;
	private String username;
	private String isRelease;

    /** default constructor */
    public PrepareCodeForm() {
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTcode() {
		return tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

	public String getSapLogID() {
		return sapLogID;
	}


	public void setSapLogID(String sapLogID) {
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


	public String getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(String modifiedDate) {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}


}
