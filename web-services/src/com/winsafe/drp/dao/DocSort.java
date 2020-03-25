package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class DocSort implements Serializable {

    /** nullable persistent field */
    private Integer id;

    /** nullable persistent field */
    private String sortname;

    /** nullable persistent field */
    private Integer userid;

    /** full constructor */
    public DocSort(Integer id, String sortname, Integer userid) {
        this.id = id;
        this.sortname = sortname;
        this.userid = userid;
    }

    /** default constructor */
    public DocSort() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSortname() {
        return this.sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

}
