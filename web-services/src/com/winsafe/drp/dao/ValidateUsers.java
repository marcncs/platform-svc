package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.struts.validator.ValidatorForm;

public class ValidateUsers extends ValidatorForm{

        private Long userid;

        /** persistent field */
        private String loginname;

        /** nullable persistent field */
        private String password;

        private String approvepwd;

        /** nullable persistent field */
        private String realname;

        /** nullable persistent field */
        private String nameen;

        /** nullable persistent field */
        private Integer sex;

        /** nullable persistent field */
        private Date birthday;

        /** persistent field */
        private String idcard;

        /** nullable persistent field */
        private String mobile;

        /** nullable persistent field */
        private String officetel;

        /** nullable persistent field */
        private String hometel;

        /** nullable persistent field */
        private String email;

        /** nullable persistent field */
        private String qq;

        /** nullable persistent field */
        private String msn;
        
        private Integer province;
        
        private Integer city;
        
        private Integer areas;

        /** nullable persistent field */
        private String addr;

        private Date createdate;

        private Date lastlogin;

        private Integer logintimes;

        /** nullable persistent field */
        private Integer dept;

        private Long parentid;

        private Integer status;

  public String getAddr() {
    return addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public void setRealname(String realname) {
    this.realname = realname;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setOfficetel(String officetel) {
    this.officetel = officetel;
  }

  public void setNameen(String nameen) {
    this.nameen = nameen;
  }

  public void setMsn(String msn) {
    this.msn = msn;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setLoginname(String loginname) {
    this.loginname = loginname;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public void setHometel(String hometel) {
    this.hometel = hometel;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setDept(Integer dept) {
    this.dept = dept;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public void setUserid(Long userid) {
    this.userid = userid;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public void setParentid(Long parentid) {
    this.parentid = parentid;
  }

  public void setLogintimes(Integer logintimes) {
    this.logintimes = logintimes;
  }

  public void setLastlogin(Date lastlogin) {
    this.lastlogin = lastlogin;
  }

  public void setCreatedate(Date createdate) {
    this.createdate = createdate;
  }

  public void setApprovepwd(String approvepwd) {
    this.approvepwd = approvepwd;
  }

  public Date getBirthday() {
    return birthday;
  }

  public Integer getDept() {
    return dept;
  }

  public String getEmail() {
    return email;
  }

  public String getHometel() {
    return hometel;
  }

  public String getIdcard() {
    return idcard;
  }

  public String getLoginname() {
    return loginname;
  }

  public String getMobile() {
    return mobile;
  }

  public String getMsn() {
    return msn;
  }

  public String getNameen() {
    return nameen;
  }

  public String getOfficetel() {
    return officetel;
  }

  public String getPassword() {
    return password;
  }

  public String getQq() {
    return qq;
  }

  public String getRealname() {
    return realname;
  }

  public Integer getSex() {
    return sex;
  }

  public Long getUserid() {
    return userid;
  }

  public Integer getStatus() {
    return status;
  }

  public Long getParentid() {
    return parentid;
  }

  public Integer getLogintimes() {
    return logintimes;
  }

  public Date getLastlogin() {
    return lastlogin;
  }

  public Date getCreatedate() {
    return createdate;
  }

  public String getApprovepwd() {
    return approvepwd;
  }

public Integer getAreas() {
	return areas;
}

public void setAreas(Integer areas) {
	this.areas = areas;
}

public Integer getCity() {
	return city;
}

public void setCity(Integer city) {
	this.city = city;
}

public Integer getProvince() {
	return province;
}

public void setProvince(Integer province) {
	this.province = province;
}
}
