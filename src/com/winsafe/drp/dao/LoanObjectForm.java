package com.winsafe.drp.dao;

import java.util.Date;

public class LoanObjectForm {

	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer uid;
    
    private String uidname;
    
    private Double totalloansum;
    
    private Double totalbacksum;
    
    private Double waitbacksum;

    /** nullable persistent field */
    private String promisedate;
    
    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;

    private String makeidname;
    
    /** nullable persistent field */
    private Date makedate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPromisedate() {
		return promisedate;
	}

	public void setPromisedate(String promisedate) {
		this.promisedate = promisedate;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUidname() {
		return uidname;
	}

	public void setUidname(String uidname) {
		this.uidname = uidname;
	}

	public Double getTotalbacksum() {
		return totalbacksum;
	}

	public void setTotalbacksum(Double totalbacksum) {
		this.totalbacksum = totalbacksum;
	}

	public Double getTotalloansum() {
		return totalloansum;
	}

	public void setTotalloansum(Double totalloansum) {
		this.totalloansum = totalloansum;
	}

	public Double getWaitbacksum() {
		return waitbacksum;
	}

	public void setWaitbacksum(Double waitbacksum) {
		this.waitbacksum = waitbacksum;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakedeptidname() {
		return makedeptidname;
	}

	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
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
