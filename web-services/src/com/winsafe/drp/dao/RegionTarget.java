package com.winsafe.drp.dao;

import java.io.Serializable;

/** @author Hibernate CodeGenerator */
public class RegionTarget implements Serializable {

	private Integer id;
    /** identifier field */
    private Integer regionid;
    
    private String targetdate;
    private Double importtarget;
    private Double chmantarget;
    private Double chbabytarget;
    private String targettype;
    private String targettypename;
    private Double totaltarget;
    
    //存储有效日期
    private String usefuldate;
    //存储对象标号
    private String objcode;
    //存取对象名称
    private String objname; 

    public RegionTarget(){}
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRegionid() {
		return regionid;
	}
	public void setRegionid(Integer regionid) {
		this.regionid = regionid;
	}

	public String getTargetdate() {
		return targetdate;
	}
	public void setTargetdate(String targetdate) {
		this.targetdate = targetdate;
	}
	public Double getImporttarget() {
		return importtarget;
	}
	public void setImporttarget(Double importtarget) {
		this.importtarget = importtarget;
	}
	public Double getChmantarget() {
		return chmantarget;
	}
	public void setChmantarget(Double chmantarget) {
		this.chmantarget = chmantarget;
	}
	public Double getChbabytarget() {
		return chbabytarget;
	}
	public void setChbabytarget(Double chbabytarget) {
		this.chbabytarget = chbabytarget;
	}

	public String getUsefuldate() {
		return usefuldate;
	}

	public void setUsefuldate(String usefuldate) {
		this.usefuldate = usefuldate;
	}

	public String getTargettype() {
		return targettype;
	}

	public void setTargettype(String targettype) {
		this.targettype = targettype;
	}
	public String getTargettypename() {
		return targettypename;
	}
	public void setTargettypename(String targettypename) {
		this.targettypename = targettypename;
	}
	public Double getTotaltarget() {
		return totaltarget;
	}
	public void setTotaltarget(Double totaltarget) {
		this.totaltarget = totaltarget;
	}

	public String getObjcode() {
		return objcode;
	}

	public void setObjcode(String objcode) {
		this.objcode = objcode;
	}

	public String getObjname() {
		return objname;
	}
	
	public void setObjname(String objname) {
		this.objname = objname;
	}
	
}
