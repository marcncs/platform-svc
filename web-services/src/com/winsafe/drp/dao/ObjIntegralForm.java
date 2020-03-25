package com.winsafe.drp.dao;

import java.io.Serializable;

/** @author Hibernate CodeGenerator */
public class ObjIntegralForm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1436366869663906851L;

	/** identifier field */
    private Integer oiid;
    
    private String oid;

    /** nullable persistent field */
    private Integer osort;
    
    private String osortname;

    /** nullable persistent field */
    private String oname;

    /** nullable persistent field */
    private String omobile;
    
    private Double rvincome;
    
    private Double alincome;
    
    private Double rvout;
    
    private Double alout;
    
    private Double balance;

    /** nullable persistent field */
    private String organid;
    
    private String organidname;
    
    private String keyscontent;

    public String getKeyscontent() {
		return keyscontent;
	}

	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}



    /** default constructor */
    public ObjIntegralForm() {
    }


    public Integer getOsort() {
        return this.osort;
    }

    public void setOsort(Integer osort) {
        this.osort = osort;
    }

    public String getOname() {
        return this.oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getOmobile() {
        return this.omobile;
    }

    public void setOmobile(String omobile) {
        this.omobile = omobile;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

   

	public Double getAlincome() {
		return alincome;
	}

	public void setAlincome(Double alincome) {
		this.alincome = alincome;
	}

	public Double getAlout() {
		return alout;
	}

	public void setAlout(Double alout) {
		this.alout = alout;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getRvincome() {
		return rvincome;
	}

	public void setRvincome(Double rvincome) {
		this.rvincome = rvincome;
	}

	public Double getRvout() {
		return rvout;
	}

	public void setRvout(Double rvout) {
		this.rvout = rvout;
	}

	public String getOsortname() {
		return osortname;
	}

	public void setOsortname(String osortname) {
		this.osortname = osortname;
	}

	public String getOrganidname() {
		return organidname;
	}

	public void setOrganidname(String organidname) {
		this.organidname = organidname;
	}

	public Integer getOiid() {
		return oiid;
	}

	public void setOiid(Integer oiid) {
		this.oiid = oiid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
	

}
