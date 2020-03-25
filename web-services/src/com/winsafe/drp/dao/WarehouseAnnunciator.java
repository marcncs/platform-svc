package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class WarehouseAnnunciator extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long warehouseid;

    /** nullable persistent field */
    private Long userid;

    /** nullable persistent field */
    private Integer isawake;

    /** full constructor */
    public WarehouseAnnunciator(Long id, Long warehouseid, Long userid, Integer isawake) {
        this.id = id;
        this.warehouseid = warehouseid;
        this.userid = userid;
        this.isawake = isawake;
    }

    /** default constructor */
    public WarehouseAnnunciator() {
    }

    /** minimal constructor */
    public WarehouseAnnunciator(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(Long warehouseid) {
        this.warehouseid = warehouseid;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getIsawake() {
        return this.isawake;
    }

    public void setIsawake(Integer isawake) {
        this.isawake = isawake;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof WarehouseAnnunciator) ) return false;
        WarehouseAnnunciator castOther = (WarehouseAnnunciator) other;
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
