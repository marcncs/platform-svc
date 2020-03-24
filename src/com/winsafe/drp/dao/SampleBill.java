package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SampleBill implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Integer objsort;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String linkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String postcode;

    /** nullable persistent field */
    private String receiveaddr;

    /** nullable persistent field */
    private Integer isshipment;

    /** nullable persistent field */
    private Integer shipmentid;

    /** nullable persistent field */
    private Date shipmentdate;

    /** nullable persistent field */
    private Date estimaterecycle;

    /** nullable persistent field */
    private String makeuser;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isrecycle;

    /** nullable persistent field */
    private Integer recycleid;

    /** nullable persistent field */
    private Date actualrecycle;

    /** nullable persistent field */
    private String keyscontent;

    private String warehouseID;

    /** full constructor */
    public SampleBill(String id, Integer objsort, String cid, String cname,
	    String linkman, String tel, String postcode, String receiveaddr,
	    Date shipmentdate, Date estimaterecycle, String makeuser,
	    Double totalsum, String remark, String makeorganid,
	    Integer makedeptid, Integer makeid, Date makedate, Integer isaudit,
	    Integer auditid, Date auditdate, Integer isrecycle,
	    Integer recycleid, Date actualrecycle, String keyscontent,
	    String WarehouseID) {
	this.id = id;
	this.objsort = objsort;
	this.cid = cid;
	this.cname = cname;
	this.linkman = linkman;
	this.tel = tel;
	this.postcode = postcode;
	this.receiveaddr = receiveaddr;
	this.shipmentdate = shipmentdate;
	this.estimaterecycle = estimaterecycle;
	this.makeuser = makeuser;
	this.totalsum = totalsum;
	this.remark = remark;
	this.makeorganid = makeorganid;
	this.makedeptid = makedeptid;
	this.makeid = makeid;
	this.makedate = makedate;
	this.isaudit = isaudit;
	this.auditid = auditid;
	this.auditdate = auditdate;
	this.isrecycle = isrecycle;
	this.recycleid = recycleid;
	this.actualrecycle = actualrecycle;
	this.keyscontent = keyscontent;
	this.warehouseID = WarehouseID;

    }

    public String getWarehouseID() {
	return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
	this.warehouseID = warehouseID;
    }

    /** default constructor */
    public SampleBill() {
    }

    public String getId() {
	return this.id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public Integer getObjsort() {
	return this.objsort;
    }

    public void setObjsort(Integer objsort) {
	this.objsort = objsort;
    }

    public String getCid() {
	return this.cid;
    }

    public void setCid(String cid) {
	this.cid = cid;
    }

    public String getCname() {
	return this.cname;
    }

    public void setCname(String cname) {
	this.cname = cname;
    }

    public String getLinkman() {
	return this.linkman;
    }

    public void setLinkman(String linkman) {
	this.linkman = linkman;
    }

    public String getTel() {
	return this.tel;
    }

    public void setTel(String tel) {
	this.tel = tel;
    }

    public String getPostcode() {
	return this.postcode;
    }

    public void setPostcode(String postcode) {
	this.postcode = postcode;
    }

    public String getReceiveaddr() {
	return this.receiveaddr;
    }

    public void setReceiveaddr(String receiveaddr) {
	this.receiveaddr = receiveaddr;
    }

    public Date getShipmentdate() {
	return this.shipmentdate;
    }

    public void setShipmentdate(Date shipmentdate) {
	this.shipmentdate = shipmentdate;
    }

    public Date getEstimaterecycle() {
	return this.estimaterecycle;
    }

    public void setEstimaterecycle(Date estimaterecycle) {
	this.estimaterecycle = estimaterecycle;
    }

    public String getMakeuser() {
	return this.makeuser;
    }

    public void setMakeuser(String makeuser) {
	this.makeuser = makeuser;
    }

    public Double getTotalsum() {
	return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
	this.totalsum = totalsum;
    }

    public String getRemark() {
	return this.remark;
    }

    public void setRemark(String remark) {
	this.remark = remark;
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

    public Integer getIsrecycle() {
	return this.isrecycle;
    }

    public void setIsrecycle(Integer isrecycle) {
	this.isrecycle = isrecycle;
    }

    public Integer getRecycleid() {
	return this.recycleid;
    }

    public void setRecycleid(Integer recycleid) {
	this.recycleid = recycleid;
    }

    public Date getActualrecycle() {
	return this.actualrecycle;
    }

    public void setActualrecycle(Date actualrecycle) {
	this.actualrecycle = actualrecycle;
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
	if (!(other instanceof SampleBill))
	    return false;
	SampleBill castOther = (SampleBill) other;
	return new EqualsBuilder().append(this.getId(), castOther.getId())
		.isEquals();
    }

    public int hashCode() {
	return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public Integer getIsshipment() {
        return isshipment;
    }

    public void setIsshipment(Integer isshipment) {
        this.isshipment = isshipment;
    }

    public Integer getShipmentid() {
        return shipmentid;
    }

    public void setShipmentid(Integer shipmentid) {
        this.shipmentid = shipmentid;
    }

}
