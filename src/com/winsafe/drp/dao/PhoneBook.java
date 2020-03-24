package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PhoneBook implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private Integer sex;

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
    private Date birthday;

    /** nullable persistent field */
    private String addr;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer sortid;

    /** nullable persistent field */
    private Integer userid;

    /** full constructor */
    public PhoneBook(Integer id, String name, Integer sex, String phone, String mobile, String email, String qq, String msn, Date birthday, String addr, String remark, Integer sortid, Integer userid) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;
        this.qq = qq;
        this.msn = msn;
        this.birthday = birthday;
        this.addr = addr;
        this.remark = remark;
        this.sortid = sortid;
        this.userid = userid;
    }

    /** default constructor */
    public PhoneBook() {
    }

    /** minimal constructor */
    public PhoneBook(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return this.msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSortid() {
        return this.sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PhoneBook) ) return false;
        PhoneBook castOther = (PhoneBook) other;
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
