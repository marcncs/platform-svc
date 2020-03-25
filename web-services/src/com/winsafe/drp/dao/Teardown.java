package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Teardown implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String tproductid;

    /** nullable persistent field */
    private String tproductname;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double tquantity;

    /** nullable persistent field */
    private Long warehouseoutid;

    /** nullable persistent field */
    private Long tdept;

    /** nullable persistent field */
    private Date completeintenddate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Long auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer iscomplete;

    /** nullable persistent field */
    private Long completeid;

    /** nullable persistent field */
    private Date completedate;

    /** nullable persistent field */
    private String stuffbatch;

    /** nullable persistent field */
    private Integer printtimes;

    /** full constructor */
    public Teardown(String id, String tproductid, String tproductname, String batch, Double tquantity, Long warehouseoutid, Long tdept, Date completeintenddate, String remark, Long makeid, Date makedate, Integer isaudit, Long auditid, Date auditdate, Integer iscomplete, Long completeid, Date completedate, String stuffbatch, Integer printtimes) {
        this.id = id;
        this.tproductid = tproductid;
        this.tproductname = tproductname;
        this.batch = batch;
        this.tquantity = tquantity;
        this.warehouseoutid = warehouseoutid;
        this.tdept = tdept;
        this.completeintenddate = completeintenddate;
        this.remark = remark;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.iscomplete = iscomplete;
        this.completeid = completeid;
        this.completedate = completedate;
        this.stuffbatch = stuffbatch;
        this.printtimes = printtimes;
    }

    /** default constructor */
    public Teardown() {
    }

    /** minimal constructor */
    public Teardown(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTproductid() {
        return this.tproductid;
    }

    public void setTproductid(String tproductid) {
        this.tproductid = tproductid;
    }

    public String getTproductname() {
        return this.tproductname;
    }

    public void setTproductname(String tproductname) {
        this.tproductname = tproductname;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Double getTquantity() {
        return this.tquantity;
    }

    public void setTquantity(Double tquantity) {
        this.tquantity = tquantity;
    }

    public Long getWarehouseoutid() {
        return this.warehouseoutid;
    }

    public void setWarehouseoutid(Long warehouseoutid) {
        this.warehouseoutid = warehouseoutid;
    }

    public Long getTdept() {
        return this.tdept;
    }

    public void setTdept(Long tdept) {
        this.tdept = tdept;
    }

    public Date getCompleteintenddate() {
        return this.completeintenddate;
    }

    public void setCompleteintenddate(Date completeintenddate) {
        this.completeintenddate = completeintenddate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getIscomplete() {
        return this.iscomplete;
    }

    public void setIscomplete(Integer iscomplete) {
        this.iscomplete = iscomplete;
    }

    public Long getCompleteid() {
        return this.completeid;
    }

    public void setCompleteid(Long completeid) {
        this.completeid = completeid;
    }

    public Date getCompletedate() {
        return this.completedate;
    }

    public void setCompletedate(Date completedate) {
        this.completedate = completedate;
    }

    public String getStuffbatch() {
        return this.stuffbatch;
    }

    public void setStuffbatch(String stuffbatch) {
        this.stuffbatch = stuffbatch;
    }

    public Integer getPrinttimes() {
        return this.printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Teardown) ) return false;
        Teardown castOther = (Teardown) other;
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
