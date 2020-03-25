package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class LinkmanForm extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String cid;
    
    private String cidname;

    /** persistent field */
    private String name;

    /** persistent field */
    private Integer sex;
    
    private String sexname;

    /** nullable persistent field */
    private String idcard;

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
    private String addr;

    /** nullable persistent field */
    private Integer ismain;
    
    private String ismainname;
    
    private Integer relationcustomer;
    
    private String relationcustomername;

    /** nullable persistent field */
    private Long userid;
    
    private String useridname;
    
    private Integer transit;
    
    private String transitname;

    /** nullable persistent field */
    private String makedate;

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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
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

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
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

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getRelationcustomer() {
		return relationcustomer;
	}

	public void setRelationcustomer(Integer relationcustomer) {
		this.relationcustomer = relationcustomer;
	}

	public String getRelationcustomername() {
		return relationcustomername;
	}

	public void setRelationcustomername(String relationcustomername) {
		this.relationcustomername = relationcustomername;
	}

	public String getCidname() {
		return cidname;
	}

	public void setCidname(String cidname) {
		this.cidname = cidname;
	}

	public String getUseridname() {
		return useridname;
	}

	public void setUseridname(String useridname) {
		this.useridname = useridname;
	}

	public String getStrbirthday() {
		return strbirthday;
	}

	public void setStrbirthday(String strbirthday) {
		this.strbirthday = strbirthday;
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

    
}
