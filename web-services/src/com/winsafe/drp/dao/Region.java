package com.winsafe.drp.dao;

public class Region {
	private Long id;
	private String regioncode;
	private String sortname;
	private String parentid;
	private String parentname;
	private String userid;
	private String username ;
	
	private String remark;
	
	//类型 1:大区 2：办事处
	private String typeid;
	private String typename;
	//父类型
	private String parentuserid;  //上级经理编号
	private String parentusername;//上级经理姓名
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getParentuserid() {
		return parentuserid;
	}

	public void setParentuserid(String parentuserid) {
		this.parentuserid = parentuserid;
	}

	public String getParentusername() {
		return parentusername;
	}

	public void setParentusername(String parentusername) {
		this.parentusername = parentusername;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
