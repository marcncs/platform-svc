package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IntegralO implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer osort;
    
    private String oid;

    /** nullable persistent field */
    private String oname;

    /** nullable persistent field */
    private String omobile;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Integer isort;

    /** nullable persistent field */
    private Double rout;

    /** nullable persistent field */
    private Double aout;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String equiporganid;

    /** nullable persistent field */
    private String organid;

    /** full constructor */
    public IntegralO(Integer id, Integer osort, String oname, String omobile, String billno, Integer isort, Double rout, Double aout, Date makedate, String equiporganid, String organid) {
        this.id = id;
        this.osort = osort;
        this.oname = oname;
        this.omobile = omobile;
        this.billno = billno;
        this.isort = isort;
        this.rout = rout;
        this.aout = aout;
        this.makedate = makedate;
        this.equiporganid = equiporganid;
        this.organid = organid;
    }

    /** default constructor */
    public IntegralO() {
    }

    /** minimal constructor */
    public IntegralO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOsort() {
        return this.osort;
    }

    public void setOsort(Integer osort) {
        this.osort = osort;
    }

    public String getOname() {
        return this.oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getOmobile() {
        return this.omobile;
    }

    public void setOmobile(String omobile) {
        this.omobile = omobile;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public Integer getIsort() {
        return this.isort;
    }

    public void setIsort(Integer isort) {
        this.isort = isort;
    }

    public Double getRout() {
        return this.rout;
    }

    public void setRout(Double rout) {
        this.rout = rout;
    }

    public Double getAout() {
        return this.aout;
    }

    public void setAout(Double aout) {
        this.aout = aout;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getEquiporganid() {
        return this.equiporganid;
    }

    public void setEquiporganid(String equiporganid) {
        this.equiporganid = equiporganid;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IntegralO) ) return false;
        IntegralO castOther = (IntegralO) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

}
