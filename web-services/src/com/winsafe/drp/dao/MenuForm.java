/**
 * 
 */
package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author alex
 *
 */
public class MenuForm extends ActionForm {
	private Long id;
	/** persistent field */
    private String menuname;


    /** nullable persistent field */
    private String url;


	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
