package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

public class ValidateCustomer
    extends ValidatorForm {

	/** identifier field */
    private String cid;

    /** nullable persistent field */
    private String cname;
    
    private Integer vocation;

    /** nullable persistent field */
    private String cphoto;

    /** nullable persistent field */
    private String signdate;

    /** nullable persistent field */
    private Integer customertype;

    /** nullable persistent field */
    private Integer customerstatus;

    /** nullable persistent field */
    private Integer yauld;

    /** nullable persistent field */
    private Integer source;
    
    private Long ditchid;
    
    private Integer region;

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
    private Date makedate;

    /** nullable persistent field */
    private Long specializeid;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private String remark;
    
    private FormFile picture;
    
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
    
    private String commaddr;

    /** nullable persistent field */
    private Integer prompt;

    /** nullable persistent field */
    private Double creditlock;

    /** nullable persistent field */
    private Integer sort;

    /** nullable persistent field */
    private Integer rate;
    
    private Double discount;
    
    private Double taxrate;
    
    private Integer isactivate;

    private Integer paymentmode;
    
    private Integer transportmode;
    
    private String accnumber;
    
    private Integer isreceivemsg;
    
    
    
    private String[] linkid;
    private String[] name;
    private Integer[] sex;
    private String[] idcard;
    private String[] birthday;
    private String[] department;
    private String[] duty;
    private String[] linkofficetel;
    private String[] linkmobile;
    private String[] hometel;
    private String[] linkemail;
    private String[] qq;
    private String[] msn;
    private String[] addr;
    private Integer[] ismain;
    private Integer[] transit;
    
    
    private String[] bankid;
    private String[] bankname;
    private String[] doorname;
    private String[] bankaccount;
    

	public Double getCreditlock() {
		return creditlock;
	}

	public void setCreditlock(Double creditlock) {
		this.creditlock = creditlock;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Integer getPrompt() {
		return prompt;
	}

	public void setPrompt(Integer prompt) {
		this.prompt = prompt;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getAreas() {
		return areas;
	}

	public void setAreas(Integer areas) {
		this.areas = areas;
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

	public String getCphoto() {
		return cphoto;
	}

	public void setCphoto(String cphoto) {
		this.cphoto = cphoto;
	}

	public Integer getCustomerstatus() {
		return customerstatus;
	}

	public void setCustomerstatus(Integer customerstatus) {
		this.customerstatus = customerstatus;
	}

	public Integer getCustomertype() {
		return customertype;
	}

	public void setCustomertype(Integer customertype) {
		this.customertype = customertype;
	}

	public String getDetailaddr() {
		return detailaddr;
	}

	public void setDetailaddr(String detailaddr) {
		this.detailaddr = detailaddr;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
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

	public FormFile getPicture() {
		return picture;
	}

	public void setPicture(FormFile picture) {
		this.picture = picture;
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


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSigndate() {
		return signdate;
	}

	public void setSigndate(String signdate) {
		this.signdate = signdate;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Long getSpecializeid() {
		return specializeid;
	}

	public void setSpecializeid(Long specializeid) {
		this.specializeid = specializeid;
	}

	public String getTaxcode() {
		return taxcode;
	}

	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}

	public Integer getYauld() {
		return yauld;
	}

	public void setYauld(Integer yauld) {
		this.yauld = yauld;
	}

	public Long getDitchid() {
		return ditchid;
	}

	public void setDitchid(Long ditchid) {
		this.ditchid = ditchid;
	}

	public Integer getVocation() {
		return vocation;
	}

	public void setVocation(Integer vocation) {
		this.vocation = vocation;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(Double taxrate) {
		this.taxrate = taxrate;
	}

	public String[] getAddr() {
		return addr;
	}

	public void setAddr(String[] addr) {
		this.addr = addr;
	}

	public String[] getBirthday() {
		return birthday;
	}

	public void setBirthday(String[] birthday) {
		this.birthday = birthday;
	}

	public String[] getDepartment() {
		return department;
	}

	public void setDepartment(String[] department) {
		this.department = department;
	}

	public String[] getDuty() {
		return duty;
	}

	public void setDuty(String[] duty) {
		this.duty = duty;
	}

	public String[] getHometel() {
		return hometel;
	}

	public void setHometel(String[] hometel) {
		this.hometel = hometel;
	}

	public String[] getIdcard() {
		return idcard;
	}

	public void setIdcard(String[] idcard) {
		this.idcard = idcard;
	}

	public Integer[] getIsmain() {
		return ismain;
	}

	public void setIsmain(Integer[] ismain) {
		this.ismain = ismain;
	}

	public String[] getLinkemail() {
		return linkemail;
	}

	public void setLinkemail(String[] linkemail) {
		this.linkemail = linkemail;
	}

	public String[] getLinkmobile() {
		return linkmobile;
	}

	public void setLinkmobile(String[] linkmobile) {
		this.linkmobile = linkmobile;
	}

	public String[] getLinkofficetel() {
		return linkofficetel;
	}

	public void setLinkofficetel(String[] linkofficetel) {
		this.linkofficetel = linkofficetel;
	}

	public String[] getMsn() {
		return msn;
	}

	public void setMsn(String[] msn) {
		this.msn = msn;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getQq() {
		return qq;
	}

	public void setQq(String[] qq) {
		this.qq = qq;
	}

	public Integer[] getSex() {
		return sex;
	}

	public void setSex(Integer[] sex) {
		this.sex = sex;
	}

	public String[] getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String[] bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String[] getBankname() {
		return bankname;
	}

	public void setBankname(String[] bankname) {
		this.bankname = bankname;
	}

	public String[] getDoorname() {
		return doorname;
	}

	public void setDoorname(String[] doorname) {
		this.doorname = doorname;
	}

	public String[] getBankid() {
		return bankid;
	}

	public void setBankid(String[] bankid) {
		this.bankid = bankid;
	}

	public String[] getLinkid() {
		return linkid;
	}

	public void setLinkid(String[] linkid) {
		this.linkid = linkid;
	}

	public Integer[]getTransit() {
		return transit;
	}

	public void setTransit(Integer[] transit) {
		this.transit = transit;
	}

	public Integer getIsactivate() {
		return isactivate;
	}

	public void setIsactivate(Integer isactivate) {
		this.isactivate = isactivate;
	}


	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Long getMakeid() {
		return makeid;
	}

	public void setMakeid(Long makeid) {
		this.makeid = makeid;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getAccnumber() {
		return accnumber;
	}

	public void setAccnumber(String accnumber) {
		this.accnumber = accnumber;
	}

	public Integer getTransportmode() {
		return transportmode;
	}

	public void setTransportmode(Integer transportmode) {
		this.transportmode = transportmode;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCommaddr() {
		return commaddr;
	}

	public void setCommaddr(String commaddr) {
		this.commaddr = commaddr;
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

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTickettitle() {
		return tickettitle;
	}

	public void setTickettitle(String tickettitle) {
		this.tickettitle = tickettitle;
	}

	/**
	 * @return the isreceivemsg
	 */
	public Integer getIsreceivemsg() {
		return isreceivemsg;
	}

	/**
	 * @param isreceivemsg the isreceivemsg to set
	 */
	public void setIsreceivemsg(Integer isreceivemsg) {
		this.isreceivemsg = isreceivemsg;
	}

	

}
