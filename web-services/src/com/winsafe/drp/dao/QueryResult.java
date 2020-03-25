package com.winsafe.drp.dao; 

public class QueryResult {
	//查询码
	private String queryCode;
	//产品编号
	private String productId;
	//产品名称
	private String productName;
	//登记证持有人
	private String regCertUser;
	//农药标准名称
	private String standardName;
	//批次号
	private String batch; 
	//检验日期
	private String inspectionDate;
	//检验机构
	private String inspectionInstitution;
	//是否存在 
	private boolean isExist;
	//规格
	private String specMode;
	private String productionDate;
	
	private String firstQuery;
	private int queryCount;
	private String cartonCode;
	private String flow;
	private String ppid;
	//产品类型
	private String productType;
	private boolean isQualified; 
	
	/** yufeng.wang add*/
	// 是否是农药（农药登记证号中填入“999999”）
	private String regcertCodeFixed;
	
	private String covertCode;
	private String outPincode;
	private String materialCode;
	private String plantName;
	private String expiryDate;
	private String primaryCode;
//	//验证结果
//	private String validResult;
//	
//	public String getValidResult() {
//		return validResult;
//	}
//	public void setValidResult(String validResult) {
//		this.validResult = validResult;
//	}
	public String getRegcertCodeFixed() {
		return regcertCodeFixed;
	}
	public void setRegcertCodeFixed(String regcertCodeFixed) {
		this.regcertCodeFixed = regcertCodeFixed;
	}
	
	public QueryResult(String queryCode, boolean isExist) {
		super();
		this.queryCode = queryCode;
		this.isExist = isExist;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRegCertUser() {
		return regCertUser;
	}
	public void setRegCertUser(String regCertUser) {
		this.regCertUser = regCertUser;
	}
	public String getStandardName() {
		return standardName;
	}
	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getInspectionDate() {
		return inspectionDate;
	}
	public void setInspectionDate(String inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	public String getInspectionInstitution() {
		return inspectionInstitution;
	}
	public void setInspectionInstitution(String inspectionInstitution) {
		this.inspectionInstitution = inspectionInstitution;
	}
	public String getQueryCode() {
		return queryCode;
	}
	public void setQueryCode(String queryCode) {
		this.queryCode = queryCode;
	}
	public boolean isExist() {
		return isExist;
	}
	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSpecMode() {
		return specMode;
	}
	public void setSpecMode(String specMode) {
		this.specMode = specMode;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public String getFirstQuery() {
		return firstQuery;
	}
	public void setFirstQuery(String firstQuery) {
		this.firstQuery = firstQuery;
	}
	public int getQueryCount() {
		return queryCount;
	}
	public void setQueryCount(int queryCount) {
		this.queryCount = queryCount;
	}
	public String getCartonCode() {
		return cartonCode;
	}
	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public boolean isQualified() {
		return isQualified;
	}
	public void setQualified(boolean isQualified) {
		this.isQualified = isQualified;
	}
	public String getCovertCode() {
		return covertCode;
	}
	public void setCovertCode(String covertCode) {
		this.covertCode = covertCode;
	}
	public String getOutPincode() {
		return outPincode;
	}
	public void setOutPincode(String outPincode) {
		this.outPincode = outPincode;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getPrimaryCode() {
		return primaryCode;
	}
	public void setPrimaryCode(String primaryCode) {
		this.primaryCode = primaryCode;
	}
	
}
