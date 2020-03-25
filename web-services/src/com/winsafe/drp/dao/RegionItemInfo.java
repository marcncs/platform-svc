package com.winsafe.drp.dao;

import java.util.Date;

/**
 * @author Tao.sun
 *	区域节点信息
 */
public class RegionItemInfo {
	private Integer id;
	private Integer areaId;
	private Integer pId;
	private String pname;
	private String name;
	private Integer rank;
	private String nameEn;
	private Date mdate = new Date();
	
	public RegionItemInfo() {
		
	}
	
	public RegionItemInfo(Integer areaId, String areaName) {
		this.areaId = areaId;
		this.name = areaName;
	}
	
	public RegionItemInfo(Integer areaId, String areaName, String pname) {
		this.areaId = areaId;
		this.name = areaName;
		this.pname = pname;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public Date getMdate() {
		return mdate;
	}
	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}
	@Override
	public String toString() {
		return "RegionItemInfo [areaId=" + areaId + ", id=" + id + ", name="
				+ name + ", pId=" + pId + ", rank=" + rank + "]";
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
}
