package com.winsafe.drp.dao;

import java.util.Date;

/** @author Hibernate CodeGenerator */
public class ProviderForm {

    /** identifier field */
    private String pid;

    /** nullable persistent field */
    private String pname;

    /** nullable persistent field */
    private Integer vocation;
    
    private String vocationname;

    /** nullable persistent field */
    private String taxcode;

    /** nullable persistent field */
    private String corporation;

    /** nullable persistent field */
    private String bankaccount;

    /** nullable persistent field */
    private String bankname;

    private Integer genre;
    
    private String genrename;

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
    private String abcsortname;
    
    private Integer prompt;
    
    private Double taxrate;

    /** nullable persistent field */
    private String paycondition;

    /** nullable persistent field */
    private String homepage;

    /** nullable persistent field */
    private Integer province;
    
    private String provincename;

    /** nullable persistent field */
    private Integer city;
    
    private String cityname;

    /** nullable persistent field */
    private Integer areas;
    
    private String areasname;
    
    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private Date makedate;
    
    private String makedatename;

    /** nullable persistent field */
    private Integer updateid;
    
    private String updateidname;

    /** nullable persistent field */
    private Date modifydate;
    
    private String modifydatename;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isactivate;
    
    private String isactivatename;

    /** nullable persistent field */
    private Integer activateid;
    
    private String activateidname;

    /** nullable persistent field */
    private Date activatedate;
    
    private String activatedatename;

    /** nullable persistent field */
    private Integer isdel;
    
    private String isdelname;

    /** full constructor */
    
    
    private String plinkman;
    private String plinkmantel;
    private String plinkmanaddr;
     
 

    /** default constructor */
    public ProviderForm() {
    }

    /** minimal constructor */
    public ProviderForm(String pid) {
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

	public String getAbcsortname() {
		return abcsortname;
	}

	public void setAbcsortname(String abcsortname) {
		this.abcsortname = abcsortname;
	}

	public String getActivatedatename() {
		return activatedatename;
	}

	public void setActivatedatename(String activatedatename) {
		this.activatedatename = activatedatename;
	}

	public String getActivateidname() {
		return activateidname;
	}

	public void setActivateidname(String activateidname) {
		this.activateidname = activateidname;
	}

	public String getAreasname() {
		return areasname;
	}

	public void setAreasname(String areasname) {
		this.areasname = areasname;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getGenrename() {
		return genrename;
	}

	public void setGenrename(String genrename) {
		this.genrename = genrename;
	}

	public String getIsactivatename() {
		return isactivatename;
	}

	public void setIsactivatename(String isactivatename) {
		this.isactivatename = isactivatename;
	}

	public String getIsdelname() {
		return isdelname;
	}

	public void setIsdelname(String isdelname) {
		this.isdelname = isdelname;
	}

	public String getMakedatename() {
		return makedatename;
	}

	public void setMakedatename(String makedatename) {
		this.makedatename = makedatename;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getModifydatename() {
		return modifydatename;
	}

	public void setModifydatename(String modifydatename) {
		this.modifydatename = modifydatename;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public String getUpdateidname() {
		return updateidname;
	}

	public void setUpdateidname(String updateidname) {
		this.updateidname = updateidname;
	}

	public String getVocationname() {
		return vocationname;
	}

	public void setVocationname(String vocationname) {
		this.vocationname = vocationname;
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

	public String getPlinkman() {
		return plinkman;
	}

	public void setPlinkman(String plinkman) {
		this.plinkman = plinkman;
	}

	public String getPlinkmanaddr() {
		return plinkmanaddr;
	}

	public void setPlinkmanaddr(String plinkmanaddr) {
		this.plinkmanaddr = plinkmanaddr;
	}

	public String getPlinkmantel() {
		return plinkmantel;
	}

	public void setPlinkmantel(String plinkmantel) {
		this.plinkmantel = plinkmantel;
	}
 
}
