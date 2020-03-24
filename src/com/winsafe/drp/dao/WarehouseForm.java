package com.winsafe.drp.dao;

public class WarehouseForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String warehousename;

    /** nullable persistent field */
    private Integer dept;
    
    private String deptname;

    /** nullable persistent field */
    private Integer userid;
    
    private String useridname;

    /** nullable persistent field */
    private String warehousetel;

    /** nullable persistent field */
    private Integer warehouseproperty;
    
    private String warehousepropertyname;

    /** nullable persistent field */
    private String warehouseaddr;
    
    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer useflag;
    
    private String useflagname;

    /** nullable persistent field */
    private String remark;

	public Integer getDept() {
		return dept;
	}

	public void setDept(Integer dept) {
		this.dept = dept;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUseflag() {
		return useflag;
	}

	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}

	public String getUseflagname() {
		return useflagname;
	}

	public void setUseflagname(String useflagname) {
		this.useflagname = useflagname;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public String getUseridname() {
		return useridname;
	}

	public void setUseridname(String useridname) {
		this.useridname = useridname;
	}

	public String getWarehouseaddr() {
		return warehouseaddr;
	}

	public void setWarehouseaddr(String warehouseaddr) {
		this.warehouseaddr = warehouseaddr;
	}

	public String getWarehousename() {
		return warehousename;
	}

	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}

	public Integer getWarehouseproperty() {
		return warehouseproperty;
	}

	public void setWarehouseproperty(Integer warehouseproperty) {
		this.warehouseproperty = warehouseproperty;
	}

	public String getWarehousepropertyname() {
		return warehousepropertyname;
	}

	public void setWarehousepropertyname(String warehousepropertyname) {
		this.warehousepropertyname = warehousepropertyname;
	}

	public String getWarehousetel() {
		return warehousetel;
	}

	public void setWarehousetel(String warehousetel) {
		this.warehousetel = warehousetel;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakedeptidname() {
		return makedeptidname;
	}

	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public String getMakeorganidname() {
		return makeorganidname;
	}

	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}
}
