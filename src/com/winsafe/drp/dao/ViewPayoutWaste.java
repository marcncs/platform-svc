package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewPayoutWaste implements Serializable {

    /** nullable persistent field */
    private String id;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Integer paymode;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Double payablesum;

    /** nullable persistent field */
    private Double paysum;

    /** nullable persistent field */
    private String makeorganid;
    
    private VSTIDPK vstidPK;

    /** full constructor */
    public ViewPayoutWaste(String id, Date makedate, String poid, Integer makeid, Integer paymode, String memo, Double payablesum, Double paysum, String makeorganid) {
        this.id = id;
        this.makedate = makedate;
        this.poid = poid;
        this.makeid = makeid;
        this.paymode = paymode;
        this.memo = memo;
        this.payablesum = payablesum;
        this.paysum = paysum;
        this.makeorganid = makeorganid;
    }

    /** default constructor */
    public ViewPayoutWaste() {
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

    public String getPoid() {
        return this.poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public Integer getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Integer makeid) {
        this.makeid = makeid;
    }

    public Integer getPaymode() {
        return this.paymode;
    }

    public void setPaymode(Integer paymode) {
        this.paymode = paymode;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getPayablesum() {
        return this.payablesum;
    }

    public void setPayablesum(Double payablesum) {
        this.payablesum = payablesum;
    }

    public Double getPaysum() {
        return this.paysum;
    }

    public void setPaysum(Double paysum) {
        this.paysum = paysum;
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
