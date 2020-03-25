package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.struts.validator.ValidatorForm;

/** @author Hibernate CodeGenerator */
public class ValidateMember extends ValidatorForm  {

    /** identifier field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String cardno;

    /** nullable persistent field */
    private String parentid;

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
    private Integer rate;

    /** nullable persistent field */
    private Double discount;

    /** nullable persistent field */
    private Double taxrate;

    /** nullable persistent field */
    private Integer source;

    /** nullable persistent field */
    private Integer region;

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
    private String officetel;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String remark;

	public Integer getAreas() {
		return areas;
	}

	public void setAreas(Integer areas) {
		this.areas = areas;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCommaddr() {
		return commaddr;
	}

	public void setCommaddr(String commaddr) {
		this.commaddr = commaddr;
	}

	public String getDetailaddr() {
		return detailaddr;
	}

	public void setDetailaddr(String detailaddr) {
		this.detailaddr = detailaddr;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getMemberbirthday() {
		return memberbirthday;
	}

	public void setMemberbirthday(Date memberbirthday) {
		this.memberbirthday = memberbirthday;
	}

	public String getMembercompany() {
		return membercompany;
	}

	public void setMembercompany(String membercompany) {
		this.membercompany = membercompany;
	}

	public String getMemberduty() {
		return memberduty;
	}

	public void setMemberduty(String memberduty) {
		this.memberduty = memberduty;
	}

	public String getMemberidcard() {
		return memberidcard;
	}

	public void setMemberidcard(String memberidcard) {
		this.memberidcard = memberidcard;
	}

	public Integer getMembersex() {
		return membersex;
	}

	public void setMembersex(Integer membersex) {
		this.membersex = membersex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficetel() {
		return officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Double getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(Double taxrate) {
		this.taxrate = taxrate;
	}

	public String getTickettitle() {
		return tickettitle;
	}

	public void setTickettitle(String tickettitle) {
		this.tickettitle = tickettitle;
	}


    

}
