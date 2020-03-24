package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CodeRuleUpload implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer cruproperty;

    /** nullable persistent field */
    private Integer startno;

    /** nullable persistent field */
    private Integer lno;

    /** full constructor */
    public CodeRuleUpload(Integer id, Integer cruproperty, Integer startno, Integer lno) {
        this.id = id;
        this.cruproperty = cruproperty;
        this.startno = startno;
        this.lno = lno;
    }

    /** default constructor */
    public CodeRuleUpload() {
    }

    /** minimal constructor */
    public CodeRuleUpload(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCruproperty() {
        return this.cruproperty;
    }

    public void setCruproperty(Integer cruproperty) {
        this.cruproperty = cruproperty;
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
        if ( !(other instanceof CodeRuleUpload) ) return false;
        CodeRuleUpload castOther = (CodeRuleUpload) other;
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
