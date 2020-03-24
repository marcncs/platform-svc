package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/** @author Hibernate CodeGenerator */
public class STransfelationForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5422245060554435056L;
	/** sequence field */
	private String sequence;
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

	private String ratename;

	/** nullable persistent field */
	private Integer prompt;
	private Integer pprompt;

	private FormFile logo;

	private String organprintd;

	/** nullable persistent field */
	private String paycondition;

	/** nullable persistent field */
	private String parentid;

	private String parentidname;

	/** nullable persistent field */
	private Integer rank;

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
	private String oaddr;

	/** nullable persistent field */
	private String bankacci;

	/** nullable persistent field */
	private String accnamei;

	/** nullable persistent field */
	private String banknamei;

	/** nullable persistent field */
	private String bankaccii;

	/** nullable persistent field */
	private String accnameii;

	/** nullable persistent field */
	private String banknameii;

	/** nullable persistent field */
	private String organprinta;

	/** nullable persistent field */
	private String organprintb;

	/** nullable persistent field */
	private String organprintc;

	/** nullable persistent field */
	private Integer isrepeal;

	/** nullable persistent field */
	private Integer repealid;

	/** nullable persistent field */
	private String repealdate;

	private Double integral;
	// 是否被选择的标志1，选择，0 未选择
	private Integer isCheck = 0;

	// 区域字段
	private String regionareaid;
	private String regionarea;

	/** 大区 */
	private Integer bigRegionId;
	private String bigRegionName;

	/** 办事处 */
	private Integer officeId;
	private String officeName;

	// 工厂类型
	private Integer organType;

	// 机构类别
	private Integer organModel;

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

	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getOrganname() {
		return organname;
	}

	public void setOrganname(String organname) {
		this.organname = organname;
	}

	public String getOtel() {
		return otel;
	}

	public void setOtel(String otel) {
		this.otel = otel;
	}

	public String getOmobile() {
		return omobile;
	}

	public void setOmobile(String omobile) {
		this.omobile = omobile;
	}

	public String getOfax() {
		return ofax;
	}

	public void setOfax(String ofax) {
		this.ofax = ofax;
	}

	public Integer getPprompt() {
		return pprompt;
	}

	public void setPprompt(Integer pprompt) {
		this.pprompt = pprompt;
	}

	public FormFile getLogo() {
		return logo;
	}

	public void setLogo(FormFile logo) {
		this.logo = logo;
	}

	public String getOrganprintd() {
		return organprintd;
	}

	public void setOrganprintd(String organprintd) {
		this.organprintd = organprintd;
	}

	public String getOemail() {
		return oemail;
	}

	public void setOemail(String oemail) {
		this.oemail = oemail;
	}

	public String getOecode() {
		return oecode;
	}

	public void setOecode(String oecode) {
		this.oecode = oecode;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getRatename() {
		return ratename;
	}

	public void setRatename(String ratename) {
		this.ratename = ratename;
	}

	public Integer getPrompt() {
		return prompt;
	}

	public void setPrompt(Integer prompt) {
		this.prompt = prompt;
	}

	public String getPaycondition() {
		return paycondition;
	}

	public void setPaycondition(String paycondition) {
		this.paycondition = paycondition;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getParentidname() {
		return parentidname;
	}

	public void setParentidname(String parentidname) {
		this.parentidname = parentidname;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
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

	public String getOaddr() {
		return oaddr;
	}

	public void setOaddr(String oaddr) {
		this.oaddr = oaddr;
	}

	public String getBankacci() {
		return bankacci;
	}

	public void setBankacci(String bankacci) {
		this.bankacci = bankacci;
	}

	public String getAccnamei() {
		return accnamei;
	}

	public void setAccnamei(String accnamei) {
		this.accnamei = accnamei;
	}

	public String getBanknamei() {
		return banknamei;
	}

	public void setBanknamei(String banknamei) {
		this.banknamei = banknamei;
	}

	public String getBankaccii() {
		return bankaccii;
	}

	public void setBankaccii(String bankaccii) {
		this.bankaccii = bankaccii;
	}

	public String getAccnameii() {
		return accnameii;
	}

	public void setAccnameii(String accnameii) {
		this.accnameii = accnameii;
	}

	public String getBanknameii() {
		return banknameii;
	}

	public void setBanknameii(String banknameii) {
		this.banknameii = banknameii;
	}

	public String getOrganprinta() {
		return organprinta;
	}

	public void setOrganprinta(String organprinta) {
		this.organprinta = organprinta;
	}

	public String getOrganprintb() {
		return organprintb;
	}

	public void setOrganprintb(String organprintb) {
		this.organprintb = organprintb;
	}

	public String getOrganprintc() {
		return organprintc;
	}

	public void setOrganprintc(String organprintc) {
		this.organprintc = organprintc;
	}

	public Integer getIsrepeal() {
		return isrepeal;
	}

	public void setIsrepeal(Integer isrepeal) {
		this.isrepeal = isrepeal;
	}

	public Integer getRepealid() {
		return repealid;
	}

	public void setRepealid(Integer repealid) {
		this.repealid = repealid;
	}

	public String getRepealdate() {
		return repealdate;
	}

	public void setRepealdate(String repealdate) {
		this.repealdate = repealdate;
	}

	public Double getIntegral() {
		return integral;
	}

	public void setIntegral(Double integral) {
		this.integral = integral;
	}

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

	public Integer getOrganModel() {
		return organModel;
	}

	public void setOrganModel(Integer organModel) {
		this.organModel = organModel;
	}

	public Integer getOrganType() {
		return organType;
	}

	public void setOrganType(Integer organType) {
		this.organType = organType;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
