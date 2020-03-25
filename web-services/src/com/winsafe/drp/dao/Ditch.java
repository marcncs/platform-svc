package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class Ditch extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String dname;

    /** nullable persistent field */
    private String telone;

    /** nullable persistent field */
    private String teltwo;

    /** nullable persistent field */
    private String fax;

    /** nullable persistent field */
    private Integer province;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Integer areas;

    /** nullable persistent field */
    private String detailaddr;

    /** nullable persistent field */
    private String postcode;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String homepage;

    /** nullable persistent field */
    private Integer ditchrank;

    /** nullable persistent field */
    private Integer prestige;

    /** nullable persistent field */
    private String scale;

    /** nullable persistent field */
    private Integer vocation;

    /** nullable persistent field */
    private String taxcode;

    /** nullable persistent field */
    private String bankname;

    /** nullable persistent field */
    private String doorname;

    /** nullable persistent field */
    private String bankaccount;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Long userid;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public Ditch(Long id, String dname, String telone, String teltwo, String fax, Integer province, Integer city, Integer areas, String detailaddr, String postcode, String email, String homepage, Integer ditchrank, Integer prestige, String scale, Integer vocation, String taxcode, String bankname, String doorname, String bankaccount, String memo, Long userid, Long makeid, Date makedate) {
        this.id = id;
        this.dname = dname;
        this.telone = telone;
        this.teltwo = teltwo;
        this.fax = fax;
        this.province = province;
        this.city = city;
        this.areas = areas;
        this.detailaddr = detailaddr;
        this.postcode = postcode;
        this.email = email;
        this.homepage = homepage;
        this.ditchrank = ditchrank;
        this.prestige = prestige;
        this.scale = scale;
        this.vocation = vocation;
        this.taxcode = taxcode;
        this.bankname = bankname;
        this.doorname = doorname;
        this.bankaccount = bankaccount;
        this.memo = memo;
        this.userid = userid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public Ditch() {
    }

    /** minimal constructor */
    public Ditch(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDname() {
        return this.dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getTelone() {
        return this.telone;
    }

    public void setTelone(String telone) {
        this.telone = telone;
    }

    public String getTeltwo() {
        return this.teltwo;
    }

    public void setTeltwo(String teltwo) {
        this.teltwo = teltwo;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public String getDetailaddr() {
        return this.detailaddr;
    }

    public void setDetailaddr(String detailaddr) {
        this.detailaddr = detailaddr;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getDitchrank() {
        return this.ditchrank;
    }

    public void setDitchrank(Integer ditchrank) {
        this.ditchrank = ditchrank;
    }

    public Integer getPrestige() {
        return this.prestige;
    }

    public void setPrestige(Integer prestige) {
        this.prestige = prestige;
    }

    public String getScale() {
        return this.scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public Integer getVocation() {
        return this.vocation;
    }

    public void setVocation(Integer vocation) {
        this.vocation = vocation;
    }

    public String getTaxcode() {
        return this.taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    public String getBankname() {
        return this.bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getDoorname() {
        return this.doorname;
    }

    public void setDoorname(String doorname) {
        this.doorname = doorname;
    }

    public String getBankaccount() {
        return this.bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Long makeid) {
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
        if ( !(other instanceof Ditch) ) return false;
        Ditch castOther = (Ditch) other;
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
