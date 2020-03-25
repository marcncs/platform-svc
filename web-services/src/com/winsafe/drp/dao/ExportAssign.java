package com.winsafe.drp.dao;
// default package



/**
 * ExportAssign entity. @author MyEclipse Persistence Tools
 */

public class ExportAssign implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer userId;
     private String warehouseId;


    // Constructors

    /** default constructor */
    public ExportAssign() {
    }

	/** minimal constructor */
    public ExportAssign(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public ExportAssign(Integer id, Integer userId, String warehouseId) {
        this.id = id;
        this.userId = userId;
        this.warehouseId = warehouseId;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWarehouseId() {
        return this.warehouseId;
    }
    
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }
   








}