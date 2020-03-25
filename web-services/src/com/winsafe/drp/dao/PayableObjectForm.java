package com.winsafe.drp.dao;


public class PayableObjectForm {
	/** identifier field */
    private Integer id;
    
    private String oid;

    /** nullable persistent field */
    private Integer objectsort;
    
    private String objectsortname;

    /** nullable persistent field */
    private String payee;
    
    /** nullable persistent field */
    private Double previoussum;
    
    private Double currentsum;
    
    private Double currentalreadysum;
    
    private Double waitpayablesum;
    
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getObjectsort() {
		return objectsort;
	}

	public void setObjectsort(Integer objectsort) {
		this.objectsort = objectsort;
	}

	public String getObjectsortname() {
		return objectsortname;
	}

	public void setObjectsortname(String objectsortname) {
		this.objectsortname = objectsortname;
	}

	public Double getCurrentalreadysum() {
		return currentalreadysum;
	}

	public void setCurrentalreadysum(Double currentalreadysum) {
		this.currentalreadysum = currentalreadysum;
	}

	public Double getCurrentsum() {
		return currentsum;
	}

	public void setCurrentsum(Double currentsum) {
		this.currentsum = currentsum;
	}

	public Double getPrevioussum() {
		return previoussum;
	}

	public void setPrevioussum(Double previoussum) {
		this.previoussum = previoussum;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public Double getWaitpayablesum() {
		return waitpayablesum;
	}

	public void setWaitpayablesum(Double waitpayablesum) {
		this.waitpayablesum = waitpayablesum;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
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

}
