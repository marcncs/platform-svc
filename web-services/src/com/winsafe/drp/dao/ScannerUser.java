package com.winsafe.drp.dao;

/**
 * Project:bright->Class:ScannerUser.java
 * <p style="font-size:16px;">Description：</p>
 * Create Time Oct 8, 2011 4:37:10 PM 
 * @author <a href='fazuo.du@winsafe.com'>dufazuo</a>
 * @version 0.8
 */
public class ScannerUser
{
	private Integer id;
	private String scanner;
	private String userid;
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ScannerUser() {
	}

	public ScannerUser(Integer id, String scanner, String userid) {
		super();
		this.id = id;
		this.scanner = scanner;
		this.userid = userid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getScanner() {
		return scanner;
	}

	public void setScanner(String scanner) {
		this.scanner = scanner;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) throws NumberFormatException, Exception {
		this.userid = userid;
	}
}
