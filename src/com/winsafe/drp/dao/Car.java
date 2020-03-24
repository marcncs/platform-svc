package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Car implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String carbrand;

    /** nullable persistent field */
    private Integer carsort;

    /** nullable persistent field */
    private Date purchasedate;

    /** nullable persistent field */
    private Double worth;

    /** nullable persistent field */
    private Integer isleisure;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public Car(Integer id, String carbrand, Integer carsort, Date purchasedate, Double worth, Integer isleisure, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.carbrand = carbrand;
        this.carsort = carsort;
        this.purchasedate = purchasedate;
        this.worth = worth;
        this.isleisure = isleisure;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public Car() {
    }

    /** minimal constructor */
    public Car(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarbrand() {
        return this.carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand;
    }

    public Integer getCarsort() {
        return this.carsort;
    }

    public void setCarsort(Integer carsort) {
        this.carsort = carsort;
    }

    public Date getPurchasedate() {
        return this.purchasedate;
    }

    public void setPurchasedate(Date purchasedate) {
        this.purchasedate = purchasedate;
    }

    public Double getWorth() {
        return this.worth;
    }

    public void setWorth(Double worth) {
        this.worth = worth;
    }

    public Integer getIsleisure() {
        return this.isleisure;
    }

    public void setIsleisure(Integer isleisure) {
        this.isleisure = isleisure;
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
        if ( !(other instanceof Car) ) return false;
        Car castOther = (Car) other;
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
