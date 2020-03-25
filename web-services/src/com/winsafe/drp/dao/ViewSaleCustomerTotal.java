package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewSaleCustomerTotal implements Serializable {

    /** nullable persistent field */
    private String id;

    /** nullable persistent field */
    private String billtype;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private String equiporganid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String cmobile;

    /** nullable persistent field */
    private Double totalsum;
    
    private VSTIDPK vstidPK;

    /** full constructor */
    public ViewSaleCustomerTotal(String id, String billtype, String makeorganid, String equiporganid, Integer makeid, Date makedate, String cid, String cname, String cmobile, Double totalsum) {
        this.id = id;
        this.billtype = billtype;
        this.makeorganid = makeorganid;
        this.equiporganid = equiporganid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.cid = cid;
        this.cname = cname;
        this.cmobile = cmobile;
        this.totalsum = totalsum;
    }

    /** default constructor */
    public ViewSaleCustomerTotal() {
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

    public String getEquiporganid() {
        return this.equiporganid;
    }

    public void setEquiporganid(String equiporganid) {
        this.equiporganid = equiporganid;
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

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCmobile() {
        return this.cmobile;
    }

    public void setCmobile(String cmobile) {
        this.cmobile = cmobile;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
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
