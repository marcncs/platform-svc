package com.winsafe.drp.dao;

import java.util.Date;

public class PlinkmanForm {
	/** identifier field */
    private Integer id;

    /** persistent field */
    private String pid;
    
    private String providename;

    /** persistent field */
    private String name;

    /** persistent field */
    private Integer sex;
    
    private String sexname;
    
    private String idcard;

    /** nullable persistent field */
    private Integer wedlock;
    
    private String wedlockname;

    /** nullable persistent field */
    private Date birthday;
    
    private String strbirthday;

    /** nullable persistent field */
    private String department;

    /** nullable persistent field */
    private String duty;

    /** nullable persistent field */
    private String officetel;

    /** nullable persistent field */
    private String fax;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String hometel;

    /** nullable persistent field */
    private String qq;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String msn;

    /** nullable persistent field */
    private String addr;

    /** nullable persistent field */
    private Integer ismain;
    
    private String ismainname;

    /** nullable persistent field */
    private String memo;

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
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

	public String getHometel() {
		return hometel;
	}

	public void setHometel(String hometel) {
		this.hometel = hometel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsmain() {
		return ismain;
	}

	public void setIsmain(Integer ismain) {
		this.ismain = ismain;
	}

	public String getIsmainname() {
		return ismainname;
	}

	public void setIsmainname(String ismainname) {
		this.ismainname = ismainname;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOfficetel() {
		return officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getProvidename() {
		return providename;
	}

	public void setProvidename(String providename) {
		this.providename = providename;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSexname() {
		return sexname;
	}

	public void setSexname(String sexname) {
		this.sexname = sexname;
	}

	public Integer getWedlock() {
		return wedlock;
	}

	public void setWedlock(Integer wedlock) {
		this.wedlock = wedlock;
	}

	public String getWedlockname() {
		return wedlockname;
	}

	public void setWedlockname(String wedlockname) {
		this.wedlockname = wedlockname;
	}

	public String getStrbirthday() {
		return strbirthday;
	}

	public void setStrbirthday(String strbirthday) {
		this.strbirthday = strbirthday;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

}
