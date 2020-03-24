package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-3-24
 */
public class CallCenterEventForm extends ActionForm {

	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String callnum;

    /** nullable persistent field */
    private String callednum;

    /** nullable persistent field */
    private Integer eventtype;

    /** nullable persistent field */
    private String eventdate;

    /** nullable persistent field */
    private String alarmtype;

    /** nullable persistent field */
    private String computerid;

    /** nullable persistent field */
    private Integer userid;
    
    private String useridname;

    /** nullable persistent field */
    private Integer optype;
    
    private String soundname;
    
    private String soundfile;
    
    /** nullable persistent field */
    private String username;

    /** nullable persistent field */
    private Integer makeorganid;

    /** nullable persistent field */
    private String customername;

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
	 * @return the useridname
	 */
	public String getUseridname() {
		return useridname;
	}

	/**
	 * @param useridname the useridname to set
	 */
	public void setUseridname(String useridname) {
		this.useridname = useridname;
	}

	/**
	 * @return the callnum
	 */
	public String getCallnum() {
		return callnum;
	}

	/**
	 * @param callnum the callnum to set
	 */
	public void setCallnum(String callnum) {
		this.callnum = callnum;
	}

	/**
	 * @return the callednum
	 */
	public String getCallednum() {
		return callednum;
	}

	/**
	 * @param callednum the callednum to set
	 */
	public void setCallednum(String callednum) {
		this.callednum = callednum;
	}

	/**
	 * @return the eventtype
	 */
	public Integer getEventtype() {
		return eventtype;
	}

	/**
	 * @param eventtype the eventtype to set
	 */
	public void setEventtype(Integer eventtype) {
		this.eventtype = eventtype;
	}

	/**
	 * @return the eventdate
	 */
	public String getEventdate() {
		return eventdate;
	}

	/**
	 * @param eventdate the eventdate to set
	 */
	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}

	/**
	 * @return the alarmtype
	 */
	public String getAlarmtype() {
		return alarmtype;
	}

	/**
	 * @param alarmtype the alarmtype to set
	 */
	public void setAlarmtype(String alarmtype) {
		this.alarmtype = alarmtype;
	}

	/**
	 * @return the computerid
	 */
	public String getComputerid() {
		return computerid;
	}

	/**
	 * @param computerid the computerid to set
	 */
	public void setComputerid(String computerid) {
		this.computerid = computerid;
	}

	/**
	 * @return the userid
	 */
	public Integer getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	/**
	 * @return the optype
	 */
	public Integer getOptype() {
		return optype;
	}

	/**
	 * @param optype the optype to set
	 */
	public void setOptype(Integer optype) {
		this.optype = optype;
	}

	/**
	 * @return the soundname
	 */
	public String getSoundname() {
		return soundname;
	}

	/**
	 * @param soundname the soundname to set
	 */
	public void setSoundname(String soundname) {
		this.soundname = soundname;
	}

	/**
	 * @return the soundfile
	 */
	public String getSoundfile() {
		return soundfile;
	}

	/**
	 * @param soundfile the soundfile to set
	 */
	public void setSoundfile(String soundfile) {
		this.soundfile = soundfile;
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
	 * @return the makeorganid
	 */
	public Integer getMakeorganid() {
		return makeorganid;
	}

	/**
	 * @param makeorganid the makeorganid to set
	 */
	public void setMakeorganid(Integer makeorganid) {
		this.makeorganid = makeorganid;
	}

	/**
	 * @return the customername
	 */
	public String getCustomername() {
		return customername;
	}

	/**
	 * @param customername the customername to set
	 */
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	
	
    
    
    
}
