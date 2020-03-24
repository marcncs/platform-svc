package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

/**
 * @author alex
 *
 */
public class RoleForm extends ActionForm implements Serializable {
	private Integer id;
	private String rolename;
	private String describes;

	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

}
