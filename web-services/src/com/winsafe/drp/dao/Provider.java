package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class Provider extends ActionForm implements Serializable {

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

    /** nullable persistent field */
    private Integer province;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Integer areas;
    
    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;
    
    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer updateid;

    /** nullable persistent field */
    private Date modifydate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isactivate;

    /** nullable persistent field */
    private Integer activateid;

    /** nullable persistent field */
    private Date activatedate;

    /** nullable persistent field */
    private Integer isdel;
    
    private String nccode;

    /** full constructor */
    public Provider(String pid, String pname, Integer vocation, String taxcode, String corporation, String bankaccount, String bankname, Integer genre, String tel, String fax, String mobile, String email, String postcode, String addr, Integer abcsort, String paycondition, String homepage, Integer province, Integer city, Integer areas, Integer makeid, Date makedate, Integer updateid, Date modifydate, String remark, Integer isactivate, Integer activateid, Date activatedate, Integer isdel,String nccode) {
        this.pid = pid;
        this.pname = pname;
        this.vocation = vocation;
        this.taxcode = taxcode;
        this.corporation = corporation;
        this.bankaccount = bankaccount;
        this.bankname = bankname;
        this.genre = genre;
        this.tel = tel;
        this.fax = fax;
        this.mobile = mobile;
        this.email = email;
        this.postcode = postcode;
        this.addr = addr;
        this.abcsort = abcsort;
        this.paycondition = paycondition;
        this.homepage = homepage;
        this.province = province;
        this.city = city;
        this.areas = areas;
        this.makeid = makeid;
        this.makedate = makedate;
        this.updateid = updateid;
        this.modifydate = modifydate;
        this.remark = remark;
        this.isactivate = isactivate;
        this.activateid = activateid;
        this.activatedate = activatedate;
        this.isdel = isdel;
        this.nccode=nccode;
    }

    public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	/** default constructor */
    public Provider() {
    }

    /** minimal constructor */
    public Provider(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return this.pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getVocation() {
        return this.vocation;
    }

    public void setVocation(Integer vocation) {
        this.vocation = vocation;
    }

    public String getTaxcode() {
        return this.taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    public String getCorporation() {
        return this.corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getBankaccount() {
        return this.bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getBankname() {
        return this.bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public Integer getGenre() {
        return this.genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getAbcsort() {
        return this.abcsort;
    }

    public void setAbcsort(Integer abcsort) {
        this.abcsort = abcsort;
    }

    public String getPaycondition() {
        return this.paycondition;
    }

    public void setPaycondition(String paycondition) {
        this.paycondition = paycondition;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getProvince() {
        return this.province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getAreas() {
        return this.areas;
    }

    public void setAreas(Integer areas) {
        this.areas = areas;
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

    public Integer getUpdateid() {
        return this.updateid;
    }

    public void setUpdateid(Integer updateid) {
        this.updateid = updateid;
    }

    public Date getModifydate() {
        return this.modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsactivate() {
        return this.isactivate;
    }

    public void setIsactivate(Integer isactivate) {
        this.isactivate = isactivate;
    }

    public Integer getActivateid() {
        return this.activateid;
    }

    public void setActivateid(Integer activateid) {
        this.activateid = activateid;
    }

    public Date getActivatedate() {
        return this.activatedate;
    }

    public void setActivatedate(Date activatedate) {
        this.activatedate = activatedate;
    }

    public Integer getIsdel() {
        return this.isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pid", getPid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Provider) ) return false;
        Provider castOther = (Provider) other;
        return new EqualsBuilder()
            .append(this.getPid(), castOther.getPid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getPid())
            .toHashCode();
    }

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
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
