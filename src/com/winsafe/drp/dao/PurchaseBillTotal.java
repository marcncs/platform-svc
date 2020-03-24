package com.winsafe.drp.dao;

import java.util.List;

public class PurchaseBillTotal {
	private String cname;
	private List sodls;
	
	private String strsubsum;
	private String makedate;
	
	private String pid;
	private String pname;
	private String tel;
	private Double totalsum;
	
	
	private String productid;
	private String productname;
	private String specmode;
	private String unitidname;
	private Double quantity;
	private Double subsum;
	
	
	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}
	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the totalsum
	 */
	public Double getTotalsum() {
		return totalsum;
	}
	/**
	 * @param totalsum the totalsum to set
	 */
	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
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
	 * @return the unitidname
	 */
	public String getUnitidname() {
		return unitidname;
	}
	/**
	 * @param unitidname the unitidname to set
	 */
	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}
	/**
	 * @return the quantity
	 */
	public Double getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the strsubsum
	 */
	public String getStrsubsum() {
		return strsubsum;
	}
	/**
	 * @param strsubsum the strsubsum to set
	 */
	public void setStrsubsum(String strsubsum) {
		this.strsubsum = strsubsum;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public List getSodls() {
		return sodls;
	}
	public void setSodls(List sodls) {
		this.sodls = sodls;
	}
	public Double getSubsum() {
		return subsum;
	}
	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}
	/**
	 * @param makedate the makedate to set
	 */
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}
	/**
	 * @return the makedate
	 */
	public String getMakedate() {
		return makedate;
	}
	
}
