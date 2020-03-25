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
@Table(name="product_feedback")
public class ProductFeedback implements Serializable {

	private static final long serialVersionUID = -4988782238399381306L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private Integer id;
	
	@Column(name="product_id")
	private String productId;
	
	private String productName;
	
	@Column(name="star_score")
	private int starScore;
	
	private String content;
	
	/*@OneToOne
	@JoinColumn(name="individual_id")
	private Individual individual;*/
	
	@Column(name="pic_url")
	private String picUrl;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="audit_status")
	private Integer auditStatus;
	
	private String auditStatusName;
	
	@Column(name="is_replied")
	private Integer replied;
	
	private String reply;
	
	@Column(name="manufacturer_account_id")
	private Integer manufacturerAccountId;
	
	@Column(name="reply_time")
	private Date replyTime;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "delete_time")
	private Date deleteTime;
	
	/** 逻辑删除 */
	public void delete() {
		this.setIsDeleted(true);
		this.setDeleteTime(new Date());
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getStarScore() {
		return starScore;
	}

	public void setStarScore(int starScore) {
		this.starScore = starScore;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public Individual getIndividual() {
//		return individual;
//	}
//
//	public void setIndividual(Individual individual) {
//		this.individual = individual;
//	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Integer getReplied() {
		return replied;
	}

	public void setReplied(Integer replied) {
		this.replied = replied;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Integer getManufacturerAccountId() {
		return manufacturerAccountId;
	}

	public void setManufacturerAccountId(Integer manufacturerAccountId) {
		this.manufacturerAccountId = manufacturerAccountId;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
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

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}
}
