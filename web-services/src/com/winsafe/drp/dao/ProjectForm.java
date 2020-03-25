package com.winsafe.drp.dao;


public class ProjectForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String cid;
    
    private String cidname;

    /** nullable persistent field */
    private Integer pcontent;
    
    private String pcontentname;

    /** nullable persistent field */
    private Integer pstatus;
    
    private String pstatusname;

    /** nullable persistent field */
    private Double amount;

    /** nullable persistent field */
    private String pbegin;

    /** nullable persistent field */
    private String pend;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPbegin() {
		return pbegin;
	}

	public void setPbegin(String pbegin) {
		this.pbegin = pbegin;
	}

	public Integer getPcontent() {
		return pcontent;
	}

	public void setPcontent(Integer pcontent) {
		this.pcontent = pcontent;
	}

	public String getPcontentname() {
		return pcontentname;
	}

	public void setPcontentname(String pcontentname) {
		this.pcontentname = pcontentname;
	}

	public String getPend() {
		return pend;
	}

	public void setPend(String pend) {
		this.pend = pend;
	}

	public Integer getPstatus() {
		return pstatus;
	}

	public void setPstatus(Integer pstatus) {
		this.pstatus = pstatus;
	}

	public String getPstatusname() {
		return pstatusname;
	}

	public void setPstatusname(String pstatusname) {
		this.pstatusname = pstatusname;
	}
}
