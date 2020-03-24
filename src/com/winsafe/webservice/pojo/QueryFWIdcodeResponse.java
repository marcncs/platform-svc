package com.winsafe.webservice.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.NONE)
public class QueryFWIdcodeResponse {
	//处理结果代码
	@XmlElement
	private String returnCode;
	//处理结果信息
	@XmlElement
	private String returnMsg;
	@XmlElement
	private String pName;
	@XmlElement
	private String produceDate;
	@XmlElement
	private String count;
	@XmlElement
	private String fwIdcode;
	@XmlElement
	private String batch;
	@XmlElement
	private String specMode;
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getFwIdcode() {
		return fwIdcode;
	}
	public void setFwIdcode(String fwIdcode) {
		this.fwIdcode = fwIdcode;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getSpecMode() {
		return specMode;
	}
	public void setSpecMode(String specMode) {
		this.specMode = specMode;
	}
	
}
