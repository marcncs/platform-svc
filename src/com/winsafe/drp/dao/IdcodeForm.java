package com.winsafe.drp.dao;

public class IdcodeForm {

	 private Long id;

    /** nullable persistent field */
    private String productid;
    
    private String productidname;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String idcode;

    /** nullable persistent field */
    private Integer isuse;
    
    private String isusename;

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdcode() {
		return idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	public Integer getIsuse() {
		return isuse;
	}

	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}

	public String getIsusename() {
		return isusename;
	}

	public void setIsusename(String isusename) {
		this.isusename = isusename;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductidname() {
		return productidname;
	}

	public void setProductidname(String productidname) {
		this.productidname = productidname;
	}
    
    
}
