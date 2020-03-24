package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "media_image_group")
public class MediaImageGroup implements Serializable {
	
	private static final long serialVersionUID = -8813055712429033077L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private int id;
	
	@Column(name = "manufacturer_id")
	private int manufacturerId;
	
	private String name;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "last_modify_time")
	private Date lastModifyTime;
	
	@Transient
	private int count;
	
	public MediaImageGroup() {}
	
	public MediaImageGroup(String name) {
		this.name = name;
		this.createTime = new Date();
		this.lastModifyTime = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
