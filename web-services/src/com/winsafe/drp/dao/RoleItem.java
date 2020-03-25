/**
 * 
 */
package com.winsafe.drp.dao;

import java.io.Serializable;

/**
 * @author alex
 *
 */
public class RoleItem implements Serializable{
	private Integer id;
	private Integer userroleid;
    /** persistent field */
    private String rolename;
    private String username;
    private Integer ispopedom;
    private String userid;
    
    private String describes;

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Integer getIspopedom() {
		return ispopedom;
	}

	public void setIspopedom(Integer ispopedom) {
		this.ispopedom = ispopedom;
	}

	public Integer getUserroleid() {
		return userroleid;
	}

	public void setUserroleid(Integer userroleid) {
		this.userroleid = userroleid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	




}
