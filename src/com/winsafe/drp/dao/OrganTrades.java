package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrganTrades implements Serializable {

	/** identifier field */
	private String id;
	
	private String idii;

	/** nullable persistent field */
	private String porganid;

	/** nullable persistent field */
	private String porganname;

	/** nullable persistent field */
	private String plinkman;

	/** nullable persistent field */
	private String tel;
	/** nullable persistent field */
	private String rlinkman;

	/** nullable persistent field */
	private String rtel;

	/** nullable persistent field */
	private String transportaddr;

	/** nullable persistent field */
	private String rtransportaddr;

	/** nullable persistent field */
	private String outwarehouseid;
	
	private String poutwarehouseid;

	/** nullable persistent field */
	private String inwarehouseid;

	/** nullable persistent field */
	private String makeorganid;

	/** nullable persistent field */
	private Integer makedeptid;

	/** nullable persistent field */
	private Integer makeid;

	/** nullable persistent field */
	private Date makedate;

	/** nullable persistent field */
	private String withdrawcause;

	/** nullable persistent field */
	private Double totalsum;

	/** nullable persistent field */
	private Integer isaudit;

	/** nullable persistent field */
	private Integer auditid;

	/** nullable persistent field */
	private Date auditdate;

	/** nullable persistent field */
	private Integer isratify;

	/** nullable persistent field */
	private Integer ratifyid;

	/** nullable persistent field */
	private Date ratifydate;

	/** nullable persistent field */
	private Integer isaffirm;

	/** nullable persistent field */
	private Integer affirmid;

	/** nullable persistent field */
	private Date affirmdate;

	/** nullable persistent field */
	private Integer pisreceive;

	/** nullable persistent field */
	private Integer preceiveid;

	/** nullable persistent field */
	private Date preceivedate;

	/** nullable persistent field */
	private Integer pisaffirm;

	/** nullable persistent field */
	private Integer paffirmid;

	/** nullable persistent field */
	private Date paffirmdate;

	/** nullable persistent field */
	private Integer isreceive;

	/** nullable persistent field */
	private Integer receiveid;

	/** nullable persistent field */
	private Date receivedate;

	/** nullable persistent field */
	private Integer isblankout;

	/** nullable persistent field */
	private Integer blankoutid;

	/** nullable persistent field */
	private Date blankoutdate;

	/** nullable persistent field */
	private String blankoutreason;

	/** nullable persistent field */
	private Integer printtimes;

	/** nullable persistent field */
	private Integer takestatus;
	
	private Integer ptakestatus;

	/** nullable persistent field */
	private String keyscontent;

	public OrganTrades(String id, String porganid, String porganname,
			String plinkman, String tel, String rlinkman, String rtel,
			String transportaddr, String rtransportaddr, String outwarehouseid,
			String inwarehouseid, String makeorganid, Integer makedeptid,
			Integer makeid, Date makedate, String withdrawcause,
			Double totalsum, Integer isaudit, Integer auditid, Date auditdate,
			Integer isratify, Integer ratifyid, Date ratifydate,
			Integer isaffirm, Integer affirmid, Date affirmdate,
			Integer pisreceive, Integer preceiveid, Date preceivedate,
			Integer pisaffirm, Integer paffirmid, Date paffirmdate,
			Integer isreceive, Integer receiveid, Date receivedate,
			Integer isblankout, Integer blankoutid, Date blankoutdate,
			String blankoutreason, Integer printtimes, Integer takestatus) {
		super();
		this.id = id;
		this.porganid = porganid;
		this.porganname = porganname;
		this.plinkman = plinkman;
		this.tel = tel;
		this.rlinkman = rlinkman;
		this.rtel = rtel;
		this.transportaddr = transportaddr;
		this.rtransportaddr = rtransportaddr;
		this.outwarehouseid = outwarehouseid;
		this.inwarehouseid = inwarehouseid;
		this.makeorganid = makeorganid;
		this.makedeptid = makedeptid;
		this.makeid = makeid;
		this.makedate = makedate;
		this.withdrawcause = withdrawcause;
		this.totalsum = totalsum;
		this.isaudit = isaudit;
		this.auditid = auditid;
		this.auditdate = auditdate;
		this.isratify = isratify;
		this.ratifyid = ratifyid;
		this.ratifydate = ratifydate;
		this.isaffirm = isaffirm;
		this.affirmid = affirmid;
		this.affirmdate = affirmdate;
		this.pisreceive = pisreceive;
		this.preceiveid = preceiveid;
		this.preceivedate = preceivedate;
		this.pisaffirm = pisaffirm;
		this.paffirmid = paffirmid;
		this.paffirmdate = paffirmdate;
		this.isreceive = isreceive;
		this.receiveid = receiveid;
		this.receivedate = receivedate;
		this.isblankout = isblankout;
		this.blankoutid = blankoutid;
		this.blankoutdate = blankoutdate;
		this.blankoutreason = blankoutreason;
		this.printtimes = printtimes;
		this.takestatus = takestatus;
	}

	/** default constructor */
	public OrganTrades() {
	}

	/** minimal constructor */
	public OrganTrades(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPorganid() {
		return this.porganid;
	}

	public void setPorganid(String porganid) {
		this.porganid = porganid;
	}

	public String getPorganname() {
		return this.porganname;
	}

	public void setPorganname(String porganname) {
		this.porganname = porganname;
	}

	public String getPlinkman() {
		return this.plinkman;
	}

	public void setPlinkman(String plinkman) {
		this.plinkman = plinkman;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRlinkman() {
		return rlinkman;
	}

	public void setRlinkman(String rlinkman) {
		this.rlinkman = rlinkman;
	}

	public String getRtel() {
		return rtel;
	}

	public void setRtel(String rtel) {
		this.rtel = rtel;
	}

	public String getTransportaddr() {
		return this.transportaddr;
	}

	public void setTransportaddr(String transportaddr) {
		this.transportaddr = transportaddr;
	}

	public String getRtransportaddr() {
		return this.rtransportaddr;
	}

	public void setRtransportaddr(String rtransportaddr) {
		this.rtransportaddr = rtransportaddr;
	}

	public String getOutwarehouseid() {
		return this.outwarehouseid;
	}

	public void setOutwarehouseid(String outwarehouseid) {
		this.outwarehouseid = outwarehouseid;
	}

	public String getInwarehouseid() {
		return this.inwarehouseid;
	}

	public void setInwarehouseid(String inwarehouseid) {
		this.inwarehouseid = inwarehouseid;
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

	public String getWithdrawcause() {
		return this.withdrawcause;
	}

	public void setWithdrawcause(String withdrawcause) {
		this.withdrawcause = withdrawcause;
	}

	public Double getTotalsum() {
		return this.totalsum;
	}

	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public Integer getIsaudit() {
		return this.isaudit;
	}

	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	public Integer getAuditid() {
		return this.auditid;
	}

	public void setAuditid(Integer auditid) {
		this.auditid = auditid;
	}

	public Date getAuditdate() {
		return this.auditdate;
	}

	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}

	public Integer getIsratify() {
		return this.isratify;
	}

	public void setIsratify(Integer isratify) {
		this.isratify = isratify;
	}

	public Integer getRatifyid() {
		return this.ratifyid;
	}

	public void setRatifyid(Integer ratifyid) {
		this.ratifyid = ratifyid;
	}

	public Date getRatifydate() {
		return this.ratifydate;
	}

	public void setRatifydate(Date ratifydate) {
		this.ratifydate = ratifydate;
	}

	public Integer getIsaffirm() {
		return this.isaffirm;
	}

	public void setIsaffirm(Integer isaffirm) {
		this.isaffirm = isaffirm;
	}

	public Integer getAffirmid() {
		return this.affirmid;
	}

	public void setAffirmid(Integer affirmid) {
		this.affirmid = affirmid;
	}

	public Date getAffirmdate() {
		return this.affirmdate;
	}

	public void setAffirmdate(Date affirmdate) {
		this.affirmdate = affirmdate;
	}

	public Integer getPisreceive() {
		return this.pisreceive;
	}

	public void setPisreceive(Integer pisreceive) {
		this.pisreceive = pisreceive;
	}

	public Integer getPreceiveid() {
		return this.preceiveid;
	}

	public void setPreceiveid(Integer preceiveid) {
		this.preceiveid = preceiveid;
	}

	public Date getPreceivedate() {
		return this.preceivedate;
	}

	public void setPreceivedate(Date preceivedate) {
		this.preceivedate = preceivedate;
	}

	public Integer getPisaffirm() {
		return this.pisaffirm;
	}

	public void setPisaffirm(Integer pisaffirm) {
		this.pisaffirm = pisaffirm;
	}

	public Integer getPaffirmid() {
		return this.paffirmid;
	}

	public void setPaffirmid(Integer paffirmid) {
		this.paffirmid = paffirmid;
	}

	public Date getPaffirmdate() {
		return this.paffirmdate;
	}

	public void setPaffirmdate(Date paffirmdate) {
		this.paffirmdate = paffirmdate;
	}

	public Integer getIsreceive() {
		return this.isreceive;
	}

	public void setIsreceive(Integer isreceive) {
		this.isreceive = isreceive;
	}

	public Integer getReceiveid() {
		return this.receiveid;
	}

	public void setReceiveid(Integer receiveid) {
		this.receiveid = receiveid;
	}

	public Date getReceivedate() {
		return this.receivedate;
	}

	public void setReceivedate(Date receivedate) {
		this.receivedate = receivedate;
	}

	public Integer getIsblankout() {
		return this.isblankout;
	}

	public void setIsblankout(Integer isblankout) {
		this.isblankout = isblankout;
	}

	public Integer getBlankoutid() {
		return this.blankoutid;
	}

	public void setBlankoutid(Integer blankoutid) {
		this.blankoutid = blankoutid;
	}

	public Date getBlankoutdate() {
		return this.blankoutdate;
	}

	public void setBlankoutdate(Date blankoutdate) {
		this.blankoutdate = blankoutdate;
	}

	public String getBlankoutreason() {
		return this.blankoutreason;
	}

	public void setBlankoutreason(String blankoutreason) {
		this.blankoutreason = blankoutreason;
	}

	public Integer getPrinttimes() {
		return this.printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	public Integer getTakestatus() {
		return this.takestatus;
	}

	public void setTakestatus(Integer takestatus) {
		this.takestatus = takestatus;
	}

	public String getKeyscontent() {
		return this.keyscontent;
	}

	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}
	
	

	public String getIdii() {
		return idii;
	}

	public void setIdii(String idii) {
		this.idii = idii;
	}

	public String getPoutwarehouseid() {
		return poutwarehouseid;
	}

	public void setPoutwarehouseid(String poutwarehouseid) {
		this.poutwarehouseid = poutwarehouseid;
	}

	public Integer getPtakestatus() {
		return ptakestatus;
	}

	public void setPtakestatus(Integer ptakestatus) {
		this.ptakestatus = ptakestatus;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof OrganTrades))
			return false;
		OrganTrades castOther = (OrganTrades) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

}
