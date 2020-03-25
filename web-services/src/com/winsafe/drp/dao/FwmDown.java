package com.winsafe.drp.dao;

public class FwmDown {
	private Integer id;
	private String filePath;
	
	public FwmDown() {
	}
	
	public FwmDown(Integer id, String filePath) {
		super();
		this.id = id;
		this.filePath = filePath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
