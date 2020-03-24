package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FleeProduct implements Serializable {

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String queryid;

	/** nullable persistent field */
	private Integer province;

	/** nullable persistent field */
	private Integer findprovince;

	/** nullable persistent field */
	private String cid;

	/** nullable persistent field */
	private String syncode;

	/** nullable persistent field */
	private String cname;

	/** nullable persistent field */
	private String productid;

	/** nullable persistent field */
	private String productname;

	/** nullable persistent field */
	private String specmode;

	/** nullable persistent field */
	private String startno;

	/** nullable persistent field */
	private String endno;

	/** nullable persistent field */
	private Date senddate;

	/** nullable persistent field */
	private Integer isdeal;

	/** nullable persistent field */
	private String makeorganid;

	/** nullable persistent field */
	private Integer makedeptid;

	/** nullable persistent field */
	private Integer makeid;

	/** nullable persistent field */
	private Date makedate;

	private String cartoncode;

	private String firstorgan;

	private String secondorgan;

	private Date deliverdate;

	/** full constructor */
	public FleeProduct(Integer id, String queryid, Integer province, Integer findprovince,
			String cid, String syncode, String cname, String productid, String productname,
			String specmode, String startno, String endno, Date senddate, Integer isdeal,
			String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
		this.id = id;
		this.queryid = queryid;
		this.province = province;
		this.findprovince = findprovince;
		this.cid = cid;
		this.syncode = syncode;
		this.cname = cname;
		this.productid = productid;
		this.productname = productname;
		this.specmode = specmode;
		this.startno = startno;
		this.endno = endno;
		this.senddate = senddate;
		this.isdeal = isdeal;
		this.makeorganid = makeorganid;
		this.makedeptid = makedeptid;
		this.makeid = makeid;
		this.makedate = makedate;
	}

	/** default constructor */
	public FleeProduct() {
	}

	/** minimal constructor */
	public FleeProduct(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQueryid() {
		return this.queryid;
	}

	public void setQueryid(String queryid) {
		this.queryid = queryid;
	}

	public Integer getProvince() {
		return this.province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getFindprovince() {
		return this.findprovince;
	}

	public void setFindprovince(Integer findprovince) {
		this.findprovince = findprovince;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getSyncode() {
		return this.syncode;
	}

	public void setSyncode(String syncode) {
		this.syncode = syncode;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getProductid() {
		return this.productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return this.productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getSpecmode() {
		return this.specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public String getStartno() {
		return this.startno;
	}

	public void setStartno(String startno) {
		this.startno = startno;
	}

	public String getEndno() {
		return this.endno;
	}

	public void setEndno(String endno) {
		this.endno = endno;
	}

	public Date getSenddate() {
		return this.senddate;
	}

	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}

	public Integer getIsdeal() {
		return this.isdeal;
	}

	public void setIsdeal(Integer isdeal) {
		this.isdeal = isdeal;
	}

	public String getMakeorganid() {
		return this.makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public Integer getMakedeptid() {
		return this.makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public Integer getMakeid() {
		return this.makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public Date getMakedate() {
		return this.makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof FleeProduct))
			return false;
		FleeProduct castOther = (FleeProduct) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String getCartoncode() {
		return cartoncode;
	}

	public void setCartoncode(String cartoncode) {
		this.cartoncode = cartoncode;
	}

	public String getFirstorgan() {
		return firstorgan;
	}

	public void setFirstorgan(String firstorgan) {
		this.firstorgan = firstorgan;
	}

	public String getSecondorgan() {
		return secondorgan;
	}

	public void setSecondorgan(String secondorgan) {
		this.secondorgan = secondorgan;
	}

	public Date getDeliverdate() {
		return deliverdate;
	}

	public void setDeliverdate(Date deliverdate) {
		this.deliverdate = deliverdate;
	}

}
