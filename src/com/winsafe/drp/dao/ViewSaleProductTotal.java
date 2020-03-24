package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewSaleProductTotal implements Serializable {

    /** nullable persistent field */
    private String id;

    /** nullable persistent field */
    private String billtype;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Long equiporganid;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Long unitid;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double subsum;

    /** nullable persistent field */
    private Double subcost;

    /** nullable persistent field */
    private Double gain;
    
    private VSTIDPK vstidPK;

    /** full constructor */
    public ViewSaleProductTotal(String id, String billtype, String makeorganid, Long equiporganid, Long makeid, Date makedate, String productid, String productname, String specmode, Long unitid, Double quantity, Double subsum, Double subcost, Double gain) {
        this.id = id;
        this.billtype = billtype;
        this.makeorganid = makeorganid;
        this.equiporganid = equiporganid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.quantity = quantity;
        this.subsum = subsum;
        this.subcost = subcost;
        this.gain = gain;
    }

    /** default constructor */
    public ViewSaleProductTotal() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBilltype() {
        return this.billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Long getEquiporganid() {
        return this.equiporganid;
    }

    public void setEquiporganid(Long equiporganid) {
        this.equiporganid = equiporganid;
    }

    public Long getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Long makeid) {
        this.makeid = makeid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
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

    public Long getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Long unitid) {
        this.unitid = unitid;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getSubsum() {
        return this.subsum;
    }

    public void setSubsum(Double subsum) {
        this.subsum = subsum;
    }

    public Double getSubcost() {
        return this.subcost;
    }

    public void setSubcost(Double subcost) {
        this.subcost = subcost;
    }

    public Double getGain() {
        return this.gain;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

	/**
	 * @return the vstidPK
	 */
	public VSTIDPK getVstidPK() {
		return vstidPK;
	}

	/**
	 * @param vstidPK the vstidPK to set
	 */
	public void setVstidPK(VSTIDPK vstidPK) {
		this.vstidPK = vstidPK;
	}
    
    

}
