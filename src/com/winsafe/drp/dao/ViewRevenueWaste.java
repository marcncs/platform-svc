package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewRevenueWaste implements Serializable {

    /** nullable persistent field */
    private String id;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String roid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Double receivablesum;

    /** nullable persistent field */
    private Double incomesum;

    /** nullable persistent field */
    private String makeorganid;
    
    private VSTIDPK vstidPK; 

    /** full constructor */
    public ViewRevenueWaste(String id, Date makedate, String roid, Integer makeid, Integer paymentmode, String memo, Double receivablesum, Double incomesum, String makeorganid) {
        this.id = id;
        this.makedate = makedate;
        this.roid = roid;
        this.makeid = makeid;
        this.paymentmode = paymentmode;
        this.memo = memo;
        this.receivablesum = receivablesum;
        this.incomesum = incomesum;
        this.makeorganid = makeorganid;
    }

    /** default constructor */
    public ViewRevenueWaste() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getRoid() {
        return this.roid;
    }

    public void setRoid(String roid) {
        this.roid = roid;
    }

    public Integer getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Integer makeid) {
        this.makeid = makeid;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getReceivablesum() {
        return this.receivablesum;
    }

    public void setReceivablesum(Double receivablesum) {
        this.receivablesum = receivablesum;
    }

    public Double getIncomesum() {
        return this.incomesum;
    }

    public void setIncomesum(Double incomesum) {
        this.incomesum = incomesum;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
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
