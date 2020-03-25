package com.winsafe.drp.dao;

import java.util.Date;


/** @author Hibernate CodeGenerator */
public class MoveCanuseOrgan {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String oid;
    
    private String oname;
    
    private Date begindate;
    private Date enddate;

    /** persistent field */
    private Integer uidi;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Integer getUidi() {
		return uidi;
	}

	public void setUidi(Integer uidi) {
		this.uidi = uidi;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public Date getBegindate() {
		return begindate;
	}

	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
    
    

}
