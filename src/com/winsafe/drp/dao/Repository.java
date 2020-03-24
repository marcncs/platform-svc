package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Repository implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Long rtid;

    /** nullable persistent field */
    private String title;

    /** nullable persistent field */
    private String content;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Long makedeptid;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String keyscontent;

    /** full constructor */
    public Repository(String id, Long rtid, String title, String content, String makeorganid, Long makedeptid, Long makeid, Date makedate, String keyscontent) {
        this.id = id;
        this.rtid = rtid;
        this.title = title;
        this.content = content;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public Repository() {
    }

    /** minimal constructor */
    public Repository(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRtid() {
        return this.rtid;
    }

    public void setRtid(Long rtid) {
        this.rtid = rtid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Long getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Long makedeptid) {
        this.makedeptid = makedeptid;
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

    public String getKeyscontent() {
        return this.keyscontent;
    }

    public void setKeyscontent(String keyscontent) {
        this.keyscontent = keyscontent;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Repository) ) return false;
        Repository castOther = (Repository) other;
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
