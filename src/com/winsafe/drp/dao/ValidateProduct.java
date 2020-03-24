package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.struts.validator.ValidatorForm;
public class ValidateProduct extends ValidatorForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2907289370685834051L;

	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String productname;
    
    private String productnameen;

    /** nullable persistent field */
    private String pycode;

    /** nullable persistent field */
    private String psid;
    
    /** nullable persistent field */
    private String specmode;
    
    /** nullable persistent field */
    private Integer countunit;
    
    private Integer sunit;

    /** nullable persistent field */
    private Integer brand;

    /** nullable persistent field */
    private Integer wise;
    
    private Integer country;
    
    private String produceaddr;
    
    private String variety;
    
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
    
    /** nullable persistent field */
    private String barcode;

    /** nullable persistent field */
    private Integer isidcode;
    
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
    
    private String explain;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Integer useflag;
    
    private Integer isunify;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Long makedeptid;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isspecify;

    /** nullable persistent field */
    private Integer isemit;



	public Integer getAbcsort() {
		return abcsort;
	}

	public void setAbcsort(Integer abcsort) {
		this.abcsort = abcsort;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
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

	public Integer getIsemit() {
		return isemit;
	}

	public void setIsemit(Integer isemit) {
		this.isemit = isemit;
	}

	public Integer getIsidcode() {
		return isidcode;
	}

	public void setIsidcode(Integer isidcode) {
		this.isidcode = isidcode;
	}

	public Integer getIsspecify() {
		return isspecify;
	}

	public void setIsspecify(Integer isspecify) {
		this.isspecify = isspecify;
	}

	public Integer getIsunify() {
		return isunify;
	}

	public void setIsunify(Integer isunify) {
		this.isunify = isunify;
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

	public Long getMakeid() {
		return makeid;
	}

	public void setMakeid(Long makeid) {
		this.makeid = makeid;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
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

	public Integer getWise() {
		return wise;
	}

	public void setWise(Integer wise) {
		this.wise = wise;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
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

	public String getYearnum() {
		return yearnum;
	}

	public void setYearnum(String yearnum) {
		this.yearnum = yearnum;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public String getVariety() {
		return variety;
	}
    
    
}
