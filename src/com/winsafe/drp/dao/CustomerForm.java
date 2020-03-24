package com.winsafe.drp.dao;

import java.util.Date;

public class CustomerForm {

	/** identifier field */
    private String cid;

    /** nullable persistent field */
    private String cname;
    
    
    private String sendpostcode;
    
    private Integer vocation;
    
    private String vocationname;

    /** nullable persistent field */
    private String cphoto;

    /** nullable persistent field */
    private String signdate;

    /** nullable persistent field */
    private Integer customertype;
    
    private String customertypename;

    /** nullable persistent field */
    private Integer customerstatus;
    
    private String customerstatusname;

    /** nullable persistent field */
    private Integer yauld;
    
    private String yauldname;

    /** nullable persistent field */
    private Integer source;
    
    private String sourcename;
    
    private Integer ditchid;
    
    private String ditchidname;

    /** nullable persistent field */
    private Integer province;
    
    private String provincename;

    /** nullable persistent field */
    private Integer city;
    
    private String cityname;

    /** nullable persistent field */
    private Integer areas;
    
    private String areasname;

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
    private String bankname;

    /** nullable persistent field */
    private String doorname;

    /** nullable persistent field */
    private String bankaccount;

    /** nullable persistent field */
    private String taxcode;

    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private Integer specializeid;
    
    private String specializeidname;
    
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private Integer updid;
    
    private String updidname;

    /** nullable persistent field */
    private Date upddate;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;
    
    /** nullable persistent field */
    private String lastcontact;

    /** nullable persistent field */
    private String nextcontact;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isdel;
    
    private String isdelname;
    
    /** nullable persistent field */
    private Integer kind;
    
    private String kindname;
    
    /** nullable persistent field */
    private String cardno;

    /** nullable persistent field */
    private String parentid;

    private String parentidname;
    
    /** nullable persistent field */
    private String pwd;

    /** nullable persistent field */
    private Integer membersex;
    
    private String membersexname;

    /** nullable persistent field */
    private Date memberbirthday;
    
    private String memberbirthdayname;

    /** nullable persistent field */
    private String memberidcard;

    /** nullable persistent field */
    private String membercompany;

    /** nullable persistent field */
    private String memberduty;

    /** nullable persistent field */
    private String tickettitle;
    
    private Double integral;
    
    private String commaddr;

    /** nullable persistent field */
    private Integer prompt;   

    /** nullable persistent field */
    private Double creditlock;

    /** nullable persistent field */
    private Integer sort;
    
    private String sortname;

    /** nullable persistent field */
    private Integer rate;
    
    private String ratename;
    
    private Double discount;
    
    private Double taxrate;
    
    private Integer transit;
    
    private String transitname;
    
    private Integer isactivate;
    
    private String isactivatename;
    
    private  Integer activateid;
    
    private String activateidname;
    
    private String activatedate;
    
    /** nullable persistent field */
    private Integer specializedept;
    
    private String specializedeptname;
    
    private Integer paymentmode;
    
    private String paymentmodename;
    
    private Integer transportmode;
    
    private String transportmodename;
    
    private String accnumber;
    
    private Integer policyid;
    
    private String policyidname;
    
    private Double totalintegral;
    
    private Integer isreceivemsg;
    
    private String isreceivemsgname;
    
    

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

	public String getTransportmodename() {
		return transportmodename;
	}

	public void setTransportmodename(String transportmodename) {
		this.transportmodename = transportmodename;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getPaymentmodename() {
		return paymentmodename;
	}

	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
	}

	public Integer getSpecializedept() {
		return specializedept;
	}

	public void setSpecializedept(Integer specializedept) {
		this.specializedept = specializedept;
	}

	public String getSpecializedeptname() {
		return specializedeptname;
	}

	public void setSpecializedeptname(String specializedeptname) {
		this.specializedeptname = specializedeptname;
	}

	public Integer getTransit() {
		return transit;
	}

	public void setTransit(Integer transit) {
		this.transit = transit;
	}

	public String getTransitname() {
		return transitname;
	}

	public void setTransitname(String transitname) {
		this.transitname = transitname;
	}

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

	public String getKindname() {
		return kindname;
	}

