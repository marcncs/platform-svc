package com.winsafe.drp.dao;

import java.util.Date;

public class PhoneBookForm {

  private Integer id;
  private String name;
  private Integer sex;
  private String phone;
  private String mobile;
  private String email;
  private String qq;
  private String msn;
  private String birthday;
  private String addr;
  private String remark;
  private String sortname;
  private String username;
  private Date makedate;
  public Date getMakedate() {
	return makedate;
}

public void setMakedate(Date makedate) {
	this.makedate = makedate;
}

public String getAddr() {
    return addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setSortname(String sortname) {
    this.sortname = sortname;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMsn(String msn) {
    this.msn = msn;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getBirthday() {
    return birthday;
  }

  public String getEmail() {
    return email;
  }

  public Integer getId() {
    return id;
  }

  public String getMobile() {
    return mobile;
  }

  public String getMsn() {
    return msn;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public String getQq() {
    return qq;
  }

  public String getRemark() {
    return remark;
  }

  public Integer getSex() {
    return sex;
  }

  public String getSortname() {
    return sortname;
  }

  public String getUsername() {
    return username;
  }
}
