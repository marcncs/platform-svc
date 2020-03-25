package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BarcodeInventory {
	 /** identifier field */
    private String id;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private Integer shipmentsort;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String clinkman;

    /** nullable persistent field */
    private Date requiredate;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isendcase;

    /** nullable persistent field */
    private Integer endcaseid;

    /** nullable persistent field */
    private Date endcasedate;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isblankout;

    /** nullable persistent field */
    private Integer blankoutid;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private Integer printtimes;

    /** nullable persistent field */
    private Integer takestaus;

    /** nullable persistent field */
    private String keyscontent;
    
    private Integer isaccount;
    
    private String organid;
    
    private Integer version;
    
    private Integer isapprove;
    
    public BarcodeInventory(){
    	
    }
    
    /** full constructor */
    public BarcodeInventory(String id, String warehouseid, Integer shipmentsort, String billno, Integer userid, String cid, String cname, String clinkman, Date requiredate, Double totalsum, String remark, Integer isaudit, Integer auditid, Date auditdate, Integer isendcase, Integer endcaseid, Date endcasedate, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isblankout, Integer blankoutid, Date blankoutdate, Integer printtimes, Integer takestaus, String keyscontent) {
        this.id = id;
        this.warehouseid = warehouseid;
        this.shipmentsort = shipmentsort;
        this.billno = billno;
        this.userid = userid;
        this.cid = cid;
        this.cname = cname;
        this.clinkman = clinkman;
        this.requiredate = requiredate;
        this.totalsum = totalsum;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isendcase = isendcase;
        this.endcaseid = endcaseid;
        this.endcasedate = endcasedate;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
        this.printtimes = printtimes;
        this.takestaus = takestaus;
        this.keyscontent = keyscontent;
    }

    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public Integer getShipmentsort() {
		return shipmentsort;
	}

	public void setShipmentsort(Integer shipmentsort) {
		this.shipmentsort = shipmentsort;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
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

	public String getClinkman() {
		return clinkman;
	}

	public void setClinkman(String clinkman) {
		this.clinkman = clinkman;
	}

	public Date getRequiredate() {
		return requiredate;
	}

	public void setRequiredate(Date requiredate) {
		this.requiredate = requiredate;
	}

	public Double getTotalsum() {
		return totalsum;
	}

	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	public Integer getAuditid() {
		return auditid;
	}

	public void setAuditid(Integer auditid) {
		this.auditid = auditid;
	}

	public Date getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}

	public Integer getIsendcase() {
		return isendcase;
	}

	public void setIsendcase(Integer isendcase) {
		this.isendcase = isendcase;
	}

	public Integer getEndcaseid() {
		return endcaseid;
	}

	public void setEndcaseid(Integer endcaseid) {
		this.endcaseid = endcaseid;
	}

	public Date getEndcasedate() {
		return endcasedate;
	}

	public void setEndcasedate(Date endcasedate) {
		this.endcasedate = endcasedate;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Integer getIsblankout() {
		return isblankout;
	}

	public void setIsblankout(Integer isblankout) {
		this.isblankout = isblankout;
	}

	public Integer getBlankoutid() {
		return blankoutid;
	}

	public void setBlankoutid(Integer blankoutid) {
		this.blankoutid = blankoutid;
	}

	public Date getBlankoutdate() {
		return blankoutdate;
	}

	public void setBlankoutdate(Date blankoutdate) {
		this.blankoutdate = blankoutdate;
	}

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	public Integer getTakestaus() {
		return takestaus;
	}

	public void setTakestaus(Integer takestaus) {
		this.takestaus = takestaus;
	}

	public String getKeyscontent() {
		return keyscontent;
	}

	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}

	public Integer getIsaccount() {
		return isaccount;
	}

	public void setIsaccount(Integer isaccount) {
		this.isaccount = isaccount;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

	public String getOrganid() {
		return organid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getIsapprove() {
		return isapprove;
	}

	public void setIsapprove(Integer isapprove) {
		this.isapprove = isapprove;
	}
	
}
