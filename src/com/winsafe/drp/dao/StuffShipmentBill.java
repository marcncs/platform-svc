package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class StuffShipmentBill extends ActionForm implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Long warehouseid;

    /** nullable persistent field */
    private Integer shipmentsort;

    /** nullable persistent field */
    private Long shipmentdept;

    /** nullable persistent field */
    private Date requiredate;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isrefer;

    /** nullable persistent field */
    private Integer approvestatus;

    /** nullable persistent field */
    private Date approvedate;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Long auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public StuffShipmentBill(String id, Long warehouseid, Integer shipmentsort, Long shipmentdept, Date requiredate, Double totalsum, String remark, Integer isrefer, Integer approvestatus, Date approvedate, Integer isaudit, Long auditid, Date auditdate, Long makeid, Date makedate) {
        this.id = id;
        this.warehouseid = warehouseid;
        this.shipmentsort = shipmentsort;
        this.shipmentdept = shipmentdept;
        this.requiredate = requiredate;
        this.totalsum = totalsum;
        this.remark = remark;
        this.isrefer = isrefer;
        this.approvestatus = approvestatus;
        this.approvedate = approvedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public StuffShipmentBill() {
    }

    /** minimal constructor */
    public StuffShipmentBill(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(Long warehouseid) {
        this.warehouseid = warehouseid;
    }

    public Integer getShipmentsort() {
        return this.shipmentsort;
    }

    public void setShipmentsort(Integer shipmentsort) {
        this.shipmentsort = shipmentsort;
    }

    public Long getShipmentdept() {
        return this.shipmentdept;
    }

    public void setShipmentdept(Long shipmentdept) {
        this.shipmentdept = shipmentdept;
    }

    public Date getRequiredate() {
        return this.requiredate;
    }

    public void setRequiredate(Date requiredate) {
        this.requiredate = requiredate;
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

    public Integer getIsrefer() {
        return this.isrefer;
    }

    public void setIsrefer(Integer isrefer) {
        this.isrefer = isrefer;
    }

    public Integer getApprovestatus() {
        return this.approvestatus;
    }

    public void setApprovestatus(Integer approvestatus) {
        this.approvestatus = approvestatus;
    }

    public Date getApprovedate() {
        return this.approvedate;
    }

    public void setApprovedate(Date approvedate) {
        this.approvedate = approvedate;
    }

    public Integer getIsaudit() {
        return this.isaudit;
    }

    public void setIsaudit(Integer isaudit) {
        this.isaudit = isaudit;
    }

    public Long getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Long auditid) {
        this.auditid = auditid;
    }

    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Long getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Long makeid) {
        this.makeid = makeid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof StuffShipmentBill) ) return false;
        StuffShipmentBill castOther = (StuffShipmentBill) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
