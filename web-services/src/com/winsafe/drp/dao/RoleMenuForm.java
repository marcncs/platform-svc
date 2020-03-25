/**
 * 
 */
package com.winsafe.drp.dao;

import java.io.Serializable;

/**
 * @author alex	
 *
 */
public class RoleMenuForm implements Serializable{
	private long id;
	private String menuname;
	private Integer rmId;
	private String modulename;
	private int moduleid;
	private String menuurl;
	private Integer ispopedom;


	public Integer getIspopedom() {
		return ispopedom;
	}
	public void setIspopedom(Integer ispopedom) {
		this.ispopedom = ispopedom;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	
	public Integer getRmId() {
		return rmId;
	}
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getMenuurl() {
		return menuurl;
	}
	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
	public int getModuleid() {
		return moduleid;
	}
	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
