package com.winsafe.sap.pojo;

import java.util.Date;

import com.winsafe.drp.util.Constants;

public class CartonSeq {
	private Long id;
	private String seq; 
	private String productId;
	//使用状态0未使用1已使用2已拆包
	private Integer status;
	private Date makeDate;
	private Integer version;
	private Integer applyId;
	private String cartonCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getApplyId() {
		return applyId;
	}
	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}
	public String getCartonCode() {
		return cartonCode;
	}
	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}
	public static String getSeqWithPrefix(String seqWithoutPrefix) {
		return Constants.ZERO_PREFIX[Constants.CARTON_SEQ_LEN-seqWithoutPrefix.length()]+seqWithoutPrefix;
	}
	
	public static String getPrimaryCodeSeqWithPrefix(String seqWithoutPrefix) { 
		return Constants.ZERO_PREFIX[Constants.PRIMARY_SEQ_LEN-seqWithoutPrefix.length()]+seqWithoutPrefix;
	}
}
