package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class WlmIdcodeLog implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer province;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Integer areas;

    /** nullable persistent field */
    private String wlmidcode;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String syncode;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;
    
    //箱码
    private String cartoncode;
    
    //查询分类
    private Integer querysort;
    
    /** full constructor */
    public WlmIdcodeLog(Integer id, Integer province, Integer city, Integer areas, String wlmidcode, String warehouseid, String cid, String syncode, String cname, String productid, String productname, String specmode, String organid, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.province = province;
        this.city = city;
        this.areas = areas;
        this.wlmidcode = wlmidcode;
        this.warehouseid = warehouseid;
        this.cid = cid;
        this.syncode = syncode;
        this.cname = cname;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.organid = organid;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public WlmIdcodeLog() {
    }

    /** minimal constructor */
    public WlmIdcodeLog(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProvince() {
        return this.province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getAreas() {
        return this.areas;
    }

    public void setAreas(Integer areas) {
        this.areas = areas;
    }

    public String getWlmidcode() {
        return this.wlmidcode;
    }

    public void setWlmidcode(String wlmidcode) {
        this.wlmidcode = wlmidcode;
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSyncode() {
        return this.syncode;
    }

    public void setSyncode(String syncode) {
        this.syncode = syncode;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
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

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
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
        if ( !(other instanceof WlmIdcodeLog) ) return false;
        WlmIdcodeLog castOther = (WlmIdcodeLog) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public void setCartoncode(String cartoncode) {
		this.cartoncode = cartoncode;
	}

	public String getCartoncode() {
		return cartoncode;
	}

	public void setQuerysort(Integer querysort) {
		this.querysort = querysort;
	}

	public Integer getQuerysort() {
		return querysort;
	}

}