	public void setKindname(String kindname) {
		this.kindname = kindname;
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

	public String getRatename() {
		return ratename;
	}

	public void setRatename(String ratename) {
		this.ratename = ratename;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getAreasname() {
		return areasname;
	}

	public void setAreasname(String areasname) {
		this.areasname = areasname;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCustomerstatusname() {
		return customerstatusname;
	}

	public void setCustomerstatusname(String customerstatusname) {
		this.customerstatusname = customerstatusname;
	}

	public String getCustomertypename() {
		return customertypename;
	}

	public void setCustomertypename(String customertypename) {
		this.customertypename = customertypename;
	}

	public String getIsdelname() {
		return isdelname;
	}

	public void setIsdelname(String isdelname) {
		this.isdelname = isdelname;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}


	public String getSourcename() {
		return sourcename;
	}

	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}

	public String getSpecializeidname() {
		return specializeidname;
	}

	public void setSpecializeidname(String specializeidname) {
		this.specializeidname = specializeidname;
	}

	public String getYauldname() {
		return yauldname;
	}

	public void setYauldname(String yauldname) {
		this.yauldname = yauldname;
	}

	public Integer getAreas() {
		return areas;
	}

	public void setAreas(Integer areas) {
		this.areas = areas;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
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

	public String getDoorname() {
		return doorname;
	}

	public void setDoorname(String doorname) {
		this.doorname = doorname;
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

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
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

	public Integer getSpecializeid() {
		return specializeid;
	}

	public void setSpecializeid(Integer specializeid) {
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

	public Integer getDitchid() {
		return ditchid;
	}

	public void setDitchid(Integer ditchid) {
		this.ditchid = ditchid;
	}

	public String getDitchidname() {
		return ditchidname;
	}

	public void setDitchidname(String ditchidname) {
		this.ditchidname = ditchidname;
	}

	public String getLastcontact() {
		return lastcontact;
	}

	public void setLastcontact(String lastcontact) {
		this.lastcontact = lastcontact;
	}

	public String getNextcontact() {
		return nextcontact;
	}

	public void setNextcontact(String nextcontact) {
		this.nextcontact = nextcontact;
	}

	public Integer getVocation() {
		return vocation;
	}

	public void setVocation(Integer vocation) {
		this.vocation = vocation;
	}

	public String getVocationname() {
		return vocationname;
	}

	public void setVocationname(String vocationname) {
		this.vocationname = vocationname;
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

	public String getActivatedate() {
		return activatedate;
	}

	public void setActivatedate(String activatedate) {
		this.activatedate = activatedate;
	}

	public Integer getActivateid() {
		return activateid;
	}

	public void setActivateid(Integer activateid) {
		this.activateid = activateid;
	}

	public String getActivateidname() {
		return activateidname;
	}

	public void setActivateidname(String activateidname) {
		this.activateidname = activateidname;
	}

	public Integer getIsactivate() {
		return isactivate;
	}

	public void setIsactivate(Integer isactivate) {
		this.isactivate = isactivate;
	}

	public String getIsactivatename() {
		return isactivatename;
	}

	public void setIsactivatename(String isactivatename) {
		this.isactivatename = isactivatename;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
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

	

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	

	public String getTickettitle() {
		return tickettitle;
	}

	public void setTickettitle(String tickettitle) {
		this.tickettitle = tickettitle;
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMembersexname() {
		return membersexname;
	}

	public void setMembersexname(String membersexname) {
		this.membersexname = membersexname;
	}

	public String getParentidname() {
		return parentidname;
	}

	public void setParentidname(String parentidname) {
		this.parentidname = parentidname;
	}

	public Double getIntegral() {
		return integral;
	}

	public void setIntegral(Double integral) {
		this.integral = integral;
	}

	public Date getUpddate() {
		return upddate;
	}

	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}

	public Integer getUpdid() {
		return updid;
	}

	public void setUpdid(Integer updid) {
		this.updid = updid;
	}

	public String getUpdidname() {
		return updidname;
	}

	public void setUpdidname(String updidname) {
		this.updidname = updidname;
	}

	public String getMemberbirthdayname() {
		return memberbirthdayname;
	}

	public void setMemberbirthdayname(String memberbirthdayname) {
		this.memberbirthdayname = memberbirthdayname;
	}


	public Integer getPolicyid() {
		return policyid;
	}

	public void setPolicyid(Integer policyid) {
		this.policyid = policyid;
	}

	public String getPolicyidname() {
		return policyidname;
	}

	public void setPolicyidname(String policyidname) {
		this.policyidname = policyidname;
	}

	public String getSendpostcode() {
		return sendpostcode;
	}

	public void setSendpostcode(String sendpostcode) {
		this.sendpostcode = sendpostcode;
	}

	public String getMakeorganidname() {
		return makeorganidname;
	}

	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

	/**
	 * @return the totalintegral
	 */
	public Double getTotalintegral() {
		return totalintegral;
	}

	/**
	 * @param totalintegral the totalintegral to set
	 */
	public void setTotalintegral(Double totalintegral) {
		this.totalintegral = totalintegral;
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

	/**
	 * @return the isreceivemsgname
	 */
	public String getIsreceivemsgname() {
		return isreceivemsgname;
	}

	/**
	 * @param isreceivemsgname the isreceivemsgname to set
	 */
	public void setIsreceivemsgname(String isreceivemsgname) {
		this.isreceivemsgname = isreceivemsgname;
	}
	
	
	
	

	
    
}
