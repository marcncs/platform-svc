package com.winsafe.drp.dao;


public class PayoutWasteObject {
	private String makedate;
	private String id;
	private String poid;
	private String makeid;
	private Integer paymode;
	private String memo;
	private Double payablesum;
	private Double paysum;
	private String makeorganid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Double getPayablesum() {
		return payablesum;
	}
	public void setPayablesum(Double payablesum) {
		this.payablesum = payablesum;
	}
	public Integer getPaymode() {
		return paymode;
	}
	public void setPaymode(Integer paymode) {
		this.paymode = paymode;
	}
	public Double getPaysum() {
		return paysum;
	}
	public void setPaysum(Double paysum) {
		this.paysum = paysum;
	}
	public String getPoid() {
		return poid;
	}
	public void setPoid(String poid) {
		this.poid = poid;
	}
	public String getMakedate() {
		return makedate;
	}
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}
	public String getMakeid() {
		return makeid;
	}
	public void setMakeid(String makeid) {
		this.makeid = makeid;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @return the makeorganid
	 */
	public String getMakeorganid() {
		return makeorganid;
	}
	/**
	 * @param makeorganid the makeorganid to set
	 */
	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}
	
	
	
	
}
