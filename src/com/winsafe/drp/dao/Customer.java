package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Customer implements Serializable {

    /** identifier field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String cpycode;

    /** nullable persistent field */
    private Integer kind;

    /** nullable persistent field */
    private String cardno;

    /** nullable persistent field */
    private String parentid;

    /** nullable persistent field */
    private String pwd;

    /** nullable persistent field */
    private Integer membersex;

    /** nullable persistent field */
    private Date memberbirthday;

    /** nullable persistent field */
    private String memberidcard;

    /** nullable persistent field */
    private String membercompany;

    /** nullable persistent field */
    private String memberduty;

    /** nullable persistent field */
    private String tickettitle;

    /** nullable persistent field */
    private Integer mgrade;

    /** nullable persistent field */
    private Double integral;

    /** nullable persistent field */
    private Integer prompt;

    /** nullable persistent field */
    private Double creditlock;

    /** nullable persistent field */
    private Integer sort;

    /** nullable persistent field */
    private Integer rate;

    /** nullable persistent field */
    private Integer policyid;

    /** nullable persistent field */
    private Double discount;

    /** nullable persistent field */
    private Double taxrate;

    /** nullable persistent field */
    private Integer vocation;

    /** nullable persistent field */
    private String cphoto;

    /** nullable persistent field */
    private Integer customertype;

    /** nullable persistent field */
    private Integer customerstatus;

    /** nullable persistent field */
    private Integer yauld;

    /** nullable persistent field */
    private Integer source;

    /** nullable persistent field */
    private Integer transportmode;

    /** nullable persistent field */
    private Integer province;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Integer areas;

    /** nullable persistent field */
    private String commaddr;

    /** nullable persistent field */
    private String detailaddr;

    /** nullable persistent field */
    private String postcode;

    /** nullable persistent field */
    private String sendpostcode;

    /** nullable persistent field */
    private String homepage;

    /** nullable persistent field */
    private String officetel;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String fax;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String taxcode;

    /** nullable persistent field */
    private Integer specializeid;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer updid;

    /** nullable persistent field */
    private Date upddate;

    /** nullable persistent field */
    private Date lastcontact;

    /** nullable persistent field */
    private Date nextcontact;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isactivate;

    /** nullable persistent field */
    private Integer activateid;

    /** nullable persistent field */
    private Date activatedate;

    /** nullable persistent field */
    private Integer isdel;

    /** nullable persistent field */
    private Integer isreceivemsg;

    /** full constructor */
    public Customer(String cid, String cname, String cpycode, Integer kind, String cardno, String parentid, String pwd, Integer membersex, Date memberbirthday, String memberidcard, String membercompany, String memberduty, String tickettitle, Integer mgrade, Double integral, Integer prompt, Double creditlock, Integer sort, Integer rate, Integer policyid, Double discount, Double taxrate, Integer vocation, String cphoto, Integer customertype, Integer customerstatus, Integer yauld, Integer source, Integer transportmode, Integer province, Integer city, Integer areas, String commaddr, String detailaddr, String postcode, String sendpostcode, String homepage, String officetel, String mobile, String fax, String email, String taxcode, Integer specializeid, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer updid, Date upddate, Date lastcontact, Date nextcontact, String remark, Integer isactivate, Integer activateid, Date activatedate, Integer isdel, Integer isreceivemsg) {
        this.cid = cid;
        this.cname = cname;
        this.cpycode = cpycode;
        this.kind = kind;
        this.cardno = cardno;
        this.parentid = parentid;
        this.pwd = pwd;
        this.membersex = membersex;
        this.memberbirthday = memberbirthday;
        this.memberidcard = memberidcard;
        this.membercompany = membercompany;
        this.memberduty = memberduty;
        this.tickettitle = tickettitle;
        this.mgrade = mgrade;
        this.integral = integral;
        this.prompt = prompt;
        this.creditlock = creditlock;
        this.sort = sort;
        this.rate = rate;
        this.policyid = policyid;
        this.discount = discount;
        this.taxrate = taxrate;
        this.vocation = vocation;
        this.cphoto = cphoto;
        this.customertype = customertype;
        this.customerstatus = customerstatus;
        this.yauld = yauld;
        this.source = source;
        this.transportmode = transportmode;
        this.province = province;
        this.city = city;
        this.areas = areas;
        this.commaddr = commaddr;
        this.detailaddr = detailaddr;
        this.postcode = postcode;
        this.sendpostcode = sendpostcode;
        this.homepage = homepage;
        this.officetel = officetel;
        this.mobile = mobile;
        this.fax = fax;
        this.email = email;
        this.taxcode = taxcode;
        this.specializeid = specializeid;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.updid = updid;
        this.upddate = upddate;
        this.lastcontact = lastcontact;
        this.nextcontact = nextcontact;
        this.remark = remark;
        this.isactivate = isactivate;
        this.activateid = activateid;
        this.activatedate = activatedate;
        this.isdel = isdel;
        this.isreceivemsg = isreceivemsg;
    }

    /** default constructor */
    public Customer() {
    }

    /** minimal constructor */
    public Customer(String cid) {
        this.cid = cid;
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

    public String getCpycode() {
        return this.cpycode;
    }

    public void setCpycode(String cpycode) {
        this.cpycode = cpycode;
    }

    public Integer getKind() {
        return this.kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getCardno() {
        return this.cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getMembersex() {
        return this.membersex;
    }

    public void setMembersex(Integer membersex) {
        this.membersex = membersex;
    }

    public Date getMemberbirthday() {
        return this.memberbirthday;
    }

    public void setMemberbirthday(Date memberbirthday) {
        this.memberbirthday = memberbirthday;
    }

    public String getMemberidcard() {
        return this.memberidcard;
    }

    public void setMemberidcard(String memberidcard) {
        this.memberidcard = memberidcard;
    }

    public String getMembercompany() {
        return this.membercompany;
    }

    public void setMembercompany(String membercompany) {
        this.membercompany = membercompany;
    }

    public String getMemberduty() {
        return this.memberduty;
    }

    public void setMemberduty(String memberduty) {
        this.memberduty = memberduty;
    }

    public String getTickettitle() {
        return this.tickettitle;
    }

    public void setTickettitle(String tickettitle) {
        this.tickettitle = tickettitle;
    }

    public Integer getMgrade() {
        return this.mgrade;
    }

    public void setMgrade(Integer mgrade) {
        this.mgrade = mgrade;
    }

    public Double getIntegral() {
        return this.integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public Integer getPrompt() {
        return this.prompt;
    }

    public void setPrompt(Integer prompt) {
        this.prompt = prompt;
    }

    public Double getCreditlock() {
        return this.creditlock;
    }

    public void setCreditlock(Double creditlock) {
        this.creditlock = creditlock;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getRate() {
        return this.rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getPolicyid() {
        return this.policyid;
    }

    public void setPolicyid(Integer policyid) {
        this.policyid = policyid;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTaxrate() {
        return this.taxrate;
    }

    public void setTaxrate(Double taxrate) {
        this.taxrate = taxrate;
    }

    public Integer getVocation() {
        return this.vocation;
    }

    public void setVocation(Integer vocation) {
        this.vocation = vocation;
    }

    public String getCphoto() {
        return this.cphoto;
    }

    public void setCphoto(String cphoto) {
        this.cphoto = cphoto;
    }

    public Integer getCustomertype() {
        return this.customertype;
    }

    public void setCustomertype(Integer customertype) {
        this.customertype = customertype;
    }

    public Integer getCustomerstatus() {
        return this.customerstatus;
    }

    public void setCustomerstatus(Integer customerstatus) {
        this.customerstatus = customerstatus;
    }

    public Integer getYauld() {
        return this.yauld;
    }

    public void setYauld(Integer yauld) {
        this.yauld = yauld;
    }

    public Integer getSource() {
        return this.source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTransportmode() {
        return this.transportmode;
    }

    public void setTransportmode(Integer transportmode) {
        this.transportmode = transportmode;
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

    public String getCommaddr() {
        return this.commaddr;
    }

    public void setCommaddr(String commaddr) {
        this.commaddr = commaddr;
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

    public String getSendpostcode() {
        return this.sendpostcode;
    }

    public void setSendpostcode(String sendpostcode) {
        this.sendpostcode = sendpostcode;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOfficetel() {
        return this.officetel;
    }

    public void setOfficetel(String officetel) {
        this.officetel = officetel;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxcode() {
        return this.taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    public Integer getSpecializeid() {
        return this.specializeid;
    }

    public void setSpecializeid(Integer specializeid) {
        this.specializeid = specializeid;
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

    public Integer getUpdid() {
        return this.updid;
    }

    public void setUpdid(Integer updid) {
        this.updid = updid;
    }

    public Date getUpddate() {
        return this.upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public Date getLastcontact() {
        return this.lastcontact;
    }

    public void setLastcontact(Date lastcontact) {
        this.lastcontact = lastcontact;
    }

    public Date getNextcontact() {
        return this.nextcontact;
    }

    public void setNextcontact(Date nextcontact) {
        this.nextcontact = nextcontact;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsactivate() {
        return this.isactivate;
    }

    public void setIsactivate(Integer isactivate) {
        this.isactivate = isactivate;
    }

    public Integer getActivateid() {
        return this.activateid;
    }

    public void setActivateid(Integer activateid) {
        this.activateid = activateid;
    }

    public Date getActivatedate() {
        return this.activatedate;
    }

    public void setActivatedate(Date activatedate) {
        this.activatedate = activatedate;
    }

    public Integer getIsdel() {
        return this.isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getIsreceivemsg() {
        return this.isreceivemsg;
    }

    public void setIsreceivemsg(Integer isreceivemsg) {
        this.isreceivemsg = isreceivemsg;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("cid", getCid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Customer) ) return false;
        Customer castOther = (Customer) other;
        return new EqualsBuilder()
            .append(this.getCid(), castOther.getCid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCid())
            .toHashCode();
    }

}
