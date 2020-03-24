package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : jerry
 * @version : 2009-7-28 下午06:23:51
 * www.winsafe.cn
 */
public class WorkReportView implements Serializable {
	
	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String reportcontent;

    /** nullable persistent field */
    private Integer reportsort;

    /** nullable persistent field */
    private Integer isrefer;

    /** nullable persistent field */
    private Integer approvestatus;

    /** nullable persistent field */
    private String makeorganid;
  
    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;
    
    private Integer approve;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReportcontent() {
		return reportcontent;
	}

	public void setReportcontent(String reportcontent) {
		this.reportcontent = reportcontent;
	}

	public Integer getReportsort() {
		return reportsort;
	}

	public void setReportsort(Integer reportsort) {
		this.reportsort = reportsort;
	}

	public Integer getIsrefer() {
		return isrefer;
	}

	public void setIsrefer(Integer isrefer) {
		this.isrefer = isrefer;
	}

	public Integer getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(Integer approvestatus) {
		this.approvestatus = approvestatus;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Integer getApprove() {
		return approve;
	}

	public void setApprove(Integer approve) {
		this.approve = approve;
	}

   
}
