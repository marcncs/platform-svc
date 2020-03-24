package com.winsafe.drp.dao;

import java.io.Serializable;

public class CustomerSort implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5181608701642368052L;
	private Integer id;
	private String sortname;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	
}
