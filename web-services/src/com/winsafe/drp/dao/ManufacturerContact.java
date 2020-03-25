package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="manufacturer_contact")
public class ManufacturerContact implements Serializable {
	
	private static final long serialVersionUID = 2806777098491065199L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private Integer id;
	
	@Column(name = "manufacturer_id")
	private Integer manufacturerId;
	
	private String title;
	
	private String tel;

	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="last_modify_time")
	private Date lastModifyTime;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "delete_time")
	private Date deleteTime;
	
	/** 逻辑删除 */
	public void delete() {
		this.setIsDeleted(true);
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

}
