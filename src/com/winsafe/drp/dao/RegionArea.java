package com.winsafe.drp.dao;


public class RegionArea {

	private Long id;
	
	private String regioncodeid;
	private String areaid;

    private Integer arearank;

    private String remark;
    
    private String areaname;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegioncodeid() {
		return regioncodeid;
	}

	public void setRegioncodeid(String regioncodeid) {
		this.regioncodeid = regioncodeid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getArearank() {
		return arearank;
	}

	public void setArearank(Integer arearank) {
		this.arearank = arearank;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	
}

