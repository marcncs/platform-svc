package com.winsafe.drp.dao;
import org.apache.struts.action.ActionForm;

public class ValidateLinkman extends ActionForm{
  /**
	 * 
	 */
	private static final long serialVersionUID = 3894265231754493895L;

private Integer id;

    /** persistent field */
    private String cid;

    /** persistent field */
    private String name;

    /** persistent field */
    private String sex;

    /** nullable persistent field */
    private String idcard;

    /** nullable persistent field */
    private String birthday;

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
    
    private String qq;

    /** nullable persistent field */
    private String msn;

    /** nullable persistent field */
    private String addr;

    /** nullable persistent field */
    private String ismain;

    /** nullable persistent field */
    private Integer userid;

    /** full constructor */
    

   
   

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return this.idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
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

    public String getMsn() {
        return this.msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getIsmain() {
        return this.ismain;
    }

    public void setIsmain(String ismain) {
        this.ismain = ismain;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

}
