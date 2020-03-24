package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CallCenterEvent implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String callnum;

    /** nullable persistent field */
    private String callednum;

    /** nullable persistent field */
    private Integer eventtype;

    /** nullable persistent field */
    private Date eventdate;

    /** nullable persistent field */
    private String alarmtype;

    /** nullable persistent field */
    private String computerid;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private Integer optype;

    /** nullable persistent field */
    private String soundname;

    /** nullable persistent field */
    private String username;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private String customername;
    
    private String cid;
    
    private String seatnum;

    /** full constructor */
    public CallCenterEvent(Integer id, String callnum, String callednum, Integer eventtype, Date eventdate, String alarmtype, String computerid, Integer userid, Integer optype, String soundname, String username, String makeorganid, String customername) {
        this.id = id;
        this.callnum = callnum;
        this.callednum = callednum;
        this.eventtype = eventtype;
        this.eventdate = eventdate;
        this.alarmtype = alarmtype;
        this.computerid = computerid;
        this.userid = userid;
        this.optype = optype;
        this.soundname = soundname;
        this.username = username;
        this.makeorganid = makeorganid;
        this.customername = customername;
    }

    /** default constructor */
    public CallCenterEvent() {
    }

    /** minimal constructor */
    public CallCenterEvent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCallnum() {
        return this.callnum;
    }

    public void setCallnum(String callnum) {
        this.callnum = callnum;
    }

    public String getCallednum() {
        return this.callednum;
    }

    public void setCallednum(String callednum) {
        this.callednum = callednum;
    }

    public Integer getEventtype() {
        return this.eventtype;
    }

    public void setEventtype(Integer eventtype) {
        this.eventtype = eventtype;
    }

    public Date getEventdate() {
        return this.eventdate;
    }

    public void setEventdate(Date eventdate) {
        this.eventdate = eventdate;
    }

    public String getAlarmtype() {
        return this.alarmtype;
    }

    public void setAlarmtype(String alarmtype) {
        this.alarmtype = alarmtype;
    }

    public String getComputerid() {
        return this.computerid;
    }

    public void setComputerid(String computerid) {
        this.computerid = computerid;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getOptype() {
        return this.optype;
    }

    public void setOptype(Integer optype) {
        this.optype = optype;
    }

    public String getSoundname() {
        return this.soundname;
    }

    public void setSoundname(String soundname) {
        this.soundname = soundname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public String getCustomername() {
        return this.customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }
    
    

    /**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	

	/**
	 * @return the seatnum
	 */
	public String getSeatnum() {
		return seatnum;
	}

	/**
	 * @param seatnum the seatnum to set
	 */
	public void setSeatnum(String seatnum) {
		this.seatnum = seatnum;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CallCenterEvent) ) return false;
        CallCenterEvent castOther = (CallCenterEvent) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
