package com.winsafe.drp.dao;

import java.util.List;

public class StockMoveTotal {
	private String cname;
	private List sodls;
	private Double subsum;
	private String strsubsum;
	private String makedate;
	
	private Double subqt;
	    
	private Double subtqt;
	
	
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
	/**
	 * @return the subqt
	 */
	public Double getSubqt() {
		return subqt;
	}
	/**
	 * @param subqt the subqt to set
	 */
	public void setSubqt(Double subqt) {
		this.subqt = subqt;
	}
	/**
	 * @return the subtqt
	 */
	public Double getSubtqt() {
		return subtqt;
	}
	/**
	 * @param subtqt the subtqt to set
	 */
	public void setSubtqt(Double subtqt) {
		this.subtqt = subtqt;
	}
	
	
	
}
