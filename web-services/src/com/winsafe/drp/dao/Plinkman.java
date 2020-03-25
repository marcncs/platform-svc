package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class Plinkman extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String pid;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private Integer sex;

    /** nullable persistent field */
    private String idcard;

    /** nullable persistent field */
    private Date birthday;

    /** nullable persistent field */
    private String department;

    /** nullable persistent field */
    private String duty;

    /** nullable persistent field */
    private String officetel;
    
    private String fax;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String hometel;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String qq;

    /** nullable persistent field */
    private String msn;

    /** nullable persistent field */
    private Integer ismain;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;
    
    private String nccode;

    /** full constructor */
    public Plinkman(Integer id, String pid, String name, Integer sex, String idcard, Date birthday, String department, String duty, String officetel, String mobile, String hometel, String email, String qq, String msn, Integer ismain, String makeorganid, Integer makedeptid, Integer makeid, Date makedate,String nccode) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.sex = sex;
        this.idcard = idcard;
        this.birthday = birthday;
        this.department = department;
        this.duty = duty;
        this.officetel = officetel;
        this.mobile = mobile;
        this.hometel = hometel;
        this.email = email;
        this.qq = qq;
        this.msn = msn;
        this.ismain = ismain;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.nccode=nccode;
    }

    public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	/** default constructor */
    public Plinkman() {
    }

    /** minimal constructor */
    public Plinkman(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getIdcard() {
        return this.idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDuty() {
        return this.duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
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

    public String getHometel() {
        return this.hometel;
    }

    public void setHometel(String hometel) {
        this.hometel = hometel;
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

    public Integer getIsmain() {
        return this.ismain;
    }

    public void setIsmain(Integer ismain) {
        this.ismain = ismain;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Plinkman) ) return false;
        Plinkman castOther = (Plinkman) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

}
