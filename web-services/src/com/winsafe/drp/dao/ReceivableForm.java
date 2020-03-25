package com.winsafe.drp.dao;

import java.util.Date;

public class ReceivableForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String roid;
    
    private String receivableobjectname;
    
    private Double settlementsum;

    /** nullable persistent field */
    private Double receivablesum;
    
    private String billno;
    
    private String bn;

    private String receivabledescribe;

    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;
    
    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;
    
    private Integer paymentmode;
    
    private String paymentmodename;
    
    private Date awakedate;
    
    private Integer overage;
    
    private Double alreadysum;
    
    private Integer isclose;
    
    private String isclosename;
    
    private Date closedate;
    

	public Date getClosedate() {
		return closedate;
	}

	public void setClosedate(Date closedate) {
		this.closedate = closedate;
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

	public String getIsclosename() {
		return isclosename;
	}

	public void setIsclosename(String isclosename) {
		this.isclosename = isclosename;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getPaymentmodename() {
		return paymentmodename;
	}

	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
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

	public Double getReceivablesum() {
		return receivablesum;
	}

	public void setReceivablesum(Double receivablesum) {
		this.receivablesum = receivablesum;
	}

	public String getRoid() {
		return roid;
	}

	public void setRoid(String roid) {
		this.roid = roid;
	}


	public String getReceivabledescribe() {
		return receivabledescribe;
	}

	public void setReceivabledescribe(String receivabledescribe) {
		this.receivabledescribe = receivabledescribe;
	}

	public String getReceivableobjectname() {
		return receivableobjectname;
	}

	public void setReceivableobjectname(String receivableobjectname) {
		this.receivableobjectname = receivableobjectname;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
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

	public String getMakedeptidname() {
		return makedeptidname;
	}

	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
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

	public Integer getOverage() {
		return overage;
	}

	public void setOverage(Integer overage) {
		this.overage = overage;
	}
}
