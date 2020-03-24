package com.winsafe.drp.dao;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

public class ValidateProductPicture extends ValidatorForm{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -5555719590216835644L;

	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;

    private String productidname;
    
    /** nullable persistent field */
    private String pictureurl;
    
    private FormFile picture;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPictureurl() {
		return pictureurl;
	}

	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
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

	public FormFile getPicture() {
		return picture;
	}

	public void setPicture(FormFile picture) {
		this.picture = picture;
	}
}
