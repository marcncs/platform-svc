package com.winsafe.drp.dao;

import java.util.Date;

public class PayableForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String poid;
    
    private String payableobjectname;
    
    private Double settlementsum;

    /** nullable persistent field */
    private Double payablesum;
    
    private Date awakedate;
    
    private int overage;
    
    private String billno;
    
    private String bn;
    
    private String payabledescribe;
    
    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;
    
    /** nullable persistent field */
    private Integer paymode;
    
    private String paymodename;
    
    /** nullable persistent field */
    private Double alreadysum;
    
    /** nullable persistent field */
    private Integer isclose;

    private String isclosename;
    
    private Date closedate;

	public Date getClosedate() {
		return closedate;
	}

	public void setClosedate(Date closedate) {
		this.closedate = closedate;
	}

	public String getIsclosename() {
		return isclosename;
	}

	public void setIsclosename(String isclosename) {
		this.isclosename = isclosename;
	}

	public Double getAlreadysum() {
		return alreadysum;
	}

	public void setAlreadysum(Double alreadysum) {
		this.alreadysum = alreadysum;
	}

	public Integer getIsclose() {
		return isclose;
	}

	public void setIsclose(Integer isclose) {
		this.isclose = isclose;
	}

	public Integer getPaymode() {
		return paymode;
	}

	public void setPaymode(Integer paymode) {
		this.paymode = paymode;
	}

	public String getPaymodename() {
		return paymodename;
	}

	public void setPaymodename(String paymodename) {
		this.paymodename = paymodename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public Double getPayablesum() {
		return payablesum;
	}

	public void setPayablesum(Double payablesum) {
		this.payablesum = payablesum;
	}

	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}

	
	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getPayabledescribe() {
		return payabledescribe;
	}

	public void setPayabledescribe(String payabledescribe) {
		this.payabledescribe = payabledescribe;
	}

	public String getPayableobjectname() {
		return payableobjectname;
	}

	public void setPayableobjectname(String payableobjectname) {
		this.payableobjectname = payableobjectname;
	}

	public String getBn() {
		return bn;
	}

	public void setBn(String bn) {
		this.bn = bn;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public String getMakeorganidname() {
		return makeorganidname;
	}

	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

	public Double getSettlementsum() {
		return settlementsum;
	}

	public void setSettlementsum(Double settlementsum) {
		this.settlementsum = settlementsum;
	}

	public Date getAwakedate() {
		return awakedate;
	}

	public void setAwakedate(Date awakedate) {
		this.awakedate = awakedate;
	}

	public void setOverage(int overage) {
		this.overage = overage;
	}

	public int getOverage() {
		return overage;
	}
}
