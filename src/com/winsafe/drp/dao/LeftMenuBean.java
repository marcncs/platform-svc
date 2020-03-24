package com.winsafe.drp.dao;

import java.io.Serializable;

/**
 * @author jelli
 * 2009-4-4
 */
public class LeftMenuBean implements Serializable {

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String lmenuname;

    /** nullable persistent field */
    private String lmenuurl;
    
    private Integer lmenuparentid;
    
    private Integer lmenulevel;
    
    private Integer lmenuorder;
    

    private Integer hassonmenu=0;

    private Integer roleid;
    
 

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the lmenuname
	 */
	public String getLmenuname() {
		return lmenuname;
	}

	/**
	 * @param lmenuname the lmenuname to set
	 */
	public void setLmenuname(String lmenuname) {
		this.lmenuname = lmenuname;
	}

	/**
	 * @return the lmenuurl
	 */
	public String getLmenuurl() {
		return lmenuurl;
	}

	/**
	 * @param lmenuurl the lmenuurl to set
	 */
	public void setLmenuurl(String lmenuurl) {
		this.lmenuurl = lmenuurl;
	}

	/**
	 * @return the lmenuparentid
	 */
	public Integer getLmenuparentid() {
		return lmenuparentid;
	}

	/**
	 * @param lmenuparentid the lmenuparentid to set
	 */
	public void setLmenuparentid(Integer lmenuparentid) {
		this.lmenuparentid = lmenuparentid;
	}

	/**
	 * @return the lmenulevel
	 */
	public Integer getLmenulevel() {
		return lmenulevel;
	}

	/**
	 * @param lmenulevel the lmenulevel to set
	 */
	public void setLmenulevel(Integer lmenulevel) {
		this.lmenulevel = lmenulevel;
	}

	/**
	 * @return the lmenuorder
	 */
	public Integer getLmenuorder() {
		return lmenuorder;
	}

	/**
	 * @param lmenuorder the lmenuorder to set
	 */
	public void setLmenuorder(Integer lmenuorder) {
		this.lmenuorder = lmenuorder;
	}

	/**
	 * @return the hassonmenu
	 */
	public Integer getHassonmenu() {
		return hassonmenu;
	}

	/**
	 * @param hassonmenu the hassonmenu to set
	 */
	public void setHassonmenu(Integer hassonmenu) {
		this.hassonmenu = hassonmenu;
	}

	/**
	 * @return the roleid
	 */
	public Integer getRoleid() {
		return roleid;
	}

	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	
	

    
}
