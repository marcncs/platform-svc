package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

/** @author Hibernate CodeGenerator */
public class CarForm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7612575171585040309L;

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String carbrand;

    /** nullable persistent field */
    private Integer carsort;
    
    private String carsortname;

    /** nullable persistent field */
    private Date purchasedate;
    
    private String purchasedatename;

    /** nullable persistent field */
    private Double worth;
    
    private String worthname;

    /** nullable persistent field */
    private Integer isleisure;
    
    private String isleisurename;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private Date makedate;
    
    private String makedatename;

    /** full constructor */
     

    /** default constructor */
    public CarForm() {
    }

    /** minimal constructor */
    public CarForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarbrand() {
        return this.carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand;
    }

    public Integer getCarsort() {
        return this.carsort;
    }

    public void setCarsort(Integer carsort) {
        this.carsort = carsort;
    }

    public Date getPurchasedate() {
        return this.purchasedate;
    }

    public void setPurchasedate(Date purchasedate) {
        this.purchasedate = purchasedate;
    }

    

    public Double getWorth() {
		return worth;
	}

	public void setWorth(Double worth) {
		this.worth = worth;
	}

	public Integer getIsleisure() {
        return this.isleisure;
    }

    public void setIsleisure(Integer isleisure) {
        this.isleisure = isleisure;
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

	public String getIsleisurename() {
		return isleisurename;
	}

	public void setIsleisurename(String isleisurename) {
		this.isleisurename = isleisurename;
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

	public String getPurchasedatename() {
		return purchasedatename;
	}

	public void setPurchasedatename(String purchasedatename) {
		this.purchasedatename = purchasedatename;
	}

	public String getWorthname() {
		return worthname;
	}

	public void setWorthname(String worthname) {
		this.worthname = worthname;
	}

	public String getCarsortname() {
		return carsortname;
	}

	public void setCarsortname(String carsortname) {
		this.carsortname = carsortname;
	}

 

}
