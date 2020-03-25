package com.winsafe.drp.dao;

public class ProviderProductCompareForm {

	private Long id;
	
	private String pid;
	
	private String providername;
	
	private Double unitprice;
	
	private Integer vocation;
	
	private String vocationname;
	
	private String abcsortname;
	
	private String provincename;
	
	private String cityname;
	
	private String areasname;

	public String getAbcsortname() {
		return abcsortname;
	}

	public void setAbcsortname(String abcsortname) {
		this.abcsortname = abcsortname;
	}

	public String getAreasname() {
		return areasname;
	}

	public void setAreasname(String areasname) {
		this.areasname = areasname;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvidername() {
		return providername;
	}

	public void setProvidername(String providername) {
		this.providername = providername;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Integer getVocation() {
		return vocation;
	}

	public void setVocation(Integer vocation) {
		this.vocation = vocation;
	}

	public String getVocationname() {
		return vocationname;
	}

	public void setVocationname(String vocationname) {
		this.vocationname = vocationname;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
