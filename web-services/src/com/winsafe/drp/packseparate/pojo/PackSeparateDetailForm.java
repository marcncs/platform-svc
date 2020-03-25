package com.winsafe.drp.packseparate.pojo;

public class PackSeparateDetailForm {
	
	private Long id;
	private String psid;
	private String productName;
	private String outProductId;
	private String outMcode;
	private Double outQuantity;
	
	private String inProductId;
	private String inMcode;
	private Double inQuantity;
	
	private Integer inUnitId;
	private Integer outUnitId;
	
	private Double wastage;
	
	private String outBatch;
	private String inBatch;
	
	private String outSpecMode;
	private String inSpecMode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPsid() {
		return psid;
	}
	public void setPsid(String psid) {
		this.psid = psid;
	}
	public String getOutProductId() {
		return outProductId;
	}
	public void setOutProductId(String outProductId) {
		this.outProductId = outProductId;
	}
	public String getOutMcode() {
		return outMcode;
	}
	public void setOutMcode(String outMcode) {
		this.outMcode = outMcode;
	}
	public Double getOutQuantity() {
		return outQuantity;
	}
	public void setOutQuantity(Double outQuantity) {
		this.outQuantity = outQuantity;
	}
	public String getInProductId() {
		return inProductId;
	}
	public void setInProductId(String inProductId) {
		this.inProductId = inProductId;
	}
	public String getInMcode() {
		return inMcode;
	}
	public void setInMcode(String inMcode) {
		this.inMcode = inMcode;
	}
	public Double getInQuantity() {
		return inQuantity;
	}
	public void setInQuantity(Double inQuantity) {
		this.inQuantity = inQuantity;
	}
	public Double getWastage() {
		return wastage;
	}
	public void setWastage(Double wastage) {
		this.wastage = wastage;
	}
	public String getOutBatch() {
		return outBatch;
	}
	public void setOutBatch(String outBatch) {
		this.outBatch = outBatch;
	}
	public String getInBatch() {
		return inBatch;
	}
	public void setInBatch(String inBatch) {
		this.inBatch = inBatch;
	}
	public Integer getInUnitId() {
		return inUnitId;
	}
	public void setInUnitId(Integer inUnitId) {
		this.inUnitId = inUnitId;
	}
	public Integer getOutUnitId() {
		return outUnitId;
	}
	public void setOutUnitId(Integer outUnitId) {
		this.outUnitId = outUnitId;
	}
	public String getOutSpecMode() {
		return outSpecMode;
	}
	public void setOutSpecMode(String outSpecMode) {
		this.outSpecMode = outSpecMode;
	}
	public String getInSpecMode() {
		return inSpecMode;
	}
	public void setInSpecMode(String inSpecMode) {
		this.inSpecMode = inSpecMode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
