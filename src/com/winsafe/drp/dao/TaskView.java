package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : jerry
 * @version : 2009-7-28 下午06:04:08
 * www.winsafe.cn
 */
public class TaskView implements Serializable {
	
	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer objsort;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String tptitle;

    /** nullable persistent field */
    private Date conclusiondate;

    /** nullable persistent field */
    private Integer priority;

    /** nullable persistent field */
    private Integer status;
    
    private String makeorganid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Integer overstatus;
    
    private Integer isaffirm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getTptitle() {
		return tptitle;
	}

	public void setTptitle(String tptitle) {
		this.tptitle = tptitle;
	}

	public Date getConclusiondate() {
		return conclusiondate;
	}

	public void setConclusiondate(Date conclusiondate) {
		this.conclusiondate = conclusiondate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getOverstatus() {
		return overstatus;
	}

	public void setOverstatus(Integer overstatus) {
		this.overstatus = overstatus;
	}

	public Integer getIsaffirm() {
		return isaffirm;
	}

	public void setIsaffirm(Integer isaffirm) {
		this.isaffirm = isaffirm;
	}
    
    
    


  }
