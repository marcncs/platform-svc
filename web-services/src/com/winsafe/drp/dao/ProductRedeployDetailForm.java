package com.winsafe.drp.dao;


/** @author Hibernate CodeGenerator */
public class ProductRedeployDetailForm   {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;
    
    
    private String productidname;
    /** nullable persistent field */
    private Double standardpurchase;

    /** nullable persistent field */
    private Double standardsale;

    /** nullable persistent field */
    private Double pricei;

    /** nullable persistent field */
    private Double priceii;

    /** nullable persistent field */
    private Double priceiii;

    /** nullable persistent field */
    private Double pricewholesale;

    /** nullable persistent field */
    private Double priceivs;

    /** nullable persistent field */
    private Double priceuni;

    /** nullable persistent field */
    private Double leastsale;

    /** nullable persistent field */
    private Double cost;
    
    private String specmode;
    
    private String countunitname;

    public String getCountunitname() {
		return countunitname;
	}

	public void setCountunitname(String countunitname) {
		this.countunitname = countunitname;
	}

	/** full constructor */
 

    public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	/** default constructor */
    public ProductRedeployDetailForm() {
    }

    /** minimal constructor */
    public ProductRedeployDetailForm(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Double getStandardpurchase() {
        return this.standardpurchase;
    }

    public void setStandardpurchase(Double standardpurchase) {
        this.standardpurchase = standardpurchase;
    }

    public Double getStandardsale() {
        return this.standardsale;
    }

    public void setStandardsale(Double standardsale) {
        this.standardsale = standardsale;
    }

    public Double getPricei() {
        return this.pricei;
    }

    public void setPricei(Double pricei) {
        this.pricei = pricei;
    }

    public Double getPriceii() {
        return this.priceii;
    }

    public void setPriceii(Double priceii) {
        this.priceii = priceii;
    }

    public Double getPriceiii() {
        return this.priceiii;
    }

    public void setPriceiii(Double priceiii) {
        this.priceiii = priceiii;
    }

    public Double getPricewholesale() {
        return this.pricewholesale;
    }

    public void setPricewholesale(Double pricewholesale) {
        this.pricewholesale = pricewholesale;
    }

    public Double getPriceivs() {
        return this.priceivs;
    }

    public void setPriceivs(Double priceivs) {
        this.priceivs = priceivs;
    }

    public String getProductidname() {
		return productidname;
	}

	public void setProductidname(String productidname) {
		this.productidname = productidname;
	}

	public Double getPriceuni() {
        return this.priceuni;
    }

    public void setPriceuni(Double priceuni) {
        this.priceuni = priceuni;
    }

    public Double getLeastsale() {
        return this.leastsale;
    }

    public void setLeastsale(Double leastsale) {
        this.leastsale = leastsale;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

   

}
