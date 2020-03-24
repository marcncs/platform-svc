package com.winsafe.erp.pojo;

public class ProductConfig {
	private long id;
	//机构编码
	private String organId;
	//系统中产品编码
	private String productId;
	//IS系统对应物料号
	private String mCode;
	//文件中的产品编码
	private String erpProductId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getmCode() {
		return mCode;
	}
	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
	public void setErpProductId(String erpProductId) {
		this.erpProductId = erpProductId;
	}
	public String getErpProductId() {
		return erpProductId;
	}
}
