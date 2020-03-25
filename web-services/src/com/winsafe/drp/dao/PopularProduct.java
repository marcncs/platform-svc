package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet; 
import java.util.Set;

public class PopularProduct implements Serializable {


	private static final long serialVersionUID = -4930647906378955570L;


	private String id;

	private String name;
	
	private String nameEng;
	
	private String alias;
	
	//enum
	private Integer auditStatus;
	
	//enum
	public Integer listedStatus;

	private Integer manufacturerId;
	
	private Integer group1Id;

	private Integer group2Id;
	
	private String slogan;
	
	private String component;
	
	private String certification;
	
	private String antiForgeryCode;

	private String picUrl;
	
	private String content;
	
	private Integer rank;
	
	private String labelUrl;
	
	private String posterUrl;
	
	private String sku;

	private String tel;

	private Date createTime;

	private Date lastModifyTime;
	
	private Integer isDeleted;

	private Date deleteTime;
	
//	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.LAZY)
//    @JoinTable(
//            name="related_product",
//            joinColumns=@JoinColumn(name="product_id"),
//            inverseJoinColumns=@JoinColumn(name="related_product_id")
//    )
	
	private Set<PopularProduct> relatedProducts = new HashSet<PopularProduct>();
	
	private Manufacturer manufacturer;
	
	public void addRelatedProduct(PopularProduct product) {
		if (this.relatedProducts == null) {
			this.relatedProducts = new HashSet<PopularProduct>();
		}
		if (product == null) throw new NullPointerException();
		
		this.relatedProducts.add(product);
	}
	
	/** 逻辑删除 */
	public void delete() {
		this.setIsDeleted(1);
		this.setDeleteTime(new Date());
		this.setLastModifyTime(new Date());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEng() {
		return nameEng;
	}

	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getListedStatus() {
		return listedStatus;
	}

	public void setListedStatus(Integer listedStatus) {
		this.listedStatus = listedStatus;
	}

	public Integer getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public Integer getGroup1Id() {
		return group1Id;
	}

	public void setGroup1Id(Integer group1Id) {
		this.group1Id = group1Id;
	}

	public Integer getGroup2Id() {
		return group2Id;
	}

	public void setGroup2Id(Integer group2Id) {
		this.group2Id = group2Id;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getAntiForgeryCode() {
		return antiForgeryCode;
	}

	public void setAntiForgeryCode(String antiForgeryCode) {
		this.antiForgeryCode = antiForgeryCode;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getLabelUrl() {
		return labelUrl;
	}

	public void setLabelUrl(String labelUrl) {
		this.labelUrl = labelUrl;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Set<PopularProduct> getRelatedProducts() {
		return relatedProducts;
	}

	public void setRelatedProducts(Set<PopularProduct> relatedProducts) {
		this.relatedProducts = relatedProducts;
	}

}
