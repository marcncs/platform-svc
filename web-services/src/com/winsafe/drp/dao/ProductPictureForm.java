package com.winsafe.drp.dao;

public class ProductPictureForm {
	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String productid;

    private String productidname;
    
    /** nullable persistent field */
    private String pictureurl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
}
