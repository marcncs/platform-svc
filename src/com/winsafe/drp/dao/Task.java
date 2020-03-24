package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Task implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer objsort;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String tptitle;

    /** nullable persistent field */
    private Date conclusiondate;

    /** nullable persistent field */
    private String endtime;

    /** nullable persistent field */
    private Integer priority;

    /** nullable persistent field */
    private Integer tasksort;

    /** nullable persistent field */
    private Integer status;

    /** nullable persistent field */
    private String tpcontent;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isallot;

    /** nullable persistent field */
    private Integer overstatus;

    /** nullable persistent field */
    private Date overdate;

    /** full constructor */
    public Task(Integer id, Integer objsort, String cid, String cname, String tptitle, Date conclusiondate, String endtime, Integer priority, Integer tasksort, Integer status, String tpcontent, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isallot, Integer overstatus, Date overdate) {
        this.id = id;
        this.objsort = objsort;
        this.cid = cid;
        this.cname = cname;
        this.tptitle = tptitle;
        this.conclusiondate = conclusiondate;
        this.endtime = endtime;
        this.priority = priority;
        this.tasksort = tasksort;
        this.status = status;
        this.tpcontent = tpcontent;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isallot = isallot;
        this.overstatus = overstatus;
        this.overdate = overdate;
    }

    /** default constructor */
    public Task() {
    }

    /** minimal constructor */
    public Task(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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

    public String getTptitle() {
        return this.tptitle;
    }

    public void setTptitle(String tptitle) {
        this.tptitle = tptitle;
    }

    public Date getConclusiondate() {
        return this.conclusiondate;
    }

    public void setConclusiondate(Date conclusiondate) {
        this.conclusiondate = conclusiondate;
    }

    public String getEndtime() {
        return this.endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getTasksort() {
        return this.tasksort;
    }

    public void setTasksort(Integer tasksort) {
        this.tasksort = tasksort;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTpcontent() {
        return this.tpcontent;
    }

    public void setTpcontent(String tpcontent) {
        this.tpcontent = tpcontent;
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

    public Integer getIsallot() {
        return this.isallot;
    }

    public void setIsallot(Integer isallot) {
        this.isallot = isallot;
    }

    public Integer getOverstatus() {
        return this.overstatus;
    }

    public void setOverstatus(Integer overstatus) {
        this.overstatus = overstatus;
    }

    public Date getOverdate() {
        return this.overdate;
    }

    public void setOverdate(Date overdate) {
        this.overdate = overdate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Task) ) return false;
        Task castOther = (Task) other;
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
