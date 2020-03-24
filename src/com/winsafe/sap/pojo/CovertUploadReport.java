package com.winsafe.sap.pojo;

import java.util.Date;

public class CovertUploadReport {
	
	private Long id;
	// 错误记录数
	private Integer errorCount;
	// 总记录数
	private Integer totalCount;
	// 生产线
	private String lineNo;
	// 生产类型(0 正常，1 补打， -1 错误)
	private Integer recodeType;
	// 生产打印时间
	private String printDate;
	// 上传日志ID
	private Integer uploadPrId;
	
	private Date uploadDate;
	
	private Integer uploadUser;
	
	private String materialCode;
	
	private String productName;
	
	private String productId;
	
	private String batch;
	//正确率
	private String accuracy;
	//上传次数
	private String uploadCount;
	//规格
	private String packSizeName;
	
	public CovertUploadReport(){}
	
	public CovertUploadReport(String lineNo, Integer recodeType,
			String printDate, Integer uploadPrId, Date uploadDate, Integer uploadUser, String materialCode,String productName, String batch, String productId) {
		super();
		this.lineNo = lineNo;
		this.recodeType = recodeType;
		this.printDate = printDate;
		this.uploadPrId = uploadPrId;
		this.uploadDate = uploadDate;
		this.uploadUser = uploadUser;
		this.materialCode = materialCode;
		this.productName = productName;
		this.batch = batch;
		this.productId = productId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public Integer getRecodeType() {
		return recodeType;
	}
	public void setRecodeType(Integer recodeType) {
		this.recodeType = recodeType;
	}
	public String getPrintDate() {
		return printDate;
	}
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}
	public Integer getUploadPrId() {
		return uploadPrId;
	}
	public void setUploadPrId(Integer uploadPrId) {
		this.uploadPrId = uploadPrId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lineNo == null) ? 0 : lineNo.hashCode());
//		result = prime * result
//				+ ((materialCode == null) ? 0 : materialCode.hashCode());
//		result = prime * result
//				+ ((batch == null) ? 0 : batch.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CovertUploadReport other = (CovertUploadReport) obj;
		if (lineNo == null) {
			if (other.lineNo != null)
				return false;
		} else if (!lineNo.equals(other.lineNo))
			return false;
//		if (materialCode == null) {
//			if (other.materialCode != null)
//				return false;
//		} else if (!materialCode.equals(other.materialCode))
//			return false;
//		if (batch == null) {
//			if (other.batch != null)
//				return false;
//		} else if (!batch.equals(other.batch))
//			return false;
		return true;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Integer getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(Integer uploadUser) {
		this.uploadUser = uploadUser;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getUploadCount() {
		return uploadCount;
	}

	public void setUploadCount(String uploadCount) {
		this.uploadCount = uploadCount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPackSizeName() {
		return packSizeName;
	}

	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
	}
	
}
