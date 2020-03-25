package com.winsafe.drp.dao;


public class ServiceAgreementForm {
	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String cid;
    
    private Integer objsort;
    
    public Integer getObjsort() {
		return objsort;
	}

	public void setObjsort(Integer objsort) {
		this.objsort = objsort;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	private String cname;

    /** nullable persistent field */
    private String linkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer scontent;
    
    private String scontentname;

    /** nullable persistent field */
    private Double sfee;

    /** nullable persistent field */
    private Integer sstatus;
    
    private String sstatusname;

    /** nullable persistent field */
    private Integer rank;
    
    private String rankname;

    /** nullable persistent field */
    private String sdate;

    /** nullable persistent field */
    private String question;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Integer isallot;
    
    private String isallotname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;
    
    private String makeorganid;
    private Integer makedeptid;

    public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	/** nullable persistent field */
    private String makedate;
    private Integer isaffirm;
    
	public Integer getIsaffirm() {
		return isaffirm;
	}

	public void setIsaffirm(Integer isaffirm) {
		this.isaffirm = isaffirm;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsallot() {
		return isallot;
	}

	public void setIsallot(Integer isallot) {
		this.isallot = isallot;
	}

	public String getIsallotname() {
		return isallotname;
	}

	public void setIsallotname(String isallotname) {
		this.isallotname = isallotname;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getRankname() {
		return rankname;
	}

	public void setRankname(String rankname) {
		this.rankname = rankname;
	}

	public Integer getScontent() {
		return scontent;
	}

	public void setScontent(Integer scontent) {
		this.scontent = scontent;
	}

	public String getScontentname() {
		return scontentname;
	}

	public void setScontentname(String scontentname) {
		this.scontentname = scontentname;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public Double getSfee() {
		return sfee;
	}

	public void setSfee(Double sfee) {
		this.sfee = sfee;
	}

	public Integer getSstatus() {
		return sstatus;
	}

	public void setSstatus(Integer sstatus) {
		this.sstatus = sstatus;
	}

	public String getSstatusname() {
		return sstatusname;
	}

	public void setSstatusname(String sstatusname) {
		this.sstatusname = sstatusname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
