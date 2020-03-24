package com.winsafe.drp.dao;


public class DitchForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String dname;

    /** nullable persistent field */
    private String telone;

    /** nullable persistent field */
    private String teltwo;

    /** nullable persistent field */
    private String fax;

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
    private String detailaddr;

    /** nullable persistent field */
    private String postcode;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String homepage;

    /** nullable persistent field */
    private Integer ditchrank;
    
    private String ditchrankname;

    /** nullable persistent field */
    private Integer prestige;
    
    private String prestigename;

    /** nullable persistent field */
    private String scale;

    /** nullable persistent field */
    private Integer vocation;
    
    private String vocationname;

    /** nullable persistent field */
    private String taxcode;

    /** nullable persistent field */
    private String bankname;

    /** nullable persistent field */
    private String doorname;

    /** nullable persistent field */
    private String bankaccount;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Long userid;
    
    private String useridname;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

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

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
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

	public String getDetailaddr() {
		return detailaddr;
	}

	public void setDetailaddr(String detailaddr) {
		this.detailaddr = detailaddr;
	}

	public Integer getDitchrank() {
		return ditchrank;
	}

	public void setDitchrank(Integer ditchrank) {
		this.ditchrank = ditchrank;
	}

	public String getDitchrankname() {
		return ditchrankname;
	}

	public void setDitchrankname(String ditchrankname) {
		this.ditchrankname = ditchrankname;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getDoorname() {
		return doorname;
	}

	public void setDoorname(String doorname) {
		this.doorname = doorname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public Long getMakeid() {
		return makeid;
	}

	public void setMakeid(Long makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getPrestige() {
		return prestige;
	}

	public void setPrestige(Integer prestige) {
		this.prestige = prestige;
	}

	public String getPrestigename() {
		return prestigename;
	}

	public void setPrestigename(String prestigename) {
		this.prestigename = prestigename;
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

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getTaxcode() {
		return taxcode;
	}

	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}

	public String getTelone() {
		return telone;
	}

	public void setTelone(String telone) {
		this.telone = telone;
	}

	public String getTeltwo() {
		return teltwo;
	}

	public void setTeltwo(String teltwo) {
		this.teltwo = teltwo;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUseridname() {
		return useridname;
	}

	public void setUseridname(String useridname) {
		this.useridname = useridname;
	}

	public Integer getVocation() {
		return vocation;
	}

	public void setVocation(Integer vocation) {
		this.vocation = vocation;
	}

	public String getVocationname() {
		return vocationname;
	}

	public void setVocationname(String vocationname) {
		this.vocationname = vocationname;
	}
}
