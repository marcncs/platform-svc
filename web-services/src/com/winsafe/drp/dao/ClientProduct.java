package com.winsafe.drp.dao;

import java.util.Date;

public class ClientProduct {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 私有属性
	 * 
	 */
	private Integer id;
	private String clientName;
	private String clientEnglishName;
	private String clientCode;
	private String sellArea;
	private String daoFormat;
	private Integer fwmLength;
	private Integer yuLiang;
	private Date date;
	private String phone;
	private String dataAdress;
	private String functionSelect;

	/**
	 * 无参构造方法
	 * 
	 */
	public ClientProduct() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientEnglishName() {
		return clientEnglishName;
	}

	public void setClientEnglishName(String clientEnglishName) {
		this.clientEnglishName = clientEnglishName;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getSellArea() {
		return sellArea;
	}

	public void setSellArea(String sellArea) {
		this.sellArea = sellArea;
	}

	public String getDaoFormat() {
		return daoFormat;
	}

	public void setDaoFormat(String daoFormat) {
		this.daoFormat = daoFormat;
	}

	public Integer getFwmLength() {
		return fwmLength;
	}

	public void setFwmLength(Integer fwmLength) {
		this.fwmLength = fwmLength;
	}

	public Integer getYuLiang() {
		return yuLiang;
	}

	public void setYuLiang(Integer yuLiang) {
		this.yuLiang = yuLiang;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDataAdress() {
		return dataAdress;
	}

	public void setDataAdress(String dataAdress) {
		this.dataAdress = dataAdress;
	}

	public String getFunctionSelect() {
		return functionSelect;
	}

	public void setFunctionSelect(String functionSelect) {
		this.functionSelect = functionSelect;
	}
}
