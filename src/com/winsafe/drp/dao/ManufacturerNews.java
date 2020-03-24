package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;


//@Entity
//@Table(name="manufacturer_news")
public class ManufacturerNews implements Serializable {
	
	private static final long serialVersionUID = 7952293596860272531L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(unique = true, nullable = true, length = 32)
	private Integer id;
	
//	@Column(name = "manufacturer_id")
	private Integer manufacturerId;
	
	private String title;
	
	private String summary;
	
	private String content;
	
//	@Column(name="publish_time")
	private Date publishTime;
	
//	@Column(name="pic_url")
	private String picUrl;
	
//	@Column(name="is_show_pic")
	private Integer isShowPic = 0;
	
//	@Column(name="last_modify_time")
	private Date lastModifyTime;
	
//	@Column(name = "is_deleted")
	private Integer isDeleted;

//	@Column(name = "delete_time")
	private Date deleteTime;
	
	private Integer viewCount;
	
	/** 逻辑删除 */
	public void delete() {
		this.setIsDeleted(1);
		this.setDeleteTime(new Date());
		this.setLastModifyTime(new Date());
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Integer getIsShowPic() {
		return isShowPic;
	}

	public void setIsShowPic(Integer isShowPic) {
		this.isShowPic = isShowPic;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

}
