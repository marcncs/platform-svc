package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class PeddleOrderDetailForm extends ActionForm implements Serializable {

    /** nullable persistent field */
    private Long id;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;
    
    private Integer salesort;

    /** nullable persistent field */
    private Long warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private Long unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private String batch;
    
    private Double orgunitprice;

    /** nullable persistent field */
    private Double unitprice;
    
    private Double taxunitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double discount;

    /** nullable persistent field */
    private Double taxrate;

    /** nullable persistent field */
    private Double cost;

    /** nullable persistent field */
    private Double subsum;
    
    private Integer rows;
    
    private String cname;

    /** full constructor */
     
    public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/** default constructor */
    public PeddleOrderDetailForm() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoid() {
        return this.poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSpecmode() {
        return this.specmode;
    }

    public void setSpecmode(String specmode) {
        this.specmode = specmode;
    }

    public Long getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(Long warehouseid) {
        this.warehouseid = warehouseid;
    }

    public Long getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Long unitid) {
        this.unitid = unitid;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Double getUnitprice() {
        return this.unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTaxrate() {
        return this.taxrate;
    }

    public void setTaxrate(Double taxrate) {
        this.taxrate = taxrate;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getSubsum() {
        return this.subsum;
    }

    public void setSubsum(Double subsum) {
        this.subsum = subsum;
    }

	public String getUnitidname() {
		return unitidname;
	}

	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}


	public String getWarehouseidname() {
		return warehouseidname;
	}

	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
	}

	public Double getOrgunitprice() {
		return orgunitprice;
	}

	public void setOrgunitprice(Double orgunitprice) {
		this.orgunitprice = orgunitprice;
	}

	public Double getTaxunitprice() {
		return taxunitprice;
	}

	public void setTaxunitprice(Double taxunitprice) {
		this.taxunitprice = taxunitprice;
	}

	public Integer getSalesort() {
		return salesort;
	}

	public void setSalesort(Integer salesort) {
		this.salesort = salesort;
	}

	/**
	 * @return the cname
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	
    
}
