package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AfficheBrowse implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer afficheid;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private Integer isbrowse;

    /** full constructor */
    public AfficheBrowse(Integer id, Integer afficheid, Integer userid, Integer isbrowse) {
        this.id = id;
        this.afficheid = afficheid;
        this.userid = userid;
        this.isbrowse = isbrowse;
    }

    /** default constructor */
    public AfficheBrowse() {
    }

    /** minimal constructor */
    public AfficheBrowse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAfficheid() {
        return this.afficheid;
    }

    public void setAfficheid(Integer afficheid) {
        this.afficheid = afficheid;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getIsbrowse() {
        return this.isbrowse;
    }

    public void setIsbrowse(Integer isbrowse) {
        this.isbrowse = isbrowse;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AfficheBrowse) ) return false;
        AfficheBrowse castOther = (AfficheBrowse) other;
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
