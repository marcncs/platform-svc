package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class UsersForm extends ActionForm implements Serializable{

    /** identifier field */
    private Integer userid;

    /** persistent field */
    private String loginname; 

    /** nullable persistent field */
    private String password;

    /** nullable persistent field */
    private String approvepwd;

    /** nullable persistent field */
    private String realname;

    /** nullable persistent field */
    private String nameen;

    /** nullable persistent field */
    private Integer sex;
    
    private String sexname;

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
    
    private String provincename;
    
    private Integer city;
    
    private String cityname;
    
    private Integer areas;
    
    private String areasname;

    /** nullable persistent field */
    private String addr;

    /** nullable persistent field */
    private Date createdate;
    
    private String validate;

    /** nullable persistent field */
    private Date lastlogin;

    /** nullable persistent field */
    private Integer logintimes;

    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private String supername;

    /** nullable persistent field */
    private Integer status;
    
    private String statusname;
    
    private Integer islogin;
    
    private String isloginname;
    
    private Integer iscall;
    
    private String iscallname;
    
    //用户性质
    private Integer UserType;

    //是否选中
    private Integer isChecked = 0;
    
    private String isCwid; 
    
    private String userTypeName;
    
    private Integer isLocked;

	public Integer getUserType() {
		return UserType;
	}

	public void setUserType(Integer userType) {
		UserType = userType;
	}

	public Integer getIslogin() {
		return islogin;
	}

	public void setIslogin(Integer islogin) {
		this.islogin = islogin;
	}

	public String getIsloginname() {
		return isloginname;
	}

	public void setIsloginname(String isloginname) {
		this.isloginname = isloginname;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public String getSupername() {
		return supername;
	}

	public void setSupername(String supername) {
		this.supername = supername;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getApprovepwd() {
		return approvepwd;
	}

	public void setApprovepwd(String approvepwd) {
		this.approvepwd = approvepwd;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
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

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Date getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public Integer getLogintimes() {
		return logintimes;
	}

	public void setLogintimes(Integer logintimes) {
		this.logintimes = logintimes;
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

	public String getNameen() {
		return nameen;
	}

	public void setNameen(String nameen) {
		this.nameen = nameen;
	}

	public String getOfficetel() {
		return officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakedeptidname() {
		return makedeptidname;
	}

	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public String getMakeorganidname() {
		return makeorganidname;
	}

	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

	/**
	 * @return the iscall
	 */
	public Integer getIscall() {
		return iscall;
	}

	/**
	 * @param iscall the iscall to set
	 */
	public void setIscall(Integer iscall) {
		this.iscall = iscall;
	}

	/**
	 * @return the iscallname
	 */
	public String getIscallname() {
		return iscallname;
	}

	/**
	 * @param iscallname the iscallname to set
	 */
	public void setIscallname(String iscallname) {
		this.iscallname = iscallname;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public Integer getAreas() {
		return areas;
	}

	public void setAreas(Integer areas) {
		this.areas = areas;
	}

	public String getAreasname() {
		return areasname;
	}

	public void setAreasname(String areasname) {
		this.areasname = areasname;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public Integer getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

	public String getIsCwid() {
		return isCwid;
	}

	public void setIsCwid(String isCwid) {
		this.isCwid = isCwid;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public Integer getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}
    
}
