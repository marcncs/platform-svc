package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Affiche implements Serializable {

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String affichetitle;

	/** nullable persistent field */
	private String affichecontent;

	/** nullable persistent field */
	private Integer affichetype;

	/** nullable persistent field */
	private Integer ponderance;

	/** nullable persistent field */
	private String afficheorganid;

	/** nullable persistent field */
	private Integer affichedeptid;

	/** nullable persistent field */
	private String makeorganid;

	/** nullable persistent field */
	private Integer makedeptid;

	/** nullable persistent field */
	private Integer makeid;

	/** nullable persistent field */
	private Date makedate;

	private String isread;

	// 是否发布
	private String isPublish;
	// 系统消息截至日期
	private Date endDate;

	/** full constructor */
	public Affiche(Integer id, String affichetitle, String affichecontent,
			Integer affichetype, Integer ponderance, String afficheorganid,
			Integer affichedeptid, String makeorganid, Integer makedeptid,
			Integer makeid, Date makedate) {
		this.id = id;
		this.affichetitle = affichetitle;
		this.affichecontent = affichecontent;
		this.affichetype = affichetype;
		this.ponderance = ponderance;
		this.afficheorganid = afficheorganid;
		this.affichedeptid = affichedeptid;
		this.makeorganid = makeorganid;
		this.makedeptid = makedeptid;
		this.makeid = makeid;
		this.makedate = makedate;
	}

	/** default constructor */
	public Affiche() {
	}

	/** minimal constructor */
	public Affiche(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAffichetitle() {
		return this.affichetitle;
	}

	public void setAffichetitle(String affichetitle) {
		this.affichetitle = affichetitle;
	}

	public String getAffichecontent() {
		return this.affichecontent;
	}

	public void setAffichecontent(String affichecontent) {
		this.affichecontent = affichecontent;
	}

	public Integer getAffichetype() {
		return this.affichetype;
	}

	public void setAffichetype(Integer affichetype) {
		this.affichetype = affichetype;
	}

	public Integer getPonderance() {
		return this.ponderance;
	}

	public void setPonderance(Integer ponderance) {
		this.ponderance = ponderance;
	}

	public String getAfficheorganid() {
		return this.afficheorganid;
	}

	public void setAfficheorganid(String afficheorganid) {
		this.afficheorganid = afficheorganid;
	}

	public Integer getAffichedeptid() {
		return this.affichedeptid;
	}

	public void setAffichedeptid(Integer affichedeptid) {
		this.affichedeptid = affichedeptid;
	}

	public String getMakeorganid() {
		return this.makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public Integer getMakedeptid() {
		return this.makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public Integer getMakeid() {
		return this.makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public Date getMakedate() {
		return this.makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof Affiche))
			return false;
		Affiche castOther = (Affiche) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

}
