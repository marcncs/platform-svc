package com.winsafe.sap.pojo;

import java.io.Serializable;
import java.util.Date;

public class PrimaryCode implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	//小包装码
	private String primaryCode;
	//箱码
	private String cartonCode;
	//托盘码
	private String palletCode;
	//创建日期
	private Date createDate;
	//是否被使用
	private Integer isUsed;
	//对应的打印任务id号
	private Integer printJobId;
	//暗码
	private String covertCode;
	//暗码上传日志编号
	private String uploadPrId;
	//小包装码长度，目前有10位与13位
	private Integer codeLength;
	//查询次数
	private Integer numberOfQuery; 
	//第一次查询时间
	private Date firstTime;
	//通用码任务日志
	private Long commonCodeLogId;
	//辅助字段,小码序号 
	private String seq;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPrimaryCode() {
		return primaryCode;
	}
	public void setPrimaryCode(String primaryCode) {
		this.primaryCode = primaryCode;
	}
	public String getCartonCode() {
		return cartonCode;
	}
	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}
	public String getPalletCode() {
		return palletCode;
	}
	public void setPalletCode(String palletCode) {
		this.palletCode = palletCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	
	public Integer getPrintJobId() {
		return printJobId;
	}
	public void setPrintJobId(Integer printJobId) {
		this.printJobId = printJobId;
	}
	
	public String getCovertCode() {
		return covertCode;
	}
	public void setCovertCode(String convertCode) {
		this.covertCode = convertCode;
	}
	public String getUploadPrId() {
		return uploadPrId;
	}
	public void setUploadPrId(String uploadPrId) {
		this.uploadPrId = uploadPrId;
	}
	
	public Integer getCodeLength() {
		return codeLength;
	}
	public void setCodeLength(Integer codeLength) {
		this.codeLength = codeLength;
	}
	
	public Long getCommonCodeLogId() {
		return commonCodeLogId;
	}
	public void setCommonCodeLogId(Long commonCodeLogId) {
		this.commonCodeLogId = commonCodeLogId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((primaryCode == null) ? 0 : primaryCode.hashCode());
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
		PrimaryCode other = (PrimaryCode) obj;
		if (primaryCode == null) {
			if (other.primaryCode != null)
				return false;
		} else if (!primaryCode.equals(other.primaryCode))
			return false;
		return true;
	}
	public void setNumberOfQuery(Integer numberOfQuery) {
		this.numberOfQuery = numberOfQuery;
	}
	public Integer getNumberOfQuery() {
		return numberOfQuery;
	}
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}
	public Date getFirstTime() {
		return firstTime;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	
}
