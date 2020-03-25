package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class MsgReceive implements Serializable{
	private String id;
	private String mobileno;
	private String content;
	private Date createdate;
	private int trycount;
	private String smsid;
	private Date receivedate;
	private String msgtype;
	private String longcode;
	private String issend;

	
	public MsgReceive() {

	}
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMobileno() {
		return mobileno;
	}


	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getCreatedate() {
		return createdate;
	}


	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}


	public int getTrycount() {
		return trycount;
	}


	public void setTrycount(int trycount) {
		this.trycount = trycount;
	}


	public String getSmsid() {
		return smsid;
	}


	public void setSmsid(String smsid) {
		this.smsid = smsid;
	}


	public Date getReceivedate() {
		return receivedate;
	}


	public void setReceivedate(Date receivedate) {
		this.receivedate = receivedate;
	}


	public String getMsgtype() {
		return msgtype;
	}


	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}


	public String getLongcode() {
		return longcode;
	}


	public void setLongcode(String longcode) {
		this.longcode = longcode;
	}


	public String getIssend() {
		return issend;
	}


	public void setIssend(String issend) {
		this.issend = issend;
	}



}
