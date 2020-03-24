package com.winsafe.drp.dao;
/**
 * @author : jerry
 * @version : 2009-9-15 下午03:08:53
 * www.winsafe.cn
 */
public class SupplySaleApplyDetailForm {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ssid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String unitidname;

    /** nullable persistent field */
    private Double punitprice;

    /** nullable persistent field */
    private Double sunitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double canquantity;

    /** nullable persistent field */
    private Double alreadyquantity;

    /** nullable persistent field */
    private Double psubsum;

    /** nullable persistent field */
    private Double ssubsum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public String getUnitidname() {
		return unitidname;
	}

	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}

	public Double getPunitprice() {
		return punitprice;
	}

	public void setPunitprice(Double punitprice) {
		this.punitprice = punitprice;
	}

	public Double getSunitprice() {
		return sunitprice;
	}

	public void setSunitprice(Double sunitprice) {
		this.sunitprice = sunitprice;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getCanquantity() {
		return canquantity;
	}

	public void setCanquantity(Double canquantity) {
		this.canquantity = canquantity;
	}

	public Double getAlreadyquantity() {
		return alreadyquantity;
	}

	public void setAlreadyquantity(Double alreadyquantity) {
		this.alreadyquantity = alreadyquantity;
	}

	public Double getPsubsum() {
		return psubsum;
	}

	public void setPsubsum(Double psubsum) {
		this.psubsum = psubsum;
	}

	public Double getSsubsum() {
		return ssubsum;
	}

	public void setSsubsum(Double ssubsum) {
		this.ssubsum = ssubsum;
	}
    
}
