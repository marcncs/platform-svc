package com.winsafe.drp.dao;

import java.util.Date;

public class PurchaseIncomeDetailForm {

	/** identifier field */
    private Long id;

    /** persistent field */
    private String piid;

    /** persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** persistent field */
    private Integer unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double subsum;

    /** nullable persistent field */
    private Integer issettlement;
    
    private String issettlementname;
    
    private Long settlementid;
    
    private String settlementidname;
    
    private Double cost;
    
    private Date makedate;

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Long getSettlementid() {
		return settlementid;
	}

	public void setSettlementid(Long settlementid) {
		this.settlementid = settlementid;
	}

	public String getSettlementidname() {
		return settlementidname;
	}

	public void setSettlementidname(String settlementidname) {
		this.settlementidname = settlementidname;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIssettlement() {
		return issettlement;
	}

	public void setIssettlement(Integer issettlement) {
		this.issettlement = issettlement;
	}

	public String getIssettlementname() {
		return issettlementname;
	}

	public void setIssettlementname(String issettlementname) {
		this.issettlementname = issettlementname;
	}

	public String getPiid() {
		return piid;
	}

	public void setPiid(String piid) {
		this.piid = piid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public Double getSubsum() {
		return subsum;
	}

	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}

	public Integer getUnitid() {
		return unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	public String getUnitidname() {
		return unitidname;
	}

	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}
}
