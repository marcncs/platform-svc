package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IdcodeDetail implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String billid;

    /** nullable persistent field */
    private String idcode;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private String makeuser;

    /** nullable persistent field */
    private Integer idbilltype;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private String equiporganid;

    /** nullable persistent field */
    private Long warehouseid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String cmobile;

    /** nullable persistent field */
    private String provideid;

    /** nullable persistent field */
    private String providename;

    /** full constructor */
    public IdcodeDetail(Long id, String productid, String productname, String specmode, String billid, String idcode, Date makedate, Long makeid, String makeuser, Integer idbilltype, String makeorganid, String equiporganid, Long warehouseid, String cid, String cname, String cmobile, String provideid, String providename) {
        this.id = id;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.billid = billid;
        this.idcode = idcode;
        this.makedate = makedate;
        this.makeid = makeid;
        this.makeuser = makeuser;
        this.idbilltype = idbilltype;
        this.makeorganid = makeorganid;
        this.equiporganid = equiporganid;
        this.warehouseid = warehouseid;
        this.cid = cid;
        this.cname = cname;
        this.cmobile = cmobile;
        this.provideid = provideid;
        this.providename = providename;
    }

    /** default constructor */
    public IdcodeDetail() {
    }

    /** minimal constructor */
    public IdcodeDetail(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSpecmode() {
        return this.specmode;
    }

    public void setSpecmode(String specmode) {
        this.specmode = specmode;
    }

    public String getBillid() {
        return this.billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public String getIdcode() {
        return this.idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public Long getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Long makeid) {
        this.makeid = makeid;
    }

    public String getMakeuser() {
        return this.makeuser;
    }

    public void setMakeuser(String makeuser) {
        this.makeuser = makeuser;
    }

    public Integer getIdbilltype() {
        return this.idbilltype;
    }

    public void setIdbilltype(Integer idbilltype) {
        this.idbilltype = idbilltype;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public String getEquiporganid() {
        return this.equiporganid;
    }

    public void setEquiporganid(String equiporganid) {
        this.equiporganid = equiporganid;
    }

    public Long getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(Long warehouseid) {
        this.warehouseid = warehouseid;
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

    public String getCmobile() {
        return this.cmobile;
    }

    public void setCmobile(String cmobile) {
        this.cmobile = cmobile;
    }

    public String getProvideid() {
        return this.provideid;
    }

    public void setProvideid(String provideid) {
        this.provideid = provideid;
    }

    public String getProvidename() {
        return this.providename;
    }

    public void setProvidename(String providename) {
        this.providename = providename;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IdcodeDetail) ) return false;
        IdcodeDetail castOther = (IdcodeDetail) other;
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
