package com.winsafe.drp.dao;


public class SuitForm {
	 /** identifier field */
    private String id;

    /** nullable persistent field */
    private String cid;
    
    private Integer objsort;
    
    public Integer getObjsort() {
		return objsort;
	}

	public void setObjsort(Integer objsort) {
		this.objsort = objsort;
	}

	private String cidname;

    /** nullable persistent field */
    private Integer suitcontent;
    

    /** nullable persistent field */
    private Integer suitway;
    

    /** nullable persistent field */
    private String suittools;

    /** nullable persistent field */
    private String suitstatus;

    /** nullable persistent field */
    private String suitdate;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Integer isdeal;
    


    /** nullable persistent field */
    private Integer dealway;
    
    private String dealwayname;

    /** nullable persistent field */
    private Long dealuser;
    
    private String dealusername;

    private String dealdate;
    
    /** nullable persistent field */
    private String dealcontent;

    /** nullable persistent field */
    private String dealfinal;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;
    
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private String makedate;
    
    private Integer approvestatus;
    
    private String approvestatusname;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCidname() {
		return cidname;
	}

	public void setCidname(String cidname) {
		this.cidname = cidname;
	}

	public String getDealcontent() {
		return dealcontent;
	}

	public void setDealcontent(String dealcontent) {
		this.dealcontent = dealcontent;
	}

	public String getDealfinal() {
		return dealfinal;
	}

	public void setDealfinal(String dealfinal) {
		this.dealfinal = dealfinal;
	}

	public Long getDealuser() {
		return dealuser;
	}

	public void setDealuser(Long dealuser) {
		this.dealuser = dealuser;
	}

	public String getDealusername() {
		return dealusername;
	}

	public void setDealusername(String dealusername) {
		this.dealusername = dealusername;
	}

	public Integer getDealway() {
		return dealway;
	}

	public void setDealway(Integer dealway) {
		this.dealway = dealway;
	}

	public String getDealwayname() {
		return dealwayname;
	}

	public void setDealwayname(String dealwayname) {
		this.dealwayname = dealwayname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsdeal() {
		return isdeal;
	}

	public void setIsdeal(Integer isdeal) {
		this.isdeal = isdeal;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public Long getMakeid() {
		return makeid;
	}

	public void setMakeid(Long makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getSuitcontent() {
		return suitcontent;
	}

	public void setSuitcontent(Integer suitcontent) {
		this.suitcontent = suitcontent;
	}


	public String getSuitdate() {
		return suitdate;
	}

	public void setSuitdate(String suitdate) {
		this.suitdate = suitdate;
	}

	public String getSuitstatus() {
		return suitstatus;
	}

	public void setSuitstatus(String suitstatus) {
		this.suitstatus = suitstatus;
	}

	public String getSuittools() {
		return suittools;
	}

	public void setSuittools(String suittools) {
		this.suittools = suittools;
	}

	public Integer getSuitway() {
		return suitway;
	}

	public void setSuitway(Integer suitway) {
		this.suitway = suitway;
	}

	public String getDealdate() {
		return dealdate;
	}

	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}

	/**
	 * @return the makeorganid
	 */
	public String getMakeorganid() {
		return makeorganid;
	}

	/**
	 * @param makeorganid the makeorganid to set
	 */
	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	/**
	 * @return the makeorganidname
	 */
	public String getMakeorganidname() {
		return makeorganidname;
	}

	/**
	 * @param makeorganidname the makeorganidname to set
	 */
	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

	/**
	 * @return the approvestatus
	 */
	public Integer getApprovestatus() {
		return approvestatus;
	}

	/**
	 * @param approvestatus the approvestatus to set
	 */
	public void setApprovestatus(Integer approvestatus) {
		this.approvestatus = approvestatus;
	}

	/**
	 * @return the approvestatusname
	 */
	public String getApprovestatusname() {
		return approvestatusname;
	}

	/**
	 * @param approvestatusname the approvestatusname to set
	 */
	public void setApprovestatusname(String approvestatusname) {
		this.approvestatusname = approvestatusname;
	}
	
	
	
}
