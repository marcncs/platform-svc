package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Organ implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String sysid;

    /** nullable persistent field */
    private String organname;

    /** nullable persistent field */
    private String otel;

    /** nullable persistent field */
    private String omobile;

    /** nullable persistent field */
    private String ofax;

    /** nullable persistent field */
    private String oemail;

    /** nullable persistent field */
    private String oecode;

    /** nullable persistent field */
    private Integer rate;

    /** nullable persistent field */
    private Integer prompt;
    private Integer pprompt;
    
    private String logo;
    
    /** nullable persistent field */
    private String paycondition;

    /** nullable persistent field */
    private String parentid;

    /** nullable persistent field */
    private Integer rank;

    /** nullable persistent field */
    private Integer province;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Integer areas;

    /** nullable persistent field */
    private String oaddr;

    /** nullable persistent field */
    private Integer isrepeal;

    /** nullable persistent field */
    private Integer repealid;

    /** nullable persistent field */
    private Date repealdate;
    
    private String bigRegion;
    
    private Integer organType;
    
    //销售员id
    private String  salemanId;
    
    //区域字段
    private String regionareaid;
    private String regionarea;
    // 机构类别
    private Integer organModel;
    //营业执照
    private String license;
    //审核状态
    private Integer validatestatus;
    //审核时间
    private Date validatedate;
    //匹配后审核关联ID
    private String validateLoaclId;
    //创建时间
    private Date creationTime;
    
    private Date modificationTime;
 
    //审核人
    private Integer validateuserid;
    //客户级别(如:金牌，银牌等)
    private String customerlevel;
    //是否是关键零售商
    private Integer isKeyRetailer;
    //关键零售商的类型 1-零售商/2-大农户/3-合作社/4-其他
    private String organkeytype;
    
    //机构内转仓是否需要审批 0-不需要 1-需要审批
    private Integer isNeedApprove;

	public Organ(String id, String organname, String oecode) {
		super();
		this.id = id;
		this.organname = organname;
		this.oecode = oecode;
	}

	public String getRegionareaid() {
		return regionareaid;
	}
	public void setRegionareaid(String regionareaid) {
		this.regionareaid = regionareaid;
	}

	public String getRegionarea() {
		return regionarea;
	}
	public void setRegionarea(String regionarea) {
		this.regionarea = regionarea;
	}
	public String getSalemanId() {
		return salemanId;
	}

	public void setSalemanId(String salemanId) {
		this.salemanId = salemanId;
	}

	public String getBigRegion() {
		return bigRegion;
	}

	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	public Integer getOrganType() {
		return organType;
	}

	public void setOrganType(Integer organType) {
		this.organType = organType;
	}
	
	/**大区*/
    private Integer bigRegionId;
    private String bigRegionName;
     
    /**办事处*/
    private Integer officeId;
    private String officeName;
    

	public Integer getBigRegionId() {
		return bigRegionId;
	}

	public void setBigRegionId(Integer bigRegionId) {
		this.bigRegionId = bigRegionId;
	}

	public String getBigRegionName() {
		return bigRegionName;
	}

	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}

	public Integer getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
     * 机构简称
     */
    private String shortname;

    /** full constructor */
    public Organ(String id, String sysid, String organname, String otel, String omobile, String ofax, String oemail, String oecode, Integer rate, Integer prompt, String paycondition, String parentid, Integer rank, Integer province, Integer city, Integer areas, String oaddr, String bankacci, String accnamei, String banknamei, String bankaccii, String accnameii, String banknameii, String organprinta, String organprintb, String organprintc, Integer isrepeal, Integer repealid, Date repealdate) {
        this.id = id;
        this.sysid = sysid;
        this.organname = organname;
        this.otel = otel;
        this.omobile = omobile;
        this.ofax = ofax;
        this.oemail = oemail;
        this.oecode = oecode;
        this.rate = rate;
        this.prompt = prompt;
        this.paycondition = paycondition;
        this.parentid = parentid;
        this.rank = rank;
        this.province = province;
        this.city = city;
        this.areas = areas;
        this.oaddr = oaddr;
        this.isrepeal = isrepeal;
        this.repealid = repealid;
        this.repealdate = repealdate;
    }

    /** default constructor */
    public Organ() {
    }

    /** minimal constructor */
    public Organ(String id) {
        this.id = id;
    }

    public Integer getPprompt() {
		return pprompt;
	}

	public void setPprompt(Integer pprompt) {
		this.pprompt = pprompt;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysid() {
        return this.sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    public String getOrganname() {
        return this.organname;
    }

    public void setOrganname(String organname) {
        this.organname = organname;
    }

    public String getOtel() {
        return this.otel;
    }

    public void setOtel(String otel) {
        this.otel = otel;
    }

    public String getOmobile() {
        return this.omobile;
    }

    public void setOmobile(String omobile) {
        this.omobile = omobile;
    }

    public String getOfax() {
        return this.ofax;
    }

    public void setOfax(String ofax) {
        this.ofax = ofax;
    }

    public String getOemail() {
        return this.oemail;
    }

    public void setOemail(String oemail) {
        this.oemail = oemail;
    }

    public String getOecode() {
        return this.oecode;
    }

    public void setOecode(String oecode) {
        this.oecode = oecode;
    }

    public Integer getRate() {
        return this.rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getPrompt() {
        return this.prompt;
    }

    public void setPrompt(Integer prompt) {
        this.prompt = prompt;
    }

    public String getPaycondition() {
        return this.paycondition;
    }

    public void setPaycondition(String paycondition) {
        this.paycondition = paycondition;
    }

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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

    public String getOaddr() {
        return this.oaddr;
    }

    public void setOaddr(String oaddr) {
        this.oaddr = oaddr;
    }

    public Integer getIsrepeal() {
        return this.isrepeal;
    }

    public void setIsrepeal(Integer isrepeal) {
        this.isrepeal = isrepeal;
    }

    public Integer getRepealid() {
        return this.repealid;
    }

    public void setRepealid(Integer repealid) {
        this.repealid = repealid;
    }

    public Date getRepealdate() {
        return this.repealdate;
    }

    public void setRepealdate(Date repealdate) {
        this.repealdate = repealdate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Organ) ) return false;
        Organ castOther = (Organ) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Integer getOrganModel() {
		return organModel;
	}

	public void setOrganModel(Integer organModel) {
		this.organModel = organModel;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Integer getValidatestatus() {
		return validatestatus;
	}

	public void setValidatestatus(Integer validatestatus) {
		this.validatestatus = validatestatus;
	}

	public Date getValidatedate() {
		return validatedate;
	}

	public void setValidatedate(Date validatedate) {
		this.validatedate = validatedate;
	}

	public String getValidateLoaclId() {
		return validateLoaclId;
	}

	public void setValidateLoaclId(String validateLoaclId) {
		this.validateLoaclId = validateLoaclId;
	}

	public Integer getValidateuserid() {
		return validateuserid;
	}

	public void setValidateuserid(Integer validateuserid) {
		this.validateuserid = validateuserid;
	}

	public String getCustomerlevel() {
		return customerlevel;
	}

	public void setCustomerlevel(String customerlevel) {
		this.customerlevel = customerlevel;
	}

	public Integer getIsKeyRetailer() {
		return isKeyRetailer;
	}

	public void setIsKeyRetailer(Integer isKeyRetailer) {
		this.isKeyRetailer = isKeyRetailer;
	}

	public String getOrgankeytype() {
		return organkeytype;
	}

	public void setOrgankeytype(String organkeytype) {
		this.organkeytype = organkeytype;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public Integer getIsNeedApprove() {
		return isNeedApprove;
	}

	public void setIsNeedApprove(Integer isNeedApprove) {
		this.isNeedApprove = isNeedApprove;
	}
	
}
