package com.winsafe.sap.pojo;

import java.io.Serializable;
import java.util.Date;
/*******************************************************************************************  
 * 类描述：  
 * 通用码生成日志实体类
 * @author: ryan.xi	  
 * @date：2014-10-15  
 * @version 1.0  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-10-15   ryan.xi 
 * 1.1      2017-10-20   ryan.xi            添加同步状态,同步文件保存路径字段                   
 *******************************************************************************************  
 */  
public class CommonCodeLog implements Serializable{
	
	private static final long serialVersionUID = -4760374299064775069L;
	private Long id;
	//打印任务编号
	private Integer printJobId;
	//文件路径
	private String filePath;
    //生成状态 1.未生成 2.生成中 3.已生成
	private Integer status;
	//生成数量
	private Integer count;
	//创建日期
	private Date makeDate;
	//创建人
	private Integer makeId;
	//物料码
	private String materialCode;
	//错误信息
	private String errorMsg;
	//同步状态0未同步1已上传
	private Integer syncStatus;
	//同步文件保存路径
	private String syncFilePath; 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPrintJobId() {
		return printJobId;
	}
	public void setPrintJobId(Integer printJobId) {
		this.printJobId = printJobId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public Integer getMakeId() {
		return makeId;
	}
	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Integer getSyncStatus() {
		return syncStatus;
	}
	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}
	public String getSyncFilePath() {
		return syncFilePath;
	}
	public void setSyncFilePath(String syncFilePath) {
		this.syncFilePath = syncFilePath;
	}
	
}
