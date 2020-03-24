package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class WorkReport implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String reportcontent;

    /** nullable persistent field */
    private Integer reportsort;

    /** nullable persistent field */
    private Date referdate;

    /** nullable persistent field */
    private Integer isrefer;

    /** nullable persistent field */
    private Integer approvestatus;

    /** nullable persistent field */
    private Date approvedate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String affix;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public WorkReport(Integer id, String reportcontent, Integer reportsort, Date referdate, Integer isrefer, Integer approvestatus, Date approvedate, String remark, String affix, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.reportcontent = reportcontent;
        this.reportsort = reportsort;
        this.referdate = referdate;
        this.isrefer = isrefer;
        this.approvestatus = approvestatus;
        this.approvedate = approvedate;
        this.remark = remark;
        this.affix = affix;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public WorkReport() {
    }

    /** minimal constructor */
    public WorkReport(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportcontent() {
        return this.reportcontent;
    }

    public void setReportcontent(String reportcontent) {
        this.reportcontent = reportcontent;
    }

    public Integer getReportsort() {
        return this.reportsort;
    }

    public void setReportsort(Integer reportsort) {
        this.reportsort = reportsort;
    }

    public Date getReferdate() {
        return this.referdate;
    }

    public void setReferdate(Date referdate) {
        this.referdate = referdate;
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

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAffix() {
        return this.affix;
    }

    public void setAffix(String affix) {
        this.affix = affix;
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
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof WorkReport) ) return false;
        WorkReport castOther = (WorkReport) other;
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
