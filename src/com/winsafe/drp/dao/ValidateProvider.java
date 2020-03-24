package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.struts.validator.ValidatorForm;

public class ValidateProvider extends ValidatorForm{

	/** identifier field */
    private String pid;
    
    /** nullable persistent field */
    private String pname;

    /** nullable persistent field */
    private Integer vocation;

    /** nullable persistent field */
    private String taxcode;

    /** nullable persistent field */
    private String corporation;

    /** nullable persistent field */
    private String bankaccount;

    /** nullable persistent field */
    private String bankname;

    /** nullable persistent field */
    private Integer genre;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String fax;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String postcode;

    /** nullable persistent field */
    private String addr;

    /** nullable persistent field */
    private Integer abcsort;
    
    private Integer prompt;
    
    private Double taxrate;

    /** nullable persistent field */
    private String paycondition;

    /** nullable persistent field */
    private String homepage;
    
    private Integer region;

    /** nullable persistent field */
    private Integer province;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Integer areas;

    /** nullable persistent field */
    private Long recordid;

    /** nullable persistent field */
    private Date registdate;

    /** nullable persistent field */
    private Integer useflag;

    /** nullable persistent field */
    private Date haltdate;

    /** nullable persistent field */
    private Long updateid;

    /** nullable persistent field */
    private Date modifydate;

    /** nullable persistent field */
    private String remark;
    
    
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
    private String[] lkaddr;
    private Integer[] ismain;
    private Integer[] wedlock;

	public Integer[] getWedlock() {
		return wedlock;
	}

	public void setWedlock(Integer[] wedlock) {
		this.wedlock = wedlock;
	}

	public Integer getAbcsort() {
		return abcsort;
	}

	public void setAbcsort(Integer abcsort) {
		this.abcsort = abcsort;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
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

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
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

	public Integer getGenre() {
		return genre;
	}

	public void setGenre(Integer genre) {
		this.genre = genre;
	}

	public Date getHaltdate() {
		return haltdate;
	}

	public void setHaltdate(Date haltdate) {
		this.haltdate = haltdate;
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

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getPaycondition() {
		return paycondition;
	}

	public void setPaycondition(String paycondition) {
		this.paycondition = paycondition;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
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

	public Long getRecordid() {
		return recordid;
	}

	public void setRecordid(Long recordid) {
		this.recordid = recordid;
	}

	public Date getRegistdate() {
		return registdate;
	}

	public void setRegistdate(Date registdate) {
		this.registdate = registdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTaxcode() {
		return taxcode;
	}

	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Long getUpdateid() {
		return updateid;
	}

	public void setUpdateid(Long updateid) {
		this.updateid = updateid;
	}

	public Integer getUseflag() {
		return useflag;
	}

	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}

	public Integer getVocation() {
		return vocation;
	}

	public void setVocation(Integer vocation) {
		this.vocation = vocation;
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

	public String[] getLinkid() {
		return linkid;
	}

	public void setLinkid(String[] linkid) {
		this.linkid = linkid;
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

	public String[] getLkaddr() {
		return lkaddr;
	}

	public void setLkaddr(String[] lkaddr) {
		this.lkaddr = lkaddr;
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

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public Integer getPrompt() {
		return prompt;
	}

	public void setPrompt(Integer prompt) {
		this.prompt = prompt;
	}

	public Double getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(Double taxrate) {
		this.taxrate = taxrate;
	}
  
}
