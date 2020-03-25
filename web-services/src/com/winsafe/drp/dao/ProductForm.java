package com.winsafe.drp.dao;

import java.util.Date;

public class ProductForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String productname;
    
    private String productnameen;

    /** nullable persistent field */
    private String pycode;

    /** nullable persistent field */
    private String psid;
    
    private String psidname;
    
    /** nullable persistent field */
    private String specmode;
    
    /** nullable persistent field */
    private Integer countunit;
    
    private String countunitname;
    
    private Integer sunit;
    
    private String sunitname;

    /** nullable persistent field */
    private Integer brand;
    
    private String brandname;

    /** nullable persistent field */
    private Integer wise;
    
    private String wisename;
    
    private Integer country;
    
    private String countryname;
    
    private String produceaddr;
    
    private String variety;
    
    private String varietyname;
    
    private String manufactory;
    
    /** nullable persistent field */
    private String manor;

    /** nullable persistent field */
    private String pgrade;

    /** nullable persistent field */
    private String yearnum;

    /** nullable persistent field */
    private String color;

    /** nullable persistent field */
    private String odor;

    /** nullable persistent field */
    private String orafeel;

    /** nullable persistent field */
    private String drinkscore;

    /** nullable persistent field */
    private String confadvice;
    
    /** nullable persistent field */
    private Integer isbarcode;
    
    private String isbarcodename;
    
    /** nullable persistent field */
    private String barcode;

    /** nullable persistent field */
    private Integer isidcode;
    
    private String isidcodename;
    
    /** nullable persistent field */
    private String productcode;
    
    private String productcodedef;
    
    private String productpicture;

    /** nullable persistent field */
    private Double leastsale;

    /** nullable persistent field */
    private Double cost;

    /** nullable persistent field */
    private Integer abcsort;
    
    private String abcsortname;
    
    private String explain;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Integer useflag;
    
    private String useflagname;
    
    private Integer isunify;
    
    private String isunifyname;

    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Long makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isemit;
    
    private String isemitname;
    private String nccode;
    
    private Double pricei;
    
    private Double priceii;
    
    private String packSizeName;

    private Double boxquantity;
    
    private Double stockpile;
    
    private Double defaultUnitQuantity;
    
    private Integer defaultUnit;
    
    private String defaultUnitName;
    
    private Integer needCovertCode;
    
    private String codeSeq; 
    
    private Integer copys;
    
	public Integer getAbcsort() {
		return abcsort;
	}

	public Double getBoxquantity() {
		return boxquantity;
	}

	public void setBoxquantity(Double boxquantity) {
		this.boxquantity = boxquantity;
	}

	public void setAbcsort(Integer abcsort) {
		this.abcsort = abcsort;
	}

	public String getAbcsortname() {
		return abcsortname;
	}

	public void setAbcsortname(String abcsortname) {
		this.abcsortname = abcsortname;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getPackSizeName() {
		return packSizeName;
	}

	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
	}

	public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getCountunit() {
		return countunit;
	}

	public void setCountunit(Integer countunit) {
		this.countunit = countunit;
	}

	public String getCountunitname() {
		return countunitname;
	}

	public void setCountunitname(String countunitname) {
		this.countunitname = countunitname;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsbarcode() {
		return isbarcode;
	}

	public void setIsbarcode(Integer isbarcode) {
		this.isbarcode = isbarcode;
	}

	public String getIsbarcodename() {
		return isbarcodename;
	}

	public void setIsbarcodename(String isbarcodename) {
		this.isbarcodename = isbarcodename;
	}

	public Integer getIsemit() {
		return isemit;
	}

	public void setIsemit(Integer isemit) {
		this.isemit = isemit;
	}

	public String getIsemitname() {
		return isemitname;
	}

	public void setIsemitname(String isemitname) {
		this.isemitname = isemitname;
	}

	public Integer getIsidcode() {
		return isidcode;
	}

	public void setIsidcode(Integer isidcode) {
		this.isidcode = isidcode;
	}

	public String getIsidcodename() {
		return isidcodename;
	}

	public void setIsidcodename(String isidcodename) {
		this.isidcodename = isidcodename;
	}

	
	public Integer getIsunify() {
		return isunify;
	}

	public void setIsunify(Integer isunify) {
		this.isunify = isunify;
	}

	public String getIsunifyname() {
		return isunifyname;
	}

	public void setIsunifyname(String isunifyname) {
		this.isunifyname = isunifyname;
	}

	public Double getLeastsale() {
		return leastsale;
	}

	public void setLeastsale(Double leastsale) {
		this.leastsale = leastsale;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Long getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Long makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakedeptidname() {
		return makedeptidname;
	}

	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getProductcodedef() {
		return productcodedef;
	}

	public void setProductcodedef(String productcodedef) {
		this.productcodedef = productcodedef;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductnameen() {
		return productnameen;
	}

	public void setProductnameen(String productnameen) {
		this.productnameen = productnameen;
	}

	public String getProductpicture() {
		return productpicture;
	}

	public void setProductpicture(String productpicture) {
		this.productpicture = productpicture;
	}

	public String getPsid() {
		return psid;
	}

	public void setPsid(String psid) {
		this.psid = psid;
	}

	public String getPsidname() {
		return psidname;
	}

	public void setPsidname(String psidname) {
		this.psidname = psidname;
	}

	public String getPycode() {
		return pycode;
	}

	public void setPycode(String pycode) {
		this.pycode = pycode;
	}

	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public Integer getUseflag() {
		return useflag;
	}

	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}

	public String getUseflagname() {
		return useflagname;
	}

	public void setUseflagname(String useflagname) {
		this.useflagname = useflagname;
	}

	public Integer getWise() {
		return wise;
	}

	public void setWise(Integer wise) {
		this.wise = wise;
	}

	public String getWisename() {
		return wisename;
	}

	public void setWisename(String wisename) {
		this.wisename = wisename;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public String getCountryname() {
		return countryname;
	}

	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}

	public String getManufactory() {
		return manufactory;
	}

	public void setManufactory(String manufactory) {
		this.manufactory = manufactory;
	}

	public String getProduceaddr() {
		return produceaddr;
	}

	public void setProduceaddr(String produceaddr) {
		this.produceaddr = produceaddr;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public String getVarietyname() {
		return varietyname;
	}

	public void setVarietyname(String varietyname) {
		this.varietyname = varietyname;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getConfadvice() {
		return confadvice;
	}

	public void setConfadvice(String confadvice) {
		this.confadvice = confadvice;
	}

	public String getDrinkscore() {
		return drinkscore;
	}

	public void setDrinkscore(String drinkscore) {
		this.drinkscore = drinkscore;
	}

	public String getManor() {
		return manor;
	}

	public void setManor(String manor) {
		this.manor = manor;
	}

	public String getOdor() {
		return odor;
	}

	public void setOdor(String odor) {
		this.odor = odor;
	}

	public String getOrafeel() {
		return orafeel;
	}

	public void setOrafeel(String orafeel) {
		this.orafeel = orafeel;
	}

	public String getPgrade() {
		return pgrade;
	}

	public void setPgrade(String pgrade) {
		this.pgrade = pgrade;
	}

	public Integer getSunit() {
		return sunit;
	}

	public void setSunit(Integer sunit) {
		this.sunit = sunit;
	}

	public String getSunitname() {
		return sunitname;
	}

	public void setSunitname(String sunitname) {
		this.sunitname = sunitname;
	}

	public String getYearnum() {
		return yearnum;
	}

	public void setYearnum(String yearnum) {
		this.yearnum = yearnum;
	}

	public Double getPricei() {
		return pricei;
	}

	public void setPricei(Double pricei) {
		this.pricei = pricei;
	}

	public Double getPriceii() {
		return priceii;
	}

	public void setPriceii(Double priceii) {
		this.priceii = priceii;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public Double getStockpile() {
		return stockpile;
	}

	public void setStockpile(Double stockpile) {
		this.stockpile = stockpile;
	}

	public Double getDefaultUnitQuantity() {
		return defaultUnitQuantity;
	}

	public void setDefaultUnitQuantity(Double defaultUnitQuantity) {
		this.defaultUnitQuantity = defaultUnitQuantity;
	}

	public Integer getDefaultUnit() {
		return defaultUnit;
	}

	public void setDefaultUnit(Integer defaultUnit) {
		this.defaultUnit = defaultUnit;
	}

	public String getDefaultUnitName() {
		return defaultUnitName;
	}

	public void setDefaultUnitName(String defaultUnitName) {
		this.defaultUnitName = defaultUnitName;
	}

	public Integer getNeedCovertCode() {
		return needCovertCode;
	}

	public void setNeedCovertCode(Integer needCovertCode) {
		this.needCovertCode = needCovertCode;
	}

	public String getCodeSeq() {
		return codeSeq;
	}

	public void setCodeSeq(String codeSeq) {
		this.codeSeq = codeSeq;
	}

	public Integer getCopys() {
		return copys;
	}

	public void setCopys(Integer copys) {
		this.copys = copys;
	}
	
}
