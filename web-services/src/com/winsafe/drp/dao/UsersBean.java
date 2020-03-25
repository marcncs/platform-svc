package com.winsafe.drp.dao;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.util.Date;

import org.apache.struts.action.ActionForm;

public class UsersBean extends ActionForm {
	private Integer userid; 
	private String loginname;
	private String realname;
	private String makeorganid;
	private String makeorganname;
	private String organsysid;
	private String visitorgan;
	private String parentorganid;
	private Integer makedeptid;
	private Integer iscall;
	private Integer status;
	private String mobile;
	private String valkey;
	private String imgurl;
	private Integer userType;
	private Integer organType;
	private Integer organModel;
	private Date vad; 
	private boolean psdExpireWarn;
	private Integer isCwid; 

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public String getOrgansysid() {
		return organsysid;
	}

	public void setOrgansysid(String organsysid) {
		this.organsysid = organsysid;
	}

	public String getLoginname() {
		return loginname;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getRealname() {
		return realname;
	}

	public String getVisitorgan() {
		return visitorgan;
	}

	public void setVisitorgan(String visitorgan) {
		this.visitorgan = visitorgan;
	}

	/**
	 * @return the iscall
	 */
	public Integer getIscall() {
		return iscall;
	}

	/**
	 * @param iscall
	 *            the iscall to set
	 */
	public void setIscall(Integer iscall) {
		this.iscall = iscall;
	}

	public String getParentorganid() {
		return parentorganid;
	}

	public void setParentorganid(String parentorganid) {
		this.parentorganid = parentorganid;
	}

	public String getMakeorganname() {
		return makeorganname;
	}

	public void setMakeorganname(String makeorganname) {
		this.makeorganname = makeorganname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getValkey() {
		return valkey;
	}

	public void setValkey(String valkey) {
		this.valkey = valkey;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getOrganType() {
		return organType;
	}

	public void setOrganType(Integer organType) {
		this.organType = organType;
	}

	public Integer getOrganModel() {
		return organModel;
	}

	public void setOrganModel(Integer organModel) {
		this.organModel = organModel;
	}

	public Date getVad() {
		return vad;
	}

	public void setVad(Date vad) {
		this.vad = vad;
	}

	public boolean isPsdExpireWarn() {
		return psdExpireWarn;
	}

	public void setPsdExpireWarn(boolean psdExpireWarn) {
		this.psdExpireWarn = psdExpireWarn;
	}

	public Integer getIsCwid() {
		return isCwid;
	}

	public void setIsCwid(Integer isCwid) {
		this.isCwid = isCwid;
	}

	
}
