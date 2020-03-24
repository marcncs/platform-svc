package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-3-22
 */
public class PayoutWasteForm extends ActionForm {

	private String makedate;
	private String id;
	private String poid;
	private String poidname;
	private String makeorganid;
	private String makeorganidname;
	private Integer makeid;
	private String makeidname;
	private Integer paymode;
	private String paymodename;
	private String memo;
	private Double payablesum;
	private Double paysum;
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
	 * @return the poid
	 */
	public String getPoid() {
		return poid;
	}
	/**
	 * @param poid the poid to set
	 */
	public void setPoid(String poid) {
		this.poid = poid;
	}
	/**
	 * @return the poidname
	 */
	public String getPoidname() {
		return poidname;
	}
	/**
	 * @param poidname the poidname to set
	 */
	public void setPoidname(String poidname) {
		this.poidname = poidname;
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
	 * @return the paymode
	 */
	public Integer getPaymode() {
		return paymode;
	}
	/**
	 * @param paymode the paymode to set
	 */
	public void setPaymode(Integer paymode) {
		this.paymode = paymode;
	}
	/**
	 * @return the paymodename
	 */
	public String getPaymodename() {
		return paymodename;
	}
	/**
	 * @param paymodename the paymodename to set
	 */
	public void setPaymodename(String paymodename) {
		this.paymodename = paymodename;
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
	 * @return the payablesum
	 */
	public Double getPayablesum() {
		return payablesum;
	}
	/**
	 * @param payablesum the payablesum to set
	 */
	public void setPayablesum(Double payablesum) {
		this.payablesum = payablesum;
	}
	/**
	 * @return the paysum
	 */
	public Double getPaysum() {
		return paysum;
	}
	/**
	 * @param paysum the paysum to set
	 */
	public void setPaysum(Double paysum) {
		this.paysum = paysum;
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
