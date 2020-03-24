package com.winsafe.drp.dao;

import org.apache.struts.validator.ValidatorForm;

public class ValidatePhoneBook extends ValidatorForm{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2697504285181121568L;

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String name;

    /** persistent field */
    private int sex;

    /** nullable persistent field */
    private String phone;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String qq;

    /** nullable persistent field */
    private String msn;

    /** nullable persistent field */
    private String birthday;

    /** nullable persistent field */
    private String addr;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer sortid;

    /** nullable persistent field */
    private Integer userid;
  public String getAddr() {
    return addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public void setSortid(Integer sortid) {
    this.sortid = sortid;
  }

  public void setSex(int sex) {
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

  public int getSex() {
    return sex;
  }

  public Integer getSortid() {
    return sortid;
  }

  public Integer getUserid() {
    return userid;
  }

  public String getBirthday() {
    return birthday;
  }
}
