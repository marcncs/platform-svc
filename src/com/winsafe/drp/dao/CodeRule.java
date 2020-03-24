package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CodeRule implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ucode;

    /** nullable persistent field */
    private Integer crproperty;

    /** nullable persistent field */
    private Integer startno;

    /** nullable persistent field */
    private Integer lno;

    /** full constructor */
    public CodeRule(Integer id, String ucode, Integer crproperty, Integer startno, Integer lno) {
        this.id = id;
        this.ucode = ucode;
        this.crproperty = crproperty;
        this.startno = startno;
        this.lno = lno;
    }

    /** default constructor */
    public CodeRule() {
    }

    /** minimal constructor */
    public CodeRule(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUcode() {
        return this.ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public Integer getCrproperty() {
        return this.crproperty;
    }

    public void setCrproperty(Integer crproperty) {
        this.crproperty = crproperty;
    }

    public Integer getStartno() {
        return this.startno;
    }

    public void setStartno(Integer startno) {
        this.startno = startno;
    }

    public Integer getLno() {
        return this.lno;
    }

    public void setLno(Integer lno) {
        this.lno = lno;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CodeRule) ) return false;
        CodeRule castOther = (CodeRule) other;
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
