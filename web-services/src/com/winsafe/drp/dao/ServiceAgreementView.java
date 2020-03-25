package com.winsafe.drp.dao;

import java.util.Date;

/**
 * @author : jerry
 * @version : 2009-7-28 下午05:18:33
 * www.winsafe.cn
 */
public class ServiceAgreementView {
	
	
	private Integer id;
	private Integer objsort;
	private String cid;
	private String cname;
	private Integer scontent;
	private Integer sstatus;
	private Integer rank;
	private Date sdate;
	private String makeorganid;
	private Integer makeid;
	private Integer isaffirm;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getObjsort() {
		return objsort;
	}
	public void setObjsort(Integer objsort) {
		this.objsort = objsort;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Integer getScontent() {
		return scontent;
	}
	public void setScontent(Integer scontent) {
		this.scontent = scontent;
	}
	public Integer getSstatus() {
		return sstatus;
	}
	public void setSstatus(Integer sstatus) {
		this.sstatus = sstatus;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public String getMakeorganid() {
		return makeorganid;
	}
	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}
	public Integer getMakeid() {
		return makeid;
	}
	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}
	public Integer getIsaffirm() {
		return isaffirm;
	}
	public void setIsaffirm(Integer isaffirm) {
		this.isaffirm = isaffirm;
	}
	
	
	

}
