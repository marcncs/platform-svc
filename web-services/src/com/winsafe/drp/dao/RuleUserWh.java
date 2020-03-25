package com.winsafe.drp.dao;

/**
 * RuleUserWh entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RuleUserWh implements java.io.Serializable {

    // Fields

    private Integer userId;
    private String warehouseId;
    private Integer id;
    //初始化仓库为可见
    private Boolean activeFlag = true;
    
    //自定义属性
    //仓库名
    private String warehouseName;
    //机构编号
    private String organId;
    //机构名称
    private String organName;
    public String getWarehouseName() {
        return warehouseName;
    }


    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }


    public RuleUserWh() {
	super();
    }

    
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseId(String warehouseId){
        this.warehouseId = warehouseId;
        //初始化仓库名
        if(warehouseName == null){
            AppWarehouse appWarehouse = new AppWarehouse();
            AppOrgan appOrgan = new AppOrgan();
            try {
        	Warehouse warehouse = appWarehouse.getWarehouseByID(warehouseId);
        	if(warehouse!=null){
        		warehouseName = warehouse.getWarehousename();
        		organId = warehouse.getMakeorganid();
        	}
		
		Organ organ = appOrgan.getOrganByID(organId);
//		this.organId = organId;
		if(organ!=null){
			organName = organ.getOrganname();
		}
		
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
        }
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Boolean getActiveFlag() {
        return activeFlag;
    }
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }


    public String getOrganId() {
        return organId;
    }


    public void setOrganId(String organId) {
        this.organId = organId;
    }


    public String getOrganName() {
        return organName;
    }


    public void setOrganName(String organName) {
        this.organName = organName;
    }
   

}