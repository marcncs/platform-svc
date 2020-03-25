package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class StockMove implements Serializable {
	private String ttid;
	
	/** identifier field */
	private String id;

	/** nullable persistent field */
	private Date movedate;

	/** nullable persistent field */
	private String outwarehouseid;

	/** nullable persistent field */
	private String inorganid;

	/** nullable persistent field */
	private String inwarehouseid;

	/** nullable persistent field */
	private Integer transportmode;
	
	private String transportaddr;
	// 出库id
	private String outorganid;

	public String getTransportaddr() {
		return transportaddr;
	}

	public void setTransportaddr(String transportaddr) {
		this.transportaddr = transportaddr;
	}

	private String olinkman;

	private String otel;

	public String getOlinkman() {
		return olinkman;
	}

	public void setOlinkman(String olinkman) {
		this.olinkman = olinkman;
	}

	public String getOtel() {
		return otel;
	}

	public void setOtel(String otel) {
		this.otel = otel;
	}

	/** nullable persistent field */
	private Double totalsum;

	/** nullable persistent field */
	private String movecause;

	/** nullable persistent field */
	private String remark;

	/** nullable persistent field */
	private Integer isaudit;

	/** nullable persistent field */
	private Integer auditid;

	/** nullable persistent field */
	private Date auditdate;

	/** nullable persistent field */
	private String makeorganid;

	/** nullable persistent field */
	private Integer makedeptid;

	/** nullable persistent field */
	private Integer makeid;

	/** nullable persistent field */
	private Date makedate;

	/** nullable persistent field */
	private Integer isshipment;

	/** nullable persistent field */
	private Integer shipmentid;

	/** nullable persistent field */
	private Date shipmentdate;

	/** nullable persistent field */
	private Integer iscomplete;

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

	/** nullable persistent field */
	private String keyscontent;
	
	private String nccode;
	
	private String nccode_rec;
    /**
     * 配送单号
     */
    private String nccode2;
    
    /** 运输车车牌号 */
    private String busNo;
    
    /** 运输路线 */
    private String busWay;
    
    private Integer version;

	/** full constructor */
	public StockMove(String id, Date movedate, String outwarehouseid,
			String inorganid, String inwarehouseid, Integer transportmode,
			Double totalsum, String movecause, String remark, Integer isaudit,
			Integer auditid, Date auditdate, String makeorganid,
			Integer makedeptid, Integer makeid, Date makedate,
			Integer isshipment, Integer shipmentid, Date shipmentdate,
			Integer iscomplete, Integer receiveid, Date receivedate,
			Integer isblankout, Integer blankoutid, Date blankoutdate,
			String blankoutreason, Integer printtimes, Integer takestatus,
			String keyscontent,String nccode,String nccode_rec) {
		this.id = id;
		this.movedate = movedate;
		this.outwarehouseid = outwarehouseid;
		this.inorganid = inorganid;
		this.inwarehouseid = inwarehouseid;
		this.transportmode = transportmode;
		this.totalsum = totalsum;
		this.movecause = movecause;
		this.remark = remark;
		this.isaudit = isaudit;
		this.auditid = auditid;
		this.auditdate = auditdate;
		this.makeorganid = makeorganid;
		this.makedeptid = makedeptid;
		this.makeid = makeid;
		this.makedate = makedate;
		this.isshipment = isshipment;
		this.shipmentid = shipmentid;
		this.shipmentdate = shipmentdate;
		this.iscomplete = iscomplete;
		this.receiveid = receiveid;
		this.receivedate = receivedate;
		this.isblankout = isblankout;
		this.blankoutid = blankoutid;
		this.blankoutdate = blankoutdate;
		this.blankoutreason = blankoutreason;
		this.printtimes = printtimes;
		this.takestatus = takestatus;
		this.keyscontent = keyscontent;
		this.nccode=nccode;
		this.nccode_rec=nccode_rec;
	}

	public String getNccode_rec() {
		return nccode_rec;
	}

	public void setNccode_rec(String nccode_rec) {
		this.nccode_rec = nccode_rec;
	}

	/** default constructor */
	public StockMove() {
	}


	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/** minimal constructor */
	public StockMove(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getMovedate() {
		return this.movedate;
	}

	public void setMovedate(Date movedate) {
		this.movedate = movedate;
	}

	public String getOutwarehouseid() {
		return this.outwarehouseid;
	}

	public void setOutwarehouseid(String outwarehouseid) {
		this.outwarehouseid = outwarehouseid;
	}

	public String getInorganid() {
		return this.inorganid;
	}

	public void setInorganid(String inorganid) {
		this.inorganid = inorganid;
	}

	public String getInwarehouseid() {
		return this.inwarehouseid;
	}

	public void setInwarehouseid(String inwarehouseid) {
		this.inwarehouseid = inwarehouseid;
	}

	public Integer getTransportmode() {
		return this.transportmode;
	}

	public void setTransportmode(Integer transportmode) {
		this.transportmode = transportmode;
	}

	public Double getTotalsum() {
		return this.totalsum;
	}

	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public String getMovecause() {
		return this.movecause;
	}

	public void setMovecause(String movecause) {
		this.movecause = movecause;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getIsshipment() {
		return this.isshipment;
	}

	public void setIsshipment(Integer isshipment) {
		this.isshipment = isshipment;
	}

	public Integer getShipmentid() {
		return this.shipmentid;
	}

	public void setShipmentid(Integer shipmentid) {
		this.shipmentid = shipmentid;
	}

	public Date getShipmentdate() {
		return this.shipmentdate;
	}

	public void setShipmentdate(Date shipmentdate) {
		this.shipmentdate = shipmentdate;
	}

	public Integer getIscomplete() {
		return this.iscomplete;
	}

	public void setIscomplete(Integer iscomplete) {
		this.iscomplete = iscomplete;
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

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof StockMove))
			return false;
		StockMove castOther = (StockMove) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public String getNccode2() {
		return nccode2;
	}

	public void setNccode2(String nccode2) {
		this.nccode2 = nccode2;
	}

	public String getBusNo() {
		return busNo;
	}

	public void setBusNo(String busNo) {
		this.busNo = busNo;
	}

	public String getBusWay() {
		return busWay;
	}

	public void setBusWay(String busWay) {
		this.busWay = busWay;
	}

	public String getTtid() {
		return ttid;
	}

	public void setTtid(String ttid) {
		this.ttid = ttid;
	}

	public String getOutorganid() {
		return outorganid;
	}

	public void setOutorganid(String outorganid) {
		this.outorganid = outorganid;
	}

	
}
