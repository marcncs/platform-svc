package com.winsafe.sap.pojo;

import java.util.Date;

public class CartonSeqLog {
	private Integer id;
	private String organId; 
	private String productId;
	private Date makeDate;
	private String range;
	private Integer makeId;
	private String batch;
	private Date productionDate;
	private Date packingDate; 
	private Date inspectionDate;
	private String inspectionInstitution;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public Integer getMakeId() {
		return makeId;
	}
	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public Date getPackingDate() {
		return packingDate;
	}
	public void setPackingDate(Date packingDate) {
		this.packingDate = packingDate;
	}
	public Date getInspectionDate() {
		return inspectionDate;
	}
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	public String getInspectionInstitution() {
		return inspectionInstitution;
	}
	public void setInspectionInstitution(String inspectionInstitution) {
		this.inspectionInstitution = inspectionInstitution;
	}
	
}
