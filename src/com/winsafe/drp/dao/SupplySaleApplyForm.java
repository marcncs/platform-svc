package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

/** @author Hibernate CodeGenerator */
public class SupplySaleApplyForm implements Serializable {

	/** identifier field */
	private String id;

	/** nullable persistent field */
	private Date movedate;

	/** nullable persistent field */
	private String outorganid;

	/** nullable persistent field */
	private String inorganid;

	/** nullable persistent field */
	private Integer makeid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getMovedate() {
		return movedate;
	}

	public void setMovedate(Date movedate) {
		this.movedate = movedate;
	}

	public String getOutorganid() {
		return outorganid;
	}

	public void setOutorganid(String outorganid) {
		this.outorganid = outorganid;
	}

	public String getInorganid() {
		return inorganid;
	}

	public void setInorganid(String inorganid) {
		this.inorganid = inorganid;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

}
