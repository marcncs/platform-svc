package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

public class ReceivableObjectForm extends ActionForm{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 6565905121680227323L;

	/** nullable persistent field */
    private Integer id;
    
    private String oid;

    /** nullable persistent field */
    private Integer objectsort;
    
    private String objectsortname;

    /** nullable persistent field */
    private String payer;

    /** nullable persistent field */
    private Double previoussum;
    
    private Double currentsum;
    
    private Double currentalreadysum;
    
    private Double waitreceivablesum;
    
    private String makeorganid;
    
    private String makeorganidname;
    
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;
    
    private String promisedate;

	public String getPromisedate() {
		return promisedate;
	}

	public void setPromisedate(String promisedate) {
		this.promisedate = promisedate;
	}

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

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public Double getWaitreceivablesum() {
		return waitreceivablesum;
	}

	public void setWaitreceivablesum(Double waitreceivablesum) {
		this.waitreceivablesum = waitreceivablesum;
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

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

}
