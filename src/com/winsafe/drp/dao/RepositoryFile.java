package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class RepositoryFile implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String rid;

    /** nullable persistent field */
    private String title;

    /** nullable persistent field */
    private String filepath;

    /** full constructor */
    public RepositoryFile(Long id, String rid, String title, String filepath) {
        this.id = id;
        this.rid = rid;
        this.title = title;
        this.filepath = filepath;
    }

    /** default constructor */
    public RepositoryFile() {
    }

    /** minimal constructor */
    public RepositoryFile(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilepath() {
        return this.filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RepositoryFile) ) return false;
        RepositoryFile castOther = (RepositoryFile) other;
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
