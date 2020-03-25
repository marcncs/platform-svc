package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-3-22
 */
public class RevenueWasteForm extends ActionForm {

	private String makedate;
	private String id;
	private String roid;
	private String roidname;
	private String makeorganid;
	private String makeorganidname;
	private Integer makeid;
	private String makeidname;
	private Integer paymentmode;
	private String paymentmodename;
	private String memo;
	private Double receivablesum;
	private Double incomesum;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the roid
	 */
	public String getRoid() {
		return roid;
	}
	/**
	 * @param roid the roid to set
	 */
	public void setRoid(String roid) {
		this.roid = roid;
	}
	/**
	 * @return the roidname
	 */
	public String getRoidname() {
		return roidname;
	}
	/**
	 * @param roidname the roidname to set
	 */
	public void setRoidname(String roidname) {
		this.roidname = roidname;
	}
	/**
	 * @return the makeid
	 */
	public Integer getMakeid() {
		return makeid;
	}
	/**
	 * @param makeid the makeid to set
	 */
	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}
	/**
	 * @return the makeidname
	 */
	public String getMakeidname() {
		return makeidname;
	}
	/**
	 * @param makeidname the makeidname to set
	 */
	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}
	/**
	 * @return the paymentmode
	 */
	public Integer getPaymentmode() {
		return paymentmode;
	}
	/**
	 * @param paymentmode the paymentmode to set
	 */
	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}
	/**
	 * @return the paymentmodename
	 */
	public String getPaymentmodename() {
		return paymentmodename;
	}
	/**
	 * @param paymentmodename the paymentmodename to set
	 */
	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
	}
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @return the receivablesum
	 */
	public Double getReceivablesum() {
		return receivablesum;
	}
	/**
	 * @param receivablesum the receivablesum to set
	 */
	public void setReceivablesum(Double receivablesum) {
		this.receivablesum = receivablesum;
	}
	/**
	 * @return the incomesum
	 */
	public Double getIncomesum() {
		return incomesum;
	}
	/**
	 * @param incomesum the incomesum to set
	 */
	public void setIncomesum(Double incomesum) {
		this.incomesum = incomesum;
	}
	public String getMakeorganid() {
		return makeorganid;
	}
	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}
	public String getMakeorganidname() {
		return makeorganidname;
	}
	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}
	
	

}
